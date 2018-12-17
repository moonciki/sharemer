package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.Danmaku;

import java.util.List;

/**
 * Create by 18073 on 2018/12/17.
 */
@Mapper
public interface DanmakuMapper {

    void insert(@Param("num") Integer num, @Param("pojo") Danmaku danmaku);

    List<Danmaku> getDanmakusByAid(@Param("num") Integer num, @Param("archive_id") Integer archive_id);
}
