package live.yremp.community.provider;

import live.yremp.community.entity.User;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserProvider {
    @Autowired
    private UserService userService;

    public User login(HttpServletRequest request, User user) {

        Cookie[] cookies;
        cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userService.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        return user;
                    }

                }
            }
        }
        return user;
    }

    public void login(HttpServletRequest request) {

        Cookie[] cookies;
        cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userService.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        break;
                    }

                }
            }
        }
    }

}
