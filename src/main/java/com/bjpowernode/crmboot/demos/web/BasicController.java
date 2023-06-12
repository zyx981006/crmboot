/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bjpowernode.crmboot.demos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Controller
public class BasicController {

    @Autowired
    private UserService userService;
    // http://127.0.0.1:8080/hello?name=lisi
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String hello(HttpServletRequest request) {
        System.out.println(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/");
        return "settings/qx/user/login" ;
    }

    // http://127.0.0.1:8080/user
    @RequestMapping("settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct , String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response){


        Map<String,Object> map=new HashMap<String, Object>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user= userService.queryUserByLoginActAndPwd(map);
        ReturnObject returnObject=new ReturnObject();
        if (user==null){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或者密码错误");
        }
        else {
            if(DateUtils.formateDateTime(new Date()).compareTo(user.getExpireTime())>0){

                //登录失败，账号已过期
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期");
            }
            else if ("0".equals(user.getLockState())){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号锁定");
            }
            else if (!user.getAllowIps().contains(request.getRemoteAddr())){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("ip受限");

            }

            else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                session.setAttribute(Contants.SESSION_USER,user);
                if ("true".equals(isRemPwd)){
                    Cookie c1=  new Cookie("loginAct",user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2=  new Cookie("loginPwd",user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }
                else {
                    Cookie c1=  new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2=  new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }

        }
        return returnObject;
    }

    // http://127.0.0.1:8080/save_user?name=newName&age=11

    // http://127.0.0.1:8080/html
    @RequestMapping("/")
    public String html() {
        return "index";
    }


}
