package com.rolldata.web.system.service.impl;

import com.rolldata.core.util.AdministratorUtils;
import com.rolldata.core.util.BrowserUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.common.enums.TreeNodeType;
import com.rolldata.web.common.pojo.TreeNode;
import com.rolldata.web.common.pojo.tree.ResponseTree;
import com.rolldata.web.system.dao.*;
import com.rolldata.web.system.entity.*;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.service.FunctionService;
import com.rolldata.web.system.service.RoleService;
import com.rolldata.web.system.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Title: FunctionServiceImpl
 * @Description:功能的实现类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 */
@Service("functionService")
@Transactional
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionDao functionDao;

	@Autowired
	private SysFunctionOperDao sysFunctionOperDao;

	@Autowired
	private SysRolePowerDao sysRolePowerDao;

	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

    @Autowired
	private SysPostMenuDao sysPostMenuDao;

    @Autowired
    private SysPostPowerDao sysPostPowerDao;

	@Autowired
	private RoleService roleService;

	/**
	 * 功能列表(全部一级菜单)
	 *
	 * @return
	 * @throws Exception
	 */
	public List<SysFunction> getFirstFunction() throws Exception {

		List<SysFunction> list = functionDao.findChildren(SysFunction.ROOT);
		return list;
	}

	/**
	 * 获取可用的菜单
	 *
	 * @param pageJson 分页信息
	 * @throws Exception
	 */
	@Override
	public void getAvailableFunList(PageJson pageJson) throws Exception {

//		Sort sort = new Sort("sort");
//		Pageable pageable = new PageRequest(pageJson.getPageable(), pageJson.getSize(), sort);
		Pageable pageable = PageRequest.of(pageJson.getPageable(), pageJson.getSize());
		Specification specification = new Specification<SysFunction>() {
			/**
			 * Creates a WHERE clause for a query of the referenced entity in form of a
			 * {@link Predicate} for the given {@link Root} and {@link CriteriaQuery}.
			 *
			 * @param root
			 * @param query
			 * @param cb
			 * @return a {@link Predicate}, must not be {@literal null}.
			 */
			@Override
			public Predicate toPredicate(Root<SysFunction> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(cb.equal(root.get("parentId").as(String.class), "0"));
				list.add(cb.equal(root.get("state").as(String.class), SysFunction.STATUS_NORMAL));
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
		Page<SysFunction> page = functionDao.findAll(specification, pageable);
		pageJson.setResult(queryFuncTreeList(page, false));
		pageJson.setTotalPagets(page.getTotalPages());
	}

	/**
	 * 查菜单层级
	 *
	 * @param page jpa提供的分页对象
	 * @param flag 是否 查全部
	 * @return
	 * @throws Exception
	 */
	public List queryFuncTreeList(Page page, Boolean flag) throws Exception {

		List<FunctionList> result = new ArrayList<FunctionList>();
		List<SysFunction> rootList = page.getContent();
		for (SysFunction sysFunction : rootList) {
			FunctionList rootResult = new FunctionList();
			rootResult.setId(sysFunction.getId());
			rootResult.setFuncName(sysFunction.getFuncName());
			rootResult.setFunctionState(sysFunction.getState());
			rootResult.setHrefLink(sysFunction.getHrefLink());
			rootResult.setType(sysFunction.getType());
			rootResult.setRelationId(sysFunction.getRelationId());
			rootResult.setIsSystem(sysFunction.getIsSystem());
			List<SysFunction> childrenList;
			if (flag) {
				childrenList = functionDao.findChildren(sysFunction.getId());
			} else {
				childrenList = functionDao.findAvailableChildren(sysFunction.getId(), SysFunction.STATUS_NORMAL);
			}

			if (null != childrenList && childrenList.size() > 0) {
				List<FunctionList> resultChildren = new ArrayList<FunctionList>();
				for (SysFunction childrenSysFuncton : childrenList) {
					FunctionList childrenResult = new FunctionList();
					childrenResult.setId(childrenSysFuncton.getId());
					childrenResult.setHrefLink(childrenSysFuncton.getHrefLink());
					childrenResult.setFunctionState(childrenSysFuncton.getState());
					childrenResult.setFuncName(childrenSysFuncton.getFuncName());
					childrenResult.setType(childrenSysFuncton.getType());
					childrenResult.setRelationId(childrenSysFuncton.getRelationId());
					childrenResult.setIsSystem(childrenSysFuncton.getIsSystem());
					findChildren(childrenSysFuncton, childrenResult, flag);
					resultChildren.add(childrenResult);
				}
				rootResult.setChildren(resultChildren);
			}
			result.add(rootResult);
		}
		return result;
	}

	/**
	 *
	 * 递归查菜单子级
	 *
	 * @param sysFunction SysFunction实体
	 * @param result      返回前台的json实体
	 * @param flag        是否查全部菜单
	 * @throws Exception
	 */
	private void findChildren(SysFunction sysFunction, FunctionList result, Boolean flag) throws Exception {

		List<SysFunction> childrenList;
		if (flag) {
			childrenList = functionDao.findChildren(sysFunction.getId());
		} else {
			childrenList = functionDao.findAvailableChildren(sysFunction.getId(), SysFunction.STATUS_NORMAL);

		}
		if (null != childrenList && childrenList.size() > 0) {
			List<FunctionList> resultChildren = new ArrayList<FunctionList>();
			for (SysFunction childrenSysFuncton : childrenList) {
				FunctionList childrenResult = new FunctionList();
				childrenResult.setId(childrenSysFuncton.getId());
				childrenResult.setHrefLink(childrenSysFuncton.getHrefLink());
				childrenResult.setFunctionState(childrenSysFuncton.getState());
				childrenResult.setFuncName(childrenSysFuncton.getFuncName());
				childrenResult.setType(childrenSysFuncton.getType());
				childrenResult.setRelationId(childrenSysFuncton.getRelationId());
				childrenResult.setIsSystem(childrenSysFuncton.getIsSystem());
				findChildren(childrenSysFuncton, childrenResult, flag);
				resultChildren.add(childrenResult);
			}
			result.setChildren(resultChildren);
		}
	}

	/**
	 * 根据角色id查寻菜单
	 *
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Result getRoleHaveMenu(String roleId) throws Exception {

		/* all function data */
		List<SysFunction> rootList = this.getFirstFunction();

		/* Already existing function and function_oper */
		List alreadyExistFuncList = sysRolePowerDao.findRolePower(roleId, SysRolePower.TYPE_FUN);
		List alreadyExistOpercList = sysRolePowerDao.findRolePower(roleId, SysRolePower.TYPE_OPER);
		if (null == alreadyExistFuncList) {
			alreadyExistFuncList = new ArrayList();
		}
		if (null == alreadyExistOpercList) {
			alreadyExistOpercList = new ArrayList();
		}
		return compent(rootList, alreadyExistFuncList, alreadyExistOpercList);
	}

    /**
     * 根据用户id 查菜单
     *
     * @param userId   用户id
     * @param position 职位id
     * @return
     * @throws Exception
     */
	@Override
	public List<SysFunction> findFuncByUserId(String userId, String position) throws Exception {

        List<SysFunction> allFunction = new ArrayList<>();
        List<SysFunction> result = new LinkedList<>();
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
			result = functionDao.findFunc();
		} else {
			List<SysFunction> roleFunction = functionDao.findFuncByUserId(userId);
			List<SysFunction> postFunction = functionDao.findPostFuncByUserId(
					StringUtil.isEmpty(position) ? "" : position
			);
			allFunction.addAll(roleFunction);
			allFunction.addAll(postFunction);
			orderFunc(allFunction);

			Set<String> funIds = new HashSet<>();

			// 由于角色和职务查询没放一起,存在重复的菜单
			for (SysFunction sysFunction : allFunction) {
				if (!funIds.contains(sysFunction.getId())) {
					result.add(sysFunction);
				}
				funIds.add(sysFunction.getId());
			}
		}
        return result;
	}

	/**
	 * 排序菜单
	 *
	 * @param functions 全部菜单集合
	 * @return
	 */
	public static List<SysFunction> orderFunc(List<SysFunction> functions) {

		Collections.sort(functions, (func1, func2) -> {
			return func1.getSort() - func2.getSort();
		});
		return functions;
	}

	/**
	 * 用户拥有的菜单
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public MainFunctionTreeParent findHierFuncByUserId(HttpServletRequest request, String parentId,
													   String userId, String isAdmin) throws Exception {

		MainFunctionTreeParent mainTreeParent = new MainFunctionTreeParent();
		List<MainFunctionTree> mainFunctionTrees = new ArrayList<MainFunctionTree>();
        List<SysFunction> rootList = new ArrayList<>();
//        List<SysFunction> result = UserUtils.getFunctionList();
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            rootList = functionDao.findChildren(parentId,isAdmin);
        } else {
            rootList = getMainFunc(request, parentId, userId,isAdmin);
        }
        extractMainTree(rootList, mainFunctionTrees, userId, request,isAdmin);
        mainTreeParent.setTreeNodes(mainFunctionTrees);
		return mainTreeParent;
	}

	/**
	 * 查用户拥有的子菜单
	 *
	 * @param parentId     菜单的父id
	 * @param mainFunctionTree 封装对象
	 * @param userId       用户id(如果为空则全查)
	 * @throws Exception
	 */
	private void findChildHierFuncByUserId(HttpServletRequest request, String parentId,
            MainFunctionTree mainFunctionTree, String userId) throws Exception {

		List<MainFunctionTree> mainFunctionTrees = new ArrayList<MainFunctionTree>();
		List<SysFunction> childrenList = new ArrayList<SysFunction>();
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde()) ||
            StringUtil.isEmpty(userId)) {
            childrenList = functionDao.findChildren(parentId);
        } else {
            childrenList = getMainFunc(request, parentId, userId);
        }
		extractMainTree(childrenList, mainFunctionTrees, userId, request);
		if (childrenList.size() > 0) {
			mainFunctionTree.setChildren(mainFunctionTrees);
		}
	}

	/**
	 * 查用户拥有的子菜单
	 *
	 * @param parentId     菜单的父id
	 * @param mainFunctionTree 封装对象
	 * @param userId       用户id(如果为空则全查)
	 * @param isAdmin 是否管理端
	 * @throws Exception
	 */
	private void findChildHierFuncByUserId(HttpServletRequest request, String parentId,
										   MainFunctionTree mainFunctionTree, String userId,String isAdmin) throws Exception {

		List<MainFunctionTree> mainFunctionTrees = new ArrayList<MainFunctionTree>();
		List<SysFunction> childrenList = new ArrayList<SysFunction>();
		if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde()) ||
				StringUtil.isEmpty(userId)) {
			childrenList = functionDao.findChildren(parentId,isAdmin);
		} else {
			childrenList = getMainFunc(request, parentId, userId,isAdmin);
		}
		extractMainTree(childrenList, mainFunctionTrees, userId, request,isAdmin);
		if (childrenList.size() > 0) {
			mainFunctionTree.setChildren(mainFunctionTrees);
		}
	}

	/**
	 * 抽取菜单递归时循环的方法
	 *
	 * @param childrenList
	 * @param mainFunctionTrees
	 * @param userId
	 * @throws Exception
	 */
	private void extractMainTree(List<SysFunction> childrenList, List<MainFunctionTree> mainFunctionTrees,
								 String userId, HttpServletRequest request) throws Exception {

		for (SysFunction childrenSysFunction : childrenList) {
			MainFunctionTree childrenMainFunctionTree = new MainFunctionTree();
			childrenMainFunctionTree.setId(childrenSysFunction.getId());
			childrenMainFunctionTree.setFuncId(childrenSysFunction.getId());
			childrenMainFunctionTree.setHrefLink(childrenSysFunction.getHrefLink());
			childrenMainFunctionTree.setName(childrenSysFunction.getFuncName());
			childrenMainFunctionTree.setType(TreeNodeType.FUNC);
			childrenMainFunctionTree.setHrefType(childrenSysFunction.getType());
			childrenMainFunctionTree.setpId(childrenSysFunction.getParentId());
			childrenMainFunctionTree.setIsSystem(childrenSysFunction.getIsSystem());
			childrenMainFunctionTree.setIconSkin(childrenSysFunction.getIconClass());
			// 不是根目录再递归
			if (!childrenSysFunction.getParentId().equals(SysFunction.ROOT)) {
				findChildHierFuncByUserId(request, childrenSysFunction.getId(), childrenMainFunctionTree, userId);
			}
			mainFunctionTrees.add(childrenMainFunctionTree);
		}
	}

	/**
	 * 抽取菜单递归时循环的方法
	 *
	 * @param childrenList
	 * @param mainFunctionTrees
	 * @param userId
	 * @param isAdmin 是否管理端
	 * @throws Exception
	 */
	private void extractMainTree(List<SysFunction> childrenList, List<MainFunctionTree> mainFunctionTrees,
								 String userId, HttpServletRequest request,String isAdmin) throws Exception {

		for (SysFunction childrenSysFunction : childrenList) {
			MainFunctionTree childrenMainFunctionTree = new MainFunctionTree();
			childrenMainFunctionTree.setId(childrenSysFunction.getId());
			childrenMainFunctionTree.setFuncId(childrenSysFunction.getId());
			childrenMainFunctionTree.setHrefLink(childrenSysFunction.getHrefLink());
			childrenMainFunctionTree.setName(childrenSysFunction.getFuncName());
			childrenMainFunctionTree.setType(TreeNodeType.FUNC);
			childrenMainFunctionTree.setHrefType(childrenSysFunction.getType());
			childrenMainFunctionTree.setpId(childrenSysFunction.getParentId());
			childrenMainFunctionTree.setIsSystem(childrenSysFunction.getIsSystem());
			childrenMainFunctionTree.setIconSkin(childrenSysFunction.getIconClass());
			// 不是根目录再递归
			if (!childrenSysFunction.getParentId().equals(SysFunction.ROOT)) {
				findChildHierFuncByUserId(request, childrenSysFunction.getId(), childrenMainFunctionTree, userId,isAdmin);
			}
			mainFunctionTrees.add(childrenMainFunctionTree);
		}
	}
    /**
     * 根据终端,获取角色菜单
     *
     * @param request  请求
     * @param parentId 菜单父id
     * @param userId   用户id
     * @return
     * @throws Exception
     */
    private List<SysFunction> getMainFunc(HttpServletRequest request, String parentId, String userId) throws Exception {

        List<SysFunction> rootList = new ArrayList<>();
        String postId = UserUtils.getUser().getPosition();
        if (BrowserUtils.isMobile(request)) {

            // mobile
            rootList = functionDao.findTerminalMobileOwnFuncByUserId(parentId, userId, postId);
        } else if (BrowserUtils.isIpad(request)) {

            // ipad
            rootList = functionDao.findTerminalIpadOwnFuncByUserId(parentId, userId, postId);
        } else {

            // PC
            rootList = functionDao.findTerminalPCOwnFuncByUserId(parentId, userId, postId);
        }
        return rootList;
    }

	/**
	 * 根据终端,获取角色菜单
	 *
	 * @param request  请求
	 * @param parentId 菜单父id
	 * @param userId   用户id
	 * @param isAdmin 是否管理端
	 * @return
	 * @throws Exception
	 */
	private List<SysFunction> getMainFunc(HttpServletRequest request, String parentId, String userId,String isAdmin) throws Exception {

		List<SysFunction> rootList = new ArrayList<>();
		String postId = UserUtils.getUser().getPosition();
		if (BrowserUtils.isMobile(request)) {

			// mobile
			rootList = functionDao.findTerminalMobileOwnFuncByUserId(parentId, userId, postId);
		} else if (BrowserUtils.isIpad(request)) {

			// ipad
			rootList = functionDao.findTerminalIpadOwnFuncByUserId(parentId, userId, postId);
		} else {

			// PC
			rootList = functionDao.findTerminalPCOwnFuncByUserId(parentId, userId, postId, isAdmin);
		}
		return rootList;
	}

	/**
	 * 比较 (为了前台选中效果)
	 *
	 * @param rootList              一级结果集
	 * @param alreadyExistFuncList  已经存在的菜单
	 * @param alreadyExistOpercList 已经存在的按钮
	 * @return
	 * @throws Exception
	 */
	private Result compent(List<SysFunction> rootList, List<SysRolePower> alreadyExistFuncList,
						   List alreadyExistOpercList) throws Exception {

		List<FunctionList> result = new ArrayList();
		for (SysFunction sysFunction : rootList) {
			FunctionList functionList = new FunctionList();
			functionList.setFuncName(sysFunction.getFuncName());
			functionList.setFunctionState(sysFunction.getState());
			functionList.setId(sysFunction.getId());
			setSysRolePower(sysFunction, alreadyExistOpercList, functionList);
			findChildrenAndCompent(sysFunction, functionList, alreadyExistFuncList, alreadyExistOpercList, true);
			if (alreadyExistFuncList.contains(sysFunction.getId())) {
				functionList.setChecked(Boolean.TRUE);
			}
			result.add(functionList);
		}
		return new Result(result);
	}

	/**
	 * 递归比较
	 *
	 * @param sysFunction
	 * @param result
	 * @param alreadyExistList
	 * @param alreadyExistOpercList
	 * @param flag
	 * @throws Exception
	 */
	private void findChildrenAndCompent(SysFunction sysFunction, FunctionList result,
										List<SysRolePower> alreadyExistList, List alreadyExistOpercList, Boolean flag) throws Exception {

		List<SysFunction> childrenList;
		if (flag) {
			childrenList = functionDao.findChildren(sysFunction.getId());
		} else {
			childrenList = functionDao.findAvailableChildren(sysFunction.getId(), SysFunction.STATUS_NORMAL);
		}
		if (null != childrenList && childrenList.size() > 0) {
			List<FunctionList> resultChildren = new ArrayList<FunctionList>();
			for (SysFunction childrenSysFuncton : childrenList) {
				FunctionList childrenResult = new FunctionList();
				childrenResult.setId(childrenSysFuncton.getId());
				childrenResult.setHrefLink(childrenSysFuncton.getHrefLink());
				childrenResult.setFunctionState(childrenSysFuncton.getState());
				childrenResult.setFuncName(childrenSysFuncton.getFuncName());
				if (alreadyExistList.contains(childrenSysFuncton.getId())) {
					childrenResult.setChecked(Boolean.TRUE);
				}
				setSysRolePower(childrenSysFuncton, alreadyExistOpercList, childrenResult);
				findChildrenAndCompent(childrenSysFuncton, childrenResult, alreadyExistList, alreadyExistOpercList,
						flag);
				resultChildren.add(childrenResult);
			}
			result.setChildren(resultChildren);
		}
	}

	/**
	 * 如果是超链,并且是系统菜单, 给菜单下set按钮
	 *
	 * @param sysFunction
	 * @param alreadyExistOpercList 已经存在的按钮
	 * @param functionList
	 * @throws Exception
	 */
	private void setSysRolePower(SysFunction sysFunction, List alreadyExistOpercList, FunctionList functionList)
			throws Exception {

		if (SysFunction.IS_SYSTEM.equals(sysFunction.getIsSystem())) {
			List<ButtonPower> buttonPowerList = new ArrayList();
			List<SysFunctionOper> sysFunctionOperList = sysFunctionOperDao
					.findSysFunctionOperByFuncId(sysFunction.getId());
			for (SysFunctionOper sysFunctionOper : sysFunctionOperList) {
				ButtonPower buttonPower = new ButtonPower();
				buttonPower.setButtonPowerId(sysFunctionOper.getId());
				buttonPower.setName(sysFunctionOper.getOperName());
				if (alreadyExistOpercList.contains(sysFunctionOper.getId())) {
					buttonPower.setChecked(Boolean.TRUE);
				}
				buttonPowerList.add(buttonPower);
			}
			functionList.setHaveButton(Boolean.TRUE);
			functionList.setButtonChildren(buttonPowerList);
		}
	}

	/**
	 * 拼菜单
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<FunctionList> getAllAndButton() throws Exception {

		List<FunctionList> sysFunctionList = new ArrayList<FunctionList>();
		List<SysFunction> rootList = functionDao.findChildren(SysFunction.ROOT);
		for (SysFunction sysFunction : rootList) {
			FunctionList function = new FunctionList();
			function.setFuncName(sysFunction.getFuncName());
			function.setHrefLink(sysFunction.getHrefLink());
			sysFunctionList.add(function);
			getChildAllAndButton(sysFunctionList, function, sysFunction.getId());
		}
		return sysFunctionList;
	}

	/**
	 * 递归给菜单拼方法名称
	 *
	 * @param sysFunctionList 拼接的集合
	 * @param parentFunction  FunctionList对象
	 * @param id              菜单id
	 * @throws Exception
	 */
	private void getChildAllAndButton(List<FunctionList> sysFunctionList, FunctionList parentFunction, String id)
			throws Exception {

		List<SysFunction> childList = functionDao.findChildren(id);
		for (SysFunction childSysFunction : childList) {
			FunctionList function = new FunctionList();
			function.setFuncName(parentFunction.getFuncName() + "-" + childSysFunction.getFuncName());
			function.setHrefLink(childSysFunction.getHrefLink());
			sysFunctionList.add(function);
			getChildAllAndButton(sysFunctionList, function, childSysFunction.getId());
			setHref(sysFunctionList, function, childSysFunction);
		}
	}

	/**
	 * 给菜单追加方法
	 *
	 * @param sysFunctionList
	 * @param parentFunction
	 * @param childSysFunction
	 * @throws Exception
	 */
	private void setHref(List<FunctionList> sysFunctionList, FunctionList parentFunction, SysFunction childSysFunction)
			throws Exception {

		if (SysFunction.IS_SYSTEM.equals(childSysFunction.getIsSystem())) {
			List<SysFunctionOper> sysFunctionOperList = sysFunctionOperDao
					.findSysFunctionOperByFuncId(childSysFunction.getId());
			for (SysFunctionOper sysFunctionOper : sysFunctionOperList) {
				FunctionList function = new FunctionList();

				// wd_sys_function_oper 表里的权限标识用冒号分割的
				String[] buttonHrefs = sysFunctionOper.getPowerFlag().split(":");
				String[] functionHrefs = parentFunction.getHrefLink().split("/");
				String functionHref = functionHrefs[functionHrefs.length - 1];
				function.setFuncName(parentFunction.getFuncName() + "-" + sysFunctionOper.getOperName());
				function.setHrefLink(
						parentFunction.getHrefLink().replace(functionHref, "") + buttonHrefs[buttonHrefs.length - 1]);
				sysFunctionList.add(function);
			}
		}
	}

	/**
	 * 菜单功能权限树(子节点形式数据)
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
    public Map findRoleHaveMenu() throws Exception {

		Map map = new HashMap();
        List<SysFuncButtons> buttons = new ArrayList<>();
        MainFunctionTreeParent MainFunctionTreeParent = new MainFunctionTreeParent();
        List<MainFunctionTree> treeNodes = new ArrayList<MainFunctionTree>();

        //菜单数据
        List<SysFunction> allFunctionList = AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())
                                  ? functionDao.findAdminSysFunctionOrderBySort()
                                  : functionDao.findSysFunctionOrderBySort(UserUtils.getUser().getId());
		//按钮数据
		List<SysFunctionOper> allFunctionPowerList = AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())
				? sysFunctionOperDao.queryAdminSysFunctionOperList()
				: sysFunctionOperDao.findFuncnOperByUserId(UserUtils.getUser().getId());


		/*admin all function data*/
        for (SysFunction sysFunction : allFunctionList) {
            if (SysFunction.ROOT.equals(sysFunction.getParentId())) {
                MainFunctionTree treeNode = new MainFunctionTree();
                treeNode.setId(sysFunction.getId());
                treeNode.setpId(sysFunction.getParentId());
                treeNode.setFuncId(sysFunction.getId());
                treeNode.setName(sysFunction.getFuncName());
                treeNode.setType(TreeNodeType.FUNC);
                addCompentTreeNode(treeNode, allFunctionList);
                treeNodes.add(treeNode);
            }
        }
        MainFunctionTreeParent.setCheck(true);
        MainFunctionTreeParent.setTreeNodes(treeNodes);

        // 菜单列表
        if (null != allFunctionPowerList) {
            Map<String, List<SysFunctionOper>> funcButtonsMap = new HashMap<>();
            for (SysFunctionOper sysFunctionOper : allFunctionPowerList) {
                if (funcButtonsMap.containsKey(sysFunctionOper.getFuncId())) {
                    List<SysFunctionOper> oldSysFuncButtons = funcButtonsMap.get(sysFunctionOper.getFuncId());
                    SysFunctionOper functionOper = new SysFunctionOper();
                    functionOper.setId(sysFunctionOper.getId());
                    functionOper.setOperName(sysFunctionOper.getOperName());
                    oldSysFuncButtons.add(functionOper);
                } else {
                    List<SysFunctionOper> sysFuncButtonsList = new ArrayList<>();

                    // 不让前台发生歧义,还是new个对象
                    SysFunctionOper functionOper = new SysFunctionOper();
                    functionOper.setId(sysFunctionOper.getId());
                    functionOper.setOperName(sysFunctionOper.getOperName());
                    sysFuncButtonsList.add(functionOper);
                    funcButtonsMap.put(sysFunctionOper.getFuncId(), sysFuncButtonsList);
                }
            }
            Iterator<String> iterator = funcButtonsMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                SysFuncButtons funcButtons = new SysFuncButtons();
                funcButtons.setFuncId(key);
                funcButtons.setButtons(funcButtonsMap.get(key));
                buttons.add(funcButtons);
            }
        }
        map.put("funcTree", MainFunctionTreeParent);
        map.put("buttons", buttons);
        return map;
    }

	/**
	 * 比较
	 *
	 * @param rootList  一级结果集
	 * @param treeNodes 封装json的对象集合
	 * @throws Exception
	 */
	private void compentTreeNode(List<SysFunction> rootList, List<MainFunctionTree> treeNodes) throws Exception {

		for (SysFunction sysFunction : rootList) {
			MainFunctionTree treeNode = new MainFunctionTree();
			treeNode.setId(sysFunction.getId());
			treeNode.setpId(sysFunction.getParentId());
			treeNode.setFuncId(sysFunction.getId());
			treeNode.setName(sysFunction.getFuncName());

			// 状态是0, 禁用 状态是禁用的不用查出来(暂时保留)
			if (SysFunction.STATUS_DELETE.equals(sysFunction.getState())) {
				treeNode.setNocheck(true);
			}
			setRolePowerTreeNode(sysFunction, treeNodes);
			treeNode.setType(TreeNodeType.FUNC);
			treeNodes.add(treeNode);
		}
	}

	/**
	 * 如果是超链,并且是系统菜单, 给菜单下set按钮
	 *
	 * @param sysFunction
	 * @param treeNodes 封装json的对象集合
	 * @throws Exception
	 */
	private void setRolePowerTreeNode(SysFunction sysFunction, List<MainFunctionTree> treeNodes) throws Exception {
		if (SysFunction.IS_SYSTEM.equals(sysFunction.getIsSystem())) {
			List<SysFunctionOper> sysFunctionOperList = sysFunctionOperDao
					.findSysFunctionOperByFuncId(sysFunction.getId());
			for (SysFunctionOper sysFunctionOper : sysFunctionOperList) {
				MainFunctionTree treeNode = new MainFunctionTree();
				treeNode.setType(TreeNodeType.OPER);
				treeNode.setId(sysFunctionOper.getId());
				treeNode.setpId(sysFunction.getId());
				treeNode.setName(sysFunctionOper.getOperName());
				treeNode.setFuncId(sysFunctionOper.getId());
				treeNodes.add(treeNode);
			}
		}
	}

    /**
     * 递归添加子节点
     * @param treeNodeParent 父节点
     * @param allList 全部数据集合
     * @throws Exception
     */
    private void addCompentTreeNode(MainFunctionTree treeNodeParent, List<SysFunction> allList) throws Exception {

        List<MainFunctionTree> mainFunctionTreeList = new ArrayList<MainFunctionTree>();
        for (SysFunction sysFunction : allList) {
            if (treeNodeParent.getId().equals(sysFunction.getParentId())) {
                MainFunctionTree treeNode = new MainFunctionTree();
                treeNode.setId(sysFunction.getId());
                treeNode.setpId(sysFunction.getParentId());
                treeNode.setFuncId(sysFunction.getId());
                treeNode.setName(sysFunction.getFuncName());
                treeNode.setType(TreeNodeType.FUNC);
                addCompentTreeNode(treeNode, allList);
                mainFunctionTreeList.add(treeNode);
            }
        }
        if (mainFunctionTreeList.size() > 0) {
            treeNodeParent.setChildren(mainFunctionTreeList);
        }
    }

    /**
     * 查询目录结构树
     *
     * @param request 请求
     * @return
     * @throws Exception
     */
	@Override
	public List<SysMenus> queryMenuTree(HttpServletRequest request) throws Exception {

        List<SysFunction> queryAllFunctions = new ArrayList<>();
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            queryAllFunctions = functionDao.queryAllMenus(SysFunction.NOT_IS_SYSTEM);
        } else {

            // 职务id
            String postId = UserUtils.getUser().getPosition();
            if (BrowserUtils.isMobile(request)) {

                // mobile
                queryAllFunctions = functionDao.queryTerminalMobileAllMenus(
                    SysFunction.NOT_IS_SYSTEM,
                    UserUtils.getUser().getId(),
                    StringUtil.isEmpty(postId) ? "" : postId
                );
            } else if (BrowserUtils.isIpad(request)) {

                // ipad
                queryAllFunctions = functionDao.queryTerminalIpadAllMenus(
                    SysFunction.NOT_IS_SYSTEM,
                    UserUtils.getUser().getId(),
                    StringUtil.isEmpty(postId) ? "" : postId
                );
            } else {

                // PC
                queryAllFunctions = functionDao.queryTerminalPCAllMenus(
                    SysFunction.NOT_IS_SYSTEM,
                    UserUtils.getUser().getId(),
                    StringUtil.isEmpty(postId) ? "" : postId
                );
            }
        }
		List<SysMenus> allMenus = new ArrayList<>();
		if (queryAllFunctions.size() > 0) {
			for (int i = 0; i < queryAllFunctions.size(); i++) {
                SysFunction sysFunction = queryAllFunctions.get(i);
                if (!TreeNode.ROOT.equals(sysFunction.getParentId())) {
                    continue;
                }
                SysMenus sysMenus = newSysMenus(sysFunction);
                sysMenus.setTerminalPC(sysFunction.getTerminalPC());
                sysMenus.setTerminalIpad(sysFunction.getTerminalIpad());
                sysMenus.setTerminalMobile(sysFunction.getTerminalMobile());
				allMenus.add(sysMenus);
			}
		}
		return allMenus;
	}

    /**
     * 目录管理(不带终端控制)
     *
     * @return
     * @throws Exception
     */
    @Override
	public List<SysMenus> queryMenuManageTree() throws Exception {

        List<SysFunction> queryAllFunctions = new ArrayList<>();
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            queryAllFunctions = functionDao.queryAllMenus(SysFunction.NOT_IS_SYSTEM);
        } else {

            // 职务id
            String postId = UserUtils.getUser().getPosition();
            queryAllFunctions = functionDao.queryMenuManageTree(
                SysFunction.NOT_IS_SYSTEM,
                UserUtils.getUser().getId(),
                StringUtil.isEmpty(postId) ? "" : postId
            );
        }
        SysMenus sysMenus = new SysMenus();
        sysMenus.setId(TreeNode.ROOT);
        recursionMenu(sysMenus, queryAllFunctions);
        return sysMenus.getChildren();
    }

    private void recursionMenu(SysMenus parentMenu,
        List<SysFunction> queryAllFunctions) throws Exception {

        List<SysMenus> childMenus = new ArrayList<>();
        for (int i = 0; i < queryAllFunctions.size(); i++) {
            SysFunction sysFunction = queryAllFunctions.get(i);
            if (parentMenu.getId().equals(sysFunction.getParentId())) {
                SysMenus sysMenus = newSysMenus(sysFunction);
                sysMenus.setTerminalPC(sysFunction.getTerminalPC());
                sysMenus.setTerminalIpad(sysFunction.getTerminalIpad());
                sysMenus.setTerminalMobile(sysFunction.getTerminalMobile());
                childMenus.add(sysMenus);
                recursionMenu(sysMenus, queryAllFunctions);
            }
        }
        parentMenu.setChildren(childMenus);
    }

    /**
	 * 查询单个目录详细
	 *
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysMenus queryMenuInfo(String menuId) throws Exception {
		SysFunction sysFunction = functionDao.queryMenuInfo(menuId);
		Objects.requireNonNull(sysFunction, "未查到菜单信息");
		SysMenus sysMenus = newSysMenus(sysFunction);

		sysMenus.setTerminalPC(sysFunction.getTerminalPC());
		sysMenus.setTerminalIpad(sysFunction.getTerminalIpad());
		sysMenus.setTerminalMobile(sysFunction.getTerminalMobile());
		return sysMenus;
	}

	/**
	 * 保存单个目录信息
     * <p>更新：2021-10-20 目录改造（目录为树形，资源不变）
     * <br>目录改为树形,资源只挂非目录类型,前台可控制,后台先不动,传啥存啥(万一要改回来很快)。
     * </p>
	 *
	 * @param sysMenu
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysMenus saveMenuInfo(SysMenus sysMenu) throws Exception {

        StringUtil.requireNonNull(sysMenu.getParentId(), "父节点不能为空");
        if (!TreeNode.ROOT.equals(sysMenu.getParentId())) {

            // 校验父id是否存在
            SysFunction parentFunc = this.queryFuncById(sysMenu.getParentId());
            Objects.requireNonNull(parentFunc, "父级目录不存在");
        }
        SysFunction sysFunction = new SysFunction();
        sysFunction.setFuncName(sysMenu.getName());
        sysFunction.setHrefLink("resourceController/queryResourceTreeByMenuId");

        // 序号自增，获取非系统类型中最大的序号
        int sort = functionDao.queryFunctionMaxSort(SysFunction.NOT_IS_SYSTEM);
        if (sort == 0) {
            sort += 3;//前面有3个固定的
        }
        sysFunction.setSort(sort + 1);
        sysFunction.setParentId(sysMenu.getParentId());
        sysFunction.setType(SysFunction.TYPE_TREE);
        sysFunction.setIsSystem(SysFunction.NOT_IS_SYSTEM);
        sysFunction.setState(SysFunction.STATUS_NORMAL);
        SysUser user = UserUtils.getUser();
        sysFunction.setCreateUser(user.getId());
        sysFunction.setCreateTime(new Date());
        sysFunction.setTerminalPC(sysMenu.getTerminalPC());
        sysFunction.setTerminalIpad(sysMenu.getTerminalIpad());
        sysFunction.setTerminalMobile(sysMenu.getTerminalMobile());
        sysFunction.setBusinessType(sysMenu.getBusinessType());
        sysFunction.setIsAdmin(SysFunction.NOT_IS_ADMIN);
        if(StringUtil.isNotEmpty(sysMenu.getWdModelId())){
			sysFunction.setWdModelId(sysMenu.getWdModelId());
		}
        sysFunction = functionDao.saveAndFlush(sysFunction);
		sysMenu.setId(sysFunction.getId());//返回id前台默认选中用
		sysMenu.setHrefLink(sysFunction.getHrefLink());
        List<String> roleIds = roleService.getRoleList();
        List<String> userRoleIds = UserUtils.getRolesIdList();

        // 新增目录分配管理员角色
        for (int i = 0; i < roleIds.size(); i++) {
            SysRolePower sysRolePower = new SysRolePower();
            sysRolePower.setRoleId(roleIds.get(i));
            sysRolePower.setPowerType(SysRolePower.TYPE_FUN);
            sysRolePower.setPowerId(sysFunction.getId());
            sysRolePowerDao.saveAndFlush(sysRolePower);

            // 目录下资源也分配角色
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRelationId(sysFunction.getId());
            sysRoleMenu.setType("0");
            sysRoleMenu.setRoleId(roleIds.get(i));
            sysRoleMenu.setCreateTime(new Date());
            sysRoleMenu.setCreateUser(user.getId());
            sysRoleMenuDao.saveAndFlush(sysRoleMenu);
        }
        if (userRoleIds.isEmpty()) {
            SysPostPower sysPostPower = new SysPostPower();
            sysPostPower.setPostId(user.getPosition());
            sysPostPower.setPowerType(SysPostPower.TYPE_FUN);
            sysPostPower.setPowerId(sysFunction.getId());
            sysPostPowerDao.saveAndFlush(sysPostPower);

            SysPostMenu sysPostMenu = new SysPostMenu();
            sysPostMenu.setPostId(user.getPosition());
            sysPostMenu.setType("0");
            sysPostMenu.setCreateUser(user.getId());
            sysPostMenu.setCreateTime(new Date());
            sysPostMenu.setRelationId(sysFunction.getId());
            sysPostMenuDao.saveAndFlush(sysPostMenu);
        }
        return sysMenu;
    }

	/**
	 * 修改单个目录信息
	 *
	 * @param sysMenu
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateMenuInfo(SysMenus sysMenu) throws Exception {

        SysUser user = UserUtils.getUser();
        functionDao.updateMenuById(
            sysMenu.getId(),
            sysMenu.getName(),
            sysMenu.getTerminalPC(),
            sysMenu.getTerminalIpad(),
            sysMenu.getTerminalMobile(),
            sysMenu.getBusinessType(),
            new Date(),
            user.getId()
        );

        // 包含管理员角色
        List<String> roleIds = roleService.getRoleList();

        // 当前登录人的角色id集合
        List<String> userRoleIds = UserUtils.getRolesIdList();

    }


    /**
	 * 删除单个目录
	 *
	 * @param id
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public void deleteMenuInfo(String id) throws Exception {

        // 查询所有需要删除的节点
        functionDao.deleteSysFunctionById(id);

        // 同时删除资源关联，角色关联
        sysRolePowerDao.deleteSysRolePowerByPowerId(id);
        sysPostPowerDao.deleteSysPostPowerByPowerId(id);

        sysRoleMenuDao.deleteByResourceId(id);
        sysPostMenuDao.deleteByResourceId(id);

        // 角色资源/职务资源 表 暂时没删除。

        // 删除子目录
        List<SysFunction> children = functionDao.findChildren(id);
        if (!children.isEmpty()) {
            for (SysFunction childFunc : children) {

                // 目录应该不太多,先递归查库试试
                deleteMenuInfo(childFunc.getId());
            }
        }
    }

	/**
	 * 根据名称查询结果集
	 *
	 * @param sysMenuReq
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysFunction> queryFunctionObjByName(SysMenus sysMenuReq) throws Exception {
		return functionDao.queryFunctionObjByName(sysMenuReq.getName());
	}

	/**
	 * 根据名称查询结果集(排除自己)
	 *
	 * @param sysMenuReq
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysFunction> queryFunctionObjByNameAndNotOwn(SysMenus sysMenuReq) throws Exception {
		return functionDao.queryFunctionObjByNameAndNotOwn(sysMenuReq.getName(), sysMenuReq.getId());
	}

    /**
     * 查询角色配置目录结构树
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<SysMenus> queryRoleMenuTree () throws Exception {

        List<SysFunction> sysFunctions = getSysFunctions();

        // 不想要
        SysMenus rootMenus = new SysMenus();
        rootMenus.setId(TreeNode.ROOT);
        SysUser user = UserUtils.getUser();
        mountResMenuTree(rootMenus, sysFunctions);
        return rootMenus.getChildren();
    }

    private void mountResMenuTree(SysMenus parentMenu, List<SysFunction> sysFunctions) throws Exception {

        if (null == parentMenu){
            return;
        }
        List<SysMenus> childMenus = new ArrayList<>();
        for (SysFunction func : sysFunctions) {
            if (parentMenu.getId().equals(func.getParentId())) {
                SysMenus sysMenus = newSysMenus(func);
                mountResMenuTree(sysMenus, sysFunctions);
                childMenus.add(sysMenus);
            }
        }
        if (!childMenus.isEmpty()) {
            parentMenu.setChildren(childMenus);

        }
    }

    private List<SysFunction> getSysFunctions() throws Exception {
        List<SysFunction> sysFunctions = new ArrayList<>();
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            sysFunctions = functionDao.queryAllMenus(SysFunction.NOT_IS_SYSTEM);
        } else {
            String postId = UserUtils.getUser().getPosition();

            // 角色管理这还是查询全部
            sysFunctions = functionDao.queryRoleAllMenus(
                SysFunction.NOT_IS_SYSTEM,
                UserUtils.getUser().getId(),
                StringUtil.isEmpty(postId) ? "" : postId
            );
        }
        return sysFunctions;
    }

    private SysMenus newSysMenus(SysFunction sysFunction) {
        SysMenus sysMenus = new SysMenus(
            sysFunction.getId(),
            sysFunction.getParentId(),
            sysFunction.getFuncName(),
            sysFunction.getHrefLink(),
            sysFunction.getType(),
            StringUtil.isEmpty(sysFunction.getBusinessType()) ? "0" : sysFunction.getBusinessType()
        );
        return sysMenus;
    }

	/**
	 * 交换目录顺序
	 * @param sysMenuReq
	 * @throws Exception
	 */
	@Override
	public void updateExchangeOrder(SysMenus sysMenuReq) throws Exception {
		SysFunction sysFunction = functionDao.queryMenuInfo(sysMenuReq.getId());
		SysFunction toSysFunction = functionDao.queryMenuInfo(sysMenuReq.getToId());
		functionDao.updateSortById(sysFunction.getId(),toSysFunction.getSort(),UserUtils.getUser().getId(),new Date());
		functionDao.updateSortById(toSysFunction.getId(),sysFunction.getSort(),UserUtils.getUser().getId(),new Date());
	}

    /**
     * 菜单下按钮列表
     *
     * @param funcId 菜单id
     * @return
     * @throws Exception
     */
    @Override
    public List<MainFunctionTree> queryFuncButton(String funcId) throws Exception {

        //按钮数据
        List<MainFunctionTree> buttonListTree = new ArrayList<MainFunctionTree>();
        List<SysFunctionOper> allFunctionPowerList = null;
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            allFunctionPowerList = sysFunctionOperDao.queryAdminSysFunctionOperList(funcId);
        } else {
            allFunctionPowerList = sysFunctionOperDao.findFuncnOperByUserId(funcId, UserUtils.getUser().getId());
        }
        if (null != allFunctionPowerList && allFunctionPowerList.size() > 0) {
            for (SysFunctionOper sysFunctionOper : allFunctionPowerList) {
                MainFunctionTree buttonTree = new MainFunctionTree();
                buttonTree.setType(TreeNodeType.OPER);
                buttonTree.setId(sysFunctionOper.getId());
                buttonTree.setpId(funcId);
                buttonTree.setName(sysFunctionOper.getOperName());
                buttonTree.setFuncId(funcId);
                buttonListTree.add(buttonTree);
            }
        }
        return buttonListTree;
    }

	/**
	 * 用户登录后拥有的全部菜单(树形结构)
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<FunctionResourcePojo> queryUserAllAvailableTree(HttpServletRequest request) throws Exception {
		List<SysFunction> sysAllFunctions = UserUtils.getFunctionList();
		queryUserAllAvailable(sysAllFunctions, request);
		List<FunctionResourcePojo> mainFunctionTrees= new ArrayList<FunctionResourcePojo>();
		for (SysFunction childrenSysFunction : sysAllFunctions) {
			if(childrenSysFunction.getParentId().equals(SysFunction.ROOT)){
				FunctionResourcePojo childrenMainFunctionTree = new FunctionResourcePojo();
				childrenMainFunctionTree.setId(childrenSysFunction.getId());
				childrenMainFunctionTree.setFuncId(childrenSysFunction.getId());
				childrenMainFunctionTree.setHrefLink(childrenSysFunction.getHrefLink());
				childrenMainFunctionTree.setName(childrenSysFunction.getFuncName());
				childrenMainFunctionTree.setType(TreeNodeType.FUNC.toString());
				childrenMainFunctionTree.setHrefType(childrenSysFunction.getType());
				childrenMainFunctionTree.setPId(childrenSysFunction.getParentId());
				childrenMainFunctionTree.setIsSystem(childrenSysFunction.getIsSystem());
				childrenMainFunctionTree.setIconSkin(childrenSysFunction.getIconClass());
				if(childrenSysFunction.getIsSystem().equals(SysFunction.NOT_IS_SYSTEM)){
				}else{
					childrenMainFunctionTree.setChildren(dealChildFunction( childrenSysFunction.getId(),sysAllFunctions ));
				}
				mainFunctionTrees.add(childrenMainFunctionTree);
			}
		}
		return mainFunctionTrees;
	}

	private List<SysFunction> queryUserAllAvailable(List<SysFunction> functionList, HttpServletRequest request) throws Exception {

		if (null == functionList) {
			return Collections.emptyList();
		}
		if (BrowserUtils.isMobile(request)) {

			// mobile
			functionList.removeIf(func -> !"1".equals(func.getTerminalMobile()));
		} else if (BrowserUtils.isIpad(request)) {

			// ipad
			functionList.removeIf(func -> !"1".equals(func.getTerminalIpad()));
		} else {

			// PC
			functionList.removeIf(func -> "0".equals(func.getTerminalPC()));
		}
		return functionList;
	}

	private List<FunctionResourcePojo> dealChildFunction(String parentId, List<SysFunction> sysAllFunctions) {
		List<FunctionResourcePojo> mainFunctionTrees= new ArrayList<FunctionResourcePojo>();
		for (SysFunction childrenSysFunction : sysAllFunctions) {
			if(childrenSysFunction.getParentId().equals(parentId)){
				FunctionResourcePojo childrenMainFunctionTree = new FunctionResourcePojo();
				childrenMainFunctionTree.setId(childrenSysFunction.getId());
				childrenMainFunctionTree.setFuncId(childrenSysFunction.getId());
				childrenMainFunctionTree.setHrefLink(childrenSysFunction.getHrefLink());
				childrenMainFunctionTree.setName(childrenSysFunction.getFuncName());
				childrenMainFunctionTree.setType(TreeNodeType.FUNC.toString());
				childrenMainFunctionTree.setHrefType(childrenSysFunction.getType());
				childrenMainFunctionTree.setPId(childrenSysFunction.getParentId());
				childrenMainFunctionTree.setIsSystem(childrenSysFunction.getIsSystem());
				childrenMainFunctionTree.setIconSkin(childrenSysFunction.getIconClass());
				childrenMainFunctionTree.setChildren(dealChildFunction( childrenSysFunction.getId(),sysAllFunctions ));
				mainFunctionTrees.add(childrenMainFunctionTree);
			}
		}
		return mainFunctionTrees;
	}

	/**
     * 目录管理(不带终端控制，无权限)
     *
     * @return
     * @throws Exception
     */
    @Override
	public List<SysMenus> queryMenuManageTreeNoPermission() throws Exception {

        List<SysFunction> queryAllFunctions = functionDao.queryAllMenus(SysFunction.NOT_IS_SYSTEM);
        List<SysMenus> allMenus = new ArrayList<SysMenus>();
        if (queryAllFunctions.size() > 0) {
            for (int i = 0; i < queryAllFunctions.size(); i++) {
                SysFunction sysFunction = queryAllFunctions.get(i);
                SysMenus sysMenus = newSysMenus(sysFunction);
                sysMenus.setTerminalPC(sysFunction.getTerminalPC());
                sysMenus.setTerminalIpad(sysFunction.getTerminalIpad());
                sysMenus.setTerminalMobile(sysFunction.getTerminalMobile());
                allMenus.add(sysMenus);
            }
        }
        return allMenus;
    }

	/***
	 * 根据用户id查询管理控制台目录节点
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public MainFunctionTreeParent findConsoleFuncByUserId(HttpServletRequest request, String userId) throws Exception {
		MainFunctionTreeParent mainTreeParent = new MainFunctionTreeParent();
		List<MainFunctionTree> mainFunctionTrees = new ArrayList<MainFunctionTree>();
		List<SysFunction> rootList = new ArrayList<>();
//        List<SysFunction> result = UserUtils.getFunctionList();
		if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
			rootList = functionDao.findChildren(SysFunction.ROOT,SysFunction.IS_ADMIN);
		} else {
			rootList = getMainFunc(request, SysFunction.ROOT, userId,SysFunction.IS_ADMIN);
		}
		extractMainTreeToConsole(rootList, mainFunctionTrees, userId, request,SysFunction.IS_ADMIN);
		mainTreeParent.setTreeNodes(mainFunctionTrees);
		return mainTreeParent;
	}

    /**
     * 根据id查询菜单
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public SysFunction queryFuncById(String id) throws Exception {

        return this.functionDao.queryMenuInfo(id);
    }

	/**
	 * 抽取菜单递归时循环的方法
	 *
	 * @param childrenList
	 * @param mainFunctionTrees
	 * @param userId
	 * @param isAdmin 是否管理端
	 * @throws Exception
	 */
	private void extractMainTreeToConsole(List<SysFunction> childrenList, List<MainFunctionTree> mainFunctionTrees,
										  String userId, HttpServletRequest request,String isAdmin) throws Exception {

		for (SysFunction childrenSysFunction : childrenList) {
			MainFunctionTree childrenMainFunctionTree = new MainFunctionTree();
			childrenMainFunctionTree.setId(childrenSysFunction.getId());
			childrenMainFunctionTree.setFuncId(childrenSysFunction.getId());
			childrenMainFunctionTree.setHrefLink(childrenSysFunction.getHrefLink());
			childrenMainFunctionTree.setName(childrenSysFunction.getFuncName());
			childrenMainFunctionTree.setType(TreeNodeType.FUNC);
			childrenMainFunctionTree.setHrefType(childrenSysFunction.getType());
			childrenMainFunctionTree.setpId(childrenSysFunction.getParentId());
			childrenMainFunctionTree.setIsSystem(childrenSysFunction.getIsSystem());
			childrenMainFunctionTree.setIconSkin(childrenSysFunction.getIconClass());
			// 是根目录再递归
			if (childrenSysFunction.getParentId().equals(SysFunction.ROOT)) {
				findChildHierFuncByUserId(request, childrenSysFunction.getId(), childrenMainFunctionTree, userId,isAdmin);
			}
			mainFunctionTrees.add(childrenMainFunctionTree);
		}
	}

	/**
	 * 财务管理和指标管理页获取左侧菜单（排除根目录）
	 * @param request
	 * @param parentId
	 * @param userId
	 * @param isAdmin
	 * @return
	 */
	@Override
	public MainFunctionTreeParent findHierFuncByUserIdAndParam(HttpServletRequest request, String parentId, String userId, String isAdmin) throws Exception {
		MainFunctionTreeParent mainTreeParent = new MainFunctionTreeParent();
		List<MainFunctionTree> mainFunctionTrees = new ArrayList<MainFunctionTree>();
		List<SysFunction> rootList = new ArrayList<>();
//        List<SysFunction> result = UserUtils.getFunctionList();
		if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
			rootList = functionDao.findChildren(parentId,isAdmin);
		} else {
			rootList = getMainFunc(request, parentId, userId,isAdmin);
		}
		for (SysFunction sysFunction : rootList) {
			// 是根目录再递归
			if (sysFunction.getParentId().equals(SysFunction.ROOT)) {
				List<SysFunction> childrenList = new ArrayList<SysFunction>();
				if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde()) ||
						StringUtil.isEmpty(userId)) {
					childrenList = functionDao.findChildren(sysFunction.getId(),isAdmin);
				} else {
					childrenList = getMainFunc(request, sysFunction.getId(), userId,isAdmin);
				}
				extractMainTree(childrenList, mainFunctionTrees, userId, request,isAdmin);
			}
		}
		mainTreeParent.setTreeNodes(mainFunctionTrees);
		return mainTreeParent;
	}

	/**
	 * 用户拥有的菜单（含主目录+递归）
	 * @param request
	 * @param parentId
	 * @param userId
	 * @param isAdmin
	 * @return
	 * @throws Exception
	 */
	@Override
	public MainFunctionTreeParent findHierFuncByUserIdToRe(HttpServletRequest request, String parentId,
														   String userId, String isAdmin) throws Exception {
//		long startTime = System.currentTimeMillis();
		//门户主目录处理
		MainFunctionTreeParent mainTreeParent = new MainFunctionTreeParent();
		List<MainFunctionTree> mainFunctionTrees = new ArrayList<MainFunctionTree>();
		List<SysFunction> rootList = new ArrayList<>();
		if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
			rootList = functionDao.findChildren(parentId,isAdmin);
		} else {
			rootList = getMainFunc(request, parentId, userId,isAdmin);
		}
		extractMainTreeToRe(rootList, mainFunctionTrees, userId, request,isAdmin);
		//查询拥有权限的目录存入map
		List<SysFunction> allFunctionList = getSysFunctions();
		Map<String,SysFunction> allFunctionMap = new HashMap<String,SysFunction>();
		for (int i = 0; i < allFunctionList.size(); i++) {
			SysFunction sysFunction = allFunctionList.get(i);
			allFunctionMap.put(sysFunction.getId(),sysFunction);
		}
		mainTreeParent.setTreeNodes(mainFunctionTrees);
