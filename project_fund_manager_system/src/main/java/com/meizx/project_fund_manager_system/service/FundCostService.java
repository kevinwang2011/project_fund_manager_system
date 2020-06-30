package com.meizx.project_fund_manager_system.service;

import com.meizx.project_fund_manager_system.bo.FundCostRspBO;
import com.meizx.project_fund_manager_system.bo.FundCostReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import com.meizx.project_fund_manager_system.bo.YearTabReqBO;
import com.meizx.project_fund_manager_system.bo.YearTabRspBO;

/**
* @author: meizx
* @className: FundCostPO 服务接口类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundCostService {

    /**
    * @explain 根据 FundCostPO 新增数据
    * @param fundCostReqBO
    * @return RspBO
    */
    public RspBO insert(FundCostReqBO fundCostReqBO);

    /**
    * @explain 根据 id 删除数据
    * @param id
    * @return RspBO
    */
    public RspBO deleteById(String id);

    /**
    * @explain 根据 FundCostPO 删除数据
    * @param fundCostReqBO
    * @return RspBO
    */
    public RspBO delete(FundCostReqBO fundCostReqBO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return RspBO
    */
    public RspBO updateById(String id);

    /**
    * @explain 根据 FundCostPO 修改数据
    * @param fundCostReqBO
    * @return RspBO
    */
    public RspBO update(FundCostReqBO fundCostReqBO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return RspBO
    */
    public FundCostRspBO selectInfoByPrimaryKey(String id);

    /**
    * @explain 根据 FundCostPO 批量查询数据
    * @param fundCostReqBO
    * @return RspPageBO
    */
    public RspPageBO<FundCostRspBO> queryList(FundCostReqBO fundCostReqBO);

    /**
    * @explain 根据 FundCostPO 分页查询数据
    * @param fundCostReqBO
    *        pageSize：分页大小
    *        pageCount：当前页数
    * @return RspPageBO
    */
    public RspPageBO<FundCostRspBO> queryPage(FundCostReqBO fundCostReqBO);
    public RspBO insertYear(YearTabReqBO yearTabReqBO);
    public YearTabRspBO query1(YearTabReqBO yearTabReqBO);
    public YearTabRspBO query2(YearTabReqBO yearTabReqBO);
}
