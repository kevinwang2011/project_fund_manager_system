package com.meizx.project_fund_manager_system.service;

import com.meizx.project_fund_manager_system.bo.UserRspBO;
import com.meizx.project_fund_manager_system.bo.UserReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;

/**
* @author: meizx
* @className: UserPO 服务接口类
* @desc:
* @date: 2020-04-26T10:19
* @version: 1.0.0
*/
public interface UserService {

    /**
    * @explain 根据 UserPO 新增数据
    * @param userReqBO
    * @return RspBO
    */
    public RspBO insert(UserReqBO userReqBO);

    /**
    * @explain 根据 id 删除数据
    * @param id
    * @return RspBO
    */
    public RspBO deleteById(String id);

    /**
    * @explain 根据 UserPO 删除数据
    * @param userReqBO
    * @return RspBO
    */
    public RspBO delete(UserReqBO userReqBO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return RspBO
    */
    public RspBO updateById(String id);
    public RspBO updateByUserId(UserReqBO userReqBO);

    /**
    * @explain 根据 UserPO 修改数据
    * @param userReqBO
    * @return RspBO
    */
    public RspBO update(UserReqBO userReqBO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return RspBO
    */
    public UserRspBO selectInfoByPrimaryKey(String id);

    /**
    * @explain 根据 UserPO 批量查询数据
    * @param userReqBO
    * @return RspPageBO
    */
    public RspPageBO<UserRspBO> queryList(UserReqBO userReqBO);

    /**
    * @explain 根据 UserPO 分页查询数据
    * @param userReqBO
    *        pageSize：分页大小
    *        pageCount：当前页数
    * @return RspPageBO
    */
    public RspPageBO<UserRspBO> queryPage(UserReqBO userReqBO);
}