//		System.out.println("----用时------" + (System.currentTimeMillis() - startTime) + " ms.");
		return mainTreeParent;
	}

	/**
	 * 用户拥有的菜单（含主目录+递归）
	 *
	 * @param request
	 * @param parentId
	 * @param userId
	 * @param isAdmin
	 * @return
	 * @throws Exception
	 */
	@Override
	public MainFunctionTreeParent queryMiraclePortalMenus(HttpServletRequest request, String parentId, String userId, String isAdmin) throws Exception {

		long startTime = System.currentTimeMillis();

		MainFunctionTreeParent mainTreeParent = new MainFunctionTreeParent();
		List<MainFunctionTree> mainFunctionTrees = new ArrayList<>();
		List<SysFunction> rootList = new ArrayList<>();
		if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
			rootList = functionDao.findChildren(parentId,SysFunction.IS_Portal);
		} else {
			rootList = getMainFunc(request, parentId, userId,SysFunction.IS_Portal);
		}
		rootList.removeIf(s -> "运维管理".equals(s.getFuncName()));
		extractMainTreeToRe(rootList, mainFunctionTrees, userId, request,SysFunction.IS_Portal);

		if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
			rootList = functionDao.findChildren(parentId,isAdmin);
		} else {
			rootList = getMainFunc(request, parentId, userId,isAdmin);
		}
		extractMainTreeToRe(rootList, mainFunctionTrees, userId, request,isAdmin);

		//查询拥有权限的目录存入map
		List<SysFunction> allFunctionList = getSysFunctions();
		Map<String,SysFunction> allFunctionMap = new HashMap<>();
		for (int i = 0; i < allFunctionList.size(); i++) {
			SysFunction sysFunction = allFunctionList.get(i);
			allFunctionMap.put(sysFunction.getId(),sysFunction);
		}

		System.out.println("----用时------" + (System.currentTimeMillis() - startTime) + " ms.");
		mainTreeParent.setTreeNodes(mainFunctionTrees);
		return mainTreeParent;
	}

	private ResponseTree createNode(String name, String treeId, String url, String type,
        String rsId) {

        ResponseTree tree = new ResponseTree();
        tree.setName(name);
        tree.setId(treeId);
        tree.setRsUrl(url);
        tree.setType(type);
        tree.setRsid(rsId);
        tree.setRsPath(name);
        tree.setLevel(String.valueOf(1));
        return tree;
    }

	@Override
	public List<SysMenus> queryBusinessMenu(HttpServletRequest request) throws Exception {

		List<SysFunction> mainFunc = getMainFunc(request, SysFunction.ROOT, UserUtils.getUser().getId(), SysFunction.NOT_IS_ADMIN);
		List<SysMenus> sysMenus = new ArrayList<>(mainFunc.size());
		for (SysFunction func : mainFunc) {
			if ("1".equals(func.getBusinessType())) {
				SysMenus sysMenu = newSysMenus(func);
				sysMenus.add(sysMenu);
			}
		}
		return sysMenus;
	}

	private MainFunctionTree changeToFunctionTree(SysFunction tempFunc,String hrefLink) {
		MainFunctionTree tempMainFunc = new MainFunctionTree();
		tempMainFunc.setFuncId(tempFunc.getId());
		tempMainFunc.setId(tempFunc.getId());
		tempMainFunc.setHrefLink(hrefLink);
		tempMainFunc.setName(tempFunc.getFuncName());
		tempMainFunc.setHrefType(tempFunc.getType());
		tempMainFunc.setType(TreeNodeType.FUNC);
		tempMainFunc.setIsSystem(tempFunc.getIsSystem());
		tempMainFunc.setSort(tempFunc.getSort());
		return tempMainFunc;
	}

	private void dealFuncParent(String tempFuncId, List<String> biMenuIds, Map<String, SysFunction> allFunctionMap) {
		SysFunction tempFunc = allFunctionMap.get(tempFuncId);
		if(StringUtil.isNotEmpty(tempFunc)) {
			if (tempFunc.getParentId().equals("0")) {
				if (!biMenuIds.contains(tempFuncId)) {
					biMenuIds.add(tempFuncId);
				}
			} else {
				dealFuncParent(tempFunc.getParentId(), biMenuIds, allFunctionMap);
			}
		}
	}

	/**
	 * 抽取菜单递归时循环的方法
	 *
	 * @param childrenList
	 * @param mainFunctionTrees
	 * @param userId
	 * @param isAdmin 是否管理端
	 * @throws Exception
	 */
	private void extractMainTreeToRe(List<SysFunction> childrenList, List<MainFunctionTree> mainFunctionTrees,
									 String userId, HttpServletRequest request,String isAdmin) throws Exception {

		for (SysFunction childrenSysFunction : childrenList) {
			MainFunctionTree childrenMainFunctionTree = new MainFunctionTree();
			childrenMainFunctionTree.setId(childrenSysFunction.getId());
			childrenMainFunctionTree.setFuncId(childrenSysFunction.getId());
			childrenMainFunctionTree.setHrefLink(childrenSysFunction.getHrefLink());
			childrenMainFunctionTree.setName(childrenSysFunction.getFuncName());
			childrenMainFunctionTree.setType(TreeNodeType.FUNC);
			childrenMainFunctionTree.setHrefType(childrenSysFunction.getType());
			childrenMainFunctionTree.setpId(childrenSysFunction.getParentId());
			childrenMainFunctionTree.setIsSystem(childrenSysFunction.getIsSystem());
			childrenMainFunctionTree.setIconSkin(childrenSysFunction.getIconClass());
			// 全部递归
			findChildHierFuncByUserIdToRe(request, childrenSysFunction.getId(), childrenMainFunctionTree, userId,isAdmin);
			mainFunctionTrees.add(childrenMainFunctionTree);
		}
	}

	/**
	 * 查用户拥有的子菜单
	 *
	 * @param parentId     菜单的父id
	 * @param mainFunctionTree 封装对象
	 * @param userId       用户id(如果为空则全查)
	 * @param isAdmin 是否管理端
	 * @throws Exception
	 */
	private void findChildHierFuncByUserIdToRe(HttpServletRequest request, String parentId,
											   MainFunctionTree mainFunctionTree, String userId,String isAdmin) throws Exception {

		List<MainFunctionTree> mainFunctionTrees = new ArrayList<MainFunctionTree>();
		List<SysFunction> childrenList = new ArrayList<SysFunction>();
		if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde()) ||
				StringUtil.isEmpty(userId)) {
			childrenList = functionDao.findChildren(parentId,isAdmin);
		} else {
			childrenList = getMainFunc(request, parentId, userId,isAdmin);
		}
		extractMainTreeToRe(childrenList, mainFunctionTrees, userId, request,isAdmin);
		if (childrenList.size() > 0) {
			mainFunctionTree.setChildren(mainFunctionTrees);
		}
	}
}
