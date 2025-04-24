package com.rolldata.web.system.service.impl;

import com.rolldata.core.common.exception.WdFileNotFoundException;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.common.pojo.UploadFile;
import com.rolldata.core.util.*;
import com.rolldata.web.common.enums.TreeNodeType;
import com.rolldata.web.system.dao.*;
import com.rolldata.web.system.entity.*;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.service.*;
import com.rolldata.web.system.util.ExcelUtil;
import com.rolldata.web.system.util.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenshilong
 * @version V1.0
 * @Title: UserServiceImpl
 * @Description:
 * @Company:www.wrenchdata.com
 * @date 2018-05-24
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private LastOnlineDao lastOnlineDao;

    @Autowired
    private UserOnlineDao userOnlineDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private SysOrgRoleDao sysOrgRoleDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PostDao postDao;

    @Autowired
    private SysPostUserDao sysPostUserDao;

    @Autowired
    private PostService postService;

    @Autowired
    private SystemService systemService;
    
    @Autowired
    private OrgService orgService;

    @Autowired
    private SysUserOrgService sysUserOrgService;

    private Map<String, Integer> webChatUserMap = new HashMap<String, Integer>();

    private EntityManagerFactory emf;

    // 使用这个标记来注入EntityManagerFactory
    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * 新建用户
     *
     * @param user
     * @throws Exception
     */
    @Override
    public SysUser save(SysUser user) throws Exception {
        Date nowDate = new Date();
        user.setCreateTime(nowDate);
        if(StringUtil.isEmpty(user.getCreateUser())) {
        	user.setCreateUser(UserUtils.getUser().getId());
        }
        user.setUpdateTime(nowDate);
        user.setUpdatePasswordTime(nowDate);
        user.setIsactive(SysUser.STATUS_NORMAL);
        if (user.getGender().equals("1")) {
            user.setHeadPhoto(SysUser.HEADPHOTO_MAN);
        } else {
            user.setHeadPhoto(SysUser.HEADPHOTO_WOMAN);
        }
        user.setIsInit(SysUser.ISINIT);
        user.setIslocked(SysUser.NO_LOCKED);
        user.setType(TreeNodeType.USER.toString());
        user.setId(UUIDGenerator.generate());
        passwordService.encryptPassword(user);

        // 保存wd_sys_user_org
        this.sysUserOrgService.createUserInfo(user);
        return userDao.save(user);
    }

    /**
     * 新建用户目录（文件夹）
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void saveFolder(SysUser user) throws Exception {
        user.setCreateTime(new Date());
        user.setCreateUser(UserUtils.getUser().getId());
        user.setType(TreeNodeType.FOLDER.toString());
        user.setId(UUIDGenerator.generate());
        userDao.save(user);
    }

    /**
     * 验证用户是否已经存在
     *
     * @param user
     * @return
     * @throws Exception
     */
    private Boolean before(SysUser user) throws Exception {

        Boolean valid = Boolean.FALSE;
        SysUser existUser = this.getUserByUserCde(user.getUserCde());
        if (null != existUser) {
            valid = true;
        }
        return valid;
    }

    /**
     * 当前节点下文件夹是否重名
     *
     * @param user
     * @throws Exception
     * @createDate 2018-6-19
     */
    @Override
    public Boolean folderIsReName(SysUser user) throws Exception {
        SysUser user0 = userDao.getUserByParentIdAndUserName(user.getParentId(), user.getUserName());
        if (user0 != null && TreeNodeType.FOLDER.toString().equals(user0.getType())) {
            return true;
        }
        return false;
    }

    /**
     * 验证用户唯一
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public Boolean doValid(SysUser user) throws Exception {
        return before(user);
    }

    /**
     * 判断是否有子节点
     *
     * @param id
     * @return
     * @throws Exception
     * @author shenshilong
     * @createDate 2018-8-1
     */
    @Override
    public Boolean isHaveChildren(String id) throws Exception {
        List<SysUser> list = userDao.isHaveChildren(id);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 批量删除
     * <p>注意!此方法修改,这里也要相应修改{@link OrgServiceImpl#deleteUserIds}
     * org里再注入user则循环依赖</p>
     *
     * @param userIds 用户id集合
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void delete(List<String> userIds) throws Exception {

        if (null == userIds ||
            userIds.isEmpty()) {
            return;
        }
        SysUser adminUser = this.userDao.getUserByUserCde(AdministratorUtils.getAdministratorName());
        if (null == adminUser) {
            throw new Exception("超级管理员不存在");
        }

        // 移除admin
        userIds.removeIf(u -> u.equals(adminUser.getId()));
        if (userIds.isEmpty()) {
            return;
        }

        // 未来可以限制部门下用户最多1000
        this.userDao.deleteUserByIds(userIds);
        this.userRoleDao.deleteUserRoleByUserIds(userIds);
        this.sysPostUserDao.deleteByUserIds(userIds);

        // 操作wd_sys_user_org
        this.sysUserOrgService.deleteEntitysByUserIds(userIds);

    }

    /**
     * 根据用户名查用户
     *
     * @param userCde
     * @return
     * @throws Exception
     */
    @Override
    public SysUser getUserByUserCde(String userCde) throws Exception {

        return userDao.getUserByUserCde(userCde);
    }

    /**
     * 根据id查用户
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public SysUser getUserById(String id) throws Exception {
        Objects.requireNonNull(id, "用户信息为空");
        return userDao.findById(id).orElse(null);
    }

    /**
     * 修改用户parentId
     *
     * @throws Exception
     */
    @Override
    public void updateParentId(SysUser sysUser) throws Exception {
        userDao.updateParentId(sysUser.getParentId(), sysUser.getId());
    }

    /**
     * 用户分页信息
     *
     * @param pageJson
     * @return
     * @throws Exception
     */
    @Override
    public void getUserList(PageJson pageJson) throws Exception {
        Pageable pageable = PageRequest.of(pageJson.getPageable(), pageJson.getSize());
        Page<SysUser> page = userDao.findAll(pageable);
        pageJson.setResult(page.getContent());
        pageJson.setTotalPagets(page.getTotalPages());
    }

    /**
     * 修改用户基本信息
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updateUserInfo(SysUser user) throws Exception {
        Date nowDate = new Date();
        userDao.updateUserInfo(user.getUserName(), user.getOrgId(), user.getCompany(), user.getDepartment(),
            user.getPosition(), user.getMobilePhone(), user.getAreaCode(), user.getTelephone(), user.getGender(),
            user.getEmployType(), user.getMail(), user.getIsactive(), UserUtils.getUser().getId(), nowDate,
            user.getThirdPartyCode(), user.getId());
        SysUser newUser = (SysUser) CacheUtils.get(UserUtils.USER_CACHE,
            UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde());
        if (StringUtil.isNotEmpty(newUser) && StringUtil.isNotEmpty(newUser.getId())) {
            newUser.setUserName(user.getUserName());
            newUser.setOrgId(user.getOrgId());
            newUser.setCompany(user.getCompany());
            newUser.setDepartment(user.getDepartment());
            newUser.setPosition(user.getPosition());
            newUser.setMobilePhone(user.getMobilePhone());
            newUser.setAreaCode(user.getAreaCode());
            newUser.setTelephone(user.getTelephone());
            newUser.setGender(user.getGender());
            newUser.setEmployType(user.getEmployType());
            newUser.setMail(user.getMail());
            newUser.setIsactive(user.getIsactive());
            newUser.setUpdateTime(nowDate);
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde(), newUser);
        }
    }

    /**
     * 修改文件夹基本信息
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updateFolderInfo(SysUser user) throws Exception {
        userDao.updateFolderInfo(user.getUserName(), UserUtils.getUser().getId(), new Date(), user.getId());
    }

    /**
     * 修改密码
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updatePassword(SysUser user) throws Exception {
        Date nowDate = new Date();
        passwordService.encryptPassword(user);
        userDao.updatePassword(user.getPassword(), user.getSalt(), user.getIsactive(), UserUtils.getUser().getId(),
            nowDate, nowDate, user.getId());
        SysUser newUser = (SysUser) CacheUtils.get(UserUtils.USER_CACHE,
            UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde());
        if (StringUtil.isNotEmpty(newUser) && StringUtil.isNotEmpty(newUser.getId())) {
            newUser.setIsactive(user.getIsactive());
            newUser.setUpdatePasswordTime(nowDate);
            newUser.setUpdateTime(nowDate);
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde(), newUser);
        }
    }

    /**
     * 初始用户修改密码
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updateInitPassword(SysUser user) throws Exception {
        Date nowDate = new Date();
        passwordService.encryptPassword(user);
        userDao.updateInitPassword(user.getPassword(), user.getSalt(), user.getIsInit(), UserUtils.getUser().getId(),
            nowDate, nowDate, user.getId());
        SysUser newUser = (SysUser) CacheUtils.get(UserUtils.USER_CACHE,
            UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde());
        if (StringUtil.isNotEmpty(newUser) && StringUtil.isNotEmpty(newUser.getId())) {
            newUser.setPassword(user.getPassword());
            newUser.setSalt(user.getSalt());
            newUser.setIsInit(user.getIsInit());
            newUser.setUpdatePasswordTime(nowDate);
            newUser.setUpdateTime(nowDate);
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde(), newUser);
        }
    }

    /**
     * 删除用户
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void delete(SysUser user) throws Exception {
        userDao.deleteUserById(user.getId());
    }

    /**
     * 修改用户启停状态
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updateIsactive(SysUser user) throws Exception {
        userDao.updateIsactive(user.getIsactive(), UserUtils.getUser().getId(), new Date(), user.getId());
    }

    /**
     * 修改用户锁定状态
     *
     * @param user
     * @throws Exception
     */
    //    @Override
    //    public void updateIslockedById(SysUser user) throws Exception {
    //    	userDao.updateIslockedById(user.getIslocked(),UserUtils.getUser().getId(), new Date(), user.getId());
    //    }

    /**
     * 修改用户锁定状态
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updateIslockedByCde(SysUser user) throws Exception {
        Date nowDate = new Date();
        userDao.updateIslockedByCde(user.getIslocked(), UserUtils.getUser().getId(), nowDate, user.getUserCde());
        SysUser newUser = (SysUser) CacheUtils.get(UserUtils.USER_CACHE,
            UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde());
        if (StringUtil.isNotEmpty(newUser) && StringUtil.isNotEmpty(newUser.getId())) {
            newUser.setIslocked(user.getIslocked());
            newUser.setUpdateTime(nowDate);
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde(), newUser);
        }
    }

    /**
     * 用户组织导入
     *
     * @param ajaxJson   储存信息的对象
     * @param uploadFile 模版文件
     * @throws Exception
     */
    @Override
    public void importOrgUsers(AjaxJson ajaxJson, CommonsMultipartFile uploadFile) throws Exception {

        if (!uploadFile.isEmpty()) {
            String originalFilename = uploadFile.getOriginalFilename();

            // 当文件超过设置的大小时，则不运行上传
            if (uploadFile.getSize() > UploadFile.fileSize) {
                throw new Exception(MessageUtils.getMessageOrSelf("common.sys.file.size.error",
                    UploadFile.sysConfigInfo.getFileSize()));
            }
            int pos = originalFilename.lastIndexOf('.');

            // 获取文件名后缀
            String fileSuffix = originalFilename.substring(pos + 1).toLowerCase();
            String fileName = originalFilename.substring(0, pos);

            // 判断该类型的文件是否在允许上传的文件类型内
            if (!Arrays.asList(UploadFile.TypeMap.get("excel").split(",")).contains(fileSuffix)) {
                ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.file.type.error"));
                return;
            }
            File tmpFile = null;
            try {
                // 检查上传文件的目录
                String realPath = ResourceUtil.getUploadFileTempPath();
                FileUtil.mkDir(realPath);
                String timeMillis = System.currentTimeMillis() + "";
                // 临时文件名
                String tempfileName = fileName + "_" + timeMillis + "." + fileSuffix;
                // 正式保存的文件名
                fileName = fileName + "." + fileSuffix;
                String path = realPath + tempfileName;
                tmpFile = new File(path);

                // 通过CommonsMultipartFile的方法直接写文件
                uploadFile.transferTo(tmpFile);
                Workbook workbook = ExcelUtil.getWorkBook(tmpFile);
                int userTotal = 0;
                int orgTotal = 0;
                if (workbook != null) {

                    // 组织名称
                    Map<String, Integer> orgRepeatMap = new HashMap<>();

                    // 组织编码出现次数,判断重复
                    Map<String, Integer> orgCodeRepeatMap = new HashMap<>();
                    Map<String, SysOrg> orgMap = new LinkedHashMap<>();

                    // 用户-全部数据重复几次
                    Map<String, Integer> userRepeatMap = new HashMap<>();

                    // 用户-组织为空
                    List<SysUser> userOrgEmpty = new ArrayList<>();

                    // 用户数量提示信息
                    String userCountMsg = "";

                    int userNum = userDao.queryAllUserListByUserType(SysUser.USER_TYPE);

                    // 解析文件后的数据存储
                    Map<String, Object> rsMap = new HashMap<>();
                    for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                        List<Object> list = new ArrayList<>();
                        Sheet sheet = workbook.getSheetAt(sheetNum);
                        if (sheet == null) {
                            continue;
                        }
                        int firstRowNum = sheet.getFirstRowNum();
                        int lastRowNum = sheet.getLastRowNum();
                        String sheetName = sheet.getSheetName();
                        rsMap.put(sheetName, list);
                        if ("组织".equals(sheetName)) {

                            // 计算pid的缓存
                            Map<Integer, String> piDmap = new HashMap<>();

                            String pId = "0";

                            // 是否删除子节点
                            boolean removeChild = Boolean.FALSE;
                            int removeChildCellNum = 0;
                            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                                Row row = sheet.getRow(rowNum);
                                if (row == null) {
                                    continue;
                                }
                                orgTotal++;
                                int lastCellNum = row.getLastCellNum();
                                for (int cellNum = 0; cellNum < lastCellNum; cellNum++) {
                                    Cell cell = row.getCell(cellNum);
                                    String cellValue = ExcelUtil.getCellValue(cell);
                                    if (StringUtil.isEmpty(cellValue.trim())) {
                                        continue;
                                    }

                                    // 编码
                                    String orgCde = "";
                                    try {
                                        Cell ordCdeCell = row.getCell(cellNum - 1);
                                        orgCde = ExcelUtil.getCellValue(ordCdeCell);
                                    } catch (Exception ignored) {
                                    }
                                    if (StringUtil.isEmpty(orgCde)) {
                                        continue;
                                    }
                                    pId = piDmap.get(cellNum - 2);
                                    if (cellNum == 0 || cellNum == 1) {
                                        pId = "0";

                                        // 清空
                                        piDmap.clear();
                                    }
                                    String id = UUIDGenerator.generate();

                                    // 公司不重复
                                    if (orgRepeatMap.containsKey(cellValue) && cellValue.startsWith("#")) {
                                        Integer num = orgRepeatMap.get(cellValue) + 1;
                                        orgRepeatMap.put(cellValue, num);
                                        removeChild = Boolean.TRUE;
                                        removeChildCellNum = cellNum;
                                    } else {
                                        SysOrg sysOrg = new SysOrg();
                                        sysOrg.setId(id);

                                        // 公司还是部门(#开头是公司,$开头是部门)
                                        if (cellValue.startsWith("#")) {
                                            sysOrg.setType(SysOrg.TYPE_COMPANY);
                                            sysOrg.setOrgName(cellValue.replaceFirst("#", ""));
                                        } else if (cellValue.startsWith("$")) {
                                            sysOrg.setType(SysOrg.TYPE_DEPARTMENT);
                                            sysOrg.setOrgName(cellValue.replaceFirst("\\$", ""));
                                        } else {
                                            sysOrg.setType(SysOrg.TYPE_DEPARTMENT);
                                            sysOrg.setOrgName(cellValue);
                                        }
                                        if (!StringUtil.isNotEmpty(orgCde)) {

                                            // 编码不能为空
                                            continue;
                                        }
                                        sysOrg.setOrgCde(orgCde);
                                        if (null != orgCodeRepeatMap.get(orgCde)) {

                                            // 重复的编码
                                            continue;
                                        }
                                        if (SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                                            orgCodeRepeatMap.put(orgCde, 1);
                                        }
                                        list.add(sysOrg);
                                        orgRepeatMap.put(cellValue, 1);
                                        orgMap.put(cellValue, sysOrg);
                                        if (removeChild && cellNum == removeChildCellNum + 1) {

                                            // 取上一行的数据
                                            Row upRow = sheet.getRow(rowNum - 1);
                                            Cell upCell = upRow.getCell(cellNum - 1);
                                            sysOrg.setParentId(orgMap.get(ExcelUtil.getCellValue(upCell)).getId());

                                            // 同名的修改子集pid
                                        } else {
                                            sysOrg.setParentId(pId);
                                            removeChild = Boolean.FALSE;
                                        }
                                    }
                                    piDmap.put(cellNum, id);
                                }
                            }
                        }
                        if ("用户".equals(sheetName)) {
                            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                                Row row = sheet.getRow(rowNum);
                                if (row == null) {
                                    continue;
                                }
                                userTotal++;
                                SysUser sysUser = new SysUser();
                                sysUser.setId(UUIDGenerator.generate());
                                sysUser.setCompany(ExcelUtil.getCellValue(row.getCell(0)));
                                sysUser.setUserCde(ExcelUtil.getCellValue(row.getCell(1)));
                                sysUser.setUserName(ExcelUtil.getCellValue(row.getCell(2)));
                                sysUser.setPassword(ExcelUtil.getCellValue(row.getCell(3)));
                                sysUser.setGender(ExcelUtil.getCellValue(row.getCell(4)));
                                sysUser.setDepartment(ExcelUtil.getCellValue(row.getCell(5)));
                                sysUser.setPosition(ExcelUtil.getCellValue(row.getCell(6)));
                                sysUser.setMail(ExcelUtil.getCellValue(row.getCell(7)));
                                sysUser.setMobilePhone(ExcelUtil.getCellValue(row.getCell(8)));
                                sysUser.setAreaCode(ExcelUtil.getCellValue(row.getCell(9)));
                                sysUser.setTelephone(ExcelUtil.getCellValue(row.getCell(10)));
                                sysUser.setEmployType(ExcelUtil.getCellValue(row.getCell(11)));
                                sysUser.setThirdPartyCode(ExcelUtil.getCellValue(row.getCell(12)));

                                // 检测用户名是否重复
                                if (userRepeatMap.containsKey(sysUser.getUserCde())) {
                                    Integer num = userRepeatMap.get(sysUser.getUserCde()) + 1;
                                    userRepeatMap.put(sysUser.getUserCde(), num);
                                } else {

                                    // 用户所属组织不为空
                                    if (StringUtil.isNotEmpty(sysUser.getCompany())
                                        && StringUtil.isNotEmpty(sysUser.getUserCde())
                                        && StringUtil.isNotEmpty(sysUser.getUserName())
                                        && StringUtil.isNotEmpty(sysUser.getGender())
                                        && StringUtil.isNotEmpty(sysUser.getPassword())
                                        && StringUtil.isNotEmpty(sysUser.getPosition())) {

                                        passwordService.encryptPassword(sysUser);
                                        sysUser.setGender("男".equals(sysUser.getGender()) ? "1" : "2");
                                        sysUser.setType(SysUser.USER_TYPE);
                                        sysUser.setIsInit(SysUser.ISINIT);
                                        sysUser.setIslocked(SysUser.NO_LOCKED);
                                        sysUser.setIsactive(SysUser.STATUS_NORMAL);
                                        sysUser.setCreateTime(new Date());
                                        sysUser.setCreateUser(UserUtils.getUser().getId());
                                        sysUser.setUpdateUser(UserUtils.getUser().getId());
                                        sysUser.setUpdateTime(new Date());
                                        sysUser.setHeadPhoto(SysUser.HEADPHOTO_MAN);
                                        list.add(sysUser);
                                        userRepeatMap.put(sysUser.getUserCde(), 1);
                                    } else {
                                        userOrgEmpty.add(sysUser);
                                    }
                                }
                            }
                        }
                    }
                    workbook.close();
                    checkRsMapRedundanceData(rsMap);
                    Map msgMap = new HashMap();
                    String _uuid = UUIDGenerator.generate();
                    List<SysUser> users = (List<SysUser>) rsMap.get("用户");
                    String rightMsg = "新增用户:" + users.size() + "条," +
                        "更新用户:" + ((List) rsMap.get("更新用户")).size() + "条," +
                        "新增组织:" + ((List) rsMap.get("组织")).size() + "条," +
                        "新增职务:" + ((List) rsMap.get("新增职务")).size() + "条";
                    String badMsg = "用户:" + (userTotal - users.size() -
                        ((List) rsMap.get("更新用户")).size()) + "条," +
                        "组织:" + (orgTotal - ((List) rsMap.get("组织")).size()) + "条";
                    msgMap.put("_uuid", _uuid);
                    msgMap.put("right", rightMsg);
                    msgMap.put("bad", badMsg + userCountMsg);
                    msgMap.put("userList", users);
                    msgMap.put("orgList", rsMap.get("组织"));

                    // TODO:用户重新上传,清除上一次缓存
                    CacheUtils.put(_uuid, rsMap);
                    ajaxJson.setObj(msgMap);
                }
            } catch (FileNotFoundException e) {
                throw new WdFileNotFoundException(e);
            } catch (Exception e) {
                throw e;
            } finally {

                // 删除临时文件
                if (null != tmpFile && tmpFile.exists()) {
                    tmpFile.delete();
                }
            }
        } else {
            throw new Exception(MessageUtils.getMessage("common.sys.file.notfound.error"));
        }
    }

    /**
     * 执行导入操作
     *
     * @param _uuid 唯一标识
     * @throws Exception
     */
    @Override
    public void complyImportOrgUser(String _uuid) throws Exception {

        Map<String, Object> cacheData = (Map<String, Object>) CacheUtils.get(_uuid);
        if (null == cacheData) {
            throw new Exception("缓存中数据异常");
        }
        List<SysUser> userList = (List<SysUser>) cacheData.get("用户");
        List<SysUser> updateUserList = (List<SysUser>) cacheData.get("更新用户");
        List<SysOrg> orgList = (List<SysOrg>) cacheData.get("组织");
        SysUser user = UserUtils.getUser();
        long time = System.currentTimeMillis();
        Map<String, Long> timeMap = new HashMap<>(orgList.size());

        // 先保存组织,操作wd_sys_user_org时会用到名称
        Date updateTime = new Date();
        for (SysOrg sysOrg : orgList) {
            sysOrg.setCreateUser(user.getId());
            sysOrg.setUpdateUser(user.getId());
            sysOrg.setUpdateTime(updateTime);
            synSave(time, sysOrg, timeMap);

            // 新增组织分配管理员角色
            List<String> roleIds = roleService.getRoleList();
            for (int i = 0; i < roleIds.size(); i++) {
                SysOrgRole sysOrgRole = new SysOrgRole();
                sysOrgRole.setOrgId(sysOrg.getId());
                sysOrgRole.setRoleId(roleIds.get(i));
                sysOrgRole.setCreateTime(updateTime);
                sysOrgRoleDao.saveAndFlush(sysOrgRole);
            }
        }

        for (SysUser sysUser : userList) {
            sysUser.setCreateUser(user.getId());
            sysUser.setCreateTime(new Date());
            sysUser.setUpdateUser(user.getId());
            sysUser.setUpdateTime(new Date());
            userDao.saveAndFlush(sysUser);

            // 操作wd_sys_user_org
            this.sysUserOrgService.createUserInfo(sysUser);
        }
        this.updataCacheUsers(updateUserList);

    }

    public synchronized void synSave(long time, SysOrg sysOrg, Map<String, Long> timeMap) throws Exception {

        sysOrg.setCreateTime(new Date());
        if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE)) {
            Long timeLong = timeMap.get(sysOrg.getParentId());
            long timeAdd;
            if (timeLong == null) {
                timeAdd = time;
            } else {

                // 加一秒,oracle 排序
                timeAdd = timeLong + 1000;
            }
            timeMap.put(sysOrg.getParentId(), timeAdd);
            sysOrg.setCreateTime(new Date(timeAdd));
        }
        orgDao.saveAndFlush(sysOrg);
    }

    /**
     * 更新导入用户
     *
     * @param updateUserList 导入用户的集合
     * @throws Exception
     */
    public void updataCacheUsers(List<SysUser> updateUserList) throws Exception {

        if (null != updateUserList && updateUserList.size() > 0) {
            EntityManager entityManager = emf.createEntityManager();
            try {
                entityManager.getTransaction().begin();
                for (SysUser sysUser : updateUserList) {

                    // 操作wd_sys_user_org
                    this.sysUserOrgService.updateUserNameByUserCode(sysUser);
                    String updateSql = "update wd_sys_user set "
                        + "org_id = '" + sysUser.getOrgId() + "',"
                        + "department = '" + sysUser.getDepartment() + "',"
                        + "employ_type = '" + sysUser.getEmployType() + "',"
                        + "c_position = '" + sysUser.getPosition() + "',"
                        + "mail = '" + sysUser.getMail() + "',"
                        + "mobile_phone = '" + sysUser.getMobilePhone() + "',"
                        + "area_code = '" + sysUser.getAreaCode() + "',"
                        + "telephone = '" + sysUser.getTelephone() + "',"
                        + "update_user = '" + UserUtils.getUser().getId() + "',"
                        + "update_time = ";
                    if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE) ||
                        DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_DM8)) {
                        updateSql += "to_date('" + DateUtils.date2Str(new Date(),DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS) + "','yyyy-mm-dd hh24:mi:ss')";
                    } else {
                        updateSql += "'" + DateUtils.date2Str(new Date(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS) + "'";
                    }
                    updateSql += " where user_cde = '" + sysUser.getUserCde() + "' ";
                    entityManager.createNativeQuery(updateSql).executeUpdate();
                }
                entityManager.getTransaction().commit();
                entityManager.close();
            } catch (Exception e) {
                e.printStackTrace();
                entityManager.getTransaction().rollback();
                entityManager.close();
                throw e;
            }
        }
    }

    /**
     * 权限分配tree
     * 由之前节点下直接挂用户，改为拆分开，单独调用获取用户的接口，该接口目前只获取组织部门树
     * @throws Exception
     */
    @Override
    public UserTreeParent queryUserLimitTree() throws Exception {

        List<SysOrg> orgPermission = orgService.querySysOrgList();//UserUtils.getOrgPermission();zhaibx修改，暂时不走缓存观察下
//        List<SysUser> userPermission = UserUtils.getUserPermission();
        Map<String, List<SysUser>> userDepartmentMap = new HashMap<>();

        // 只为记录用户是否在集合里
        /*Map<String,  String> userMap = new HashMap<>();
        if (userPermission.size() > 0) {
            userPermission.forEach((user) -> {
                if (userDepartmentMap.containsKey(user.getDepartment())) {
                    List<SysUser> users = userDepartmentMap.get(user.getDepartment());
                    if (!userMap.containsKey(user.getId())) {
                        users.add(user);
                        userMap.put(user.getId(), user.getId());
                    }
                } else {
                    List<SysUser> users = new ArrayList<>();
                    users.add(user);
                    userDepartmentMap.put(user.getDepartment(), users);
                    userMap.put(user.getId(), user.getId());
                }
            });
        }*/
        UserTreeParent userTreeParent = new UserTreeParent();
        List<UserTree> treeList = new ArrayList<UserTree>();
        userTreeParent.setCheck(true);

        // 组织
        for (SysOrg org : orgPermission) {
            if (SysOrg.TYPE_ROOT.equals(org.getParentId())) {
                UserTree userTree = new UserTree();
                String orgType = org.getType();
                switch (orgType) {
				case "0":
					userTree.setType(TreeNodeType.FOLDER);
					break;
				case "1":
					userTree.setType(TreeNodeType.COMPANY);
					break;
				case "2":
					userTree.setType(TreeNodeType.DEPARTMENT);
					break;
				default:
					userTree.setType(TreeNodeType.FOLDER);
					break;
				}
                
                userTree.setId(org.getId());
                userTree.setName(org.getOrgName());
                userTree.setValid_children(null);

                // userId为什么set orgId呢？
                userTree.setUserId(org.getId());
                findUserTree(org.getId(), orgPermission, userDepartmentMap, userTree);
                treeList.add(userTree);
            }
        }
        userTreeParent.setTreeNodes(treeList);
        return userTreeParent;
    }

    private void findUserTree(String orgId, List<SysOrg> orgPermission, Map<String, List<SysUser>> userDepartmentMap,
        UserTree parentUserTree) {

        List<UserTree> treeList = new ArrayList<UserTree>();
        for (SysOrg org : orgPermission) {
            if (orgId.equals(org.getParentId())) {
                UserTree userTree = new UserTree();
                List<UserTree> childUserTree = new ArrayList<UserTree>();
                String orgType = org.getType();
                switch (orgType) {
				case "0":
					userTree.setType(TreeNodeType.FOLDER);
					break;
				case "1":
					userTree.setType(TreeNodeType.COMPANY);
					break;
				case "2":
					userTree.setType(TreeNodeType.DEPARTMENT);
					break;
				default:
					userTree.setType(TreeNodeType.FOLDER);
					break;
				}
                userTree.setId(org.getId());
                userTree.setName(org.getOrgName());
                userTree.setValid_children(null);

                // userId为什么set orgId呢？
                userTree.setUserId(org.getId());
                findUserTree(org.getId(), orgPermission, userDepartmentMap, userTree);
                List<SysUser> sysUsers = userDepartmentMap.get(org.getId());
                if (null != sysUsers && sysUsers.size() > 0) {
                    for (SysUser user : sysUsers) {
                        UserTree childUser = new UserTree();
                        childUser.setType(TreeNodeType.USER);
                        childUser.setId(user.getId());
                        childUser.setName(user.getUserName());
                        childUser.setCode(user.getUserCde());
                        childUser.setValid_children(null);
                        childUser.setUserId(user.getId());
                        childUserTree.add(childUser);
                    }
                }
                if (childUserTree.size() > 0) {
                    userTree.setChildren(childUserTree);
                }
                treeList.add(userTree);
            }
        }
        if (treeList.size() > 0) {
            parentUserTree.setChildren(treeList);
        }
    }

    /**
     * 获取当前用户最大权限下所有的用户信息
     *
     * @throws Exception
     */
    @Override
    public AllUserListParent queryAllUserList(AllUserListParent allUserListParent) throws Exception {

        String search = "%" + allUserListParent.getSearch() + "%";
        Pageable pageable = PageRequest.of(allUserListParent.getPageable(), allUserListParent.getSize());
        Page<SysUser> page = null;

        // 所有组织
        Map<String, String> orgNameMap = new HashMap<>();
        List<SysOrg> sysOrgs = this.orgService.queryAllSysOrg();
        for (SysOrg sysOrg : sysOrgs) {
            orgNameMap.put(sysOrg.getId(), sysOrg.getOrgName());
        }
        List<SysUser> allUser = new ArrayList<SysUser>();
        if (SysOrg.TYPE_DEPARTMENT.equals(allUserListParent.getType())) {
            page = queryDepmentUsers(search, pageable, page, allUserListParent);
        } else {

            // 管理员登陆
            if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
                if ("0".equals(allUserListParent.getOrgId())) {
                    page = userDao.queryUserList(search, search, pageable);
                    allUser = userDao.queryUserList();
                } else {
                    page = userDao.querySysUsersByOrgId(search, search, allUserListParent.getOrgId(), pageable);
                }
            } else {// 其他管理员
                if ("0".equals(allUserListParent.getOrgId())) {
                    page = userDao.queryUserList(search, search, UserUtils.getUser().getId(), pageable);
                    allUser = userDao.queryUserList(UserUtils.getUser().getId());
                } else {
                    page = userDao.queryUserList(
                        search,
                        search,
                        UserUtils.getUser().getId(),
                        allUserListParent.getOrgId(),
                        pageable
                    );
                }
            }
        }
        List<AllUserList> list = new ArrayList<AllUserList>();
        List<SysLastOnline> lastOnlineList = lastOnlineDao.findAll();
        List<SysUserOnline> onlineList = userOnlineDao.findAll();
        for (SysUser sysUser : page.getContent()) {
            AllUserList allUserList2 = new AllUserList();
            for (SysLastOnline sysLastOnline : lastOnlineList) { // 最后一次登录ip和退出时间
                if (sysUser.getId().equals(sysLastOnline.getUserId())) {
                    allUserList2.setLoginInformation(sysLastOnline.getHost() + " "
                        + DateUtils.date2Str(sysLastOnline.getLastLoginTimestamp(),DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
                    break;
                }
            }
            if ("0".equals(sysUser.getIsactive())) { // 状态赋值 停用
                allUserList2.setStatus("3"); // 停用
            } else if ("0".equals(sysUser.getIslocked())) { //
                allUserList2.setStatus("2"); // 锁定
            } else {
                allUserList2.setStatus("1"); // 启用
            }
            for (SysUserOnline sysUserOnline : onlineList) {
                if (sysUser.getId().equals(sysUserOnline.getUserId())) {
                    allUserList2.setStatus("0"); // 在线
                    break;
                }
            }
            allUserList2.setDepartmentName(orgNameMap.get(sysUser.getDepartment()));
            allUserList2.setUserId(sysUser.getId());
            allUserList2.setUserCde(sysUser.getUserCde());
            allUserList2.setUserName(sysUser.getUserName());
            allUserList2.setMail(sysUser.getMail());
            list.add(allUserList2);
        }
        // 只统计全部的
        int onlineNum = 0;
        int stopNum = 0;
        int lockedNum = 0;
        for (SysUser sysUser : allUser) {
            if ("0".equals(sysUser.getIsactive())) { // 状态赋值 停用
                stopNum++;
            }
            if ("0".equals(sysUser.getIslocked())) { // 锁定用户
                lockedNum++;
            }
            for (SysUserOnline sysUserOnline : onlineList) {
                if (sysUser.getId().equals(sysUserOnline.getUserId())) {
                    onlineNum++;
                    break;
                }
            }
        }
        SysOrg sysOrg = this.orgDao.findById(allUserListParent.getOrgId()).orElse(null);
        if (null != sysOrg) {
            allUserListParent.setOrgCde(sysOrg.getOrgCde());
        }
        allUserListParent.setTotalUser(Integer.toString(allUser.size()));
        allUserListParent.setActiveUser(Integer.toString(onlineNum));
        allUserListParent.setDisableUser(Integer.toString(stopNum));
        allUserListParent.setLockedUser(Integer.toString(lockedNum));
        allUserListParent.setTotalElements(page.getTotalElements());
        allUserListParent.setTotalPagets(page.getTotalPages());
        allUserListParent.setLists(list);
        return allUserListParent;
    }

    /**
     * 查询部门下的用户
     *
     * @param search
     * @param pageable
     * @param page
     * @param allUserListParent
     * @return
     * @throws Exception
     */
    private Page<SysUser> queryDepmentUsers(String search, Pageable pageable, Page<SysUser> page,
        AllUserListParent allUserListParent) throws Exception {

        // 管理员
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            page = userDao.querySysUsersByDepartmentOrgId(search, search, allUserListParent.getOrgId(), pageable);
        } else {
            page = userDao.queryUserListByDepartmentOrgId(search, search, UserUtils.getUser().getId(),
                allUserListParent.getOrgId(), pageable);
        }
        return page;
    }

    /**
     * 获取当前用户最大权限下所有的用户信息
     *
     * @throws Exception
     */
    @Override
    public UserDetailedJson queryUserDetailed(String userId) throws Exception {
        SysUser sysUser = userDao.findById(userId).orElse(null);
        Objects.requireNonNull(sysUser, "用户不存在");
        List<String> list = userRoleDao.getUserRoleByUserId(userId);
        UserDetailedJson uDetailedJson = new UserDetailedJson();
        uDetailedJson.setUserId(sysUser.getId());
        uDetailedJson.setUserCde(sysUser.getUserCde());
        uDetailedJson.setUserType("用户");
        uDetailedJson.setIsactive(sysUser.getIsactive());
        uDetailedJson.setIslocked(sysUser.getIslocked());
        uDetailedJson.setUserName(sysUser.getUserName());
        uDetailedJson.setCompany(sysUser.getCompany());
        uDetailedJson.setDepartment(sysUser.getDepartment());
        uDetailedJson.setPosition(sysUser.getPosition());
        uDetailedJson.setMobilePhone(sysUser.getMobilePhone());
        uDetailedJson.setAreaCode(sysUser.getAreaCode());
        uDetailedJson.setTelephone(sysUser.getTelephone());
        uDetailedJson.setGender(sysUser.getGender());
        uDetailedJson.setEmployType(sysUser.getEmployType());
        uDetailedJson.setMail(sysUser.getMail());
        uDetailedJson.setRoles(list);
        uDetailedJson.setOrgId(sysUser.getOrgId());
        uDetailedJson.setThirdPartyCode(sysUser.getThirdPartyCode());
        SysOrg sysOrg = this.orgDao.findById(sysUser.getDepartment()).orElse(null);
        if (null != sysOrg) {
            uDetailedJson.setDepartmentName(sysOrg.getOrgName());
        }
        SysPost sysPost = this.postDao.querySysPostById(sysUser.getPosition());
        if (null != sysPost) {
            uDetailedJson.setPositionName(sysPost.getPostName());
        }
        return uDetailedJson;
    }

    @Override
    public UserDetailedJson queryNowUserInfo() throws Exception {
        SysUser sysUser = userDao.findById(UserUtils.getUser().getId()).orElse(null);
        UserDetailedJson uDetailedJson = new UserDetailedJson();

        // 部门名字
        SysOrg departmentOrg = orgDao.findById(sysUser.getDepartment()).orElse(null);
        if (null != departmentOrg) {
            uDetailedJson.setDepartment(departmentOrg.getOrgName());
        }

        // 职务名字
        if (StringUtil.isNotEmpty(sysUser.getPosition())) {
            SysPost sysPost = postDao.querySysPostById(sysUser.getPosition());
            if (null != sysPost) {
                uDetailedJson.setPosition(sysPost.getPostName());
            } else {
                uDetailedJson.setPosition("暂无");
            }
        } else {
            uDetailedJson.setPosition("暂无");
        }
        System.out.println(UserUtils.getPrincipal());
        uDetailedJson.setUserId(sysUser.getId());
        uDetailedJson.setUserCde(sysUser.getUserCde());
        uDetailedJson.setUserType("用户");
        uDetailedJson.setIsactive(sysUser.getIsactive());
        uDetailedJson.setIslocked(sysUser.getIslocked());
        uDetailedJson.setUserName(sysUser.getUserName());
        uDetailedJson.setCompany(sysUser.getCompany());
        uDetailedJson.setMobilePhone(sysUser.getMobilePhone());
        uDetailedJson.setAreaCode(sysUser.getAreaCode());
        uDetailedJson.setTelephone(sysUser.getTelephone());
        uDetailedJson.setGender(sysUser.getGender());
        uDetailedJson.setEmployType(sysUser.getEmployType());
        uDetailedJson.setMail(sysUser.getMail());
        uDetailedJson.setRoles(UserUtils.getRolesIdList());
        uDetailedJson.setOrgId(sysUser.getOrgId());
        SysOrg userOrg = UserUtils.getUserDetail();
        if (StringUtil.isNotEmpty(userOrg)) {
            uDetailedJson.setOrgName(userOrg.getOrgName());
        } else {
            uDetailedJson.setOrgName("暂无组织");
        }
        uDetailedJson.setRoleNames(UserUtils.getRolesNameList());
        uDetailedJson.setUnionId(sysUser.getUnionId());
        uDetailedJson.setHeadPhoto(sysUser.getHeadPhoto());
        return uDetailedJson;
    }

    /**
     * 拼接返回对象ResponseUserJson
     *
     * @throws Exception
     */
    @Override
    public ResponseUserJson changeResponseUserJson(SysUser sysUser) throws Exception {
        ResponseUserJson rUserJson = new ResponseUserJson();
        // 树
        UserTree userTree = new UserTree();
        userTree.setId(sysUser.getId());
        userTree.setpId(sysUser.getParentId());
        userTree.setName(sysUser.getUserName());
        userTree.setCode(sysUser.getUserCde());
        userTree.setUserId(sysUser.getId());
        userTree.setValid_children(null);
        userTree.setDrag(true);
        userTree.setCopy(false);
        userTree.setNocheck(false);
        userTree.setChecked(true);

        List<UserTree> list = new ArrayList<UserTree>();

        // 基本信息
        UserDetailedJson userDetailedJson = new UserDetailedJson();

        if ("USER".equals(sysUser.getType())) {
            List<String> roleIdList = userRoleDao.getUserRoleByUserId(sysUser.getId());
            userTree.setDrop(false);
            userTree.setType(TreeNodeType.USER);
            userDetailedJson.setUserCde(sysUser.getUserCde());
            userDetailedJson.setUserId(sysUser.getId());
            userDetailedJson.setUserType("用户");
            userDetailedJson.setUserName(sysUser.getUserName());
            userDetailedJson.setIslocked(sysUser.getIslocked());
            userDetailedJson.setIsactive(sysUser.getIsactive());
            userDetailedJson.setCompany(sysUser.getCompany());
            userDetailedJson.setDepartment(sysUser.getDepartment());
            userDetailedJson.setPosition(sysUser.getPosition());
            userDetailedJson.setMobilePhone(sysUser.getMobilePhone());
            userDetailedJson.setAreaCode(sysUser.getAreaCode());
            userDetailedJson.setTelephone(sysUser.getTelephone());
            userDetailedJson.setGender(sysUser.getGender());
            userDetailedJson.setEmployType(sysUser.getEmployType());
            userDetailedJson.setMail(sysUser.getMail());
            userDetailedJson.setRoles(roleIdList);
            userDetailedJson.setOrgId(sysUser.getOrgId());
        }
        if ("FOLDER".equals(sysUser.getType())) {
            userTree.setDrop(true);
            userTree.setType(TreeNodeType.FOLDER);
            userDetailedJson.setUserId(sysUser.getId());
            userDetailedJson.setUserType("文件夹");
            userDetailedJson.setUserName(sysUser.getUserName());
        }

        list.add(userTree);
        rUserJson.setTreeNodes(list);
        rUserJson.setInfo(userDetailedJson);

        return rUserJson;
    }

    /**
     * 根据用户类型查询所有数量
     *
     * @param userType
     * @return
     * @throws Exception
     */
    @Override
    public int queryAllUserListByUserType(String userType) throws Exception {
        return userDao.queryAllUserListByUserType(userType);
    }

    /**
     * 修改头像
     *
     * @param headPhotoName
     * @throws Exception
     */
    @Override
    public void updateHeadPhoto(String headPhotoName) throws Exception {
        userDao.updateHeadPhoto(headPhotoName, UserUtils.getUser().getId(), new Date(), UserUtils.getUser().getId());
        SysUser newUser = UserUtils.getUser();
        newUser.setHeadPhoto(headPhotoName);
        CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_USER_NAME_ + newUser.getUserCde(), newUser);
    }

    /**
     * 重置密码
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updateResetPassword(SysUser user) throws Exception {

        SysUser sysUser = userDao.querySysUserById(user.getId());
        user.setUserName(sysUser.getUserName());
        passwordService.encryptPassword(user);
        Date nowDate = new Date();
        userDao.resetPassword(user.getPassword(), user.getSalt(), UserUtils.getUser().getId(), nowDate, nowDate,
            user.getId());
        SysUser newUser = (SysUser) CacheUtils.get(UserUtils.USER_CACHE,
            UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde());
        if (StringUtil.isNotEmpty(newUser) && StringUtil.isNotEmpty(newUser.getId())) {
            newUser.setUpdatePasswordTime(nowDate);
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_USER_NAME_ + user.getUserCde(), newUser);
        }
    }

    /**
     * 修改库里全部用户密码
     *
     * @throws Exception
     */
    @Override
    public void updateAllUserPassword(String password) throws Exception {
        // 查询未修改过初始密码的用户进行重置
        List<SysUser> users = userDao.querySysUserListByIsInit(SysUser.ISINIT);
        for (SysUser user : users) {
            if (!AdministratorUtils.getAdministratorName().equals(user.getUserCde())) {
                user.setPassword(password);
                updateResetPassword(user);
            }
        }
    }

    /**
     * 个人更新用户信息 手机、电话
     *
     * @param rUserJson
     * @throws Exception
     */
    @Override
    public void updateUserPhone(UserDetailedJson rUserJson) throws Exception {
        Date nowDate = new Date();
        userDao.updateUserInfo(
            rUserJson.getMobilePhone(),
            rUserJson.getAreaCode(),
            rUserJson.getTelephone(),
            rUserJson.getMail(),
            rUserJson.getGender(),
            UserUtils.getUser().getId(),
            nowDate,
            UserUtils.getUser().getId()
        );
        SysUser newUser = (SysUser) CacheUtils.get(UserUtils.USER_CACHE,
            UserUtils.USER_CACHE_USER_NAME_ + UserUtils.getUser().getUserCde());
        if (null != newUser && StringUtil.isNotEmpty(newUser.getId())) {
            newUser.setMobilePhone(rUserJson.getMobilePhone());
            newUser.setAreaCode(rUserJson.getAreaCode());
            newUser.setTelephone(rUserJson.getTelephone());
            newUser.setMail(rUserJson.getMail());
            newUser.setGender(rUserJson.getGender());
            newUser.setUpdateTime(nowDate);
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_USER_NAME_ + newUser.getUserCde(), newUser);
        }
    }

    /**
     * 校验模版里是否有重复的数据
     *
     * @param rsMap
     */
    public void checkRsMapRedundanceData(Map<String, Object> rsMap) throws Exception {

        List<SysOrg> sysOrgsDataBase = orgDao.queryAllOrgList();
        List<SysOrg> sysOrgs = new ArrayList<SysOrg>();
        if (null != rsMap.get("组织")) {
            sysOrgs = (List<SysOrg>) rsMap.get("组织");
            checkRsMapOrg(sysOrgs, sysOrgsDataBase);
        }
        if (null != rsMap.get("用户")) {
            List<SysUser> sysUsers = (List<SysUser>) rsMap.get("用户");
            List<SysUser> repeatUsers = new ArrayList<>();
            rsMap.put("更新用户", repeatUsers);

            List<SysPost> sysPosts = new ArrayList<>();
            rsMap.put("新增职务", sysPosts);
            List<SysUser> sysUsersDataBase = userDao.queryUserList();


            // 前台信息
            List<SysUser> msgUsers = new ArrayList<>();
            checkRsMapUser(sysUsers, sysUsersDataBase, sysOrgs, sysOrgsDataBase, repeatUsers, sysPosts, msgUsers);
        }
    }

    /**
     * 用户与数据库数据查重
     * 2022-8-30 整体思路在解析文件那就偏了,先这么改
     *
     * @param excleUserList
     * @param sysUsersDataBases
     * @param orgs
     * @param sysOrgsDataBase
     * @param updateUserCdes
     * @param sysPosts
     * @param msgUsers
     * @throws Exception
     */
    private void checkRsMapUser(List<SysUser> excleUserList, List<SysUser> sysUsersDataBases, List<SysOrg> orgs,
        List<SysOrg> sysOrgsDataBase, List<SysUser> updateUserCdes, List<SysPost> sysPosts, List<SysUser> msgUsers) throws Exception {

        // 用户cde集合
        Map<String, SysUser> repeatUser = new HashMap<String, SysUser>();
        List<String> repeatUserCdes = new ArrayList<String>();
        List<String> removeUserCdes = new ArrayList<String>();

        // 记录重复数据下标,移除excleUserList准备
        List<Integer> removeIndexs = new ArrayList<Integer>();

        // 把组织集合放入map key=orgCde,value=orgid, 为用户设置组织id使用
        Map<String, String> orgIdMap = new HashMap<String, String>();
        Map<String, SysOrg> orgMap = new HashMap<>();

        // 职务map
        Map<String, String> postMap = new HashMap<>();

        // 查职务数据
        List<SysPost> posts = postDao.querySysPostList();
        if (null != posts && posts.size() > 0) {
            posts.forEach((post) -> {
                postMap.put(post.getPostName(), post.getId());
            });
        }

        // 公司类型的put(名字),部门类型的put(公司id+名字)
        for (SysOrg sysOrg : orgs) {
            if (SysOrg.TYPE_COMPANY.equals(sysOrg.getType()) &&
                sysOrg.getParentId().equals(SysOrg.TYPE_ROOT)) {
                orgIdMap.put(sysOrg.getOrgCde(), sysOrg.getId());
                orgMap.put(sysOrg.getOrgCde(), sysOrg);
                findDeparment(sysOrg.getId(), sysOrg.getId(), orgs, orgIdMap, orgMap);
            } else {
                orgIdMap.put(sysOrg.getParentId() + sysOrg.getOrgCde(), sysOrg.getId());
                orgMap.put(sysOrg.getParentId() + sysOrg.getOrgCde(), sysOrg);
                findDeparment(sysOrg.getParentId(), sysOrg.getId(), orgs, orgIdMap, orgMap);
            }
        }
        for (SysOrg sysOrg : sysOrgsDataBase) {
            if (SysOrg.TYPE_COMPANY.equals(sysOrg.getType()) &&
                sysOrg.getParentId().equals(SysOrg.TYPE_ROOT)) {
                orgIdMap.put(sysOrg.getOrgCde(), sysOrg.getId());
                orgMap.put(sysOrg.getOrgCde(), sysOrg);
                findDeparment(sysOrg.getId(), sysOrg.getId(), sysOrgsDataBase, orgIdMap, orgMap);
            } else {
                orgIdMap.put(sysOrg.getParentId() + sysOrg.getOrgCde(), sysOrg.getId());
                orgMap.put(sysOrg.getParentId() + sysOrg.getOrgCde(), sysOrg);
                findDeparment(sysOrg.getParentId(), sysOrg.getId(), sysOrgsDataBase, orgIdMap, orgMap);
            }
        }

        // 文件里的数据和数据库里的比较查重
        for (SysUser sysUser : excleUserList) {
            repeatUser.put(sysUser.getUserCde(), sysUser);

            // 设置公司id
            SysOrg sysOrg = orgMap.get(sysUser.getCompany());
            if (StringUtil.isNotEmpty(orgIdMap.get(sysUser.getCompany())) &&
                SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                sysUser.setOrgId(orgIdMap.get(sysUser.getCompany()));

                // 设置部门id
                SysOrg orgDepartment = orgMap.get(sysUser.getOrgId() + sysUser.getDepartment());
                String department = orgIdMap.get(sysUser.getOrgId() + sysUser.getDepartment());
                if (StringUtil.isNotEmpty(department) &&
                    SysOrg.TYPE_DEPARTMENT.equals(orgDepartment.getType())) {
                    sysUser.setDepartment(department);
                    String postName = sysUser.getPosition();
                    String postId = postMap.get(postName);
                    if (StringUtil.isNotEmpty(postId)) {
                        sysUser.setPosition(postId);
                    } else {

                        // 如果职务匹配不到,则新建
                        SysPost sysPost = this.postService.createPostByName(postName);
                        postMap.put(sysPost.getPostName(), sysPost.getId());
                        sysUser.setPosition(sysPost.getId());
                        sysPosts.add(sysPost);
                    }

                    // 给前台导入人看的信息
                    SysUser msgSysUser = new SysUser();
                    msgSysUser.setUserCde(sysUser.getUserCde());
                    msgSysUser.setUserName(sysUser.getUserName());
                    msgSysUser.setCompany(sysOrg.getOrgName());
                    msgSysUser.setDepartment(orgDepartment.getOrgName());
                    msgSysUser.setPosition(postName);
                    msgUsers.add(msgSysUser);
                } else {

                    // 没找到组织id则不插入
                    removeUserCdes.add(sysUser.getUserCde());
                }
            } else {

                // 没找到组织id则不插入
                removeUserCdes.add(sysUser.getUserCde());
            }
        }
        for (SysUser sysUser : sysUsersDataBases) {
            if (repeatUser.containsKey(sysUser.getUserCde())) {

                // 重复的用户code
                repeatUserCdes.add(sysUser.getUserCde());
                updateUserCdes.add(repeatUser.get(sysUser.getUserCde()));
            } else {
                repeatUser.put(sysUser.getUserCde(), sysUser);
            }
        }

        // 取出和数据相同用户
        if (repeatUserCdes.size() > 0) {
            for (String cde : repeatUserCdes) {
                for (int i = 0; i < excleUserList.size(); i++) {
                    if (cde.equals(excleUserList.get(i).getUserCde())) {
                        removeIndexs.add(i);
                    }
                }
                for (int index = removeIndexs.size() - 1; index >= 0; index--) {
                    excleUserList.remove((int) removeIndexs.get(index));
                }
                for (int i = 0; i < msgUsers.size(); i++) {
                    if (cde.equals(msgUsers.get(i).getUserCde())) {
                        msgUsers.remove(i);
                    }
                }
                removeIndexs.clear();
            }
        }

        // 移除信息错误的用户
        if (removeUserCdes.size() > 0) {
            for (String cde : removeUserCdes) {
                for (int i = 0; i < excleUserList.size(); i++) {
                    if (cde.equals(excleUserList.get(i).getUserCde())) {
                        removeIndexs.add(i);
                    }
                }
                for (int index = removeIndexs.size() - 1; index >= 0; index--) {
                    excleUserList.remove((int) removeIndexs.get(index));
                }
                removeIndexs.clear();

                // 移除updateUserCdes,既然移除,则也不是更新的用户
                for (int i = updateUserCdes.size() - 1; i >= 0; i--) {
                    if (cde.equals(updateUserCdes.get(i).getUserCde())) {
                        updateUserCdes.remove(i);
                    }
                }
            }
        }
    }

    private void findDeparment(String rootCompany, String parentId, List<SysOrg> orgs,
        Map<String, String> orgIdMap, Map<String, SysOrg> orgMap) {

        for (SysOrg sysOrg : orgs) {
            if (parentId.equals(sysOrg.getParentId())) {

                // 子公司
                if (SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                    orgIdMap.put(sysOrg.getOrgCde(), sysOrg.getId());
                    orgMap.put(sysOrg.getOrgCde(), sysOrg);
                    findDeparment(sysOrg.getId(), sysOrg.getId(), orgs, orgIdMap, orgMap);
                } else {
                    orgIdMap.put(rootCompany + sysOrg.getOrgCde(), sysOrg.getId());
                    orgMap.put(rootCompany + sysOrg.getOrgCde(), sysOrg);
                    findDeparment(rootCompany, sysOrg.getId(), orgs, orgIdMap, orgMap);
                }
            }
        }
    }

    /**
     * 组织与数据库数据查重
     *
     * @param excleOrgList 模版文件结果集
     * @param orgList      数据库结果集
     */
    private Set<SysOrg> checkRsMapOrg(List<SysOrg> excleOrgList, List<SysOrg> orgList) throws Exception {

        // 组织名重复集合
        Map<String, Integer> repeatOrg = new HashMap<>();
        List<String> repeatOrgNames = new ArrayList<>();

        // 记录重复数据下标,移除excleOrgList准备
        Set<SysOrg> removeIndexs = new HashSet<>();

        Map<String, String> dataBaseOrgIdMap = new HashMap<>();
        Map<String, List<Integer>> orgParentIdMap = new HashMap<>(excleOrgList.size());
        Map<String, List<SysOrg>> orgParentMap = new HashMap<>(excleOrgList.size());

        // 文件里的数据和数据库里的比较查重
        for (int i = 0; i < excleOrgList.size(); i++) {
            SysOrg sysOrg = excleOrgList.get(i);
            repeatOrg.put(sysOrg.getOrgName().trim(), 1);
            List<Integer> indexs = orgParentIdMap.get(sysOrg.getParentId());
            List<SysOrg> indexOrgs = orgParentMap.get(sysOrg.getParentId());
            if (null == indexs) {
                indexs = new ArrayList<>();
                orgParentIdMap.put(sysOrg.getParentId(), indexs);

                indexOrgs = new ArrayList<>();
                orgParentMap.put(sysOrg.getParentId(), indexOrgs);
            }
            indexs.add(i);
            indexOrgs.add(sysOrg);
        }

        // 循环数据库里的数据
        for (SysOrg sysOrg : orgList) {
            String orgName = sysOrg.getOrgName().trim();
            dataBaseOrgIdMap.put(orgName, sysOrg.getId());
            if (repeatOrg.containsKey(orgName) && SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                repeatOrgNames.add(orgName);
            } else {
                repeatOrg.put(orgName, 1);
            }
        }
        if (repeatOrgNames.isEmpty()) {
            return removeIndexs;
        }

        // 取出和数据相同组织
        for (String name : repeatOrgNames) {
            for (int i = 0; i < excleOrgList.size(); i++) {
                SysOrg sysOrg = excleOrgList.get(i);
                if (removeIndexs.contains(sysOrg)) {
                    continue;
                }

                // 查重比较公司的名字,部门不排除
                if (name.equals(sysOrg.getOrgName()) && SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                    removeIndexs.add(sysOrg);

                    // 把公司下的子节点也排除掉
                    removeDept(sysOrg.getId(), orgParentIdMap, removeIndexs, orgParentMap);
                    break;
                }
            }
        }
        excleOrgList.removeAll(removeIndexs);

        return removeIndexs;
    }

    private void removeDept(String pId, Map<String, List<Integer>> orgParentIdMap,
        Set<SysOrg> removeIndexs, Map<String, List<SysOrg>> orgParentMap) throws Exception{

        if (StringUtil.isEmpty(pId)) {
            return;
        }
        if (orgParentIdMap.isEmpty()) {
            return;
        }
        List<Integer> indexs = orgParentIdMap.get(pId);
        if (null == indexs) {
            return;
        }
        List<SysOrg> sysOrgs = orgParentMap.get(pId);
        removeIndexs.addAll(sysOrgs);
        for (SysOrg sysOrg : sysOrgs) {
            removeDept(sysOrg.getId(), orgParentIdMap, removeIndexs, orgParentMap);
        }
    }

    /**
     * 根据微信id查询用户信息
     *
     * @param unionid
     * @return
     * @throws Exception
     */
    @Override
    public SysUser queryUserByUnionid(String unionid) throws Exception {
        return userDao.queryUserByUnionid(unionid);
    }

    /**
     * 根据第三方用户编码或id查询用户信息
     *
     * @param thirdPartyCode
     * @return
     * @throws Exception
     */
    @Override
    public SysUser queryUserByThirdPartyCode(String thirdPartyCode) throws Exception {
        return userDao.queryUserByThirdPartyCode(thirdPartyCode);
    }

    /**
     * 查询角色所能控制的用户集合
     *
     * @param roleIds 角色id集合
     * @throws Exception
     */
    @Override
    public List<SysUser> querySysUsersByRoleIds(List<String> roleIds) throws Exception {

        return userDao.querySysUsersByRoleIds(roleIds);
    }

    /**
     * 查询全部用户
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<SysUser> queryAllSysUsers() throws Exception {

        return userDao.queryUserList();
    }

    /**
     * 公司或部门下用户集合
     *
     * @param orgId   组织id
     * @param orgType 组织类型
     * @return
     * @throws Exception
     */
    @Override
    public List<SysUser> queryUsersByOrgIdAndType(String orgId, String orgType) throws Exception {
        List<SysUser> users = new ArrayList<>();

        // 超级管理员
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            if (SysOrg.TYPE_DEPARTMENT.equals(orgType)) {
                users = userDao.querySysUsersByDepartmentOrgId(orgId);
            } else {
                users = userDao.querySysUsersByOrgId(orgId);
            }
        } else {
            if (SysOrg.TYPE_DEPARTMENT.equals(orgType)) {
                users = userDao.queryUserListByDepartmentOrgId(UserUtils.getUser().getId(), orgId);
            } else {
                users = userDao.queryUserList(UserUtils.getUser().getId(), orgId);
            }
        }
        return users;
    }

    /**
     * 查询组织下所有用户列表
     * @param roleSelectOrg
     * @return
     * @throws Exception
     */
    @Override
    public List<SysUser> allUsers(RoleSelectOrg roleSelectOrg) throws Exception {

        String search = "%" + roleSelectOrg.getSearch() + "%";
        List<SysUser> allUser = new ArrayList<>();
        List<String> companys = new ArrayList<>();
        List<String> departments = new ArrayList<>();

        // 根据组织类型分为2份数据查询
        if (null != roleSelectOrg.getOrg() &&
            roleSelectOrg.getOrg().size() > 0) {
            roleSelectOrg.getOrg().forEach((allUserListParent) -> {
                if (SysOrg.TYPE_DEPARTMENT.equals(allUserListParent.getType())) {
                    departments.add(allUserListParent.getOrgId());
                } else {
                    companys.add(allUserListParent.getOrgId());
                }
            });

            // 测试,in时必须有1个元素
            if (departments.size() == 0) {
                departments.add("");
            }
            if (companys.size() == 0) {
                companys.add("");
            }

            // 管理员登陆
            if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
                allUser = userDao.querySysUsersByOrgId(search, departments, companys);
            } else {
                allUser = userDao.queryUserList(
                    search,
                    UserUtils.getUser().getId(),
                    departments,
                    companys
                );
            }
        } else {

            // 不传组织的时候, 返回全部用户
            allUser = userDao.querySysUsers(search);
            if (!AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {

                /*
                防止 in (userIds) 不同数据库有限制, 用户超过1000,sql报错;
                把所有用户都查询出来, 循环过滤
                 */
                allUser = allUser.stream().filter(
                    usr -> UserUtils.getUserIdPermissionList().contains(usr.getId())
                ).collect(
                    Collectors.toList()
                );
            }
        }
        return allUser;
    }

    @Override
    public List<SysUser> deptIdUsers(RoleSelectOrg roleSelectOrg) throws Exception {

        List<AllUserListParent> org = roleSelectOrg.getOrg();
        if (null != org &&
            !org.isEmpty()) {
            org.removeIf(o -> SysOrg.TYPE_COMPANY.equals(o.getType()));
        }
        return allUsers(roleSelectOrg);
    }

    /**
     * 根据用户id修改组织id
     * @param rUserJson
     * @throws Exception
     */
    @Override
	public void updateUserOrgIdByUserId(UserDetailedJson rUserJson) throws Exception {
		 userDao.updateUserOrgIdByUserId(rUserJson.getId(),rUserJson.getOrgId(),rUserJson.getCreateUser(),new Date());
	}

	/**
     * 根据用户id修改部门id
     * @param rUserJson
     * @throws Exception
     */
    @Override
	public void updateUserDeptIdByUserId(UserDetailedJson rUserJson) throws Exception {
		userDao.updateUserDeptIdByUserId(rUserJson.getId(),rUserJson.getDepartment(),rUserJson.getCreateUser(),new Date());
	}

    /**
     * 根据勾选的组织部门，查询下属配置过资源密码的用户列表
     * @param roleSelectOrg
     * @throws Exception
     */
    @Override
    public List<SysUser> queryIsBrowseUsers(RoleSelectOrg roleSelectOrg) throws Exception {
        String search = "%" + roleSelectOrg.getSearch() + "%";
        List<SysUser> allUser = new ArrayList<>();
        List<String> companys = new ArrayList<>();
        List<String> departments = new ArrayList<>();

        // 根据组织类型分为2份数据查询
        if (null != roleSelectOrg.getOrg() &&
                roleSelectOrg.getOrg().size() > 0) {
            roleSelectOrg.getOrg().forEach((allUserListParent) -> {
                if (SysOrg.TYPE_DEPARTMENT.equals(allUserListParent.getType())) {
                    departments.add(allUserListParent.getOrgId());
                } else {
                    companys.add(allUserListParent.getOrgId());
                }
            });

            // 测试,in时必须有1个元素
            if (departments.size() == 0) {
                departments.add("");
            }
            if (companys.size() == 0) {
                companys.add("");
            }

            // 管理员登陆
            if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
                allUser = userDao.queryIsBrowseSysUsersByOrgId(search, departments, companys);
            } else {
                allUser = userDao.queryIsBrowseUserList(
                        search,
                        UserUtils.getUser().getId(),
                        departments,
                        companys
                );
            }
        } else {

            // 不传组织的时候, 返回全部用户
            allUser = userDao.queryIsBrowseSysUsers(search);
            if (!AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {

                /*
                防止 in (userIds) 不同数据库有限制, 用户超过1000,sql报错;
                把所有用户都查询出来, 循环过滤
                 */
                allUser = allUser.stream().filter(
                        usr -> UserUtils.getUserIdPermissionList().contains(usr.getId())
                ).collect(
                        Collectors.toList()
                );
            }
        }
        allUser.removeIf(user -> AdministratorUtils.getAdministratorName().equals(user.getUserCde()));
        return allUser;
    }

    /**
     * 管理员重置用户的访问资源密码，用户id集合，状态改为未修改过默认的访问密码
     * @param rUserJson
     * @throws Exception
     */
    @Override
    public void updateResetIsBrowsePassword(UserDetailedJson rUserJson) throws Exception {
        userDao.updateResetIsBrowsePassword(rUserJson.getIds(),rUserJson.getBrowsePassword());
    }

    /**
     * 根据企业微信userid查询是否存在
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public SysUser queryUserByWxWorkUserId(String userId) throws Exception {
        return userDao.queryUserByWxWorkUserId(userId);
    }

    /**
     * 根据userid更新企业微信userid
     * @param userId
     * @param wxWorkUserId
     * @throws Exception
     */
    @Override
    public void updateUserWxWorkUserIdByUserId(String userId, String wxWorkUserId) throws Exception {
        userDao.updateUserWxWorkUserIdByUserId(userId,wxWorkUserId,wxWorkUserId,new Date());
    }

    /**
     * 根据userid更新第三方用户编码
     * @param userId
     * @param thirdPartyCode
     * @throws Exception
     */
    @Override
    public void updateUserThirdPartyCodeByUserId(String userId, String thirdPartyCode) throws Exception {
        userDao.updateUserThirdPartyCodeByUserId(userId,thirdPartyCode,new Date());
    }

}
