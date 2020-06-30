package com.meizx.project_fund_manager_system.bo;

import lombok.Data;

/**
 * @Author: meizx
 * @ClassName: UserLoginRspBO
 * @Date: 2020/4/23 16:40
 * @E-mail： meizxbj@gmail.com
 * @Description: UserLoginRspBO
 * @Version: V1.0.0
 */
@Data
public class UserLoginRspBO {
    private int code;
    private String token;
    private String userId;
    private String roles;
    private String name;
    private String avatar;
    private String introduction;
    private String desc;
}
