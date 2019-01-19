package sharemer.business.api.master.controller;

import sharemer.business.api.master.anno.NeedLogin;
import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PathController {

    @Resource
    private UserRao userRao;

    @RequestMapping("/")
    @NeedUser
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("home");
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        String csrfToken = this.userRao.getCsrfToken();
        view.addObject("SCRIPT", generate(user, csrfToken));
        view.addObject("token", csrfToken);
        return view;
    }

    @RequestMapping("/player")
    @NeedUser
    public ModelAndView player(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("player");
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        String csrfToken = this.userRao.getCsrfToken();
        view.addObject("SCRIPT", generate(user, csrfToken));
        view.addObject("token", csrfToken);
        return view;
    }

    @RequestMapping("/blog")
    @NeedUser
    public ModelAndView blog(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("blog");
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        String csrfToken = this.userRao.getCsrfToken();
        view.addObject("SCRIPT", generate(user, csrfToken));
        view.addObject("token", csrfToken);
        return view;
    }

    @RequestMapping("/manager")
    @NeedUser
    @NeedLogin
    public ModelAndView manager(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("manager");
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        String csrfToken = this.userRao.getCsrfToken();
        view.addObject("SCRIPT", generate(user, csrfToken));
        view.addObject("token", csrfToken);
        return view;
    }

    /**
     * CSRF_TOKEN
     */
    @RequestMapping("/pc_api/get_token")
    public WrappedResult getToken() {
        return WrappedResult.success(userRao.getCsrfToken());
    }

    /**
     * 设置登录用户全局变量
     */
    private static String generate(User user, String csrfToken) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<script type=\"text/javascript\">").append("var SHION = ");
        Map<String, Object> operad = new HashMap<String, Object>();
        if (user != null) {
            operad.put("currentUser", user);
        }
        operad.put("w_token", csrfToken);
        buffer.append(JsonUtil.toJson(operad, true)).append(";</script>");
        return buffer.toString();
    }
}
