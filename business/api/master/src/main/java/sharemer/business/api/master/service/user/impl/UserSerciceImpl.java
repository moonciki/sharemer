package sharemer.business.api.master.service.user.impl;

import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.service.user.UserSercice;
import sharemer.business.api.master.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 18073 on 2017/9/24.
 */
@Service("userSercice")
public class UserSerciceImpl implements UserSercice{

    @Resource
    private UserMao userMao;


    @Override
    public UserVo getUserInfo(Integer userId) {
        User user = userMao.getBaseOne(userId);
        if(user.getId() == null){
            return null;
        }
        UserVo userVo = new UserVo();
        userVo.setUser_info(user);
        return userVo;
    }
}
