package com.meizx.project_fund_manager_system.service.impl;

import com.meizx.project_fund_manager_system.dao.*;
import com.meizx.project_fund_manager_system.dao.po.*;
import com.meizx.project_fund_manager_system.service.FundProjectService;
import com.meizx.project_fund_manager_system.bo.FundProjectRspBO;
import com.meizx.project_fund_manager_system.bo.FundProjectReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
* @author: meizx
* @className: FundProjectPO 服务实现类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
@Service
public class FundProjectServiceImpl implements FundProjectService {

    @Autowired
    private FundProjectDAO fundProjectDao;
    @Autowired
    private FundRelatDAO fundRelatDAO;
    @Autowired
    private FundPersonDAO fundPersonDAO;
    @Autowired
    private FundCostDAO fundCostDAO;
    @Autowired
    private FundDetailDAO fundDetailDAO;
    @Autowired
    private UserDAO userDAO;

    @Transactional
    @Override
    public RspBO insert(FundProjectReqBO fundProjectReqBO){
        RspBO rspBo = new RspBO();
        FundProjectPO info = new FundProjectPO();
        BeanUtils.copyProperties(fundProjectReqBO, info);
        info.setProjectStatus(2);
        if(fundProjectDao.getCount(fundProjectReqBO.getProjectId()) > 0){
            rspBo.setCode(9999);
            rspBo.setDesc("项目已存在，请重新核实项目编号或联系管理员！");
            return rspBo;
        }
        StringBuffer name = new StringBuffer(fundProjectReqBO.getProjectMain());
        String[] group = null;
        FundPersonPO fundPersonPO = new FundPersonPO();
        if(!("".equals(fundProjectReqBO.getProjectGroup()) || fundProjectReqBO.getProjectGroup() == null)){
            group = Pattern.compile("[^0-9-]+").split(fundProjectReqBO.getProjectGroup().trim());
            String id = Arrays.deepToString(group);
            fundPersonPO.setProjectGroup(fundProjectReqBO.getUserId().trim()+","+id.substring(1, id.length()-1).trim());
        }else{
            fundPersonPO.setProjectGroup(fundProjectReqBO.getUserId().trim());
        }
        fundPersonPO.setProjectId(fundProjectReqBO.getProjectId());
        FundRelatPO fundRelatPO = null;
        List list = new ArrayList(6);
        if(group != null){
            String usernam = null;
            for (int i = 0; i < group.length; i++) {
                fundRelatPO = new FundRelatPO();
                fundRelatPO.setProjectId(fundProjectReqBO.getProjectId());
                fundRelatPO.setUserId(group[i]);
                fundRelatPO.setRelatStatus(1);
                list.add(fundRelatPO);
                usernam = userDAO.getCountRelat(fundRelatPO.getUserId());
                if(usernam == null || "".equals(usernam)){
                    rspBo.setCode(9999);
                    rspBo.setDesc("成员："+fundRelatPO.getUserId()+" 不存在或未通过审核，请重新核实账户编号或联系管理员！");
                    return rspBo;
                }else{
                    name.append("、"+usernam);
                }
            }
        }
        fundPersonPO.setGroupName(name.toString());
        FundRelatPO fundRelat = new FundRelatPO();
        fundRelat.setProjectId(fundProjectReqBO.getProjectId());
        fundRelat.setUserId(fundProjectReqBO.getUserId());
        fundRelat.setRelatStatus(0);
        list.add(fundRelat);

        FundCostPO fundCostPO = new FundCostPO();
        fundCostPO.setProjectBudget(fundProjectReqBO.getProjectBudget()*10*10);
        fundCostPO.setProjectId(fundProjectReqBO.getProjectId());
        fundCostPO.setThisYear(LocalDate.now().getYear());
        fundCostPO.setWhereBalance(fundProjectReqBO.getProjectNode());
        if(fundProjectDao.insert(info) == 1 && fundPersonDAO.insert(fundPersonPO) == 1
                && fundRelatDAO.insertBatch(list) > 0 && fundCostDAO.insert(fundCostPO) == 1){
            rspBo.setCode(20000);
            rspBo.setDesc("添加成功");
        }else{
            rspBo.setCode(9999);
            rspBo.setDesc("添加失败");
        }
        return rspBo;
    }

