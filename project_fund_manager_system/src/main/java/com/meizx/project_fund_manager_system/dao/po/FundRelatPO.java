package com.meizx.project_fund_manager_system.dao.po;

import lombok.Data;
/**
 * @author: meizx
 * @className: FundRelatPO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundRelatPO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 项目编号
     */
    private String projectId;
    private String fieleName;
    private String orders ="desc";
    private Integer page = 0;
    private Integer relatStatus;
    private Integer projectStatus;
    private String projectName;
}
