package com.meizx.project_fund_manager_system.bo;

import lombok.Data;

/**
 * @Author: meizx
 * @ClassName: YearTabReqBO
 * @Date: 2020/6/20 11:12
 * @E-mail： meizxbj@gmail.com
 * @Description: YearTabReqBO
 * @Version: V1.0.0
 */
@Data
public class YearTabReqBO {
    private String projectId;
    private int thisYear;
    private double allBalance;
}
