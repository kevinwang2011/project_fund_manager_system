package com.meizx.project_fund_manager_system.bo;

import com.meizx.project_fund_manager_system.base.ReqPageBO;
import lombok.Data;

/**
 * @author: meizx
 * @className: FundRelatReqBO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundRelatReqBO extends ReqPageBO {


    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String userId;
    private String userName;

    /**
     * 项目编号
     */
    private String projectId;
    private Integer desc;


}
