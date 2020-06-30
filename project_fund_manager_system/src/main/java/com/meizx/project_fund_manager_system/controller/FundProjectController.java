package com.meizx.project_fund_manager_system.controller;

import com.meizx.project_fund_manager_system.service.FundProjectService;
import com.meizx.project_fund_manager_system.bo.FundProjectRspBO;
import com.meizx.project_fund_manager_system.bo.FundProjectReqBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: meizx
 * @className: FundProjectPO 前端控制器
 * @desc: 创建项目，项目概览查询，项目结算查询  版权所有©meizx
 * @date: 2020-06-13 10:01
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/fund/project")
public class FundProjectController {

    @Autowired
    private FundProjectService fundProjectService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO insert(@RequestBody FundProjectReqBO fundProjectReqBO){
        return fundProjectService.insert(fundProjectReqBO);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO deleteById(@RequestBody FundProjectReqBO fundProjectReqBO){
        return fundProjectService.deleteById(fundProjectReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO delete(@RequestBody FundProjectReqBO fundProjectReqBO){
        return fundProjectService.delete(fundProjectReqBO);
    }

    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO updateById(@RequestBody FundProjectReqBO fundProjectReqBO){
        return fundProjectService.updateById(fundProjectReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO update(@RequestBody FundProjectReqBO fundProjectReqBO){
        return fundProjectService.update(fundProjectReqBO);
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public FundProjectRspBO selectInfoById(@RequestBody FundProjectReqBO fundProjectReqBO){
        return fundProjectService.selectInfoByPrimaryKey(fundProjectReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundProjectRspBO> queryList(@RequestBody FundProjectReqBO fundProjectReqBO){
        return fundProjectService.queryList(fundProjectReqBO);
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundProjectRspBO> queryPage(@RequestBody FundProjectReqBO fundProjectReqBO){
        return fundProjectService.queryPage(fundProjectReqBO);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundProjectRspBO> query(@RequestBody FundProjectReqBO fundProjectReqBO){
        return fundProjectService.query(fundProjectReqBO);
    }
}
