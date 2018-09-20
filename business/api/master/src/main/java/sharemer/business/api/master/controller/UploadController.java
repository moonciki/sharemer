package sharemer.business.api.master.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sharemer.business.api.master.service.upload.UploadService;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;

/**
 * Create by 18073 on 2018/9/17.
 */
@RestController
@RequestMapping(value = "/pc_api")
public class UploadController {

    @Resource
    private UploadService uploadService;

    @RequestMapping(value = "get_up_token", method = RequestMethod.GET)
    public WrappedResult getUpToken() {
        return WrappedResult.success(uploadService.generateUploadToken());
    }
}
