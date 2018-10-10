package sharemer.business.api.master.controller;

import com.google.common.base.Strings;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sharemer.business.api.master.po.Picture;
import sharemer.business.api.master.service.upload.UploadService;
import sharemer.business.api.master.utils.ConstantProperties;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Create by 18073 on 2018/9/17.
 */
@RestController
@RequestMapping(value = "/pc_api")
public class UploadController {

    private Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Resource
    private UploadService uploadService;

    @Resource
    private ConstantProperties constantProperties;

    @RequestMapping(value = "get_up_token", method = RequestMethod.GET)
    public WrappedResult getUpToken() {
        return WrappedResult.success(uploadService.generateUploadToken());
    }

    @RequestMapping(value = "/img/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public Callable<Picture> upload(@RequestParam("file") MultipartFile file) {
        return ()->{
            Configuration cfg = new Configuration(Zone.zone0());
            UploadManager uploadManager = new UploadManager(cfg);
            String accessKey = constantProperties.getQiniuAk();
            String secretKey = constantProperties.getQiniuSk();
            String bucket = constantProperties.getQiniuBucketName();

            String originName = file.getOriginalFilename();
            if(Strings.isNullOrEmpty(originName)){
                return null;
            }
            String suffix = originName.substring(originName.lastIndexOf("."));

            if(Strings.isNullOrEmpty(suffix) ||
                    (!".jpg".equals(suffix) &&
                    !".JPG".equals(suffix) &&
                    !".png".equals(suffix) &&
                    !".PNG".equals(suffix) &&
                    !".gif".equals(suffix) &&
                    !".GIF".equals(suffix))){
                return null;
            }

            String key = UUID.randomUUID().toString().replace("-", "") + suffix;

            try {
                byte[] uploadBytes = file.getBytes();
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                try {
                    Response response = uploadManager.put(uploadBytes, key, upToken);
                    //解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    Picture picture = new Picture();
                    picture.setHash(putRet.hash);
                    picture.setUrl(String.format("%s/%s", constantProperties.getQiniuImageDomain(), putRet.key));
                    return picture;
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println("---------------"+r.toString());
                    logger.error("upload image qiniu error ! resp={}", ex);
                }
            } catch (IOException e) {
                logger.error("upload image error !", e);
            }

            return null;
        };
    }
}
