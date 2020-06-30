package com.meizx.project_fund_manager_system.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meizx.project_fund_manager_system.base.RspBO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: meizx
 * @className: FundDetailRspBO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundDetailRspBO extends RspBO{


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
    private String fundAmountOutStr;

    /**
     * 项目收入
     */
    private Double fundAmountIn;
    private String fundAmountInStr;

    /**
     * 项目余额
     */
    private Double fundBalance;
    private String fundBalanceStr;

    /**
     * 提交人
     */
    private String commitMain;

    /**
     * 提交时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime commitTime;

    /**
     * 预计使用时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate futureTime;

    /**
     * 科目申请状态
     */
    private Integer fundStatus;
    private Integer fundType;
    /*private Integer fundInOrOut;
    private Integer askStatus;

    private String commitUserId;*/

}
