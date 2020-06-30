package com.meizx.project_fund_manager_system.utils;

import com.meizx.project_fund_manager_system.config.RedisServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: meizx
 * @ClassName: RedisUtil
 * @Date: 2020/4/28 14:22
 * @E-mail： meizxbj@gmail.com
 * @Description: RedisUtil
 * @Version: V1.0.0
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisServce redisServce;

    public boolean layout(String token){
        String userId = TokenUtil.verify(token, 0);
        if(redisServce.hasKey(userId)){
            String tk = redisServce.get(userId);
            if((!"".equals(tk)) && tk.equals(token)){
                redisServce.del(userId);
                return true;
            }
        }
        return false;
    }
}
