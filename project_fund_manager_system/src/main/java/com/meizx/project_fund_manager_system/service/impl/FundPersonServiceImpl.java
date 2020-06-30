package com.meizx.project_fund_manager_system.service.impl;

import com.meizx.project_fund_manager_system.dao.po.FundPersonPO;
import com.meizx.project_fund_manager_system.dao.FundPersonDAO;
import com.meizx.project_fund_manager_system.service.FundPersonService;
import com.meizx.project_fund_manager_system.bo.FundPersonRspBO;
import com.meizx.project_fund_manager_system.bo.FundPersonReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.ArrayList;

/**
* @author: meizx
* @className: FundPersonPO 服务实现类
* @desc:
* @date: 2020-06-13T10:01
* @version: 1.0.0
*/
@Service
public class FundPersonServiceImpl implements FundPersonService {

    @Autowired
    private FundPersonDAO fundPersonDao;

    public RspBO insert(FundPersonReqBO fundPersonReqBO){
        RspBO rspBo = new RspBO();
        FundPersonPO info = new FundPersonPO();
        BeanUtils.copyProperties(fundPersonReqBO, info);
        if(fundPersonDao.insert(info) == 1){
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
        if(fundPersonDao.deleteByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("删除成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("删除失败");
        }
        return rspBo;
    }

    public RspBO delete(FundPersonReqBO fundPersonReqBO){
        RspBO rspBo = new RspBO();
        FundPersonPO info = new FundPersonPO();
        BeanUtils.copyProperties(fundPersonReqBO, info);
        if(fundPersonDao.delete(info) == 1){
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
        if(fundPersonDao.updateByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("修改成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    public RspBO update(FundPersonReqBO fundPersonReqBO){
        RspBO rspBo = new RspBO();
        FundPersonPO info = new FundPersonPO();
        BeanUtils.copyProperties(fundPersonReqBO, info);
        if(fundPersonDao.update(info) == 1){
            rspBo.setCode(20000);
            rspBo.setDesc("修改成功");
        }else{
            rspBo.setCode(9999);
            rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    public FundPersonRspBO selectInfoByPrimaryKey(String id){
        FundPersonRspBO rspBo = new FundPersonRspBO();
        FundPersonPO info = new FundPersonPO();
        info = fundPersonDao.selectInfoByPrimaryKey(id);
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

    public RspPageBO<FundPersonRspBO> queryList(FundPersonReqBO fundPersonReqBO){
        RspPageBO<FundPersonRspBO> rspBo = new RspPageBO<>();
        FundPersonPO info = new FundPersonPO();
        BeanUtils.copyProperties(fundPersonReqBO, info);
        List<FundPersonPO> list = fundPersonDao.queryList(info);
        FundPersonRspBO bo = null;
        if(list.size() > 0){
           List<FundPersonRspBO> data = new ArrayList<>();
           for(FundPersonPO po : list){
               bo = new FundPersonRspBO();
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

    public RspPageBO<FundPersonRspBO> queryPage(FundPersonReqBO fundPersonReqBO){
        RspPageBO<FundPersonRspBO> rspBo = new RspPageBO<>();
        FundPersonPO info = new FundPersonPO();
        BeanUtils.copyProperties(fundPersonReqBO, info);
        int size = fundPersonDao.querySize(info);
        if(size > 0){
           List<FundPersonPO> list = fundPersonDao.queryPage(info, fundPersonReqBO.getPageCount());
           FundPersonRspBO bo = null;
           if(list.size() > 0){
              List<FundPersonRspBO> data = new ArrayList<>();
              for(FundPersonPO po : list){
                  bo = new FundPersonRspBO();
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
