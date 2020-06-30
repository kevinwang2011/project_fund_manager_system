package com.meizx.project_fund_manager_system.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meizx.project_fund_manager_system.base.ReqPageBO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: meizx
 * @className: FundDetailReqBO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundDetailReqBO extends ReqPageBO {


    private static final long serialVersionUID = 1L;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 科目编号
     */
    private String fundId;

    /**
     * 科目名称
     */
    private Integer fundName;

    /**
     * 科目摘要
     */
    private String fundDesc;

    /**
     * 项目支出
     */
    private Double fundAmountOut;

    /**
     * 项目收入
     */
    private Double fundAmountIn;

    /**
     * 项目余额
     */
    private Double fundBalance;

    /**
     * 提交人
     */
    private String commitMain;

    /**
     * 提交人编号
     */
    private String commitUserId;

    /**
     * 提交时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate commitTime;

    /**
     * 预计使用时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate futureTime;

    /**
     * 科目申请状态
     */
    private Integer fundStatus;

    /**
     * 科目收支状态
     */
    private Integer fundInOrOut;

    /**
     * 申请状态
     */
    private Integer askStatus;

    /**
     * 经费性质
     */
    private Integer fundType;
    private String moveBanlance;
    private String moveProjectId;


}
