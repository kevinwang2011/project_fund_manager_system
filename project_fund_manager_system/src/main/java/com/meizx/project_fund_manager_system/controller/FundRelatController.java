package com.meizx.project_fund_manager_system.controller;

import com.meizx.project_fund_manager_system.service.FundRelatService;
import com.meizx.project_fund_manager_system.bo.FundRelatRspBO;
import com.meizx.project_fund_manager_system.bo.FundRelatReqBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: meizx
 * @className: FundRelatPO 前端控制器
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/fund/projectRelat")
public class FundRelatController {

    @Autowired
    private FundRelatService fundRelatService;

    @RequestMapping(value = "/relat", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO insert(@RequestBody FundRelatReqBO fundRelatReqBO){
        return fundRelatService.insert(fundRelatReqBO);
    }

    @RequestMapping(value = "/cancelRelat", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO deleteById(@RequestBody FundRelatReqBO fundRelatReqBO){
        return fundRelatService.delete(fundRelatReqBO);
    }

    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundRelatRspBO> queryList(@RequestBody FundRelatReqBO fundRelatReqBO){
        return fundRelatService.queryList(fundRelatReqBO);
    }
}
