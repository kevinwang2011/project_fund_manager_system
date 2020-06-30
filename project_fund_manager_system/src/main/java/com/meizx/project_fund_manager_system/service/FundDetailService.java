package com.meizx.project_fund_manager_system.service;

import com.meizx.project_fund_manager_system.bo.FundDetailRspBO;
import com.meizx.project_fund_manager_system.bo.FundDetailReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;

/**
* @author: meizx
* @className: FundDetailPO 服务接口类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundDetailService {

    /**
    * @explain 根据 FundDetailPO 新增数据
    * @param fundDetailReqBO
    * @return RspBO
    */
    public RspBO insert(FundDetailReqBO fundDetailReqBO);

    /**
    * @explain 根据 id 删除数据
    * @param id
    * @return RspBO
    */
    public RspBO deleteById(String id);

    /**
    * @explain 根据 FundDetailPO 删除数据
    * @param fundDetailReqBO
    * @return RspBO
    */
    public RspBO delete(FundDetailReqBO fundDetailReqBO);

    /**
    * @explain 根据 id 修改数据
    * @param fundDetailReqBO
    * @return RspBO
    */
    public RspBO updateById(FundDetailReqBO fundDetailReqBO);

    /**
    * @explain 根据 FundDetailPO 修改数据
    * @param fundDetailReqBO
    * @return RspBO
    */
    public RspBO update(FundDetailReqBO fundDetailReqBO);
    public RspBO move(FundDetailReqBO fundDetailReqBO);
    public RspBO moveUpdate(FundDetailReqBO fundDetailReqBO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return RspBO
    */
    public FundDetailRspBO selectInfoByPrimaryKey(String id);

    /**
    * @explain 根据 FundDetailPO 批量查询数据
    * @param fundDetailReqBO
    * @return RspPageBO
    */
    public RspPageBO<FundDetailRspBO> queryList(FundDetailReqBO fundDetailReqBO);

    /**
    * @explain 根据 FundDetailPO 分页查询数据
    * @param fundDetailReqBO
    *        pageSize：分页大小
    *        pageCount：当前页数
    * @return RspPageBO
    */
    public RspPageBO<FundDetailRspBO> queryPage(FundDetailReqBO fundDetailReqBO);
}
