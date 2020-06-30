package com.meizx.project_fund_manager_system.dao;

import com.meizx.project_fund_manager_system.dao.po.UserPO;
import java.util.List;

/**
* @author: meizx
* @className: UserPO 数据访问层
* @desc:
* @date: 2020-04-26T10:19
* @version: 1.0.0
*/
public interface UserDAO {

    /**
    * @explain 新增数据
    * @param userPO
    * @return int
    */
    public int insert(UserPO userPO);

    /**
    * @explain 根据 id 删除数据
    * @param userId
    * @return int
    */
    public int deleteByPrimaryKey(String userId);

    /**
    * @explain 根据 UserPO 删除数据
    * @param userPO
    * @return int
    */
    public int delete(UserPO userPO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return int
    */
    public int updateByPrimaryKey(String id);
    public int updateByUserId(UserPO userPO);

    /**
    * @explain 根据 UserPO 修改数据
    * @param userPO
    * @return int
    */
    public int update(UserPO userPO);

    /**
    * @explain 根据 id 查询数据
    * @param userId
    * @return UserPO
    */
    public UserPO selectInfoByPrimaryKey(String userId);

    /**
    * @explain 根据 UserPO 查询数据
    * @param userId   pwd
    * @return int
    */
    public int getInfoByPwd(String userId, String pwd);

    /**
    * @explain 根据 userId 查询数据
    * @param userId
    * @return int
    */
    public int getCount(String userId);

    /**
    * @explain 根据 userId 查询数据
    * @param userId
    * @return int
    */
    public String getCountRelat(String userId);

    /**
    * @explain 根据 UserPO 批量查询数据
    * @param userPO
    * @return List<UserPO>
    */
    public List<UserPO> queryList(UserPO userPO);

    /**
    * @explain 根据 UserPO 查询数据大小
    * @param userPO
    * @return int
    */
    public int querySize(UserPO userPO);

    /**
    * @explain 根据 UserPO 分页查询数据
    * @param userPO
    *        pageCount：当前页数
    * @return List<UserPO>
    */
    public List<UserPO> queryPage(UserPO userPO, int pageCount);
}
