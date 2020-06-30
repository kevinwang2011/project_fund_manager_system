package com.meizx.project_fund_manager_system.dao.po;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
/**
 * @author: meizx
 * @className: UserPO 实体类
 * @desc:
 * @date: 2020-04-26T10:19
 * @version: 1.0.0
 */
@Data
public class UserPO {

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
     * 用户标识  0 职工  1 学生
     */
    private Integer userMark;

    /**
     * 用户邮箱
     */
    private String userMail;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 角色
     */
    private String roles;

    private String fieleName;
    private String orders ="desc";
    private Integer page = 0;


    public UserPO(String userId, String password) {
        this.userId = userId;
        this.userPwd = password;
    }
    public UserPO() {

    }
}
