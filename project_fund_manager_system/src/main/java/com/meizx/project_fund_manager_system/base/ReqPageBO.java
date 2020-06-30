package com.meizx.project_fund_manager_system.base;

import lombok.Data;

/**
* @Author: meizx
* @className: UserPO 实体类
* @desc:
* @date: 2020-04-26T10:19
* @version: V1.0.0
*/
@Data
public class ReqPageBO extends ReqBO {
    private int pageSize;
    private int pageCount;
}