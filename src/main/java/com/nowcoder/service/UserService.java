package com.nowcoder.service;

import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.User;
import com.nowcoder.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public Map<String,Object> register(String username,String password) {
        Map<String,Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msgname","用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msgpwd","密码不能为空");
            return  map;
        }

        User user = userDAO.selectByName(username);
        if (user!=null) {
            map.put("msgname","用户名已经被注册");
            return map;
        }

         user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
       // user.getHeadUrl(String.format("http://images.nowcoder.com/head/%dm.png", new Random().nextInt(1000)));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);
        return  map;
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
}
