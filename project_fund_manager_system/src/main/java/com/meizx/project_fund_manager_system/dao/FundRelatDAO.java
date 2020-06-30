package com.meizx.project_fund_manager_system.dao;

import com.meizx.project_fund_manager_system.dao.po.FundRelatPO;
import java.util.List;

/**
* @author: meizx
* @className: FundRelatPO 数据访问层
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
public interface FundRelatDAO {

    /**
    * @explain 新增数据
    * @param fundRelatPO
    * @return int
    */
    public int insert(FundRelatPO fundRelatPO);

    /**
    * @explain 新增数据
    * @param list
    * @return int
    */
    public int insertBatch(List<FundRelatPO> list);

    /**
    * @explain 根据 id 删除数据
    * @param projectId
    * @return int
    */
    public int deleteByPrimaryKey(String projectId);

    /**
    * @explain 根据 FundRelatPO 删除数据
    * @param fundRelatPO
    * @return int
    */
    public int delete(FundRelatPO fundRelatPO);

    /**
    * @explain 根据 id 修改数据
    * @param id
    * @return int
    */
    public int updateByPrimaryKey(String id);

    /**
    * @explain 根据 FundRelatPO 修改数据
    * @param fundRelatPO
    * @return int
    */
    public int update(FundRelatPO fundRelatPO);

    /**
    * @explain 根据 id 查询数据
    * @param id
    * @return FundRelatPO
    */
    public FundRelatPO selectInfoByPrimaryKey(String id);

    /**
    * @explain 根据 主键 查询数据
    * @param id
    * @return int
    */
    public int getCount(String id);

    /**
    * @explain 根据 FundRelatPO 批量查询数据
    * @param userId
    * @return List<FundRelatPO>
    */
    public List<FundRelatPO> queryList(String userId);
    public List<FundRelatPO> queryData(String userId);

    /**
    * @explain 根据 FundRelatPO 查询数据大小
    * @param fundRelatPO
    * @return int
    */
    public int querySize(FundRelatPO fundRelatPO);

    /**
    * @explain 根据 FundRelatPO 分页查询数据
    * @param fundRelatPO
    *        pageCount：当前页数
    * @return List<FundRelatPO>
    */
    public List<FundRelatPO> queryPage(FundRelatPO fundRelatPO, int pageCount);
}
