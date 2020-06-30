package com.meizx.project_fund_manager_system.service;

import com.meizx.project_fund_manager_system.bo.FundRelatRspBO;
import com.meizx.project_fund_manager_system.bo.FundRelatReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;

/**
* @author: meizx
* @className: FundRelatPO 服务接口类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundRelatService {

    /**
    * @explain 根据 FundRelatPO 新增数据
    * @param fundRelatReqBO
    * @return RspBO
    */
    public RspBO insert(FundRelatReqBO fundRelatReqBO);

    /**
    * @explain 根据 id 删除数据
    * @param id
    * @return RspBO
    */
    public RspBO deleteById(String id);

    /**
    * @explain 根据 FundRelatPO 删除数据
    * @param fundRelatReqBO
    * @return RspBO
    */
    public RspBO delete(FundRelatReqBO fundRelatReqBO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return RspBO
    */
    public RspBO updateById(String id);

    /**
    * @explain 根据 FundRelatPO 修改数据
    * @param fundRelatReqBO
    * @return RspBO
    */
    public RspBO update(FundRelatReqBO fundRelatReqBO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return RspBO
    */
    public FundRelatRspBO selectInfoByPrimaryKey(String id);

    /**
    * @explain 根据 FundRelatPO 批量查询数据
    * @param fundRelatReqBO
    * @return RspPageBO
    */
    public RspPageBO<FundRelatRspBO> queryList(FundRelatReqBO fundRelatReqBO);

    /**
    * @explain 根据 FundRelatPO 分页查询数据
    * @param fundRelatReqBO
    *        pageSize：分页大小
    *        pageCount：当前页数
    * @return RspPageBO
    */
    public RspPageBO<FundRelatRspBO> queryPage(FundRelatReqBO fundRelatReqBO);
}
