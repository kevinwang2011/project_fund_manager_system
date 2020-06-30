package com.meizx.project_fund_manager_system.bo;

import java.time.LocalDateTime;
import com.meizx.project_fund_manager_system.base.ReqPageBO;
import lombok.Data;

/**
 * @author: meizx
 * @className: UserReqBO 实体类
 * @desc:
 * @date: 2020-04-26T10:19
 * @version: 1.0.0
 */
@Data
public class UserReqBO extends ReqPageBO {


    private static final long serialVersionUID = 1L;

    /**
     * 用户号    职工编号  学生号
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 用户状态  0 已停用   1 待通过   2 已通过
     */
    private Integer userStatus;

    /**
     * 用户标识  0 职工编号  1 学生号
     */
    private Integer userMark;

    /**
     * 用户邮箱
     */
    private String userMail;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 角色
     */
    private String roles;
}
