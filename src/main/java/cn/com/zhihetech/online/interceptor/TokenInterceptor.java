package cn.com.zhihetech.online.interceptor;

import cn.com.zhihetech.online.controller.v1.api.UserApiController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by YangDaiChun on 2015/12/22.
 */
public class TokenInterceptor extends UserApiController implements HandlerInterceptor {

    private List<String> unCheckRequest;

    public void setUnCheckRequest(List<String> unCheckRequest) {
        this.unCheckRequest = unCheckRequest;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestUri = httpServletRequest.getRequestURI();
        for (String uri : unCheckRequest) {
            if (requestUri.endsWith(uri)) {
                return true;
            }
        }

        String token = httpServletRequest.getParameter("token");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
