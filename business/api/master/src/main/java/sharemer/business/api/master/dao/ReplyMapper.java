package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.Reply;
import sharemer.business.api.master.vo.ReplyVo;

import java.util.List;

/**
 * Created by 18073 on 2017/9/2.
 */
@Mapper
public interface ReplyMapper {

    void insert(@Param("num") Integer num,
                @Param("pojo") Reply reply);

    void insertU(@Param("num") Integer num,
                 @Param("pojo") Reply reply);

    ReplyVo getBaseOne(@Param("num") Integer num,
                       @Param("id") Long id);

    List<ReplyVo> getReplysByOid(@Param("num") Integer num,
                                 @Param("oid") Integer oid,
                                 @Param("otype") Integer otype);

    List<ReplyVo> getChildReplysByOid(@Param("num") Integer num,
                                      @Param("reply_id") Integer reply_id,
                                      @Param("otype") Integer otype);

    List<ReplyVo> getBaseList(@Param("num") Integer num,
                              @Param("ids") List<Long> ids);

    void like(@Param("num") Integer num,
              @Param("reply_id") Long reply_id);

    void cancel_like(@Param("num") Integer num,
                     @Param("reply_id") Long reply_id);

    void dislike(@Param("num") Integer num,
                 @Param("reply_id") Long reply_id);

    void cancel_dislike(@Param("num") Integer num,
                        @Param("reply_id") Long reply_id);


}
