package com.meizx.project_fund_manager_system.service;

import com.meizx.project_fund_manager_system.bo.FundProjectRspBO;
import com.meizx.project_fund_manager_system.bo.FundProjectReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;

/**
* @author: meizx
* @className: FundProjectPO 服务接口类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundProjectService {

    /**
    * @explain 根据 FundProjectPO 新增数据
    * @param fundProjectReqBO
    * @return RspBO
    */
    public RspBO insert(FundProjectReqBO fundProjectReqBO);

    /**
    * @explain 根据 id 删除数据
    * @param id
    * @return RspBO
    */
    public RspBO deleteById(String id);

    /**
    * @explain 根据 FundProjectPO 删除数据
    * @param fundProjectReqBO
    * @return RspBO
    */
    public RspBO delete(FundProjectReqBO fundProjectReqBO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return RspBO
    */
    public RspBO updateById(String id);

    /**
    * @explain 根据 FundProjectPO 修改数据
    * @param fundProjectReqBO
    * @return RspBO
    */
    public RspBO update(FundProjectReqBO fundProjectReqBO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return RspBO
    */
    public FundProjectRspBO selectInfoByPrimaryKey(String id);

    /**
    * @explain 根据 FundProjectPO 批量查询数据
    * @param fundProjectReqBO
    * @return RspPageBO
    */
    public RspPageBO<FundProjectRspBO> queryList(FundProjectReqBO fundProjectReqBO);

    /**
    * @explain 根据 FundProjectPO 分页查询数据
    * @param fundProjectReqBO
    *        pageSize：分页大小
    *        pageCount：当前页数
    * @return RspPageBO
    */
    public RspPageBO<FundProjectRspBO> queryPage(FundProjectReqBO fundProjectReqBO);

    /**
    * @explain 根据 FundProjectPO 分页查询数据
    * @param fundProjectReqBO
    *        pageSize：分页大小
    *        pageCount：当前页数
    * @return RspPageBO
    */
    public RspPageBO<FundProjectRspBO> query(FundProjectReqBO fundProjectReqBO);
}
