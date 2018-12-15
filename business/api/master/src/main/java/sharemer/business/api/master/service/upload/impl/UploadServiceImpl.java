package sharemer.business.api.master.service.upload.impl;

import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import sharemer.business.api.master.po.UploadTokenInfo;
import sharemer.business.api.master.service.upload.UploadService;
import sharemer.business.api.master.utils.ConstantProperties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.UUID;

/**
 * Create by 18073 on 2018/9/16.
 */
@Service
public class UploadServiceImpl implements UploadService {

    private Auth auth;

    @Resource
    private ConstantProperties constantProperties;

    @PostConstruct
    public void init(){
        auth = Auth.create(constantProperties.getQiniuAk(), constantProperties.getQiniuSk());
    }

    @Override
    public UploadTokenInfo generateUploadToken() {
        UploadTokenInfo uploadTokenInfo = new UploadTokenInfo();

        String key = String.format("medias/%s", UUID.randomUUID().toString().replace("-", ""));
        uploadTokenInfo.setKey(key);
        uploadTokenInfo.setToken(auth.uploadToken(constantProperties.getQiniuAudioBucket()));
        return uploadTokenInfo;
    }

}
