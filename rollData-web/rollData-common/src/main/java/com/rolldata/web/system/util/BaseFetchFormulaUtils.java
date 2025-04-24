package com.rolldata.web.system.util;

import com.rolldata.core.util.*;
import com.rolldata.web.system.entity.SysOrg;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.entity.SysUserOrg;
import com.rolldata.web.system.enums.Op;
import com.rolldata.web.system.pojo.DictTree;
import com.rolldata.web.system.pojo.SQLContainer;
import com.rolldata.web.system.service.BaseService;
import com.rolldata.web.system.service.OrgService;
import com.rolldata.web.system.service.SysDictionaryService;
import com.rolldata.web.system.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.rolldata.web.system.util.SpecialSymbolConstants.APOSTROPHE;
import static java.util.stream.Collectors.toList;

/**
 * @Title: BaseFetchFormulaUtils
 * @Description: 基本取数工具类
 * @Company:
 * @author shenshilong[shilong_shen@163.com]
 * @date 2019-06-27
 * @version V1.0
 */
@SuppressWarnings("unused")
public class BaseFetchFormulaUtils {

    private static Logger logger = LogManager.getLogger(BaseFetchFormulaUtils.class);

    private static UserService userService = SpringContextHolder.getBean(UserService.class);

    private static OrgService orgService = SpringContextHolder.getBean(OrgService.class);

    private static SysDictionaryService sysDictionaryService = SpringContextHolder.getBean(SysDictionaryService.class);

    /**
     * 基本公式和数据库交互相关服务层
     */
    private static BaseService baseService = SpringContextHolder.getBean(BaseService.class);

    private static final Pattern PATTERN_TRIM = Pattern.compile("");

    public static final Pattern PATTERN_COMMA = Pattern.compile(",");

    public static final String BASE_ORG_CODE = "$orgCode$";

    public static final String BASE_DATA_TIME = "$datatime$";

    /**
     * 替换下拉等控件的参数形式([地区])
     */
    public static final String COMPONENT_PARAME = "\\[{0}\\]";

    /**
     * 记录,公式里不处理的关键字,待整个公式处理完,再替换回去
     */
    public static ThreadLocal<List<String>> unProcessFormula = new ThreadLocal<>();

    /**
     * 年月日的大小写
     */
    public static Map<Character, Character> DATE_YEAR_MAP = new HashMap<>();

    /**
     * 时分秒的大小写
     */
    public static Map<Character, Character> DATE_YEAR_HOUR_MAP = new HashMap<>();

    /**
     * 存公式数据
     */
    public static final ThreadLocal<Map<String, Object>> FORMULA_DATA = new ThreadLocal<>();



    public static final Pattern COMPILE_PATTERN = Pattern.compile("\\$\\{([^}]+)}");

    static {
        DATE_YEAR_MAP.put('Y', 'y');
        DATE_YEAR_MAP.put('m', 'M');
        DATE_YEAR_MAP.put('D', 'd');
        DATE_YEAR_MAP.put('h', 'H');
        DATE_YEAR_MAP.put('S', 's');

        DATE_YEAR_HOUR_MAP.put('M', 'm');
        DATE_YEAR_HOUR_MAP.put('h', 'H');
        DATE_YEAR_HOUR_MAP.put('S', 's');

    }


    static Stream<Character> toCharacter(String str) {

        final char[] chars = str.toCharArray();
        final ArrayList<Character> list = new ArrayList<>();
        for (char aChar : chars) {
            list.add(aChar);
        }
        return list.stream();
    }

    /**
     * 找出重复字符、记录重复字符次数、记录重复字符位置
     * @param str
     * @return map
     */
    public static Map<String, ArrayList<Integer>> calculationStrCount (String str) {

        String[] strs = PATTERN_TRIM.split(str);

        //key值记录重复的字符串,value记录出现的位置
        Map<String, ArrayList<Integer>> map = new HashMap<>();
        int i = 0;
        for (String s : strs ) {
            if (map.get(s) == null) {
                ArrayList<Integer> list = new ArrayList<>();

                //重复字符串下标
                list.add(i);
                map.put(s, list);
            } else {
                map.get(s).add(i);
            }
            i++;
        }
        return map;
    }


