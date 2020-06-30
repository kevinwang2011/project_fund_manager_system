package com.meizx.project_fund_manager_system.dao;

import com.meizx.project_fund_manager_system.bo.FundProjectRspBO;
import com.meizx.project_fund_manager_system.dao.po.FundProjectPO;
import java.util.List;

/**
* @author: meizx
* @className: FundProjectPO 数据访问层
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundProjectDAO {

    /**
    * @explain 新增数据
    * @param fundProjectPO
    * @return int
    */
    public int insert(FundProjectPO fundProjectPO);

    /**
    * @explain 根据 id 删除数据
    * @param projectId
    * @return int
    */
    public int deleteByPrimaryKey(String projectId);

    /**
    * @explain 根据 FundProjectPO 删除数据
    * @param fundProjectPO
    * @return int
    */
    public int delete(FundProjectPO fundProjectPO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return int
    */
    public int updateByPrimaryKey(String id);

    /**
    * @explain 根据 FundProjectPO 修改数据
    * @param fundProjectPO
    * @return int
    */
    public int update(FundProjectPO fundProjectPO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return FundProjectPO
    */
    public FundProjectPO selectInfoByPrimaryKey(String id);
    public FundProjectPO selectInfoById(String id);

    /**
    * @explain 根据 主键 查询数据
    * @param id
    * @return int
    */
    public int getCount(String id);

    /**
    * @explain 根据 FundProjectPO 批量查询数据
    * @param fundProjectPO
    * @return List<FundProjectPO>
    */
    public List<FundProjectPO> queryList(FundProjectPO fundProjectPO);

    /**
    * @explain 根据 FundProjectPO 查询数据大小
    * @param fundProjectPO
    * @return int
    */
    public int querySize(FundProjectPO fundProjectPO);
    public int queryQuerySize(FundProjectPO fundProjectPO);

    /**
    * @explain 根据 FundProjectPO 分页查询数据
    * @param fundProjectPO
    *        pageCount：当前页数
    * @return List<FundProjectPO>
    */
    public List<FundProjectPO> queryPage(FundProjectPO fundProjectPO, int pageCount);
    public List<FundProjectRspBO> queryPage3(FundProjectPO fundProjectPO, int pageCount);
    public List<FundProjectRspBO> queryQuery(FundProjectPO fundProjectPO, int pageCount);
}
