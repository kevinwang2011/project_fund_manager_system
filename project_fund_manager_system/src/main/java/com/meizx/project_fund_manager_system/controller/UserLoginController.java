package com.meizx.project_fund_manager_system.controller;

import com.meizx.project_fund_manager_system.bo.UserLoginReqBO;
import com.meizx.project_fund_manager_system.bo.UserLoginRspBO;
import com.meizx.project_fund_manager_system.config.RedisServce;
import com.meizx.project_fund_manager_system.dao.UserDAO;
import com.meizx.project_fund_manager_system.dao.po.UserPO;
import com.meizx.project_fund_manager_system.utils.DESUtil;
import com.meizx.project_fund_manager_system.utils.RedisUtil;
import com.meizx.project_fund_manager_system.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: meizx
 * @ClassName: UserLoginController
 * @Date: 2020/4/23 13:16
 * @E-mail： meizxbj@gmail.com
 * @Description: 用户登录、注册、注销、获取信息等 版权所有©meizx
 * @Version: V1.0.0
 */
@RestController
@RequestMapping("/fund/user")
public class UserLoginController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RedisServce redisServce;
    @Autowired
    private RedisUtil redisUtil;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserLoginRspBO login(@RequestBody UserLoginReqBO user){
        UserLoginRspBO userRspBO = new UserLoginRspBO();
        System.out.println(user);
        if((!"".equals(user.getUsername())) && (!"".equals(user.getPassword()))){
            if(userDAO.getInfoByPwd(user.getUsername(), DESUtil.encryptBasedDes(user.getPassword())) == 1){
                userRspBO.setCode(20000);
                userRspBO.setToken(TokenUtil.sign(new UserPO(user.getUsername(), user.getPassword())));
                redisServce.set(user.getUsername(), userRspBO.getToken(), 60*60*2);
            }else{
                userRspBO.setCode(9999);
                userRspBO.setDesc("该账号密码不正确或已停用或未通过审核");
            }
        }else{
            userRspBO.setCode(9999);
            userRspBO.setDesc("登录失败");
        }
        return userRspBO;
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public UserLoginRspBO info(@RequestParam(value = "token")String token){
        UserLoginRspBO userRspBO = new UserLoginRspBO();
        UserPO userPO = userDAO.selectInfoByPrimaryKey(TokenUtil.verify(token, 0));
        if(!("".equals(userPO.getUserName()) || "".equals(userPO.getRoles()))){
            //userRspBO1.setAvatar("../../assets/simplogo.png");
            //userRspBO1.setIntroduction("1");
            userRspBO.setName(userPO.getUserName());
            userRspBO.setUserId(userPO.getUserId());
            userRspBO.setCode(20000);
            userRspBO.setRoles(userPO.getRoles());
        }
        return userRspBO;
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public UserLoginRspBO logout(@RequestParam(value = "token")String token){
        UserLoginRspBO userRspBO1 = new UserLoginRspBO();
        if(redisUtil.layout(token)){
            userRspBO1.setCode(20000);
        }else{
            userRspBO1.setCode(9999);
        }
        return userRspBO1;
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "成功";
    }
}
