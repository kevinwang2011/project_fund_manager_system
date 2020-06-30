package com.meizx.project_fund_manager_system.base;

import lombok.Data;
import java.util.List;

/**
* @author: meizx
* @className: UserPO 实体类
* @desc:
* @date: 2020-04-26T10:19
* @version: 1.0.0
*/
@Data
public class RspPageBO<T> extends RspBO {
    private List<T> data;
    private int pageSize;
    private int pageCount;
    private int totalSize;
}