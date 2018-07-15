package sharemer.business.api.master.rao.user;

/**
 * Created by 18073 on 2017/8/27.
 */
public interface UserRao {

    void putLoginToken(String token, Integer uid);

    Integer getLoginToken(String token);

    String getCsrfToken();
}
