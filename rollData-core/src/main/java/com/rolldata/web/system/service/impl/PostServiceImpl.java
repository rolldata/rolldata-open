package com.rolldata.web.system.service.impl;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.AdministratorUtils;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.common.enums.TreeNodeType;
import com.rolldata.web.common.pojo.TreeNode;
import com.rolldata.web.system.dao.*;
import com.rolldata.web.system.entity.SysPost;
import com.rolldata.web.system.entity.SysPostMenu;
import com.rolldata.web.system.entity.SysPostPower;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.service.PostService;
import com.rolldata.web.system.service.SysUserOrgService;
import com.rolldata.web.system.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title:PostServiceImpl
 * @Description:职务业务包操作接口实现
 * @Company:www.wrenchdata.com
 * @author: shenshilong
 * @date: 2019-12-02
 * @version: V1.0
 */
@Service("postService")
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private SysPostMenuDao postMenuDao;

    @Autowired
    private SysPostPowerDao postPowerDao;

    @Autowired
    private SysPostUserDao postUserDao;

    @Autowired
    private SysRolePowerDao sysRolePowerDao;

    @Autowired
    private SysUserOrgService sysUserOrgService;

    private EntityManagerFactory emf;

    // 使用这个标记来注入EntityManagerFactory
    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * 保存前校验名称代码是否重复
     *
     * @param ajax     前台数据容器
     * @param postName 职务名称
     * @param postCode 职务代码
     * @param id       职务id-新建时传null
     * @throws Exception
     */
    @Override
    public void before(AjaxJson ajax, String postName, String postCode, String id) throws Exception {

        SysPost sysPost = null;
        sysPost = postDao.querySysPostByPostCode(postCode);
        if (null != sysPost) {
            if (!sysPost.getId().equals(id)) {
                ajax.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessageOrSelf("common.sys.code.exis", postCode));
            }
        }
        sysPost = postDao.querySysPostByPostName(postName);
        if (null != sysPost) {
            if (!sysPost.getId().equals(id)) {
                ajax.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessageOrSelf("common.sys.code.exis", postName));
            }
        }
    }

    /**
     * 创建职务
     *
     * @param sysPostDetailedInfo 前台传递的职务信息容器
     * @return
     * @throws Exception
     */
    @Override
    public SysPostTreeResponse createPost(SysPostDetailedInfo sysPostDetailedInfo) throws Exception {

        SysPostTreeResponse postTreeResponse = new SysPostTreeResponse();
        List<SysPostTree> treeNodes = new ArrayList<SysPostTree>();
        SysPostTree postTree = new SysPostTree();
        SysPost post = new SysPost();
        post.setPostName(sysPostDetailedInfo.getPostName()).setPostCode(sysPostDetailedInfo.getPostCode())
            .setCreateTime(new Date()).setCreateUser(UserUtils.getUser().getId()).setUpdateTime(new Date())
            .setUpdateUser(UserUtils.getUser().getId());
        if(StringUtil.isNotEmpty(sysPostDetailedInfo.getWdModelId())){
            post.setWdModelId(sysPostDetailedInfo.getWdModelId());
        }
        post = postDao.save(post);
        sysPostDetailedInfo.setPostId(post.getId());
        postTree.setName(sysPostDetailedInfo.getPostName());
        postTree.setType(TreeNodeType.POST);
        postTree.setId(post.getId());
        postTree.setPostId(post.getId());
        postTree.setPostCode(post.getPostCode());
        postTree.setpId(TreeNode.ROOT);
        treeNodes.add(postTree);
        SysPostAuthorize sysPostAuthorize = new SysPostAuthorize();
        UserAuthorize userAuthorize = new UserAuthorize();
        sysPostDetailedInfo.setUserAuthorize(userAuthorize);
        sysPostDetailedInfo.setPostAuthorize(sysPostAuthorize);
        postTreeResponse.setInfo(sysPostDetailedInfo);
        postTreeResponse.setTreeNodes(treeNodes);
        return postTreeResponse;
    }

    /**
     * 修改职务
     *
     * @param sysPostDetailedInfo 前台传递的职务信息容器
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false,
                              rollbackFor = Exception.class)
    public SysPostTreeResponse updatePost(SysPostDetailedInfo sysPostDetailedInfo) throws Exception {

        SysPostTreeResponse postTreeResponse = new SysPostTreeResponse();
        List<SysPostTree> treeNodes = new ArrayList<SysPostTree>();
        SysPostTree sysPostTree = new SysPostTree();
        SysPostAuthorize postAuthorize = sysPostDetailedInfo.getPostAuthorize();
        UserAuthorize userAuthorize = sysPostDetailedInfo.getUserAuthorize();
        sysPostTree.setName(sysPostDetailedInfo.getPostName());
        sysPostTree.setType(TreeNodeType.POST);
        sysPostTree.setId(sysPostDetailedInfo.getPostId());
        sysPostTree.setPostId(sysPostDetailedInfo.getPostId());
        treeNodes.add(sysPostTree);
        postTreeResponse.setInfo(sysPostDetailedInfo);
        postTreeResponse.setTreeNodes(treeNodes);

        // 修改职务
        postDao.update(sysPostDetailedInfo.getPostCode(), sysPostDetailedInfo.getPostName(), new Date(),
                       UserUtils.getUser().getId(), sysPostDetailedInfo.getPostId());

        // 修改 职务目录权限表(分析权限)
        postMenuDao.deleteByPostId(sysPostDetailedInfo.getPostId());
        for (SysPostMenu postMenu : postAuthorize.getMenuIds()) {
            postMenu.setPostId(sysPostDetailedInfo.getPostId());
            postMenu.setCreateTime(new Date());
            postMenu.setCreateUser(UserUtils.getUser().getId());
            postMenuDao.saveAndFlush(postMenu);
        }

        // 修改用户的职务
        for (String userId : userAuthorize.getUserIds()) {
            userDao.updatePosition(sysPostDetailedInfo.getPostId(), UserUtils.getUser().getId(), new Date(), userId);
        }

        // 查询职务可管理的菜单
        List<String> postPowerIdList = postPowerDao.queryPowerIdList(sysPostDetailedInfo.getPostId());

        // 查询当前用户(角色)可管理的菜单
        List<String> rolePowerIdList = sysRolePowerDao.queryPowerIdListByUserId(UserUtils.getUser().getId());
        postPowerIdList.addAll(rolePowerIdList);

        // 修改菜单功能权限  先删除 再插入(其实可以直接写在1个sql里,先这样)
        postPowerDao.deleteByPostIdAndPowerIdList(sysPostDetailedInfo.getPostId(), postPowerIdList);

        // 保存菜单
        for (String funcId : postAuthorize.getFuncIds()) {

            // 保存职务菜单关联表
            SysPostPower postPower = new SysPostPower();
            postPower.setPostId(sysPostDetailedInfo.getPostId());
            postPower.setPowerType(SysPostPower.TYPE_FUN);
            postPower.setPowerId(funcId);
            postPowerDao.saveAndFlush(postPower);
        }
        
        // 保存按钮
        for (String buttonId : postAuthorize.getButtonIds()) {

            // 保存职务菜单关联表
            SysPostPower postPower = new SysPostPower();
            postPower.setPostId(sysPostDetailedInfo.getPostId());
            postPower.setPowerType(SysPostPower.TYPE_OPER);
            postPower.setPowerId(buttonId);
            postPowerDao.saveAndFlush(postPower);
        }

        // 保存wd_sys_user_org
        this.sysUserOrgService.createEntitysByPostId(null, null, sysPostDetailedInfo.getPostId());
        return postTreeResponse;
    }


    /**
     * 删除职务
     *
     * @param ids 职务id集合
     * @throws Exception
     */
    @Override
    public void delete(List<String> ids) throws Exception {

        for (String postId : ids) {
            delete(postId);
        }
    }

    /**
     * 删除相关数据
     *
     * @param postId 职务id
     * @throws Exception
     */
    private void delete(String postId) throws Exception {

        // 删除职务表
        postDao.deleteSysPostById(postId);

        // 删除关联的菜单和按钮
        postPowerDao.deleteByPostId(postId);

        // 删除分析权限表
        postMenuDao.deleteByPostId(postId);

        // 删除归入用户
        postUserDao.deleteByPostId(postId);
    }

    /**
     * 查询职务详细
     *
     * @param postId 职务id
     * @return
     * @throws Exception
     */
    @Override
    public SysPostDetailedInfo queryPostDetailedInfo(String postId) throws Exception {

        SysPostDetailedInfo postDetailedInfo = new SysPostDetailedInfo();
        SysPostAuthorize postAuthorize = new SysPostAuthorize();
        UserAuthorize userAuthorize = new UserAuthorize();
        List<String> funcIds = new ArrayList<>();
        List<String> buttonIds = new ArrayList<>();
        List<String> userIds = new ArrayList<>();

        // 已经拥有的菜单和按钮权限
        List<SysPostPower> alreadyExistFuncOrOperList = postPowerDao.querySysPostPowersByPostId(postId);
        for (SysPostPower sysPostPower : alreadyExistFuncOrOperList) {
            if (sysPostPower.getPowerType().equals(SysPostPower.TYPE_FUN)) {
                funcIds.add(sysPostPower.getPowerId());
            } else {
                buttonIds.add(sysPostPower.getPowerId());
            }
        }

        // 分析权限
        List<SysPostMenu> postMenus = postMenuDao.querySysPostMenusByPostId(postId);
        List<SysPostMenu> sysPostMenus = new ArrayList<SysPostMenu>(postMenus);


        // 已经拥有的归入用户
        SysUser user = UserUtils.getUser();
        List<String> alreadyExistUserPostList = null;
        if (AdministratorUtils.getAdministratorName().equals(user.getUserCde())) {
            alreadyExistUserPostList = userDao.queryPostUserIdsByPostId(postId);
        } else {
            alreadyExistUserPostList = userDao.queryPostUserIdsByPostId(postId, user.getId());
        }
        if (null != alreadyExistUserPostList) {
            userIds.addAll(alreadyExistUserPostList);
        }

        //职务详细
        SysPost post = postDao.querySysPostById(postId);
        postDetailedInfo.setPostId(post.getId());
        postDetailedInfo.setPostCode(post.getPostCode());
        postDetailedInfo.setPostName(post.getPostName());
        postAuthorize.setFuncIds(funcIds);
        postAuthorize.setButtonIds(buttonIds);
        postAuthorize.setMenuIds(sysPostMenus);
        userAuthorize.setUserIds(userIds);
        postDetailedInfo.setPostAuthorize(postAuthorize);
        postDetailedInfo.setUserAuthorize(userAuthorize);
        return postDetailedInfo;
    }

    /**
     * 查询全部职务,结果在treeNodes
     *
     * @return
     * @throws Exception
     */
    @Override
    public SysPostTreeResponse queryPostList() throws Exception {

        SysPostTreeResponse postTreeResponse = new SysPostTreeResponse();
        List<SysPostTree> postTreeList = new ArrayList<SysPostTree>();
        List<SysPost> sysPostList = postDao.querySysPostList();
        sysPostList.forEach((post) -> {
            SysPostTree postTree = new SysPostTree();
            postTree.setId(post.getId());
            postTree.setPostId(post.getId());
            postTree.setPostCode(post.getPostCode());
            postTree.setName(post.getPostName());
            postTree.setType(TreeNodeType.POST);
            postTreeList.add(postTree);
        });
        postTreeResponse.setTreeNodes(postTreeList);
        return postTreeResponse;
    }

    /**
     * 查询组织下用户的职务
     *
     * @param users 用户集合
     * @return
     * @throws Exception
     */
    @Override
    public List<SysPost> queryUserPosts(List<SysUser> users) throws Exception {

        if (null != users && users.size() > 0) {
            List<String> userPostIds = new ArrayList<>(users.size());
            users.forEach((user) -> {
                userPostIds.add(user.getPosition());
            });
            List<SysPost> posts = postDao.querySysPostByUserIds(userPostIds);
            return posts;
        }
        return new ArrayList<>();
    }

    /**
     * 查询职务用户列表
     *
     * @param users   用户列表
     * @param postIds 职务id集合
     * @return
     * @throws Exception
     */
    @Override
    public List<SysUser> queryUsersByPostIds(List<SysUser> users, List<String> postIds) throws Exception {

        if (null != users && users.size() > 0) {
            for (int i = users.size() - 1; i >= 0; i--) {
                if (!postIds.contains(users.get(i).getPosition())) {
                    users.remove(i);
                }
            }
        }
        return users;
    }


    /**
     * 查询全部职务（同步用户用）
     * @return
     * @throws Exception
     */
	@Override
    public List<SysPost> queryAllPost() throws Exception {
		return postDao.querySysPostList();
	}

	/**
     * 新建职务（同步用户用）
     * @param sysPost 
     * @return
     * @throws Exception
     */
	@Override
    public SysPost savePost(SysPost sysPost) throws Exception {
		return postDao.save(sysPost);
	}

    /**
     * 根据名称创建职务(编码自动生成)
     *
     * @param name 职务名称
     * @return
     * @throws Exception
     */
    @Override
    public SysPost createPostByName(String name) throws Exception {

        SysPost sysPost = new SysPost();

        GenerateCodeRule generateCodeRule = new OrdinaryGeneralRule();
        String postCode = generateCodeRule.generateCode(name, code -> {
            List<String> repeatCodes = new ArrayList<>();
            EntityManager entityManager = this.emf.createEntityManager();
            try {
                entityManager.getTransaction().begin();// 这个不加会报错
                Query query = null;
                String callSql = "select post_code from wd_sys_post where post_code like '" + code + "%'";
                query = entityManager.createNativeQuery(callSql);
                repeatCodes = query.getResultList();
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
            return repeatCodes;
        });
        sysPost.setPostCode(postCode)
            .setPostName(name)
            .setCreateTime(new Date())
            .setCreateUser(UserUtils.getUser().getId())
            .setUpdateTime(new Date())
            .setUpdateUser(UserUtils.getUser().getId());
        return this.postDao.saveAndFlush(sysPost);
    }
}
