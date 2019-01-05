package sharemer.business.api.master.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.business.api.master.service.user.UserSercice;
import sharemer.business.api.master.vo.UserVo;
import sharemer.component.global.resp.ConstantResults;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;

/**
 * Create by 18073 on 2018/12/30.
 */
@RestController
@RequestMapping(value = "/blog")
public class BlogController {

    @Resource
    private UserSercice userSercice;

    @RequestMapping(value = "get_user_info", method = RequestMethod.GET)
    public WrappedResult list(@RequestParam(value = "user_id") Integer user_id) {
        UserVo userVo = this.userSercice.getUserInfo(user_id);
        if(userVo == null){
            return WrappedResult.fail(ConstantResults.NODATA);
        }
        return WrappedResult.success(userVo);
    }

}
