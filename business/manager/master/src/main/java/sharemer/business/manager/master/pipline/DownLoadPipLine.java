/**
 * sharemer.com Inc.
 * Copyright (c) 2009-2018 All Rights Reserved.
 */
package sharemer.business.manager.master.pipline;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sharemer.business.manager.master.config.properties.ConstantProperties;
import sharemer.business.manager.master.dao.FavListMapper;
import sharemer.business.manager.master.dao.ImagesMapper;
import sharemer.business.manager.master.dao.MusicMapper;
import sharemer.business.manager.master.dao.VideoMapper;
import sharemer.business.manager.master.po.FavList;
import sharemer.business.manager.master.po.Images;
import sharemer.business.manager.master.po.Music;
import sharemer.business.manager.master.po.Video;
import sharemer.business.manager.master.utils.Constant;
import sharemer.business.manager.master.vo.DownLoadVo;

import javax.annotation.Resource;
import java.io.DataInputStream;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sunqinwen
 * @version \: PipLine.java,v 0.1 2018-12-06 14:26
 * 图片下载管道
 */
@Component
public class DownLoadPipLine {

    private Logger logger = LoggerFactory.getLogger(DownLoadPipLine.class);

    @Resource
    private ImagesMapper imagesMapper;

    @Resource
    private MusicMapper musicMapper;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private FavListMapper favListMapper;

    @Resource
    private ConstantProperties constantProperties;

    private BlockingQueue<DownLoadVo> queue;

    public DownLoadPipLine() {
        this.queue = new LinkedBlockingQueue<>();
        new Thread(this::pushTask).start();
    }

    public void push(DownLoadVo span) {
        queue.offer(span);
    }

    private void pushTask() {
        if (queue != null) {
            DownLoadVo downLoadVo;
            while (true) {
                try {
                    downLoadVo = queue.take();
                    if (!downLoadVo.getOrigin_url().startsWith("http://")) {
                        if (downLoadVo.getOrigin_url().startsWith("//")) {
                            downLoadVo.setOrigin_url(String.format("%s%s", "http:", downLoadVo.getOrigin_url()));
                        } else if (downLoadVo.getOrigin_url().startsWith("https")) {
                            downLoadVo.setOrigin_url(downLoadVo.getOrigin_url().replace("https", "http"));
                        } else {
                            downLoadVo.setOrigin_url(String.format("http://%s", downLoadVo.getOrigin_url()));
                        }
                    }
                    Images images = imagesMapper.getOneByOriginUrl(downLoadVo.getOrigin_url());
                    if (images == null) {
                        images = new Images();
                        images.setOrigin_url(downLoadVo.getOrigin_url());
                        Configuration cfg = new Configuration(Zone.zone0());
                        UploadManager uploadManager = new UploadManager(cfg);
                        String accessKey = constantProperties.getQiniuAk();
                        String secretKey = constantProperties.getQiniuSk();
                        String bucket = constantProperties.getQiniuBucketName();
                        String originName = downLoadVo.getOrigin_url();
                        String suffix;
                        try {
                            suffix = originName.substring(originName.lastIndexOf("."), originName.lastIndexOf("?"));
                        } catch (StringIndexOutOfBoundsException e) {
                            suffix = originName.substring(originName.lastIndexOf("."));
                        }
                        String key = "media/covers/" + UUID.randomUUID().toString().replace("-", "") + suffix;
                        URL url = new URL(originName);
                        DataInputStream dataInputStream = new DataInputStream(url.openStream());
                        Auth auth = Auth.create(accessKey, secretKey);
                        String upToken = auth.uploadToken(bucket);
                        try {
                            Response response = uploadManager.put(dataInputStream, key, upToken, null, null);
                            //解析上传成功的结果
                            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                            images.setQiniu_url(String.format("%s/%s", constantProperties.getQiniuImageDomain(), putRet.key));
                        } catch (QiniuException ex) {
                            Response r = ex.response;
                            System.err.println("---------------" + r.toString());
                            logger.error("upload image qiniu error ! origin_url={}, resp={}", originName, ex);
                        }
                        imagesMapper.insert(images);
                    }

                    switch (downLoadVo.getMediaType()){
                        case Constant.TagMedia.MUSIC_TYPE:
                            Music music = new Music();
                            music.setId(downLoadVo.getId());
                            music.setCover(images.getOrigin_url());
                            musicMapper.update(music);
                            break;
                        case Constant.TagMedia.FAV_LIST_TYPE:
                            FavList favList = new FavList();
                            favList.setId(downLoadVo.getId());
                            favList.setCover(images.getOrigin_url());
                            favListMapper.update(favList);
                            break;
                        case Constant.TagMedia.PV_TYPE:
                            Video video = new Video();
                            video.setId(downLoadVo.getId());
                            video.setCover(images.getQiniu_url());
                            break;
                    }
                } catch (Exception e) {
                    logger.error("download pipline push task error! ", e);
                }
            }
        }
    }

}
