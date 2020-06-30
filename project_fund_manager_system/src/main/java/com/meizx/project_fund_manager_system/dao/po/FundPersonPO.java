package com.meizx.project_fund_manager_system.dao.po;

import lombok.Data;
/**
 * @author: meizx
 * @className: FundPersonPO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundPersonPO {

    private static final long serialVersionUID = 1L;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 项目成员
     */
    private String projectGroup;
    private String groupName;
    private String fieleName;
    private String orders ="desc";
    private Integer page = 0;


}
