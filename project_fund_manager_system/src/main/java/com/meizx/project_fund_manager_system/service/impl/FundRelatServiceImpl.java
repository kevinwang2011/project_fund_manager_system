package com.meizx.project_fund_manager_system.service.impl;

import com.meizx.project_fund_manager_system.bo.FundProjectRspBO;
import com.meizx.project_fund_manager_system.dao.FundPersonDAO;
import com.meizx.project_fund_manager_system.dao.po.FundPersonPO;
import com.meizx.project_fund_manager_system.dao.po.FundProjectPO;
import com.meizx.project_fund_manager_system.dao.po.FundRelatPO;
import com.meizx.project_fund_manager_system.dao.FundRelatDAO;
import com.meizx.project_fund_manager_system.service.FundRelatService;
import com.meizx.project_fund_manager_system.bo.FundRelatRspBO;
import com.meizx.project_fund_manager_system.bo.FundRelatReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
* @author: meizx
* @className: FundRelatPO 服务实现类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
@Service
public class FundRelatServiceImpl implements FundRelatService {

    @Autowired
    private FundRelatDAO fundRelatDao;
    @Autowired
    private FundPersonDAO fundPersonDAO;

    @Transactional
    @Override
    public RspBO insert(FundRelatReqBO fundRelatReqBO){
        RspBO rspBo = new RspBO();
        FundRelatPO info = new FundRelatPO();
        BeanUtils.copyProperties(fundRelatReqBO, info);
        info.setRelatStatus(1);
        FundPersonPO fundPersonPO = fundPersonDAO.selectInfoByPrimaryKey(fundRelatReqBO.getProjectId());
        if(fundPersonPO == null || fundPersonPO.getProjectGroup() == null || "".equals(fundPersonPO.getProjectGroup())){
            rspBo.setCode(9999);
            rspBo.setDesc("项目不存在");
            return rspBo;
        }
        List<String> str = Arrays.asList(fundPersonPO.getProjectGroup().trim().split(","));
        for (int i = 0; i < str.size(); i++) {
            if(info.getUserId().equals(str.get(i).trim())){
                rspBo.setCode(9999);
                rspBo.setDesc("账户已关联该项目");
                return rspBo;
            }
        }
        fundPersonPO.setGroupName(fundPersonPO.getGroupName()+"、"+fundRelatReqBO.getUserName().trim());
        fundPersonPO.setProjectGroup(fundPersonPO.getProjectGroup()+","+fundRelatReqBO.getUserId().trim());
        if(fundRelatDao.insert(info) == 1 && fundPersonDAO.update(fundPersonPO) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("关联成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("关联失败");
        }
        return rspBo;
    }

    public RspBO deleteById(String id){
        RspBO rspBo = new RspBO();
        if(fundRelatDao.deleteByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("删除成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("删除失败");
        }
        return rspBo;
    }

    @Transactional
    @Override
    public RspBO delete(FundRelatReqBO fundRelatReqBO){
        RspBO rspBo = new RspBO();
        FundRelatPO info = new FundRelatPO();
        FundPersonPO fundPersonPO = fundPersonDAO.selectInfoByPrimaryKey(fundRelatReqBO.getProjectId().trim());
        if(fundPersonPO == null || fundPersonPO.getProjectGroup() == null || "".equals(fundPersonPO.getProjectGroup())){
            rspBo.setCode(9999);
            rspBo.setDesc("项目不存在");
            return rspBo;
        }
        List<String> str = Arrays.asList(fundPersonPO.getProjectGroup().trim().split(","));
        int flag= 2;
        for (int i = 0; i < str.size(); i++) {
            if(fundRelatReqBO.getUserId().equals(str.get(i).trim())){
                flag = 1;
                if(i == 0){
                    flag = 3;
                }
            }
        }
        if(flag == 2){
            rspBo.setCode(9999);
            rspBo.setDesc("账户没有关联该项目");
            return rspBo;
        } else if(flag == 3){
            rspBo.setCode(9999);
            rspBo.setDesc("账户是该项目创建者，不能取消关联该项");
            return rspBo;
        } else { }
        fundPersonPO.setGroupName(fundPersonPO.getGroupName().replace("、"+fundRelatReqBO.getUserName(), ""));
        fundPersonPO.setProjectGroup(fundPersonPO.getProjectGroup().replace(","+fundRelatReqBO.getUserId(), ""));
        BeanUtils.copyProperties(fundRelatReqBO, info);
        if(fundRelatDao.delete(info) == 1 && fundPersonDAO.update(fundPersonPO) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("解除关联成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("解除关联失败");
        }
        return rspBo;
    }

    public RspBO updateById(String id){
        RspBO rspBo = new RspBO();
        if(fundRelatDao.updateByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("修改成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    public RspBO update(FundRelatReqBO fundRelatReqBO){
        RspBO rspBo = new RspBO();
        FundRelatPO info = new FundRelatPO();
        BeanUtils.copyProperties(fundRelatReqBO, info);
        if(fundRelatDao.update(info) == 1){
            rspBo.setCode(20000);
            rspBo.setDesc("修改成功");
        }else{
            rspBo.setCode(9999);
            rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    public FundRelatRspBO selectInfoByPrimaryKey(String id){
        FundRelatRspBO rspBo = new FundRelatRspBO();
        FundRelatPO info = new FundRelatPO();
        info = fundRelatDao.selectInfoByPrimaryKey(id);
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

    public RspPageBO<FundRelatRspBO> queryList(FundRelatReqBO fundRelatReqBO){
        RspPageBO<FundRelatRspBO> rspBo = new RspPageBO<>();
        FundRelatPO info = new FundRelatPO();
        BeanUtils.copyProperties(fundRelatReqBO, info);
        List<FundRelatPO> list = null;
        if(fundRelatReqBO.getDesc() == 1){
            list = fundRelatDao.queryList(fundRelatReqBO.getUserId());
        } else if(fundRelatReqBO.getDesc() == 2){
            list = fundRelatDao.queryData(fundRelatReqBO.getUserId());
        } else {}
        FundRelatRspBO bo = null;
        if(list.size() > 0){
           List<FundRelatRspBO> data = new ArrayList<>();
            for(FundRelatPO po : list){
                bo = new FundRelatRspBO();
                bo.setValue(po.getProjectName()+"("+(po.getProjectStatus()==0?"已结项":(po.getProjectStatus()==1?"未结项":(po.getProjectStatus()==2?"待审核":"待结项")))+")-"+po.getProjectId());
                bo.setProjectId(po.getProjectId());
                data.add(bo);
            }
           rspBo.setData(data);
           rspBo.setTotalSize(data.size());
           rspBo.setCode(20000);
           rspBo.setDesc("查询成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("查询失败");
        }
        return rspBo;
    }

    public RspPageBO<FundRelatRspBO> queryPage(FundRelatReqBO fundRelatReqBO){
        RspPageBO<FundRelatRspBO> rspBo = new RspPageBO<>();
        FundRelatPO info = new FundRelatPO();
        BeanUtils.copyProperties(fundRelatReqBO, info);
        int size = fundRelatDao.querySize(info);
        if(size > 0){
           List<FundRelatPO> list = fundRelatDao.queryPage(info, fundRelatReqBO.getPageCount());
           FundRelatRspBO bo = null;
           if(list.size() > 0){
              List<FundRelatRspBO> data = new ArrayList<>();
              for(FundRelatPO po : list){
                  bo = new FundRelatRspBO();
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
}
