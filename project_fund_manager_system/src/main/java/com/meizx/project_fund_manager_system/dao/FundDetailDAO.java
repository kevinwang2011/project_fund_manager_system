package com.meizx.project_fund_manager_system.dao;

import com.meizx.project_fund_manager_system.bo.FundDetailRspBO;
import com.meizx.project_fund_manager_system.dao.po.FundDetailPO;
import java.util.List;

/**
* @author: meizx
* @className: FundDetailPO 数据访问层
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundDetailDAO {

    /**
    * @explain 新增数据
    * @param fundDetailPO
    * @return int
    */
    public int insert(FundDetailPO fundDetailPO);

    /**
    * @explain 根据 id 删除数据
    * @param id
    * @return int
    */
    public int deleteByPrimaryKey(String id);
    public int deleteByFundId(String fundId);

    /**
    * @explain 根据 FundDetailPO 删除数据
    * @param fundDetailPO
    * @return int
    */
    public int delete(FundDetailPO fundDetailPO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return int
    */
    public int updateByPrimaryKey(String id);

    /**
    * @explain 根据 FundDetailPO 修改数据
    * @param fundDetailPO
    * @return int
    */
    public int updateById(FundDetailPO fundDetailPO);
    public int update1(FundDetailPO fundDetailPO);
    public int update2(FundDetailPO fundDetailPO);
    public int update3(FundDetailPO fundDetailPO);
    public int update33(FundDetailPO fundDetailPO);
    public int updateUp(FundDetailPO fundDetailPO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return FundDetailPO
    */
    public FundDetailPO selectInfoByPrimaryKey(String id);
    public FundDetailPO getInfo(FundDetailPO fundDetailPO);
    public FundDetailPO getInfoById(String fundId);

    /**
    * @explain 根据 主键 查询数据
    * @param id
    * @return int
    */
    public int getCount(String id);

    /**
    * @explain 根据 FundDetailPO 批量查询数据
    * @param fundDetailPO
    * @return List<FundDetailPO>
    */
    public List<FundDetailPO> queryList(FundDetailPO fundDetailPO);

    /**
    * @explain 根据 FundDetailPO 查询数据大小
    * @param fundDetailPO
    * @return int
    */
    public int querySize(FundDetailPO fundDetailPO);

    /**
    * @explain 根据 FundDetailPO 分页查询数据
    * @param fundDetailPO
    *        pageCount：当前页数
    * @return List<FundDetailPO>
    */
    public List<FundDetailRspBO> queryPage(FundDetailPO fundDetailPO, int pageCount);
}
