package com.meizx.project_fund_manager_system.controller;

import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.bo.UserReqBO;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: meizx
 * @ClassName: TestController
 * @Date: 2020/6/20 20:33
 * @E-mail： meizxbj@gmail.com
 * @Description: TestController
 * @Version: V1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/t", method = RequestMethod.GET)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public String test(){
        return "Http转Https";
    }

}
