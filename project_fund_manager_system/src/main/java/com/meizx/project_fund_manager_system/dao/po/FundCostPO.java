package com.meizx.project_fund_manager_system.dao.po;

import lombok.Data;
/**
 * @author: meizx
 * @className: FundCostPO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundCostPO {

    private static final long serialVersionUID = 1L;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 预算额度
     */
    private double projectBudget;

    /**
     * 项目余额
     */
    private double projectBalance;

    /**
     * 已用额度
     */
    private double projectTotalAmount;
    private double tempBanlance;
    private String fieleName;
    private int thisYear;
    private String orders ="desc";
    private Integer page = 0;
    private int whereBalance;

}
