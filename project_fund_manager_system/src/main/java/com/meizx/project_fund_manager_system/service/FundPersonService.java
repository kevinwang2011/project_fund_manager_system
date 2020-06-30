package com.meizx.project_fund_manager_system.service;

import com.meizx.project_fund_manager_system.bo.FundPersonRspBO;
import com.meizx.project_fund_manager_system.bo.FundPersonReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;

/**
* @author: meizx
* @className: FundPersonPO 服务接口类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundPersonService {

    /**
    * @explain 根据 FundPersonPO 新增数据
    * @param fundPersonReqBO
    * @return RspBO
    */
    public RspBO insert(FundPersonReqBO fundPersonReqBO);

    /**
    * @explain 根据 id 删除数据
    * @param id
    * @return RspBO
    */
    public RspBO deleteById(String id);

    /**
    * @explain 根据 FundPersonPO 删除数据
    * @param fundPersonReqBO
    * @return RspBO
    */
    public RspBO delete(FundPersonReqBO fundPersonReqBO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return RspBO
    */
    public RspBO updateById(String id);

    /**
    * @explain 根据 FundPersonPO 修改数据
    * @param fundPersonReqBO
    * @return RspBO
    */
    public RspBO update(FundPersonReqBO fundPersonReqBO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return RspBO
    */
    public FundPersonRspBO selectInfoByPrimaryKey(String id);

    /**
    * @explain 根据 FundPersonPO 批量查询数据
    * @param fundPersonReqBO
    * @return RspPageBO
    */
    public RspPageBO<FundPersonRspBO> queryList(FundPersonReqBO fundPersonReqBO);

    /**
    * @explain 根据 FundPersonPO 分页查询数据
    * @param fundPersonReqBO
    *        pageSize：分页大小
    *        pageCount：当前页数
    * @return RspPageBO
    */
    public RspPageBO<FundPersonRspBO> queryPage(FundPersonReqBO fundPersonReqBO);
}