    private static void getUserOrgs(List<String> dataValues, String formulaParam) {

        if (StringUtil.isEmpty(formulaParam)) {
            return;
        }
        List<String> items = splitParamList(formulaParam);
        int size = items.size();
        String column = "";

        // 当前登录人编码
        String userCode = UserUtils.getUser().getUserCde();
        if (size == 0) {

            // 默认获取部门id集合
            column = "department_id";
        }
        if (size == 1) {
            column = items.get(0);
        }
        if (size == 2) {
            userCode = items.get(0);
            column = items.get(1);
            try {
                SysUser userByUserCde = userService.getUserByUserCde(userCode);
                if (null == userByUserCde) {
                    throw new RuntimeException("公式获取数据权限的用户不存在");
                }
                userCode = userByUserCde.getUserCde();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("公式获取数据权限", e);
            }
        }
        if (StringUtil.isEmpty(userCode)) {
            throw new RuntimeException("公式获取数据权限用户参数为空");
        }

        // 执行SQL语句
        String sql = "select DISTINCT t." + column + " as departmentId from wd_sys_user_org t where t.user_code = '" + userCode + "' ";
        try {
            List<SysUserOrg> results = baseService.queryEntitiesBySQL(sql, null, SysUserOrg.class);
            if (null != results) {
                List<String> columnDatas = results.stream().map(SysUserOrg::getDepartmentId).collect(toList());
                dataValues.addAll(columnDatas);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("公式获取数据权限SQL执行失败", e);
        }
    }

    /**
     * 格式化日期
     *
     * @param formulaParam 括号里的参数
     * @return
     */
    private static String getFormatdate(String formulaParam) {

        String dataValue = "";
        if (StringUtil.isNotEmpty(formulaParam)) {

            //存放参数集合
            List<String> items = splitParamList(formulaParam);
            if (items.size() == 1) {
                try {
                    dataValue = DateUtils.parseDateStrToString(items.get(0), DateUtils.PATTERN_YYYY_MM_DD);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            } else if (items.size() == 2) {
                try {
                    String pattern = formatPattern(items.get(1));
                    dataValue = DateUtils.parseDateStrToString(items.get(0), pattern);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } else {
            dataValue = DateUtils.getDate(DateUtils.PATTERN_YYYY_MM_DD);
        }
        return dataValue;
    }

    /**
     * 截取公式
     *
     * @param formulaParam 括号里的参数
     * @return
     */
    private static String getSubstring(String formulaParam) {

        String dataValue = "";
        if (StringUtil.isNotEmpty(formulaParam)) {

            //存放参数集合
            List<String> items = splitParamList(formulaParam);
            if (items.size() == 3) {
                dataValue = items.get(0).substring(Integer.parseInt(items.get(1)), Integer.parseInt(items.get(2)));
            } else if (items.size() == 2) {
                dataValue = items.get(0).substring(Integer.parseInt(items.get(1)));
            } else {
                dataValue = items.get(0);
            }
        }
        return dataValue;
    }

    /**
     * 获取长度
     *
     * @param formulaParam
     * @return
     */
    public static String getLength(String formulaParam) {

        int dataValue = StringUtil.isNotEmpty(formulaParam) ? formulaParam.length() : 0;
        return String.valueOf(dataValue);
    }

    /**
     * 等同于 indexOf 功能
     *
     * @param formulaParam
     * @return
     */
    public static String getInstring(String formulaParam) {

        List<String> items = splitParamList(formulaParam);
        if (items.isEmpty()) {
            return "-1";
        }
        if (items.size() == 1) {
            return "-1";
        }
        if (items.get(0).indexOf(items.get(1)) != -1) {
            return "1";
        } else {
            return "-1";
        }
    }

    /**
     * 比较两个时间
     *
     * @param formulaParam
     * @return
     */
    public static String getCountDate(String formulaParam) {

        List<String> items = splitParamList(formulaParam);
        if (items.isEmpty()) {
            return "-1";
        }
        if (items.size() != 3) {
            return "-1";
        }
        Date date1 = DateUtils.str2Date(items.get(0), items.get(2));
        Date date2 = DateUtils.str2Date(items.get(1), items.get(2));
        Objects.requireNonNull(date1, "参数1,时间格式不对");
        Objects.requireNonNull(date2, "参数2,时间格式不对");
        return String.valueOf(date1.compareTo(date2));
    }

    /**
     * 处理获取组织相关信息
     *
     * @param formulaParam
     * @return
     */
    private static String getOrg(String formulaParam) {

        //返回参数
        String dataValue = "";

        //存放参数集合
        List<String> items = getParamList(formulaParam);
        try {
            Method method = BaseFetchFormulaUtils.class.getDeclaredMethod(
                "getOrg" + StringUtil.firstUpperCase(items.get(0).toLowerCase()),
                String.class
            );
            dataValue = (String) method.invoke(formulaParam, items.get(1));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return dataValue;
    }

    /**
     * 表单切换的组织/部门
     *
     * @param formulaParam
     * @param columnName   表字段
     * @return
     */
    private static String getSelectOrg(String formulaParam, String columnName) {

        Map<String, Object> cacheDataMap = FORMULA_DATA.get();
        EntityManager em = null;
        if (null != cacheDataMap) {
            Object cacheData = cacheDataMap.get(formulaParam + columnName);
            if (null != cacheData) {
                return cacheData.toString();
            }
            Object entityManager = cacheDataMap.get("EntityManager");
            if (null != entityManager) {
                em = (EntityManager) entityManager;
            }
        }

        // 返回参数
        String dataValue = "";

        // 存放参数集合
        List<String> items = getParamList(formulaParam);
        try {
            List<String> sqlParams = new ArrayList<>();
            sqlParams.add(items.get(0));
            String column = "ORG_NAME";
            if ("name".equalsIgnoreCase(items.get(1))) {
                column = "ORG_NAME";
            } else if ("code".equalsIgnoreCase(items.get(1))) {
                column = "ORG_CDE";
            } else {
                column = "ID";
            }
            String sql = "select " + column
                + " from wd_sys_org org where org.ID = ?";
            dataValue = SQLDMLPretreatmentUtil.queryUniqueData(
                em,
                sql,
                sqlParams
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        putCacheData(formulaParam + columnName, dataValue);
        return dataValue;
    }

    /**
     * 处理获取用户相关信息
     *
     * @param formulaParam
     * @return
     */
    private static String getUser(String formulaParam) {

        //返回参数
        String dataValue = "";

        //存放参数集合
        List<String> items = getParamList(formulaParam);
        try {
            Method method = BaseFetchFormulaUtils.class.getDeclaredMethod(
                "getUser" + StringUtil.firstUpperCase(items.get(0).toLowerCase()),
                String.class
            );
            dataValue = (String) method.invoke(formulaParam, items.get(1));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return dataValue;
    }

    /**
     * 处理基础档案公式
     *
     * @param formulaParam 参数
     * @return
     */
    private static String getDict(String formulaParam) {

        //存放参数集合
        List<String> items = getParamList(formulaParam);

        //基础档案公式结果
        List<String> dictList = new ArrayList<>();
        try {
            DictTree dictTree = new DictTree();
            dictTree.setDictCde(items.get(0));
            dictTree.setLevelName(items.get(1));
            Map resultMap = sysDictionaryService.getSelectCascadeComponentDict(dictTree);
            List<Map<String, Object>> mapArrayList = (ArrayList<Map<String, Object>>) resultMap.get("treeNodes");
            for (Map<String, Object> stringObjectMap : mapArrayList) {

                //第三个参数获取第几个属性,如为空则获取字典名称
                if (StringUtil.isNotEmpty(items.get(2))) {
                    dictList.add(unExtFlip(Integer.parseInt(items.get(2)), stringObjectMap));
                } else {
                    dictList.add(String.valueOf(stringObjectMap.get("dictName")));
                }
            }
        } catch (Exception e) {
            logger.error("获取失败:用户名基础档案", e);
        }
        return JsonUtil.format(dictList);
    }

    /**
     * 获取当前用户组织代码
     *
     * @param code
     * @return
     */
    private static String getOrgCode(String code) {

        String dataValue = "";
        try {
            if (StringUtil.isNotEmpty(code)) {
                SysOrg sysOrg = orgService.queryOrgByCode(code);
                if (null == sysOrg) {
                    dataValue = orgService.getSysOrgById(code).getOrgCde();
                } else {
                    dataValue = sysOrg.getOrgCde();
                }
            } else {
                dataValue = UserUtils.getUserDetail().getOrgCde();
            }
        } catch (Exception e) {
            logger.error("获取失败:用户组织代码公式", e);
        }
        return dataValue;
    }

    /**
     * 当前用户代码
     *
     * @param code
     * @return
     */
    private static String getUserCode(String code) {

        String dataValue = "";
        try {
            dataValue = StringUtil.isNotEmpty(code) ? userService.getUserByUserCde(code).getUserCde()
                                      : UserUtils.getUser().getUserCde();
        } catch (Exception e) {
            logger.error("获取失败:用户代码公式", e);
        }
        return dataValue;
    }


    /**
     * 获取基本公式个数
     *
     * @param oldFormula   总公式体
     * @param formula      基本公式体
     * @param formulaRegex 公式正则
     * @param num          基本公式总个数
     * @return
     */
    private static int indexFormulaNum(String oldFormula, String formula, String formulaRegex, int num) {

        while (oldFormula.lastIndexOf(formula) != -1) {
            num++;
            oldFormula = oldFormula.replaceFirst(formulaRegex, "");
        }
        return num;
    }

    /**
     * 获取用户名称公式结果
     *
     * @param code 参数
     * @return
     */
    private static String getUserName(String code) {

        String dataValue = "";
        try {
            dataValue = StringUtil.isNotEmpty(code) ? userService.getUserByUserCde(code).getUserName()
                                      : UserUtils.getUser().getUserName();
        } catch (Exception e) {
            logger.error("获取失败:用户名称公式", e);
        }
        return dataValue;
    }

    /**
     * 获取用户手机号
     *
     * @param code userCode
     * @return
     */
    private static String getUserPhone(String code) {

        String dataValue = "";
        try {
            dataValue = StringUtil.isNotEmpty(code) ? userService.getUserByUserCde(code).getMobilePhone()
                                      : UserUtils.getUser().getMobilePhone();
        } catch (Exception e) {
            logger.error("获取失败:用户手机号", e);
        }
        return dataValue;
    }

    /**
     * 获取用户邮箱
     *
     * @param code userCode
     * @return
     */
    private static String getUserMail(String code) {

        String dataValue = "";
        try {
            dataValue = StringUtil.isNotEmpty(code) ?
                                      userService.getUserByUserCde(code).getMail()
                                      : UserUtils.getUser().getMail();
        } catch (Exception e) {
            logger.error("获取失败:用户邮箱", e);
        }
        return dataValue;
    }

    /**
     * 获取用户部门
     *
     * @param code userCode
     * @return
     */
    private static String getUserDepartment(String code) {

        String dataValue = "";
        try {
            dataValue = StringUtil.isNotEmpty(code) ?
                                      userService.getUserByUserCde(code).getDepartment()
                                      : UserUtils.getUser().getDepartment();
        } catch (Exception e) {
            logger.error("获取失败:用户部门", e);
        }
        return dataValue;
    }

    /**
     * 获取用户职务
     *
     * @param code userCode
     * @return
     */
    private static String getUserPosition(String code) {

        String dataValue = "";
        try {
            dataValue = StringUtil.isNotEmpty(code) ?
                                      userService.getUserByUserCde(code).getPosition()
                                      : UserUtils.getUser().getPosition();
        } catch (Exception e) {
            logger.error("获取失败:用户职务", e);
        }
        return dataValue;
    }

    /**
     * 获取组织名称
     *
     * @param code 参数
     * @return
     */
    private static String getOrgName(String code) {

        String dataValue = "";
        try {
            if (StringUtil.isNotEmpty(code)) {
                SysOrg sysOrg = orgService.queryOrgByCode(code);
                if (null == sysOrg) {
                    sysOrg = orgService.getSysOrgById(code);
                    if (null == sysOrg) {
                        logger.info("该组织不存在code【" + code + "】");
                        return dataValue;
                    }
                    dataValue = sysOrg.getOrgName();
                } else {
                    dataValue = sysOrg.getOrgName();
                }
            } else {
                dataValue = UserUtils.getUserDetail().getOrgName();
            }
        } catch (Exception e) {
            logger.error("获取失败:组织名称", e);
        }
        return dataValue;
    }

    /**
     * 获取用户的组织id
     *
     * @param code
     * @return
     */
    private static String getUserOrgid(String code) {

        String dataValue = "";
        try {
            dataValue = StringUtil.isNotEmpty(code) ?
                                      userService.getUserByUserCde(code).getOrgId()
                                      : UserUtils.getUser().getOrgId();
        } catch (Exception e) {
            logger.error("获取失败:用户的组织id", e);
        }
        return dataValue;
    }

    /**
     * 格式化当前时间
     *
     * @param pattern 日期格式
     * @return
     */
    private static String getNow(String pattern) {

        pattern = pattern.trim().replace("\"", "");
        if (StringUtil.isEmpty(pattern)) {
            pattern = DateUtils.PATTERN_YYYY_MM_DD;
        } else {

            // 处理大MM小mm
            pattern = formatPattern(pattern);
        }
        return DateUtils.formatDate(pattern);
    }

    /**
     * 处理格式化日期大小写问题
     * <h1>默认不成文规则：</h1>小时的mm不会单独出现,至少也要HH:mm/HHmm
     * <h2>处理方法：</h2>
     * <ol>
     *      <li>所有字符全部转大写</li>
     *      <li>长度是1或2直接当成月份返回</li>
     *      <li>y小写d小写s小写</li>
     *      <li>出现H/h后的m都小写</li>
     * </ol>
     * <h3>常用格式：</h3>
     * <ol>
     *  <li>yyyy-MM-dd</li>
     *  <li>yyyyMMdd</li>
     *  <li>yyMM</li>
     *  <li>yyyyMM</li>
     *  <li>yyMMdd</li>
     *  <li>yy</li>
     *  <li>yyyy</li>
     *  <li>yyyy年MM月dd日</li>
     *  <li>yyyy-MM-dd HH:mm</li>
     *  <li>HH:mm</li>
     *  <li>yyyy-MM-dd HH:mm:ss</li>
     *  <li>yyyy-MM</li>
     *  <li>yyyyMMddHHmmss</li>
     *  </ol>
     *
     * @param pattern 格式的字符串
     * @return
     */
    public static String formatPattern(String pattern) {

        if (StringUtil.isEmpty(pattern)) {
            return pattern;
        }
        String upper = pattern.toUpperCase();
        int upperLength = upper.length();
        if ((upperLength == 1 && "m".equalsIgnoreCase(upper)) ||
            (upperLength == 2 && "mm".equalsIgnoreCase(upper))) {

            // M或MM直接当成月
            return upper;
        }
        char[] dateChars = upper.toCharArray();
        boolean isHour = false;
        Character character = null;
        for (int i = 0; i < dateChars.length; i++) {
            char x = dateChars[i];
            if (!isHour && (x == 'H' || x == 'h')) {
                isHour = true;
            }
            character = isHour ? DATE_YEAR_HOUR_MAP.get(x) : DATE_YEAR_MAP.get(x);
            if (null == character) {
                continue;
            }
            dateChars[i] = character.charValue();
        }
        return String.valueOf(dateChars);
    }

    /**
     * 获取字典属性
     *
     * @param i               第几个属性
     * @param stringObjectMap dict的map结果
     * @return
     */
    private static String unExtFlip(Integer i, Map<String, Object> stringObjectMap) {

        Object extValue;
        switch (i) {
            case 1:
                extValue = stringObjectMap.get("ext1");
                break;
            case 2:
                extValue = stringObjectMap.get("ext2");
                break;
            case 3:
                extValue = stringObjectMap.get("ext3");
                break;
            case 4:
                extValue = stringObjectMap.get("ext4");
                break;
            default:
                extValue = stringObjectMap.get("dictName");
                break;
        }
        return null == extValue ? "" : String.valueOf(extValue);
    }

    /**
     * 截取参数返回参数集合
     *
     * @param formulaParam
     * @return
     */
    private static List<String> getParamList(String formulaParam) {

        int initCount = 3;

        // 存放参数集合
        List<String> items = new ArrayList<>(initCount);
        params(formulaParam, items);

        for (int i = items.size(); i < initCount; i++) {
            items.add("");
        }
        return items;
    }

    private static void params(String formulaParam, List<String> items) {

        // 参数用双引号包围时
        if (formulaParam.indexOf('"') != -1) {
            Map<String, ArrayList<Integer>> resolutionMap = calculationStrCount(formulaParam);
            ArrayList<Integer> doubleDuotesList = resolutionMap.get("\"");
            for (int p = 0; p < doubleDuotesList.size(); p++) {
                items.add(formulaParam.substring(doubleDuotesList.get(p) + 1, doubleDuotesList.get(++p)));
            }
        } else if (formulaParam.indexOf('\'') != -1) {
            Map<String, ArrayList<Integer>> resolutionMap = calculationStrCount(formulaParam);
            ArrayList<Integer> doubleDuotesList = resolutionMap.get("'");
            for (int p = 0; p < doubleDuotesList.size(); p++) {
                items.add(formulaParam.substring(doubleDuotesList.get(p) + 1, doubleDuotesList.get(++p)));
            }
        } else {
            Arrays.asList(PATTERN_COMMA.split(formulaParam)).stream().forEach(item -> {
                items.add(item.trim());
            });
        }
    }

    /**
     * 参数个数,根据逗号截取
     *
     * @param formulaParam
     * @return
     */
    private static List<String> splitParamList(String formulaParam) {

        //存放参数集合
        List<String> items = new ArrayList<>();

        //参数用双引号包围时
        params(formulaParam, items);
        return items;
    }

    /**
     * 数据权限公式
     * <br>返回结果 'orgid1','orgid2' 由于公式统一返回的str,集合拼上逗号单引号返回
     *
     * @param formulaParam 公式的参数
     * @return
     */
    static private String getDataRights(String formulaParam) {

        //返回参数
        StringBuffer dataValueBuf = new StringBuffer();
        String temp = "";
        List<String> orgIdPermissionList = null;

        //存放参数集合
        List<String> items = getParamList(formulaParam);
        if (StringUtil.isEmpty(items.get(0))) {
            orgIdPermissionList = UserUtils.getOrgIdPermissionList();
        } else {
            try {
                orgIdPermissionList = orgService.queryDataRights(items.get(0));
            } catch (Exception e) {
                logger.error("取数公式GETDATARIGHTS异常");
            }
        }
        if (null == orgIdPermissionList) {
            return dataValueBuf.toString();
        }
        orgIdPermissionList.forEach((orgId) -> {
            dataValueBuf.append(APOSTROPHE).append(orgId)
                        .append(APOSTROPHE).append(SpecialSymbolConstants.COMMA);
        });
        temp = dataValueBuf.toString();
        return temp.substring(0, temp.length() - 1);
    }

    /**
     * 日期推算公式
     * SUBDIFF(year, -3)<br>
     * 第一个参数：year/month/day;<br>
     * 第二个参数前后推算数值,负数往前推,正数往后推;<br>
     * 例1：SUBDIFF(year, -3)<br>
     * 按照年,往前推3年。如果控件选择[2019-12-26]<br>
     * 公式计算结果[2016-12-26],得出[2016-12-26]到[2019-12-26]数据;<br>
     * 例2：SUBDIFF(month, -6)<br>
     * 按照月,往前推6个月。如果控件选择[2019-12-26]<br>
     * 公式计算结果[2019-6-26],得出[2019-6-26]到[2019-12-26]数据;<br>
     * 日期格式跟着控件选择的走,年/年月/年月日等。<br>
     * 例3：SUBDIFF(year, -2)<br>
     * 按照年,往前推2年。如果控件选择[2019-12]<br>
     * 公式计算结果[2017-12],得出[2017-12]到[2019-12]数据;<br>
     *
     * @param formulaParam
     * @return
     */
    static public String getSubDiff(String formulaParam) {

        //存放参数集合
        List<String> items = getParamList(formulaParam);
        String dateStr = null;
        String year = null;
        String num = null;
        String pattern = formatPattern(items.get(1));
        try {
            dateStr = items.get(0);
            year = items.get(2);
            num = items.get(3);

            if (StringUtil.isEmpty(dateStr)) {
                dateStr = DateUtils.date2Str(new Date(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS);
            } else {

                // 有单引号或双引号的,去掉
                if (dateStr.indexOf('"') != -1) {
                    dateStr = dateStr.replace("\"", "");
                }
                if (dateStr.indexOf('\'') != -1) {
                    dateStr = dateStr.replace("'", "");
                }
            }
            if ("year".equalsIgnoreCase(year)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(DateUtils.parseStringToDate(dateStr));

                // 加/减年
                cal.add(Calendar.YEAR, Integer.parseInt(num));
                return DateUtils.date2Str(cal.getTime(), pattern);
            } else if ("month".equals(year)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(DateUtils.parseStringToDate(dateStr));

                //cd.add(Calendar.DATE, n);//增加一天  
                //增加/减 月   
                cal.add(Calendar.MONTH, Integer.parseInt(num));
                return DateUtils.date2Str(cal.getTime(), pattern);
            } else if ("day".equals(year)) {
            }
        } catch (Exception e) {
            logger.error("日期推算失败:" + e.getMessage());
            throw new RuntimeException("日期推算失败:" + e.getMessage(), e);
        }
        Date date = DateUtils.str2Date(dateStr, pattern);
        Date taskDetailedEndTime = BaseFetchFormulaUtils.getTaskDetailedEndTime(date, num);
        return DateUtils.date2Str(taskDetailedEndTime, pattern);
    }

    /**
     * TODO: 后台删除,把DS包里的工具类提上来,现在提牵连的有点多
     * 获取每期的结束时间
     * @param startTime 开始时间
     * @param continuedNum 持续天数
     * @return
     */
    public static Date getTaskDetailedEndTime (Date startTime, String continuedNum) {

        Date date = null;
        date = BaseFetchFormulaUtils.getFillTaskAfterDate(startTime, Long.parseLong(continuedNum));
        return BaseFetchFormulaUtils.getEndOfDay(date);
    }

    /**
     * 推算几天后的日期(输入负数则往前计算)
     *
     * @param data 开始时间
     * @param num  推算的天数
     * @return
     */
    public static Date getFillTaskAfterDate(Date data, long num) {

        //2019-4-26 00:00:00 到 2019-4-26 23:59:59 算一天[获取结束天的方法放在了这里getTaskDetailedEndTime]
        num = num > 0 ? (num - 1) : num;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(data);
            cal.add(Calendar.DATE, (int) num);
            data = cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取日期的最大时间 2019-4-26 23:59:59
     * @param date 时间
     * @return
     */
    public static Date getEndOfDay (Date date) {

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 解析普通if公式.此方法在表单if公司里抽取出来(如果维护,则表单里的if也相应更新AnalysisFormulaUtils.getItemIfValue)
     *
     * @param formula if公式里的参数项
     * @return
     */
    public static String getIfValue(String formula) {

        String dataValue = "";
        String[] terms = getIfMatcher(formula);

        //判断里包含双引号,应该去掉
        if (terms[0].indexOf('"') != -1) {
            terms[0] = terms[0].replace("\"", "");
        }
        dataValue = MathExpress.compareTo(terms[0]) ? terms[1] : terms[2];

        //包含双引号的去除前后双引号, if公式的结果如果包含双引号,必然在前后2端,如果逻辑有问题后期优化
        if (dataValue.indexOf('"') != -1) {
            dataValue = dataValue.substring(1, dataValue.length() - 1);
        } else {

            //不包含双引号, 并且包含[+_*/]则是要计算的等式
            if (MathExpress.judge(dataValue)) {
                dataValue = getCalculation(dataValue);
            }
        }
        System.out.println(dataValue);
        return dataValue;
    }

    public static String getCalculation(String tempFormula) {

        MathExpress me = new MathExpress(tempFormula);
        String resultStr = me.caculate();
        return resultStr;
    }

    /**
     * 截取if括号里的参数<br>
     *     String[0] ··· 条件<br>
     *     String[1] ··· true结果<br>
     *     String[2] ··· false结果<br>
     *
     * @param source 参数项
     * @return
     */
    public static String[] getIfMatcher(String source) {

        String[] terms = new String[3];

        //如果有2个逗号,截取逗号,分成三组
        int count = source.length() - source.replace(",", "").length();
        if (2 == count) {
            terms = source.split(",");
        } else {

            //逗号>2时,IF(公式, "字,符","B")  截取双引号, 分成三组
            Map<String, ArrayList<Integer>> calcMap = BaseFetchFormulaUtils.calculationStrCount(source);

            /*
             * 判断包含字符串的几种形式:
             *   一:当有一组字符串的时候,判断位置
             *       IF("字符串" > 3, 指标, 指标)0
             *       IF(公式 > 3, "字符串", 指标)
             *       IF(公式 > 3, 指标, "字符串")
             *   二:当有二组字符串, 判断位置
             *       IF("字符串" == 指标, "字符串", 指标)
             *       IF("字符串" == 指标, 指标, "字符串")
             *       IF(公式, "字符串", "字符串")
             *   三:三组都包含字符串
             *       IF("字符串" == 指标, "字符串", "字符串")
             *
             * */
            String calculationSource = source;
            List<String> items = new ArrayList<>(3);
            List<Integer> doubleQuotesList = calcMap.get("\"");
            List<Integer> commaList = calcMap.get(",");
            if (doubleQuotesList.size() == 2) {
                if (commaList.get(commaList.size() - 1) > doubleQuotesList.get(doubleQuotesList.size() - 1) ) {
                    items.add(calculationSource.substring(0, commaList.get(commaList.size() - 2)));
                    items.add(calculationSource.substring(commaList.get(commaList.size() - 2) + 1, commaList.get(commaList.size() - 1)));
                    items.add(calculationSource.substring(commaList.get(commaList.size() - 1) + 1));
                } else if (commaList.get(commaList.size() - 1) < doubleQuotesList.get(doubleQuotesList.size() - 1) ) {
                    items.add(calculationSource.substring(0, commaList.get(0)));
                    items.add(calculationSource.substring(commaList.get(0) + 1, commaList.get(1)));
                    items.add(calculationSource.substring(commaList.get(1) + 1));
                } else {

                }
            } else if (doubleQuotesList.size() == 4) {
                if (commaList.get(commaList.size() - 1) > doubleQuotesList.get(doubleQuotesList.size() - 1)) {
                    items.add(calculationSource.substring(0, doubleQuotesList.get(2)));
                    items.add(calculationSource.substring(doubleQuotesList.get(2) + 1, commaList.get(commaList.size() - 1)));
                    items.add(calculationSource.substring(commaList.get(commaList.size() - 1) + 1));
                } else if (doubleQuotesList.get(0) > commaList.get(0)) {
                    String itemCenter = calculationSource.substring(commaList.get(0) + 1, doubleQuotesList.get(2)).trim();
                    items.add(calculationSource.substring(0, commaList.get(0)));
                    items.add(itemCenter.substring(0, itemCenter.length() - 1));
                    items.add(calculationSource.substring(doubleQuotesList.get(2)));
                } else {

                }
            } else if (doubleQuotesList.size() == 6) {
                String itemFirst = calculationSource.substring(0, doubleQuotesList.get(2));
                String itemCenter = calculationSource.substring(doubleQuotesList.get(2), doubleQuotesList.get(4));
                items.add(itemFirst.trim().substring(0, itemFirst.length()));
                items.add(itemCenter.trim().substring(0, itemFirst.length()));
                items.add(calculationSource.substring(doubleQuotesList.get(4)));
            }
            items.toArray(terms);
        }

        // 等号转为双等比较
        if (terms[0].indexOf("==") == -1 &&
            terms[0].indexOf(">=") == -1 &&
            terms[0].indexOf("<=") == -1 &&
            terms[0].indexOf("!=") == -1 &&
            terms[0].indexOf("=") != -1) {
            terms[0] = terms[0].replace("=", "==");
        }
        return terms;
    }

    /**
     * 获取SQL结果(和表单SQL不同,没有替换$datatime$等)
     *
     * @param formulaParam SQL里的参数项
     * @return
     */
    private static String getSQLValue(String formulaParam) {

        String dataValue = "";
        try {
            dataValue = baseService.getSQLValue(formulaParam);
        } catch (Exception e) {
            logger.info("获取SQL结果出现异常" + e.getMessage());
            dataValue = "";
        }
        return dataValue;
    }

    /**
     * 切割报表条件属性的公式
     *
     * @param formulaParam 公式
     * @return 0=Lexer,1=tokens,2=parser
     */
    public static String[] splitReportCondition(String formulaParam) {

        String[] result = new String[3];
        for (Op value : Op.values()) {
            if (formulaParam.indexOf(value.toString()) > 0) {
                String[] split = formulaParam.split(value.toString());
                result[0] = split[0];
                result[1] = value.toString();
                result[2] = split[1];
            }
        }
        return result;
    }

    /**
     * 替换公式中组件过滤的参数
     *
     * @param formula     公式
     * @param parameName  参数名
     * @param parameValue 参数值
     * @return
     */
    public static String replaceParame(String formula, String parameName, String parameValue) {

        formula = formula.replaceAll(
            MessageFormat.format(BaseFetchFormulaUtils.COMPONENT_PARAME, parameName),
            parameValue
        );
        return formula;
    }

    /**
     * 执行SQL语句,返回MAP形式的数据(ORACLE 给 AS 后的字段加上双引号即可识别大小写)
     *
     * @param sql     SQL脚本
     * @param pValues 预编译数据
     * @return
     * @throws Exception
     */
    public static List queryValueMapBySQL(String sql, List<String> pValues) throws Exception {

        return baseService.queryValueObjectsBySQL((query) ->{
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List resultList = query.getResultList();
            return null == resultList ? Collections.emptyList() : resultList;
        }, sql, pValues);
    }

    /**
     * 执行SQL语句,返回字符串集合的数据
     *
     * @param sql     SQL脚本
     * @param pValues 预编译数据
     * @return
     * @throws Exception
     */
    public static List<String> queryStringListBySQL(String sql, List<String> pValues) throws Exception {

        return baseService.queryValueObjectsBySQL((query) ->{
            List resultList = query.getResultList();
            return null == resultList ? Collections.emptyList() : resultList;
        }, sql, pValues);
    }

    /**
     * 执行SQL语句,返回字符串集合的数据
     *
     * @param sql     SQL脚本
     * @param pValues 预编译数据
     * @return
     * @throws Exception
     */
    public static List<String> queryStringListBySQL(EntityManager entityManager, String sql, List<String> pValues) throws Exception {

        return baseService.queryValueObjectsBySQL(
            entityManager,
            (query) ->{
                List<String> resultList = query.getResultList();
                return null == resultList ? Collections.emptyList() : resultList;
            },
            sql,
            pValues
        );
    }

    /**
     * 根据SQL,查询集合,SQl语句的as别名和对象属性名字对应
     *
     * @param sql     SQL语句
     * @param pValues 预编译参数
     * @param clazz   对象
     * @return 返回clazz对象集合
     * @throws Exception
     */
    public static <T> List<T> queryEntitiesBySQL(String sql, List<String> pValues, Class<T> clazz) throws Exception {
        return baseService.queryEntitiesBySQL(sql, pValues, clazz);
    }

    /**
     * 根据SQL,查询集合,SQl语句的as别名和对象属性名字对应
     *
     * @param em      查询实体管理器
     * @param sql     SQL语句
     * @param pValues 预编译参数
     * @param clazz   对象
     * @return
     * @throws Exception
     */
    public static <T> List<T> queryEntitiesBySQL(EntityManager em, String sql, List<String> pValues, Class<T> clazz) throws Exception {
        return baseService.queryEntitiesBySQL(em, sql, pValues, clazz);
    }

    /**
     * 根据SQL,查询唯一数据
     *
     * @param sql               SQL语句
     * @param pValues 预编译参数
     * @param clazz             对象
     * @return 返回clazz对象
     * @throws Exception
     */
    public static <T> T queryEntityBySQL(String sql, List<String> pValues, Class<T> clazz) throws Exception {
        return baseService.queryEntityBySQL(sql, pValues, clazz);
    }

    /**
     * 根据SQL,查询唯一数据
     *
     * @param em      查询实体管理器
     * @param sql     SQL语句
     * @param pValues 预编译参数
     * @param clazz   对象
     * @return
     * @throws Exception
     */
    public static <T> T queryEntityBySQL(EntityManager em, String sql, List<String> pValues, Class<T> clazz) throws Exception {
        return baseService.queryEntityBySQL(em, sql, pValues, clazz);
    }

    /**
     * 执行SQL语句
     *
     * @param sql               SQL语句
     * @param pValues 预编译参数
     * @throws Exception
     */
    public static void executeSQL(String sql, List<String> pValues) throws Exception {
        baseService.executeSQL(sql, pValues);
    }

    /**
     * 执行SQL语句
     *
     * @param entityManager 事务管理器
     * @param sql           SQL语句
     * @param pValues       预编译参数
     * @throws Exception
     */
    public static void executeSQL(EntityManager entityManager, String sql, List<String> pValues) throws Exception {
        baseService.executeSQL(entityManager, sql, pValues);
    }

    /**
     * 执行多条SQL语句,不用每次重新创建em
     *
     * @param sqlContainers SQL信息容器
     * @throws Exception
     */
    public static void executeSQLList(List<SQLContainer> sqlContainers) throws Exception {
        baseService.executeSQLList(sqlContainers);
    }

    /**
     * 根据任务详细获取数据时间
     *
     * @param formulaParam
     * @return
     */
    private static String getDataTime(String formulaParam) {

        // 返回参数
        String dataValue = "";
        String replace = formulaParam.trim().replace("\"", "");

        // 存放参数集合
        List<String> items = splitParamList(replace);

        // 如果参数少于2个,则直接返回;表单取数那有一个 DATATIME 公式解析
        if (items.size() != 2) {
            return formulaParam;
        }
        try {
            dataValue = items.get(0);
            if (StringUtil.isEmpty(dataValue)) {
                dataValue = DateUtils.formatDate();
            }
            Date date = DateUtils.str2Date(dataValue, DateUtils.PATTERN_YYYY_MM_DD);
            dataValue = DateUtils.date2Str(date, items.get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataValue;
    }


    public static String replaceCompileParame(String sql, String parameName, String value) {

        value = null == value ? "" : value;
        return sql.replaceFirst("\\$\\{" + parameName + "}", value);
    }

    public static void putCacheData(String key, Object data) {

        Map<String, Object> cacheDataMap = FORMULA_DATA.get();
        if (null == cacheDataMap) {
            return;
        }
        cacheDataMap.put(key, null == data ? "" : data);
    }


    public static class SimpleData {

        public String value;

        /**
         * 获取
         *
         * @return value
         */
        public String getValue() {
            return this.value;
        }

        /**
         * 设置
         *
         * @param value
         */
        public void setValue(String value) {
            this.value = value;
        }
    }
}
