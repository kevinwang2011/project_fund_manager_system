package com.meizx.project_fund_manager_system.bo;

import com.meizx.project_fund_manager_system.base.RspBO;
import lombok.Data;

/**
 * @author: meizx
 * @className: FundRelatRspBO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundRelatRspBO extends RspBO{


    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 项目编号
     */
    private String projectId;
    private String value;


}
