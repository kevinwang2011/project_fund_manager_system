package com.meizx.project_fund_manager_system.service.impl;

import com.meizx.project_fund_manager_system.bo.*;
import com.meizx.project_fund_manager_system.dao.po.FundCostPO;
import com.meizx.project_fund_manager_system.dao.FundCostDAO;
import com.meizx.project_fund_manager_system.dao.po.YearTabPO;
import com.meizx.project_fund_manager_system.service.FundCostService;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;

/**
* @author: meizx
* @className: FundCostPO 服务实现类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
@Service
public class FundCostServiceImpl implements FundCostService {

    @Autowired
    private FundCostDAO fundCostDao;

    public RspBO insert(FundCostReqBO fundCostReqBO){
        RspBO rspBo = new RspBO();
        FundCostPO info = new FundCostPO();
        BeanUtils.copyProperties(fundCostReqBO, info);
        if(fundCostDao.insert(info) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("添加成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("添加失败");
        }
        return rspBo;
    }

    public RspBO deleteById(String id){
        RspBO rspBo = new RspBO();
        if(fundCostDao.deleteByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("删除成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("删除失败");
        }
        return rspBo;
    }

    public RspBO delete(FundCostReqBO fundCostReqBO){
        RspBO rspBo = new RspBO();
        FundCostPO info = new FundCostPO();
        BeanUtils.copyProperties(fundCostReqBO, info);
        if(fundCostDao.delete(info) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("删除成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("删除失败");
        }
        return rspBo;
    }

    public RspBO updateById(String id){
        RspBO rspBo = new RspBO();
        if(fundCostDao.updateByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("修改成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    public RspBO update(FundCostReqBO fundCostReqBO){
        RspBO rspBo = new RspBO();
        FundCostPO info = new FundCostPO();
        BeanUtils.copyProperties(fundCostReqBO, info);
        if(fundCostDao.update(info) == 1){
            rspBo.setCode(20000);
            rspBo.setDesc("修改成功");
        }else{
            rspBo.setCode(9999);
            rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    public FundCostRspBO selectInfoByPrimaryKey(String id){
        FundCostRspBO rspBo = new FundCostRspBO();
        FundCostPO info = new FundCostPO();
        info = fundCostDao.selectInfoByPrimaryKey(id);
        if(info != null){
           BeanUtils.copyProperties(info, rspBo);
           rspBo.setCode(20000);
           rspBo.setDesc("查询成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("查询失败");
        }
        return rspBo;
    }

    public RspPageBO<FundCostRspBO> queryList(FundCostReqBO fundCostReqBO){
        RspPageBO<FundCostRspBO> rspBo = new RspPageBO<>();
        FundCostPO info = new FundCostPO();
        BeanUtils.copyProperties(fundCostReqBO, info);
        List<FundCostPO> list = fundCostDao.queryList(info);
        FundCostRspBO bo = null;
        if(list.size() > 0){
           List<FundCostRspBO> data = new ArrayList<>();
           for(FundCostPO po : list){
               bo = new FundCostRspBO();
               BeanUtils.copyProperties(po, bo);
               data.add(bo);
           }
           rspBo.setData(data);
           rspBo.setCode(20000);
           rspBo.setDesc("查询成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("查询失败");
        }
        return rspBo;
    }

    public RspPageBO<FundCostRspBO> queryPage(FundCostReqBO fundCostReqBO){
        RspPageBO<FundCostRspBO> rspBo = new RspPageBO<>();
        FundCostPO info = new FundCostPO();
        BeanUtils.copyProperties(fundCostReqBO, info);
        int size = fundCostDao.querySize(info);
        if(size > 0){
           List<FundCostPO> list = fundCostDao.queryPage(info, fundCostReqBO.getPageCount());
           FundCostRspBO bo = null;
           if(list.size() > 0){
              List<FundCostRspBO> data = new ArrayList<>();
              for(FundCostPO po : list){
                  bo = new FundCostRspBO();
                  BeanUtils.copyProperties(po, bo);
                  data.add(bo);
              }
              rspBo.setData(data);
              rspBo.setCode(20000);
              rspBo.setDesc("查询成功");
           }else{
              rspBo.setCode(9999);
              rspBo.setDesc("查询失败");
           }
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("查询失败");
        }
        return rspBo;
    }


    public RspBO insertYear(YearTabReqBO yearTabReqBO){
        RspBO rspBo = new RspBO();
        if(yearTabReqBO.getAllBalance() < 0){
            rspBo.setCode(9999);
            rspBo.setDesc("数据错误");
            return rspBo;
        }
        YearTabPO po = new YearTabPO();
        po.setThisYear(yearTabReqBO.getThisYear());
        YearTabPO year = fundCostDao.getYear(po);
        if(year != null && !"".equals(year) && !"".equals(year.getAllBalance())){
            year.setAllBalance(yearTabReqBO.getAllBalance()*100);
            if(fundCostDao.updateYear(year) == 1){
                rspBo.setCode(20000);
                rspBo.setDesc("资产完善成功");
            }else{
                rspBo.setCode(9999);
                rspBo.setDesc("资产完善失败");
            }
        }else{
            po.setAllBalance(yearTabReqBO.getAllBalance()*100);
            if(fundCostDao.insertYear(po) == 1){
                rspBo.setCode(20000);
                rspBo.setDesc("资产完善成功");
            }else{
                rspBo.setCode(9999);
                rspBo.setDesc("资产完善失败");
            }
        }
        return rspBo;
    }

    public YearTabRspBO query1(YearTabReqBO yearTabReqBO){
        YearTabRspBO rspBo = new YearTabRspBO();
        YearTabPO info = new YearTabPO();
        info.setThisYear(yearTabReqBO.getThisYear());
        YearTabPO po = fundCostDao.getYear(info);
        if(po != null && !"".equals(po) && new Double(po.getAllBalance()).longValue() >=0){
            double all = 0.0;
            try {
                all = fundCostDao.allBalance(info.getThisYear());
            } catch (Exception e) {
                all = 0.0;
            }
            Temp temp1 = new Temp();
            temp1.setTotal("已用资产");
            temp1.setDescr(new DecimalFormat("0.00").format(all/100));
            Temp temp2 = new Temp();
            temp2.setTotal("剩余资产");
            temp2.setDescr(new DecimalFormat("0.00").format((po.getAllBalance()-all)/100));
            Temp temp3 = new Temp();
            temp3.setTotal("总资产");
            temp3.setDescr(new DecimalFormat("0.00").format(po.getAllBalance()/100));
            List<Temp> list = new ArrayList(5);
            list.add(temp1);
            list.add(temp2);
            list.add(temp3);
            rspBo.setData(list);
            rspBo.setAllBalance(temp3.getDescr());
            rspBo.setCode(20000);
            rspBo.setDesc("查询成功");
        }else{
            Temp temp1 = new Temp();
            temp1.setTotal("已用资产");
            temp1.setDescr("0");
            Temp temp2 = new Temp();
            temp2.setTotal("剩余资产");
            temp2.setDescr("0");
            Temp temp3 = new Temp();
            temp3.setTotal("总资产");
            temp3.setDescr("0");
            List<Temp> list = new ArrayList(5);
            list.add(temp1);
            list.add(temp2);
            list.add(temp3);
            rspBo.setData(list);
            rspBo.setCode(9999);
            rspBo.setDesc("查询失败");
        }
        return rspBo;
    }

    public YearTabRspBO query2(YearTabReqBO yearTabReqBO){
        YearTabRspBO rspBo = new YearTabRspBO();
        if(yearTabReqBO.getProjectId() == null && "".equals(yearTabReqBO.getProjectId())){
            rspBo.setCode(9999);
            rspBo.setDesc("数据错误");
            return rspBo;
        }
        FundCostPO po = fundCostDao.projectBalance(yearTabReqBO.getProjectId());
        if(po != null && !"".equals(po)){
            Temp temp1 = new Temp();
            temp1.setTotal("已分配资产");
            temp1.setDescr(String.valueOf((po.getProjectBudget()-po.getTempBanlance())/100));
            Temp temp2 = new Temp();
            temp2.setTotal("未分配资产");
            temp2.setDescr(String.valueOf((po.getTempBanlance())/100));
            Temp temp3 = new Temp();
            temp3.setTotal("已用资产");
            temp3.setDescr(new Double(po.getProjectTotalAmount()/100).toString());
            Temp temp4 = new Temp();
            temp4.setTotal("剩余资产");
            temp4.setDescr(String.valueOf((po.getProjectBalance())/100));
            Temp temp5 = new Temp();
            temp5.setTotal("总资产");
            temp5.setDescr(String.valueOf(new Double(po.getProjectBudget()/100)+""));
            List<Temp> list = new ArrayList(5);
            list.add(temp1);
            list.add(temp2);
            list.add(temp3);
            list.add(temp4);
            list.add(temp5);
            rspBo.setData(list);
            rspBo.setCode(20000);
            rspBo.setDesc("查询成功");
        }else{
            rspBo.setCode(9999);
            rspBo.setDesc("查询失败");
        }
        return rspBo;
    }


}
