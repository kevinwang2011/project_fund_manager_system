package com.meizx.project_fund_manager_system.bo;

import com.meizx.project_fund_manager_system.base.ReqPageBO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: meizx
 * @className: FundProjectReqBO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundProjectReqBO extends ReqPageBO {


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
     * 待办标识
     */
    private Integer toDoMark;

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
    private LocalDateTime createTime;

    /**
     * 项目成员
     */
    private String projectGroup;

    /**
     * 项目预算
     */
    private double projectBudget;
    private double moveBanlance;
    private double banlance;
    private String moveProjectId;
    private int projectNode;

}
