package com.meizx.project_fund_manager_system.controller;

import com.meizx.project_fund_manager_system.service.UserService;
import com.meizx.project_fund_manager_system.bo.UserRspBO;
import com.meizx.project_fund_manager_system.bo.UserReqBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: meizx
 * @className: UserPO 前端控制器
 * @desc: 用户模块相关  版权所有©meizx
 * @date: 2020-04-26T10:19
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/fund/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO insert(@RequestBody UserReqBO userReqBO){
        return userService.insert(userReqBO);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO deleteById(@RequestBody UserReqBO userReqBO){
        return userService.deleteById(userReqBO.getUserId());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO delete(@RequestBody UserReqBO userReqBO){
        return userService.delete(userReqBO);
    }

    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO updateById(@RequestBody UserReqBO userReqBO){
        return userService.updateById(userReqBO.getUserId()+"");
    }

    @RequestMapping(value = "/updateId", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO updateId(@RequestBody UserReqBO userReqBO){
        return userService.updateByUserId(userReqBO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO update(@RequestBody UserReqBO userReqBO){
        return userService.update(userReqBO);
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public UserRspBO selectInfoById(@RequestBody UserReqBO userReqBO){
        return userService.selectInfoByPrimaryKey(userReqBO.getUserId()+"");
    }

    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<UserRspBO> queryList(@RequestBody UserReqBO userReqBO){
        return userService.queryList(userReqBO);
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<UserRspBO> queryPage(@RequestBody UserReqBO userReqBO){
        return userService.queryPage(userReqBO);
    }
}
