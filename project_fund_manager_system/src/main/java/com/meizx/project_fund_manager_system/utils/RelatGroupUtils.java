package com.meizx.project_fund_manager_system.utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: meizx
 * @ClassName: RelatGroupUtils
 * @Date: 2020/6/14 19:36
 * @E-mail： meizxbj@gmail.com
 * @Description: RelatGroupUtils
 * @Version: V1.0.0
 */
public class RelatGroupUtils {

    public String[] changeGroup(String group){
        return Pattern.compile("[^0-9-]+").split(group);
    }

    public static void main(String[] args) {
        System.out.println(LocalDate.now().getYear());
    }
}
