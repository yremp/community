//package live.yremp.community.interceptor;
//
//import live.yremp.community.entity.User;
//import live.yremp.community.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Service
//public class SessionInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        try {
//            Cookie[] cookies;
//            cookies = request.getCookies();
//            if (cookies != null && cookies.length != 0)
//                for (Cookie cookie : cookies) {
//                    if (cookie.getName().equals("token")) {
//                        String token = cookie.getValue();
//                        User user = userService.findByToken(token);
//                        if (user != null) {
//                            request.getSession().setAttribute("user", user);
//                        }
//                        break;
//                    }
//                }
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
