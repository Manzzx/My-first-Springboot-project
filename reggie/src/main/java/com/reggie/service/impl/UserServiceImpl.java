package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import com.reggie.entity.User;
import com.reggie.exception.SystemException;
import com.reggie.mapper.UserMapper;
import com.reggie.service.UserService;
import com.reggie.utils.SMSUtils;
import com.reggie.utils.UserNameRandomUtils;
import com.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 发送手机短信
     * @param user
     * @param request
     * @return
     */
    @Override
    public R<String> senMsg(User user, HttpServletRequest request) {
        //获取用户手机号
        String phone = user.getPhone();
        if (Strings.isNotEmpty(phone)){
            //生成验证码
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            //调用阿里云短信服务API发送短信
//            SMSUtils.sendMessage("*****","*******",phone,code);
            log.info("code==========="+code);
            //将验证码存起来用作验证
            request.getSession().setAttribute(phone,code);
            return R.success(code);
        }
        return R.error("短信发送失败");
    }

    /**
     * 移动端登录验证
     * @param map
     * @param request
     * @return
     */
    @Override
    public R<User> login(Map map, HttpServletRequest request) {
        //获取用户输入的手机号和验证码
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        //用用户输入的手机号查看是否能获取到验证码
        String codeInSession = (String) request.getSession().getAttribute(phone);
        //进行匹对用户输入验证码和短信验证码
        if (codeInSession!=null&&codeInSession.equals(code)){//匹对成功
            //登录成功，查看是否是新用户
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone,phone);
            User user = userMapper.selectOne(lqw);
            if (user==null){
                //新用户开始插入数据库
                user=new User();
                user.setName(UserNameRandomUtils.getRandomJianHan(5));
                user.setPhone(phone);
                user.setStatus(1);
                try {
                    userMapper.insert(user);
                } catch (Exception e) {
                   throw new SystemException("用户登录失败",e);
                }
            }
            request.getSession().setAttribute("user",user.getId());
            return R.success(user);
        }


        return R.error("登录失败");
    }

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    @Override
    public R<String> logOut(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return R.success("成功");
    }

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public R<User> getUser() {
        User user = userMapper.selectById(BaseContext.getCurrentId());
        if (user==null){
            throw new SystemException("用户名查询失败");
        }
        return R.success(user);
    }
}
