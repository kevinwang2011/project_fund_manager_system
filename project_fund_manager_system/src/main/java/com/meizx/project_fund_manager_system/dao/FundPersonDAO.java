package com.meizx.project_fund_manager_system.dao;

import com.meizx.project_fund_manager_system.dao.po.FundPersonPO;
import java.util.List;

/**
* @author: meizx
* @className: FundPersonPO 数据访问层
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundPersonDAO {

    /**
    * @explain 新增数据
    * @param fundPersonPO
    * @return int
    */
    public int insert(FundPersonPO fundPersonPO);

    /**
    * @explain 根据 id 删除数据
    * @param projectId
    * @return int
    */
    public int deleteByPrimaryKey(String projectId);

    /**
    * @explain 根据 FundPersonPO 删除数据
    * @param fundPersonPO
    * @return int
    */
    public int delete(FundPersonPO fundPersonPO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return int
    */
    public int updateByPrimaryKey(String id);

    /**
    * @explain 根据 FundPersonPO 修改数据
    * @param fundPersonPO
    * @return int
    */
    public int update(FundPersonPO fundPersonPO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return FundPersonPO
    */
    public FundPersonPO selectInfoByPrimaryKey(String id);

    /**
    * @explain 根据 主键 查询数据
    * @param id
    * @return int
    */
    public int getCount(String id);

    /**
    * @explain 根据 FundPersonPO 批量查询数据
    * @param fundPersonPO
    * @return List<FundPersonPO>
    */
    public List<FundPersonPO> queryList(FundPersonPO fundPersonPO);

    /**
    * @explain 根据 FundPersonPO 查询数据大小
    * @param fundPersonPO
    * @return int
    */
    public int querySize(FundPersonPO fundPersonPO);

    /**
    * @explain 根据 FundPersonPO 分页查询数据
    * @param fundPersonPO
    *        pageCount：当前页数
    * @return List<FundPersonPO>
    */
    public List<FundPersonPO> queryPage(FundPersonPO fundPersonPO, int pageCount);
}
