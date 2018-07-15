package sharemer.business.api.master.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import sharemer.business.api.master.po.Reply;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;

/**
 * Created by 18073 on 2017/9/2.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyVo extends Reply implements java.io.Serializable {

    private static final long serialVersionUID = 5591782080949551553L;

    private User user;

    private Integer is_like;

    private Integer is_dislike;

    private Page<ReplyVo> child_replies;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Page<ReplyVo> getChild_replies() {
        return child_replies;
    }

    public void setChild_replies(Page<ReplyVo> child_replies) {
        this.child_replies = child_replies;
    }

    public User getClearUser(User user){
        if(user != null){
            User result = new User();
            result.setId(user.getId());
            result.setName(user.getName());
            result.setLevel(user.getLevel());
            result.setAvater(user.getAvater());
            if(user.getLevel() != null){
                result.setLevel_color(Constant.User.getLevelColor(user.getLevel()));
            }
            return result;
        }
        return null;
    }

    public Integer getIs_like() {
        return is_like;
    }

    public void setIs_like(Integer is_like) {
        this.is_like = is_like;
    }

    public Integer getIs_dislike() {
        return is_dislike;
    }

    public void setIs_dislike(Integer is_dislike) {
        this.is_dislike = is_dislike;
    }
}
