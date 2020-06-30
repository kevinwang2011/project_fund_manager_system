package com.meizx.project_fund_manager_system.service.impl;

import com.meizx.project_fund_manager_system.dao.FundCostDAO;
import com.meizx.project_fund_manager_system.dao.FundProjectDAO;
import com.meizx.project_fund_manager_system.dao.po.FundCostPO;
import com.meizx.project_fund_manager_system.dao.po.FundDetailPO;
import com.meizx.project_fund_manager_system.dao.FundDetailDAO;
import com.meizx.project_fund_manager_system.dao.po.FundProjectPO;
import com.meizx.project_fund_manager_system.service.FundDetailService;
import com.meizx.project_fund_manager_system.bo.FundDetailRspBO;
import com.meizx.project_fund_manager_system.bo.FundDetailReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
* @author: meizx
* @className: FundDetailPO 服务实现类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
@Slf4j
@Service
public class FundDetailServiceImpl implements FundDetailService {

    @Autowired
    private FundDetailDAO fundDetailDao;
    @Autowired
    private FundCostDAO fundCostDAO;
    @Autowired
    private FundProjectDAO fundProjectDAO;

    @Transactional
    @Override
    public RspBO insert(FundDetailReqBO fundDetailReqBO){
        RspBO rspBo = new RspBO();
        FundDetailPO fundDetailPO = new FundDetailPO();
        BeanUtils.copyProperties(fundDetailReqBO, fundDetailPO);
        FundCostPO fundCostPO = fundCostDAO.selectInfoByPrimaryKey(fundDetailReqBO.getProjectId());
        if(fundCostPO.getTempBanlance() < fundDetailReqBO.getFundAmountOut()*100){
        //TODO  if(fundCostPO.getProjectBalance() < fundDetailReqBO.getFundAmountOut()*100){
            rspBo.setCode(9999);
            rspBo.setDesc("经费余额不足");
            return rspBo;
        }
        //projectId=2020-010212002,  fundAmountOut=1212.0, commitMain=张三,
        //fundName=2, fundDesc=1212,futureTime=2020-06-16,commitUserId=001,

        //fundAmountIn=null, fundBalance=null, fundId=null,commitTime=null,
        //fundStatus=null, fundInOrOut=null, askStatus=null, fundType=null
        fundDetailPO.setAskStatus(4);//科目状态
        fundDetailPO.setFundId(String.valueOf(System.currentTimeMillis()));//科目编号
        fundDetailPO.setFundAmountOut(fundDetailReqBO.getFundAmountOut()*100);//项目支出
        //fundDetailPO.setFundAmountIn(fundProjectReqBO.getProjectBudget()*100);//项目收入
        //fundDetailPO.setFundBalance(fundCostPO.getProjectBalance()-fundDetailReqBO.getFundAmountOut()*100);//项目余额
        fundDetailPO.setFundBalance(fundCostPO.getTempBanlance()-fundDetailReqBO.getFundAmountOut()*100);//项目余额
        fundDetailPO.setFundStatus(1);//科目申请状态
        fundDetailPO.setFundInOrOut(1);//科目收支状态
        //fundDetailPO.setFundType(fundDetailReqBO.getFundType());//经费性质
        fundDetailPO.setToDoMark(1);

        fundCostPO.setTempBanlance(fundCostPO.getTempBanlance()-fundDetailPO.getFundAmountOut());
        if(fundDetailDao.insert(fundDetailPO) == 1 & fundCostDAO.updateTemp(fundCostPO) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("经费申请提交成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("经费申请提交失败");
        }
        return rspBo;
    }

    public RspBO deleteById(String id){
        RspBO rspBo = new RspBO();
        if(fundDetailDao.deleteByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("删除成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("删除失败");
        }
        return rspBo;
    }

    public RspBO delete(FundDetailReqBO fundDetailReqBO){
        RspBO rspBo = new RspBO();
        FundDetailPO info = new FundDetailPO();
        BeanUtils.copyProperties(fundDetailReqBO, info);
        FundDetailPO infoo = fundDetailDao.getInfo(info);
        FundCostPO fundCostPO = new FundCostPO();
        fundCostPO.setTempBanlance(infoo.getFundAmountOut());
        fundCostPO.setProjectId(infoo.getProjectId());
        if(fundDetailDao.delete(info) == 1 && fundCostDAO.updateTempUp(fundCostPO) == 1
                && fundDetailDao.updateUp(infoo) >= 0){
           rspBo.setCode(20000);
           rspBo.setDesc("退回成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("退回失败");
        }
        return rspBo;
    }

    public RspBO updateById(FundDetailReqBO fundDetailReqBO){
        RspBO rspBo = new RspBO();
        FundDetailPO info = new FundDetailPO();
        BeanUtils.copyProperties(fundDetailReqBO, info);
        info.setToDoMark(1);
        info.setFundStatus(fundDetailReqBO.getDesc());
        if(fundDetailDao.updateById(info) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("申请提交成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("申请提交失败");
        }
        return rspBo;
    }

    public RspBO update(FundDetailReqBO fundDetailReqBO){
        RspBO rspBo = new RspBO();
        FundDetailPO detail = new FundDetailPO();
        BeanUtils.copyProperties(fundDetailReqBO, detail);
        if(detail.getAskStatus() == 1){
            //通过
            FundCostPO fundCostPO = new FundCostPO();
            FundCostPO cp = fundCostDAO.selectInfoByPrimaryKey(detail.getProjectId());
            detail.setToDoMark(0);
            detail.setFundStatus(fundDetailReqBO.getDesc());
            detail.setFundAmountOut(fundDetailReqBO.getFundBalance()*100);
            FundDetailPO info = fundDetailDao.getInfo(detail);
            if(new Double(info.getFundAmountOut()).longValue() == new Double(detail.getFundAmountOut()).longValue()){
                fundCostPO.setProjectId(info.getProjectId());
                fundCostPO.setProjectBalance(info.getFundAmountOut());
                fundCostPO.setProjectTotalAmount(info.getFundAmountOut());
                if(fundCostDAO.updateRel(fundCostPO) == 1 && fundDetailDao.update1(detail) == 1){
                    rspBo.setCode(20000);
                    rspBo.setDesc("审核通过成功");
                }else{
                    rspBo.setCode(9999);
                    rspBo.setDesc("审核通过失败");
                }
            }else if(new Double(info.getFundAmountOut()).longValue() > new Double(detail.getFundAmountOut()).longValue()){
                //批的少
                FundDetailPO dp = new FundDetailPO();
                double tempBan = info.getFundAmountOut()-detail.getFundAmountOut();
                dp.setCommitTime(info.getCommitTime());
                dp.setFundBalance(tempBan);
                dp.setProjectId(info.getProjectId());
                fundCostPO.setProjectId(info.getProjectId());
                fundCostPO.setProjectBalance(info.getFundStatus()<5?detail.getFundAmountOut():-tempBan);
                fundCostPO.setProjectTotalAmount(info.getFundStatus()<5?detail.getFundAmountOut():-tempBan);
                fundCostPO.setTempBanlance(tempBan);
                if(fundCostDAO.updateRel1(fundCostPO) == 1 && fundDetailDao.update2(detail) == 1
                        && fundDetailDao.update33(dp) >= 0){
                    rspBo.setCode(20000);
                    rspBo.setDesc("审核通过成功");
                }else{
                    rspBo.setCode(9999);
                    rspBo.setDesc("审核通过失败");
                }
            }else if(new Double(info.getFundAmountOut()).longValue() < new Double(detail.getFundAmountOut()).longValue()){
                //批的多
                FundDetailPO fdp = new FundDetailPO();
                double tempBan = detail.getFundAmountOut()-info.getFundAmountOut();
                if(new Double(cp.getTempBanlance()).longValue() < new Double(tempBan).longValue()){
                    rspBo.setCode(9999);
                    rspBo.setDesc("额度不足，原因：现有额度小于申请额度超出的批复额度");
                    return rspBo;
                }
                fdp.setCommitTime(info.getCommitTime());
                fdp.setFundBalance(tempBan);
                fdp.setProjectId(info.getProjectId());
                fundCostPO.setProjectId(info.getProjectId());
                fundCostPO.setProjectBalance(info.getFundStatus()<5?detail.getFundAmountOut():tempBan);
                fundCostPO.setProjectTotalAmount(info.getFundStatus()<5?detail.getFundAmountOut():tempBan);
                fundCostPO.setTempBanlance(tempBan);
                if(fundCostDAO.updateRel2(fundCostPO) == 1 && fundDetailDao.update2(detail) == 1
                        && fundDetailDao.update3(fdp) >= 0){
                    rspBo.setCode(20000);
                    rspBo.setDesc("审核通过成功");
                }else{
                    rspBo.setCode(9999);
                    rspBo.setDesc("审核通过失败");
                }
            }
            //FundCostPO fundCostPO = fundCostDAO.selectInfoByPrimaryKey(detail.getProjectId());
        }else if(detail.getAskStatus() == 2){
            //不通过
            detail.setToDoMark(0);
            detail.setFundStatus(5);
            if(fundDetailDao.update1(detail) == 1){
                rspBo.setCode(20000);
                rspBo.setDesc("退回成功");
            }else{
                rspBo.setCode(9999);
                rspBo.setDesc("退回失败");
            }
        }else{
            return null;
        }
        return rspBo;
    }

    @Transactional
    @Override
    public RspBO move(FundDetailReqBO fundDetailReqBO){
        RspBO rspBo = new RspBO();
        FundDetailPO detail = new FundDetailPO();
        BeanUtils.copyProperties(fundDetailReqBO, detail);
        //转移申请
        FundCostPO fundCostPO = fundCostDAO.selectInfoByPrimaryKey(fundDetailReqBO.getProjectId());
        FundProjectPO fundProjectPO = fundProjectDAO.selectInfoById(fundDetailReqBO.getMoveProjectId());
        if(fundProjectPO == null || "".equals(fundProjectPO) || "".equals(fundProjectPO.getProjectId())){
            rspBo.setCode(9999);
            rspBo.setDesc("转移失败，原因：该项目不存在或不符合转移条件");
            return rspBo;
        }
        if(new Double(fundCostPO.getTempBanlance()).longValue() >= Long.parseLong(fundDetailReqBO.getMoveBanlance())){
            /*
                projectId=JCUT-2020-GZM-02-01, moveBanlance=641100, moveProjectId=123
                commitMain=梅忠翔, commitUserId=2019204010104,

                fundId=null, fundName=null, fundBalance=null,
                fundDesc=null, fundAmountOut=null, fundAmountIn=null,
                commitTime=null, futureTime=null, fundStatus=null,
                fundInOrOut=null, askStatus=null, fundType=null,
            */

            //落源数据
            detail.setFundId(String.valueOf(System.currentTimeMillis()));//科目编号
            detail.setFundName(11);
            detail.setFundDesc("资金转移至-"+fundDetailReqBO.getMoveProjectId());
            detail.setFundAmountOut(new Double(fundDetailReqBO.getMoveBanlance()).doubleValue());//项目支出
            detail.setFundBalance(fundCostPO.getTempBanlance()-detail.getFundAmountOut());

            detail.setAskStatus(4);//科目状态
            detail.setFutureTime(LocalDate.now());
            //fundDetailPO.setFundAmountIn(fundProjectReqBO.getProjectBudget()*100);//项目收入
            //fundDetailPO.setFundBalance(fundCostPO.getProjectBalance()-fundDetailReqBO.getFundAmountOut()*100);//项目余额
            detail.setFundStatus(1);//科目申请状态
            detail.setFundInOrOut(1);//科目收支状态
            detail.setFundType(5);//经费性质
            detail.setToDoMark(1);
            fundCostPO.setTempBanlance(-(new Double(fundDetailReqBO.getMoveBanlance()).doubleValue()));
            fundCostPO.setProjectId(fundDetailReqBO.getProjectId());

            if(fundDetailDao.insert(detail) == 1 && fundCostDAO.updateTempUp(fundCostPO) == 1){
                rspBo.setCode(20000);
                rspBo.setDesc("转移申请已提交，请等待审核");
                return rspBo;
            } else {
                rspBo.setCode(9999);
                rspBo.setDesc("转移申请提交失败");
                return rspBo;
            }

            //落目标数据
            /*detail1.setFundId(String.valueOf(System.currentTimeMillis()));//科目编号
            detail1.setFundName(12);
            detail1.setFundDesc("资金转移自-"+fundDetailReqBO.getMoveProjectId());
            detail1.setFundAmountIn(new Double(fundDetailReqBO.getMoveBanlance()).doubleValue()*100);//项目支出
            detail1.setFundBalance(fundCostPO1.getTempBanlance()+detail1.getFundAmountIn());

            detail1.setAskStatus(4);//科目状态
            detail1.setFutureTime(LocalDate.now());
            detail1.setFundStatus(1);//科目申请状态
            detail1.setFundInOrOut(0);//科目收支状态
            detail1.setToDoMark(1);
            fundCostPO.setTempBanlance(new Double(fundDetailReqBO.getMoveBanlance()).doubleValue()*100);
            fundCostPO.setProjectId(fundDetailReqBO.getMoveProjectId());*/
        }else{
            rspBo.setCode(9999);
            rspBo.setDesc("余额不足，原因：该项目含有待审核费用，请审核通过或退回后重试");
            return rspBo;
        }
    }

    @Transactional
    @Override
    public RspBO moveUpdate(FundDetailReqBO fundDetailReqBO) {
        RspBO rspBo = new RspBO();
        FundDetailPO detail = new FundDetailPO();
        BeanUtils.copyProperties(fundDetailReqBO, detail);
        FundDetailPO fundDetailPO = fundDetailDao.getInfoById(fundDetailReqBO.getFundId());
        String projectId = fundDetailPO.getFundDesc().substring(fundDetailPO.getFundDesc().indexOf("-")+1);
        if (fundDetailReqBO.getDesc() == 1) {
            //转移通过
            FundCostPO fundCostPO = fundCostDAO.selectInfoByPrimaryKey(projectId);
            FundDetailPO detail1 = new FundDetailPO();
            FundCostPO fundCostPO1 = new FundCostPO();
            /*
                projectId=JCUT-2020-GZM-02-01, moveBanlance=641100, moveProjectId=123
                commitMain=梅忠翔, commitUserId=2019204010104,

                fundId=null, fundName=null, fundBalance=null,
                fundDesc=null, fundAmountOut=null, fundAmountIn=null,
                commitTime=null, futureTime=null, fundStatus=null,
                fundInOrOut=null, askStatus=null, fundType=null,
            */

            //落源数据
            detail.setProjectId(projectId);
            detail.setFundId(String.valueOf(System.currentTimeMillis()));//科目编号
            detail.setFundName(12);
            detail.setFundDesc("资金接收自-" + fundDetailPO.getProjectId());
            detail.setFundAmountIn(fundDetailPO.getFundAmountOut());//项目支出
            detail.setFundBalance(fundCostPO.getTempBanlance() + fundDetailPO.getFundAmountOut());
            detail.setCommitTime(LocalDateTime.now());
            detail.setCommitMain(fundDetailPO.getCommitMain());
            detail.setCommitUserId(fundDetailPO.getCommitUserId());
            detail.setAskStatus(4);//科目状态
            detail.setFutureTime(LocalDate.now());
            //fundDetailPO.setFundAmountIn(fundProjectReqBO.getProjectBudget()*100);//项目收入
            //fundDetailPO.setFundBalance(fundCostPO.getProjectBalance()-fundDetailReqBO.getFundAmountOut()*100);//项目余额
            detail.setFundStatus(4);//科目申请状态
            detail.setFundInOrOut(0);//科目收支状态
            //fundDetailPO.setFundType(fundDetailReqBO.getFundType());//经费性质
            detail.setToDoMark(0);
            detail1.setFundId(fundDetailPO.getFundId());
            detail1.setProjectId(fundDetailPO.getProjectId());
            detail1.setToDoMark(0);
            detail1.setFundType(1);
            detail1.setFundStatus(4);
            fundCostPO1.setProjectBudget(fundDetailPO.getFundAmountOut());
            fundCostPO1.setProjectId(fundDetailPO.getProjectId());
            fundCostPO.setTempBanlance(fundDetailPO.getFundAmountOut());
            fundCostPO.setProjectId(projectId);

            if (fundDetailDao.insert(detail) == 1 && fundDetailDao.update1(detail1) == 1 && fundCostDAO.updateTemp1(fundCostPO) == 1 && fundCostDAO.updateTemp2(fundCostPO1) == 1) {
                rspBo.setCode(20000);
                rspBo.setDesc("转移预算成功");
                return rspBo;
            } else {
                rspBo.setCode(9999);
                rspBo.setDesc("转移预算失败");
                return rspBo;
            }
        } else if (fundDetailReqBO.getDesc() == 0) {
            //转移不通过
            detail.setFundBalance(fundDetailPO.getFundAmountOut());
            detail.setCommitTime(fundDetailPO.getCommitTime());
            detail.setProjectId(fundDetailPO.getProjectId());
            detail.setFundId(fundDetailPO.getFundId());
            FundCostPO fundCostPO1 = new FundCostPO();
            fundCostPO1.setTempBanlance(fundDetailPO.getFundAmountOut());
            fundCostPO1.setProjectId(fundDetailPO.getProjectId());

            if (fundDetailDao.deleteByFundId(detail.getFundId()) == 1 && fundDetailDao.update33(detail) >= 0 && fundCostDAO.updateTempUp(fundCostPO1) == 1) {
                rspBo.setCode(20000);
                rspBo.setDesc("转移预算退回成功");
                return rspBo;
            } else {
                rspBo.setCode(9999);
                rspBo.setDesc("转移预算退回失败");
                return rspBo;
            }
        } else {
            return null;
        }
    }

    public FundDetailRspBO selectInfoByPrimaryKey(String id){
        FundDetailRspBO rspBo = new FundDetailRspBO();
        FundDetailPO info = new FundDetailPO();
        info = fundDetailDao.selectInfoByPrimaryKey(id);
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

    public RspPageBO<FundDetailRspBO> queryList(FundDetailReqBO fundDetailReqBO){
        RspPageBO<FundDetailRspBO> rspBo = new RspPageBO<>();
        FundDetailPO info = new FundDetailPO();
        BeanUtils.copyProperties(fundDetailReqBO, info);
        List<FundDetailPO> list = fundDetailDao.queryList(info);
        FundDetailRspBO bo = null;
        if(list.size() > 0){
           List<FundDetailRspBO> data = new ArrayList<>();
           for(FundDetailPO po : list){
               bo = new FundDetailRspBO();
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

    public RspPageBO<FundDetailRspBO> queryPage(FundDetailReqBO fundDetailReqBO){
        RspPageBO<FundDetailRspBO> rspBo = new RspPageBO<>();
        FundDetailPO info = new FundDetailPO();
        BeanUtils.copyProperties(fundDetailReqBO, info);
        if(fundDetailReqBO.getDesc() == 0){
            info.setToDoMark(500);
            info.setFundStatus(0);
            info.setFieleName("commitTime");
        }else if(fundDetailReqBO.getDesc() == 1){
            info.setToDoMark(1);
            info.setFundStatus(1);
            info.setFieleName("commitTime");
        }else if(fundDetailReqBO.getDesc() == 2){
            info.setToDoMark(1);
            info.setFundStatus(2);
            info.setFieleName("commitTime");
        }else if(fundDetailReqBO.getDesc() == 3){
            info.setToDoMark(1);
            info.setFundStatus(3);
            info.setFieleName("commitTime");
        }else if(fundDetailReqBO.getDesc() == 5){
            info.setToDoMark(0);
            info.setFundStatus(5);
            info.setFieleName("commitTime");
        }else {}
        int size = fundDetailDao.querySize(info);
        if(size > 0){
           List<FundDetailRspBO> list = fundDetailDao.queryPage(info, fundDetailReqBO.getPageCount() == 1 ? 0 : fundDetailReqBO.getPageCount() * 10 - 10);
           if(list.size() > 0){
               int balance = 0;
               int pout = 0;
               int pin = 0;
              List<FundDetailRspBO> data = new ArrayList<>();
              for(int i = 0; i < list.size(); i++){//FundDetailPO po : list
                  balance = new Double(list.get(i).getFundBalance()==null?0.0:list.get(i).getFundBalance()).intValue();
                  pout = new Double(list.get(i).getFundAmountOut()== null?0.0:list.get(i).getFundAmountOut()).intValue();
                  pin = new Double(list.get(i).getFundAmountIn()==null?0.0:list.get(i).getFundAmountIn()).intValue();
                  list.get(i).setFundAmountOutStr(new DecimalFormat("0.00").format(pout/100.00)+"元");
                  list.get(i).setFundAmountOut(0.00);
                  if(balance > 10000000){
                      //12.3599万元
                      list.get(i).setFundBalanceStr(String.valueOf(balance/100/10000.00)+"万元");
                      list.get(i).setFundBalance(0.00);
                  }else{
                      //32122.98元
                      list.get(i).setFundBalanceStr(String.valueOf(balance/100.00)+"元");
                      list.get(i).setFundBalance(0.00);
                  }

                  if(pin > 10000000){
                      //12.3599万元
                      list.get(i).setFundAmountInStr(String.valueOf(pin/100/10000.00)+"万元");
                      list.get(i).setFundAmountIn(0.00);
                  }else{
                      //32122.98元
                      list.get(i).setFundAmountInStr(String.valueOf(pin/100.00)+"元");
                      list.get(i).setFundAmountIn(0.00);
                  }

                  /*if(pout > 10000000){
                      //12.3599万元
                      list.get(i).setFundAmountOutStr(String.valueOf(pout/100/10000.00)+"万元");
                      list.get(i).setFundAmountOut(0.00);
                  }else{
                      //32122.98元
                      list.get(i).setFundAmountOutStr(String.valueOf(pout/100.00)+"元");
                      list.get(i).setFundAmountOut(0.00);
                  }*/
              }
              rspBo.setData(list);
              rspBo.setTotalSize(size);
              rspBo.setCode(20000);
              rspBo.setDesc("查询成功");
           }else{
              rspBo.setCode(20000);
              rspBo.setDesc("没有符合条件的数据");
           }
        }else{
           rspBo.setCode(20000);
           rspBo.setDesc("没有符合条件的数据");
        }
        return rspBo;
    }
}
