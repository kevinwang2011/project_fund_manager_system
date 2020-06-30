package com.meizx.project_fund_manager_system.controller;

import com.meizx.project_fund_manager_system.service.FundDetailService;
import com.meizx.project_fund_manager_system.bo.FundDetailRspBO;
import com.meizx.project_fund_manager_system.bo.FundDetailReqBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: meizx
 * @className: FundDetailPO 前端控制器
 * @desc:
 * @date: 2020-06-13T10:01
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/fund/project/detail")
public class FundDetailController {

    @Autowired
    private FundDetailService fundDetailService;

    @RequestMapping(value = "/applicat", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO insert(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.insert(fundDetailReqBO);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO deleteById(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.deleteById(fundDetailReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO delete(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.delete(fundDetailReqBO);
    }

    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO updateById(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.updateById(fundDetailReqBO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO update(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.update(fundDetailReqBO);
    }

    @RequestMapping(value = "/move", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO move(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.move(fundDetailReqBO);
    }

    @RequestMapping(value = "/moveInfo", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspBO moveUpdate(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.moveUpdate(fundDetailReqBO);
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public FundDetailRspBO selectInfoById(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.selectInfoByPrimaryKey(fundDetailReqBO.getProjectId()+"");
    }

    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundDetailRspBO> queryList(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.queryList(fundDetailReqBO);
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @CrossOrigin(origins = "*",maxAge = 3600)
    public RspPageBO<FundDetailRspBO> queryPage(@RequestBody FundDetailReqBO fundDetailReqBO){
        return fundDetailService.queryPage(fundDetailReqBO);
    }

}
