package sharemer.business.api.master.controller;

import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.dao.UserMapper;
import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.utils.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by 18073 on 2017/5/21.
 */
@RestController
public class LoginController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserMao userMao;

    @Resource
    private UserRao userRao;

    @RequestMapping("/login")
    @NeedUser
    public ModelAndView home(HttpServletRequest request, ModelAndView view)
            throws IOException {
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        if(user == null){
            view = new ModelAndView("login");
        }else{
            view = new ModelAndView("redirect:/");
        }
        return view;
    }

    @RequestMapping("/real_login")
    public WrappedResult realLogin(@RequestParam(value = "email")String email,
                                   @RequestParam(value = "password")String password,
                                   HttpServletRequest request, HttpServletResponse response){
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            return WrappedResult.success(1);
        }
        Integer uid = userMapper.getUserIdByNameAndPwd(email, DigestUtils.md5Hex(password));
        if(uid != null){
            User user = userMao.getBaseOne(uid);
            if(user == null){
                return WrappedResult.success(2);
            }
            /** 生成一个uuid作为登录状态令牌*/
            String token = UUID.randomUUID().toString();
            userRao.putLoginToken(token, uid);
            /** 将令牌写入cookie*/
            Cookie cookie = new Cookie(Constant.LOGIN_TOKEN, token);
            cookie.setMaxAge(Constant.LOGIN_TIME);//设置cookie最大存在时间
            response.addCookie(cookie);
            return WrappedResult.success(0);
        }else{
            return WrappedResult.success(2);
        }
    }

    @RequestMapping("/logout")
    public WrappedResult logout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return WrappedResult.success();
    }

}
