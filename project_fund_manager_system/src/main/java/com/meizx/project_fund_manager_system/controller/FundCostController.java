package com.meizx.project_fund_manager_system.controller;

import com.meizx.project_fund_manager_system.bo.YearTabReqBO;
import com.meizx.project_fund_manager_system.service.FundCostService;
import com.meizx.project_fund_manager_system.bo.FundCostRspBO;
import com.meizx.project_fund_manager_system.bo.FundCostReqBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: meizx
 * @className: FundCostPO 前端控制器
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/fund/project/cost")
public class FundCostController {

    @Autowired
    private FundCostService fundCostService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO insert(@RequestBody FundCostReqBO fundCostReqBO){
        return fundCostService.insert(fundCostReqBO);
    }

    @RequestMapping(value = "/addYear", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO insertYear(@RequestBody YearTabReqBO yearTabReqBO){
        return fundCostService.insertYear(yearTabReqBO);
    }
    @RequestMapping(value = "/query1", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO query1(@RequestBody YearTabReqBO yearTabReqBO){
        return fundCostService.query1(yearTabReqBO);
    }
    @RequestMapping(value = "/query2", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO query2(@RequestBody YearTabReqBO yearTabReqBO){
        return fundCostService.query2(yearTabReqBO);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO deleteById(@RequestBody FundCostReqBO fundCostReqBO){
        return fundCostService.deleteById(fundCostReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO delete(@RequestBody FundCostReqBO fundCostReqBO){
        return fundCostService.delete(fundCostReqBO);
    }

    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO updateById(@RequestBody FundCostReqBO fundCostReqBO){
        return fundCostService.updateById(fundCostReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO update(@RequestBody FundCostReqBO fundCostReqBO){
        return fundCostService.update(fundCostReqBO);
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public FundCostRspBO selectInfoById(@RequestBody FundCostReqBO fundCostReqBO){
        return fundCostService.selectInfoByPrimaryKey(fundCostReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundCostRspBO> queryList(@RequestBody FundCostReqBO fundCostReqBO){
        return fundCostService.queryList(fundCostReqBO);
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundCostRspBO> queryPage(@RequestBody FundCostReqBO fundCostReqBO){
        return fundCostService.queryPage(fundCostReqBO);
    }
}
