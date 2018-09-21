package com.zccoder.scc.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zccoder.scc.config.UrcPropertiesUtil;
import com.zccoder.scc.domain.vo.User;
import com.zccoder.scc.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.zccoder.scc.config.EnUrcProperties;

/**
 * 登录校验拦截器
 *
 * @author zc by 2017-06-22
 */
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private User user;
    @Autowired
    private UrcPropertiesUtil urcPropertiesUtil;

    private static Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

    /**
     * 返回值：表示我们是否需要将当期的请求拦截下来
     * false：请求会被终止
     * true：请求会被继续运行
     * Object object:表示的是被拦截的请求的目标对象
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 不拦截获取配置参数
        String path = request.getServletPath();
        logger.info("[URC] the request path is {}", path);
        if (path.length() > 8 && "/config/".equals(path.substring(0, 8))) {
            return true;
        }

        if (StringUtils.isEmpty(request.getSession().getAttribute("jsession_id"))) {
            String jsession_id = request.getParameter("jsession_id");
            logger.info("[URC] the jsession id is {}", jsession_id);

            if (StringUtils.isEmpty(jsession_id)) {
                response.getWriter().println("this request need param jsession id!");
                return false;
            }

            String host = urcPropertiesUtil.getValue(EnUrcProperties.UAC_REST_HOST);
            String url = host + "rest/baseOperRest/getBaseOperInfoByJsessionId";
            String param = "jsessionId=" + jsession_id;
            // 校验
            String respInfo = HttpUtils.sendPost(url, param);

            JSONObject respJson = JSONObject.parseObject(respInfo);
            String respCode = respJson.get("respCode").toString();
            String content = respJson.get("content").toString();

            if (!"0000".equals(respCode)) {
                // 登录校验失败
                response.setCharacterEncoding("utf-8");
                response.getWriter().println(content);
                return false;
            }

            // 解析数据
            JSONObject argsInfo = JSONObject.parseObject(respJson.get("args").toString());
            JSONObject operInfo = JSONObject.parseObject(argsInfo.get("oper_info").toString());
            String oper_name = operInfo.get("oper_name").toString();
            String oper_no = operInfo.get("oper_no").toString();

            // 封装数据
            user.setAuthor_code(oper_no);
            user.setAuthor_name(oper_name);
            user.setJsession_id(jsession_id);

            request.getSession().setAttribute("jsession_id", jsession_id);

            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