    @Transactional
    @Override
    public RspBO deleteById(String id){
        RspBO rspBo = new RspBO();
        if(fundProjectDao.deleteByPrimaryKey(id) == 1 && fundCostDAO.deleteByPrimaryKey(id) ==1
                && fundPersonDAO.deleteByPrimaryKey(id) ==1 && fundRelatDAO.deleteByPrimaryKey(id) >= 1){
            rspBo.setCode(20000);
            rspBo.setDesc("删除成功");
        }else{
            rspBo.setCode(9999);
            rspBo.setDesc("删除失败");
        }
        return rspBo;
    }

    @Override
    public RspBO delete(FundProjectReqBO fundProjectReqBO){
        RspBO rspBo = new RspBO();
        FundProjectPO info = new FundProjectPO();
        BeanUtils.copyProperties(fundProjectReqBO, info);
        if(fundProjectDao.delete(info) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("删除成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("删除失败");
        }
        return rspBo;
    }

    @Override
    public RspBO updateById(String id){
        RspBO rspBo = new RspBO();
        if(fundProjectDao.updateByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("修改成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    @Transactional
    @Override
    public RspBO update(FundProjectReqBO fundProjectReqBO){
        RspBO rspBo = new RspBO();
        FundProjectPO info = new FundProjectPO();
        FundCostPO fundCostPO = new FundCostPO();
        FundDetailPO fundDetailPO = new FundDetailPO();
        BeanUtils.copyProperties(fundProjectReqBO, info);
        if(fundProjectReqBO.getDesc() == 0){
          //结项通过
            info.setProjectStatus(0);
            info.setToDoMark(0);
            if(fundProjectDao.update(info) == 1){
                rspBo.setCode(20000);
                rspBo.setDesc("结项操作成功");
            }else{
                rspBo.setCode(9999);
                rspBo.setDesc("操作失败");
            }
        } else if(fundProjectReqBO.getDesc() == 10){
          //结项不通过
            info.setProjectStatus(1);
            info.setToDoMark(0);
            if(fundProjectDao.update(info) == 1){
                rspBo.setCode(20000);
                rspBo.setDesc("结项回退成功");
            }else{
                rspBo.setCode(9999);
                rspBo.setDesc("操作失败");
            }
        } else if(fundProjectReqBO.getDesc() == 1){
          //立项通过
            info.setProjectStatus(1);
            info.setToDoMark(0);
            fundCostPO.setProjectId(fundProjectReqBO.getProjectId());
            fundCostPO.setProjectBudget(fundProjectReqBO.getProjectBudget()*100);
            fundCostPO.setProjectBalance(fundProjectReqBO.getProjectBudget()*100);
            fundCostPO.setTempBanlance(fundProjectReqBO.getProjectBudget()*100);
            fundDetailPO.setAskStatus(4);//科目状态
            fundDetailPO.setProjectId(fundProjectReqBO.getProjectId());
            fundDetailPO.setFundId(String.valueOf(System.currentTimeMillis())); //科目编号
            fundDetailPO.setFundName(12); //科目名称
            fundDetailPO.setFundDesc("项目经费初始化");//科目摘要
            fundDetailPO.setFundAmountOut(0.00*100);//项目支出
            fundDetailPO.setFundAmountIn(fundProjectReqBO.getProjectBudget()*100);//项目收入
            fundDetailPO.setFundBalance(fundProjectReqBO.getProjectBudget()*100);//项目余额
            fundDetailPO.setCommitMain("系统提交");//提交人
            fundDetailPO.setCommitUserId("001");//提交人编号
            fundDetailPO.setCommitTime(LocalDateTime.now());//提交时间
            fundDetailPO.setFutureTime(LocalDate.now());//预计使用时间
            fundDetailPO.setFundStatus(4);//科目申请状态
            fundDetailPO.setFundInOrOut(0);//科目收支状态
            fundDetailPO.setFundType(1);//经费性质
            fundDetailPO.setToDoMark(0);//经费性质
            if(fundProjectDao.update(info) == 1 && fundDetailDAO.insert(fundDetailPO) == 1
                    && fundCostDAO.update(fundCostPO) == 1){
                rspBo.setCode(20000);
                rspBo.setDesc("立项操作成功");
            }else{
                rspBo.setCode(9999);
                rspBo.setDesc("操作失败");
            }
        } else if(fundProjectReqBO.getDesc() == 3){
          //结项审核
            info.setProjectStatus(3);
            info.setToDoMark(1);
            rspBo.setDesc("结项请求提交成功");
            if(fundProjectDao.update(info) == 1){
                rspBo.setCode(20000);
            }else{
                rspBo.setCode(9999);
                rspBo.setDesc("操作失败");
            }
        } else {}

        return rspBo;
    }

    @Override
    public FundProjectRspBO selectInfoByPrimaryKey(String id){
        FundProjectRspBO rspBo = new FundProjectRspBO();
        FundProjectPO info = new FundProjectPO();
        info = fundProjectDao.selectInfoByPrimaryKey(id);
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

    @Override
    public RspPageBO<FundProjectRspBO> queryList(FundProjectReqBO fundProjectReqBO){
        RspPageBO<FundProjectRspBO> rspBo = new RspPageBO<>();
        FundProjectPO info = new FundProjectPO();
        BeanUtils.copyProperties(fundProjectReqBO, info);
        List<FundProjectPO> list = fundProjectDao.queryList(info);
        FundProjectRspBO bo = null;
        if(list.size() > 0){
           List<FundProjectRspBO> data = new ArrayList<>();
           for(FundProjectPO po : list){
               bo = new FundProjectRspBO();
               bo.setValue(po.getProjectName()+"("+(po.getProjectStatus()==0?"已结项":(po.getProjectStatus()==1?"未结项":(po.getProjectStatus()==2?"待审核":"待结项")))+")-"+po.getProjectId());
               bo.setProjectId(po.getProjectId());
               data.add(bo);
           }
           rspBo.setData(data);
           rspBo.setCode(20000);
           rspBo.setDesc("查询成功");
        }else{
           rspBo.setCode(20000);
           rspBo.setDesc("无数据");
        }
        return rspBo;
    }

    @Override
    public RspPageBO<FundProjectRspBO> queryPage(FundProjectReqBO fundProjectReqBO){
        RspPageBO<FundProjectRspBO> rspBo = new RspPageBO<>();
        FundProjectPO fundProjectPO = new FundProjectPO();
        BeanUtils.copyProperties(fundProjectReqBO, fundProjectPO);
        int size = fundProjectDao.querySize(fundProjectPO);
        fundProjectPO.setFieleName("createTime");
        fundProjectPO.setOrders("desc");
        if(size > 0){
           List<FundProjectRspBO> list = fundProjectDao.queryPage3(fundProjectPO, fundProjectReqBO.getPageCount() == 1 ? 0 : fundProjectReqBO.getPageCount() * 10 - 10);
           if(list.size() > 0){
               long balance = 0L;
               long amount = 0L;
               long budget = 0L;
               for (int i = 0; i < list.size(); i++) {
                   balance = new Double(list.get(i).getProjectBalance()).longValue();
                   amount = new Double(list.get(i).getProjectTotalAmount()).longValue();
                   budget = new Double(list.get(i).getProjectBudget()).longValue();
                   list.get(i).setProjectBudgetStr(new DecimalFormat("0.00").format(budget/100.0
                   )+"元");
                   list.get(i).setProjectBudget(0.0);
                   if(balance > 10000000){
                       //12.3599万元
                       list.get(i).setProjectBalanceStr(String.valueOf(balance/100/10000.0)+"万元");
                       list.get(i).setProjectBalance(0.0);
                   }else{
                       //32122.98元
                       list.get(i).setProjectBalanceStr(String.valueOf(balance/100.0)+"元");
                       list.get(i).setProjectBalance(0.0);
                   }

                   if(amount > 10000000){
                       //12.3599万元
                       list.get(i).setProjectTotalAmountStr(String.valueOf(amount/100/10000.0)+"万元");
                       list.get(i).setProjectBalance(0.0);
                   }else{
                       //32122.98元
                       list.get(i).setProjectTotalAmountStr(String.valueOf(amount/100.0)+"元");
                       list.get(i).setProjectBalance(0.0);
                   }
               }
              rspBo.setData(list);
              rspBo.setCode(20000);
              rspBo.setTotalSize(size);
              rspBo.setDesc("查询成功");
           }else{
              rspBo.setCode(20000);
              rspBo.setDesc("查询成功");
           }
        }else{
           rspBo.setCode(20000);
           rspBo.setDesc("查询成功");
        }
        return rspBo;
    }

    @Override
    public RspPageBO<FundProjectRspBO> query(FundProjectReqBO fundProjectReqBO){
        RspPageBO<FundProjectRspBO> rspBo = new RspPageBO<>();
        FundProjectPO fundProjectPO = new FundProjectPO();
        BeanUtils.copyProperties(fundProjectReqBO, fundProjectPO);
        int size = fundProjectDao.queryQuerySize(fundProjectPO);
        fundProjectPO.setFieleName("createTime");
        fundProjectPO.setOrders("desc");
        if(size > 0){
           List<FundProjectRspBO> list = fundProjectDao.queryQuery(fundProjectPO, fundProjectReqBO.getPageCount() == 1 ? 0 : fundProjectReqBO.getPageCount() * 10 - 10);
           if(list.size() > 0){
               long balance = 0L;
               long amount = 0L;
               long budget = 0L;
               for (int i = 0; i < list.size(); i++) {
                   balance = new Double(list.get(i).getProjectBalance()).longValue();
                   amount = new Double(list.get(i).getProjectTotalAmount()).longValue();
                   budget = new Double(list.get(i).getProjectBudget()).longValue();
                   list.get(i).setProjectBudgetStr(new DecimalFormat("0.00").format(budget/100.0
                   )+"元");
                   list.get(i).setProjectBudget(0.0);
                   if(balance > 10000000){
                       //12.3599万元
                       list.get(i).setProjectBalanceStr(String.valueOf(balance/100/10000.0)+"万元");
                       list.get(i).setProjectBalance(0.0);
                   }else{
                       //32122.98元
                       list.get(i).setProjectBalanceStr(String.valueOf(balance/100.0)+"元");
                       list.get(i).setProjectBalance(0.0);
                   }

                   if(amount > 10000000){
                       //12.3599万元
                       list.get(i).setProjectTotalAmountStr(String.valueOf(amount/100/10000.0)+"万元");
                       list.get(i).setProjectTotalAmount(0.0);
                   }else{
                       //32122.98元
                       list.get(i).setProjectTotalAmountStr(String.valueOf(amount/100.0)+"元");
                       list.get(i).setProjectTotalAmount(0.0);
                   }
               }
              rspBo.setData(list);
              rspBo.setCode(20000);
              rspBo.setTotalSize(size);
              rspBo.setDesc("查询成功");
           }else{
              rspBo.setCode(20000);
              rspBo.setDesc("查询成功");
           }
        }else{
           rspBo.setCode(20000);
           rspBo.setDesc("查询成功");
        }
        return rspBo;
    }
}
