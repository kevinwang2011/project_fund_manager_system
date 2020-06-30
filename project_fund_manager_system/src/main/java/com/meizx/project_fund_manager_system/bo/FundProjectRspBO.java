package com.meizx.project_fund_manager_system.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meizx.project_fund_manager_system.base.RspBO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: meizx
 * @className: FundProjectRspBO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundProjectRspBO extends RspBO{


    private static final long serialVersionUID = 1L;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目状态
     */
    private Integer projectStatus;

    /**
     * 项目性质  0个人  1团队
     */
    private Integer projectType;

    /**
     * 负责人
     */
    private String projectMain;

    /**
     * 负责人id
     */
    private String userId;

    /**
     * 申请状态
     */
    private Integer applicatType;

    /**
     * 申请时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 预算额度
     */
    private double projectBudget;
    private String projectBudgetStr;

    /**
     * 项目余额
     */
    private double projectBalance;
    private String projectBalanceStr;

    /**
     * 已用额度
     */
    private double projectTotalAmount;
    private String projectTotalAmountStr;

    private String groupName;
    private String value;
    private Integer toDoMark;
    private Integer relatStatus;
    private Integer whereBalance;
    private double tempBanlance;

}
