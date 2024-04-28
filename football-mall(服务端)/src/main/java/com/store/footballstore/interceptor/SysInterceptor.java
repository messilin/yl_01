package com.store.footballstore.interceptor;

import com.store.footballstore.util.JwtUtils;
import com.store.footballstore.util.StringUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * @author yuanlin
 * @create 2023/8/27 0027 13:01
 */
public class SysInterceptor implements HandlerInterceptor {

    @Override
//    preHandle方法在处理之前执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return false会直接拦截
//        return true会继续放行
        String path=request.getRequestURI();
        System.out.println("path="+path);
        if(handler instanceof HandlerMethod){
//            判断token是否为空
            String token=request.getHeader("token");
            System.out.println("token="+token);
            if(StringUtil.isEmpty(token)){
                System.out.println("token为空");
                throw new RuntimeException("签名验证不存在");
            }else{
//                如果token不为空，我们要对token鉴权
                Claims claims= JwtUtils.validateJWT(token).getClaims();

//                管理员 /admin开头路径请求
                if (path.startsWith("/admin")){
                    if (claims==null || !claims.getSubject().equals("admin") || !claims.getId().equals("-1")){
                        throw new RuntimeException("管理员鉴权失败");
                    }else {
                        System.out.println("鉴权成功嘿嘿");
                        return true;
                    }
                }else { //普通用户鉴权
                    if (claims==null){
                        System.out.println("鉴权失败哎哎哎");
                        throw new RuntimeException("鉴权失败呜呜呜");
                    }else {
                        System.out.println("鉴权成功哈哈");
                        return true;
                    }
                }
            }
        }else {
            return true;
        }
    }
}
