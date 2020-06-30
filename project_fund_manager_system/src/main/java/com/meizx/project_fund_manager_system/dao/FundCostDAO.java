package com.meizx.project_fund_manager_system.dao;

import com.meizx.project_fund_manager_system.dao.po.FundCostPO;
import com.meizx.project_fund_manager_system.dao.po.YearTabPO;

import java.util.List;

/**
* @author: meizx
* @className: FundCostPO 数据访问层
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundCostDAO {

    /**
    * @explain 新增数据
    * @param fundCostPO
    * @return int
    */
    public int insert(FundCostPO fundCostPO);
    public double allBalance(int year);
    public FundCostPO projectBalance(String projectId);
    public int insertYear(YearTabPO yearTabPO);
    public YearTabPO getYear(YearTabPO yearTabPO);
    public int updateYear(YearTabPO yearTabPO);

    /**
    * @explain 根据 id 删除数据
    * @param projectId
    * @return int
    */
    public int deleteByPrimaryKey(String projectId);

    /**
    * @explain 根据 FundCostPO 删除数据
    * @param fundCostPO
    * @return int
    */
    public int delete(FundCostPO fundCostPO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return int
    */
    public int updateByPrimaryKey(String id);

    /**
    * @explain 根据 FundCostPO 修改数据
    * @param fundCostPO
    * @return int
    */
    public int update(FundCostPO fundCostPO);
    public int updateRel(FundCostPO fundCostPO);
    public int updateRel1(FundCostPO fundCostPO);
    public int updateRel2(FundCostPO fundCostPO);
    public int updateTemp(FundCostPO fundCostPO);
    public int updateTemp1(FundCostPO fundCostPO);
    public int updateTemp2(FundCostPO fundCostPO);
    public int updateTempUp(FundCostPO fundCostPO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return FundCostPO
    */
    public FundCostPO selectInfoByPrimaryKey(String id);

    /**
    * @explain 根据 主键 查询数据
    * @param id
    * @return int
    */
    public int getCount(String id);

    /**
    * @explain 根据 FundCostPO 批量查询数据
    * @param fundCostPO
    * @return List<FundCostPO>
    */
    public List<FundCostPO> queryList(FundCostPO fundCostPO);

    /**
    * @explain 根据 FundCostPO 查询数据大小
    * @param fundCostPO
    * @return int
    */
    public int querySize(FundCostPO fundCostPO);

    /**
    * @explain 根据 FundCostPO 分页查询数据
    * @param fundCostPO
    *        pageCount：当前页数
    * @return List<FundCostPO>
    */
    public List<FundCostPO> queryPage(FundCostPO fundCostPO, int pageCount);
}
