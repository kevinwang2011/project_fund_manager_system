package com.meizx.project_fund_manager_system.controller;

import com.meizx.project_fund_manager_system.service.FundPersonService;
import com.meizx.project_fund_manager_system.bo.FundPersonRspBO;
import com.meizx.project_fund_manager_system.bo.FundPersonReqBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: meizx
 * @className: FundPersonPO 前端控制器
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/project_fund_manager_system/fund-person-po")
public class FundPersonController {

    @Autowired
    private FundPersonService fundPersonService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO insert(@RequestBody FundPersonReqBO fundPersonReqBO){
        return fundPersonService.insert(fundPersonReqBO);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO deleteById(@RequestBody FundPersonReqBO fundPersonReqBO){
        return fundPersonService.deleteById(fundPersonReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO delete(@RequestBody FundPersonReqBO fundPersonReqBO){
        return fundPersonService.delete(fundPersonReqBO);
    }

    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO updateById(@RequestBody FundPersonReqBO fundPersonReqBO){
        return fundPersonService.updateById(fundPersonReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO update(@RequestBody FundPersonReqBO fundPersonReqBO){
        return fundPersonService.update(fundPersonReqBO);
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public FundPersonRspBO selectInfoById(@RequestBody FundPersonReqBO fundPersonReqBO){
        return fundPersonService.selectInfoByPrimaryKey(fundPersonReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundPersonRspBO> queryList(@RequestBody FundPersonReqBO fundPersonReqBO){
        return fundPersonService.queryList(fundPersonReqBO);
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundPersonRspBO> queryPage(@RequestBody FundPersonReqBO fundPersonReqBO){
        return fundPersonService.queryPage(fundPersonReqBO);
    }
}
