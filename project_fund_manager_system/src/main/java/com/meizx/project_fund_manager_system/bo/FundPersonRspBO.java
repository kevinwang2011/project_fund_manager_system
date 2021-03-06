package com.meizx.project_fund_manager_system.bo;

import com.meizx.project_fund_manager_system.base.RspBO;
import lombok.Data;

/**
 * @author: meizx
 * @className: FundPersonRspBO 实体类
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@Data
public class FundPersonRspBO extends RspBO{


    private static final long serialVersionUID = 1L;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 项目成员
     */
    private String projectGroup;


}
