package sharemer.business.manager.master.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sharemer.business.manager.master.dao.AdminMapper;
import sharemer.business.manager.master.po.Admin;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 18073 on 2017/5/21.
 */
@RestController
public class LoginController {

    @Resource
    private AdminMapper adminMapper;

    @RequestMapping("/login")
    public ModelAndView home(HttpServletRequest request) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        ModelAndView view;
        if(admin != null){
           view = new ModelAndView("redirect:/");
           return view;
        }
        view = new ModelAndView("login");
        return view;
    }

    @RequestMapping("/real_login")
    public WrappedResult realLogin(@RequestParam(value = "name")String name,
                                   @RequestParam(value = "password")String password,
                                   HttpServletRequest request){
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(password)){
            return WrappedResult.success(1);
        }
        Admin admin = adminMapper.getOneByNameAndPassword(name, DigestUtils.md5Hex(password));
        if(admin != null){
            request.getSession().setAttribute("admin", admin);
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
