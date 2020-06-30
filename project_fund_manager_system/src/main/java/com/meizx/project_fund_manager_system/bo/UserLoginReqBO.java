package com.meizx.project_fund_manager_system.bo;

import lombok.Data;

/**
 * @Author: meizx
 * @ClassName: UserLoginReqBO
 * @Date: 2020/4/23 13:18
 * @E-mail： meizxbj@gmail.com
 * @Description: UserLoginReqBO
 * @Version: V1.0.0
 */
@Data
public class UserLoginReqBO {
    private String username;
    private String password;
}
