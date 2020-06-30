package com.meizx.project_fund_manager_system.service.impl;

import com.meizx.project_fund_manager_system.dao.po.UserPO;
import com.meizx.project_fund_manager_system.dao.UserDAO;
import com.meizx.project_fund_manager_system.service.UserService;
import com.meizx.project_fund_manager_system.bo.UserRspBO;
import com.meizx.project_fund_manager_system.bo.UserReqBO;
import com.meizx.project_fund_manager_system.base.RspBO;
import com.meizx.project_fund_manager_system.base.RspPageBO;
import com.meizx.project_fund_manager_system.utils.DESUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.ArrayList;

/**
* @author: meizx
* @className: UserPO 服务实现类
* @desc:
* @date: 2020-04-26T10:19
* @version: 1.0.0
*/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;

    @Override
    public RspBO insert(UserReqBO userReqBO){
        RspBO rspBo = new RspBO();
        UserPO info = new UserPO();
        if(!("".equals(userReqBO.getUserId()) || "".equals(userReqBO.getUserName()) || "".equals(userReqBO.getUserPwd()) || "".equals(userReqBO.getUserMark()))){
            if(userDao.getCount(userReqBO.getUserId()) > 0){
                rspBo.setCode(9999);
                rspBo.setDesc("账号已被注册");
                return rspBo;
            }
            BeanUtils.copyProperties(userReqBO, info);
            info.setUserPwd(DESUtil.encryptBasedDes(userReqBO.getUserPwd()));
            info.setUserMail("");
            info.setRoles("['editor']");
            info.setUserStatus(1);
            if(userDao.insert(info) == 1){
                rspBo.setCode(20000);
                rspBo.setDesc("注册成功");
            }else{
                rspBo.setCode(9999);
                rspBo.setDesc("注册失败");
            }
        }else{
            rspBo.setCode(9999);
            rspBo.setDesc("注册失败");
        }
        return rspBo;
    }

    @Override
    public RspBO deleteById(String id){
        RspBO rspBo = new RspBO();
        if("".equals(id)){
            rspBo.setCode(9999);
            rspBo.setDesc("参数不完整");
            return rspBo;
        }
        if(userDao.deleteByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("删除成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("删除失败");
        }
        return rspBo;
    }

    @Override
    public RspBO delete(UserReqBO userReqBO){
        RspBO rspBo = new RspBO();
        if("".equals(userReqBO.getUserId())){
            rspBo.setCode(9999);
            rspBo.setDesc("参数不完整");
            return rspBo;
        }
        UserPO info = new UserPO();
        BeanUtils.copyProperties(userReqBO, info);
        if(userDao.delete(info) == 1){
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
        if(userDao.updateByPrimaryKey(id) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("修改成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    @Override
    public RspBO updateByUserId(UserReqBO userReqBO){
        RspBO rspBo = new RspBO();
        UserPO info = new UserPO();
        BeanUtils.copyProperties(userReqBO, info);
        info.setUserPwd(DESUtil.encryptBasedDes(userReqBO.getUserPwd()));
        if(userDao.updateByUserId(info) == 1){
           rspBo.setCode(20000);
           rspBo.setDesc("修改成功");
        }else{
           rspBo.setCode(9999);
           rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    @Override
    public RspBO update(UserReqBO userReqBO){
        RspBO rspBo = new RspBO();
        if("".equals(userReqBO.getUserId())){
            rspBo.setCode(9999);
            rspBo.setDesc("参数不完整");
            return rspBo;
        }
        UserPO info = new UserPO();
        if(userReqBO.getDesc() == 0){
            //更改用户状态为：已停用
            info.setUserId(userReqBO.getUserId());
            info.setUserStatus(0);
        }else if(userReqBO.getDesc() == 1){

        }else if(userReqBO.getDesc() == 2){
            //更改用户状态为：已启用
            if(userDao.getCount(userReqBO.getUserId()) == 1){
                info.setUserId(userReqBO.getUserId());
                info.setUserStatus(2);
            }else{
                rspBo.setCode(9999);
                rspBo.setDesc("该账号已存在，请联系管理员");
                return rspBo;
            }
        }else if(userReqBO.getDesc() == 3){
            //更改用户密码
            info.setUserStatus(2);
            info.setUserId(userReqBO.getUserId());
            info.setUserPwd(DESUtil.encryptBasedDes(userReqBO.getUserPwd()));
        }else{
            BeanUtils.copyProperties(userReqBO, info);
        }
        if(userDao.update(info) == 1){
            rspBo.setCode(20000);
            rspBo.setDesc("修改成功");
        }else{
            rspBo.setCode(9999);
            rspBo.setDesc("修改失败");
        }
        return rspBo;
    }

    @Override
    public UserRspBO selectInfoByPrimaryKey(String id){
        UserRspBO rspBo = new UserRspBO();
        UserPO info = new UserPO();
        info = userDao.selectInfoByPrimaryKey(id);
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
    public RspPageBO<UserRspBO> queryList(UserReqBO userReqBO){
        RspPageBO<UserRspBO> rspBo = new RspPageBO<>();
        UserPO info = new UserPO();
        BeanUtils.copyProperties(userReqBO, info);
        List<UserPO> list = userDao.queryList(info);
        UserRspBO bo = null;
        if(list.size() > 0){
           List<UserRspBO> data = new ArrayList<>();
           for(UserPO po : list){
               bo = new UserRspBO();
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

    @Override
    public RspPageBO<UserRspBO> queryPage(UserReqBO userReqBO){
        RspPageBO<UserRspBO> rspBo = new RspPageBO<>();
        UserPO info = new UserPO();
        BeanUtils.copyProperties(userReqBO, info);
        int size = userDao.querySize(info);
        info.setFieleName("createTime");
        info.setOrders("desc");
        if(size > 0){
           List<UserPO> list = userDao.queryPage(info, userReqBO.getPageCount() == 1 ? 0 : userReqBO.getPageCount() * 10 - 10);
           UserRspBO bo = null;
           if(list.size() > 0){
              List<UserRspBO> data = new ArrayList<>();
              for(UserPO po : list){
                  bo = new UserRspBO();
                  BeanUtils.copyProperties(po, bo);
                  data.add(bo);
              }
              rspBo.setTotalSize(size);
              rspBo.setData(data);
              rspBo.setCode(20000);
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
