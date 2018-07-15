package sharemer.business.manager.master.mao.impl;

import org.springframework.stereotype.Repository;
import sharemer.business.manager.master.mao.AdminMao;
import sharemer.business.manager.master.mao.BaseMao;
import sharemer.business.manager.master.po.Admin;

/**
 * Created by 18073 on 2017/5/21.
 */
@Repository
public class AdminMaoImpl extends BaseMao implements AdminMao {

    @Override
    public Admin getAdminByToken(String token) {
        //(Admin) getIfPresent(MemcachedKeys.getAdmin(token))
        Admin admin = new Admin();
        admin.setId(1);
        admin.setName("sunqinwen");
        admin.setPassword("123456");
        return admin;
    }

}
