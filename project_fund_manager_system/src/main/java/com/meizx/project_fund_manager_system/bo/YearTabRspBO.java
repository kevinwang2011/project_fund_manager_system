package com.meizx.project_fund_manager_system.bo;

import com.meizx.project_fund_manager_system.base.RspBO;
import lombok.Data;

import java.util.List;

/**
 * @Author: meizx
 * @ClassName: YearTabRspBO
 * @Date: 2020/6/20 10:48
 * @E-mail： meizxbj@gmail.com
 * @Description: YearTabRspBO
 * @Version: V1.0.0
 */
@Data
public class YearTabRspBO extends RspBO {
    private String allBalance;
    private List<Temp> data;
}

