package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.MusicVideo;
import sharemer.business.api.master.po.Video;

import java.util.List;

/**
 * Created by 18073 on 2017/6/8.
 */
@Mapper
public interface MusicVideoMapper {

    void insertMusicVideo(@Param("num") Integer num, @Param("pojo") MusicVideo musicVideo);

    void insertVideoMusic(@Param("num") Integer num, @Param("pojo") MusicVideo musicVideo);

    MusicVideo getOneFromMusicVideoByMusicIdAndVideoId(@Param("num") Integer num,
                                                       @Param("music_id") Integer music_id,
                                                       @Param("video_id") Integer video_id);

    List<Video> getVideosByMusicId(@Param("num") Integer num, @Param("music_id") Integer music_id);

    List<Integer> getVideoIdsByMusicId(@Param("num") Integer num, @Param("music_id") Integer music_id);
}
