package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.HobbyTag;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.service.IHobbyTagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ShenYunjie on 2016/1/13.
 */
@Controller
public class HobbyTagController extends SupportController {
    @Resource(name = "hobbyTagService")
    private IHobbyTagService hobbyTagService;

    @RequestMapping(value = "admin/hobbyTag")
    public String indexPage(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "admin/api/hobbyTag/add", method = RequestMethod.POST)
    public ResponseMessage addHobbyTag(HobbyTag hobbyTag) {

        return executeResult();
    }
}
