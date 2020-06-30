package com.meizx.project_fund_manager_system.dao.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: meizx
 * @className: FundProjectPO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundProjectPO {

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
    private String fieleName;
    private String orders ="desc";
    private Integer page = 0;


}
