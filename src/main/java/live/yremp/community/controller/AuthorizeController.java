package live.yremp.community.controller;

import live.yremp.community.dto.AccessTokenDTO;
import live.yremp.community.dto.GithubUserDTO;
import live.yremp.community.entity.User;
import live.yremp.community.provider.GithubProvider;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller

public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider = null;
    @Autowired
    private UserService userService = null;
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSC;
    @Value("${github.redirect.url}")
    private String redirectURL;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDto = new AccessTokenDTO();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(clientID);
        accessTokenDto.setClient_secret(clientSC);
        accessTokenDto.setRedirect_uri(redirectURL);
        String accesstaken = githubProvider.getAccessToken(accessTokenDto);
        GithubUserDTO githubUserDTO = githubProvider.getGithubUser(accesstaken);
        if (githubUserDTO != null) {
//            登陆成功
            User user1 = null;
            String token = UUID.randomUUID().toString();
            try {
                user1 = userService.findByGithubId(String.valueOf(githubUserDTO.getId()));
                if (user1 != null) {
                    response.addCookie(new Cookie("token", token));
                    user1.setUser_token(token);
                    userService.upTokenById(token, user1.getUser_id());
                } else {
                    User user = new User();
                    user.setUser_token(token);
                    user.setUser_name(githubUserDTO.getName());
                    user.setAccount_id(String.valueOf(githubUserDTO.getId()));
                    user.setGmt_create(System.currentTimeMillis());
                    user.setGmt_modified(user.getGmt_create());
                    user.setUser_img(githubUserDTO.getAvatarUrl());
                    userService.Insert(user);
                    request.getSession().setAttribute("user", user);
                }
            } catch (Exception w) {

            }
            return "redirect:/";
        } else {
//            登陆失败
            return "redirect:/";

        }

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse) {
        httpServletRequest.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        return "redirect:/";
    }
}
