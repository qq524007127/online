package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.ImgInfo;
import cn.com.zhihetech.online.commons.QiniuConfig;
import cn.com.zhihetech.online.service.IImgInfoService;
import com.alibaba.fastjson.JSON;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by ShenYunjie on 2015/11/19.
 */
@Controller
public class QiniuController extends SupportController {

    private final String CALLBACK_URL = "http://zhihetech.6655.la/qiniu/api/image/callback";
    private final String CALLBACK_BODY = "bucket=$(bucket)&key=$(key)&hash=$(etag)&width=$(imageInfo.width)&height=$(imageInfo.height)";

    @Resource(name = "imgInfoService")
    private IImgInfoService imgInfoService;

    @RequestMapping(value = "qiniu/image/upload")
    public String uploadIndex() {
        return "upload";
    }

    @RequestMapping(value = "qiniu/api/image/uptoken")
    public void getImageUploadToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Auth auth = Auth.create(QiniuConfig.AK, QiniuConfig.SK);
        long expires = 1000 * 60 * 60;
        StringMap policy = new StringMap()
                .putNotEmpty("callbackUrl", CALLBACK_URL)
                .put("mimeLimit", "image/jpeg;image/png;image/gif")
                .putNotEmpty("callbackBody", CALLBACK_BODY);
        String uploadToken = auth.uploadToken(QiniuConfig.BUCKET, null, expires, policy, true);
        String uptokenJson = "{\"uptoken\": \"" + uploadToken + "\"}";
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(uptokenJson);
    }

    @RequestMapping(value = "qiniu/api/image/callback")
    public void qiniuUploadCallback(ImgInfo imgInfo, HttpServletResponse response) throws IOException {
        imgInfo.setCreateDate(new Date());
        this.imgInfoService.add(imgInfo);
        String imgInfoJson = JSON.toJSONString(imgInfo);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(imgInfoJson);
    }
}
