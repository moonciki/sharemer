package sharemer.business.manager.master.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.business.manager.master.anno.NeedLogin;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.po.User;
import sharemer.business.manager.master.service.UserService;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;

/**
 * Created by 18073 on 2017/5/6.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "one", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult one(@RequestParam(value = "id")Integer id) throws Exception{
        User user = this.userService.getOne(id);
        return WrappedResult.success(user);
    }

    @RequestMapping(value = "txtest", method = RequestMethod.GET)
    public WrappedResult txtest(@RequestParam(value = "id")Integer id){
        return WrappedResult.success(this.userService.updAndAdd(id));
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult list(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception{
        Page<User> page = new Page<>(pageNo, pageSize);
        userService.getAllUser(page);
        return WrappedResult.success(page);
    }

}
