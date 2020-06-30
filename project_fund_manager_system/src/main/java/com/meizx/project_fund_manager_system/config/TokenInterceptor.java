package com.meizx.project_fund_manager_system.config;

import com.alibaba.fastjson.JSONObject;
import com.meizx.project_fund_manager_system.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: meizx
 * @ClassName: TokenInterceptor
 * @Date: 2020/4/28 10:25
 * @E-mail： meizxbj@gmail.com
 * @Description: TokenInterceptor
 * @Version: V1.0.0
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisServce redisServce;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("X-Token");
        if(token != null){
            boolean result = TokenUtil.verify(token);
            if(result && redisServce.hasKey(TokenUtil.verify(token, 0))){
                System.out.println("通过拦截器");
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try{
            JSONObject json = new JSONObject();
            json.put("msg","token verify fail");
            json.put("code","50000");
            response.getWriter().append(json.toJSONString());
            System.out.println("认证失败，未通过拦截器");
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
        return false;
    }
}
