package com.bjpowernode.crmboot.demos.web;



import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserByLoginActAndPwd(Map<String,Object> map);
    List<User> queryAllUsers();
}
