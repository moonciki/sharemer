package sharemer.business.manager.master.mao;

import sharemer.business.manager.master.po.Admin;

/**
 * Created by 18073 on 2017/5/21.
 */
public interface AdminMao {

    Admin getAdminByToken(String token);

}
