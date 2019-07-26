package live.yremp.community.controller;

import live.yremp.community.dto.AccessTokenDto;
import live.yremp.community.dto.GithubUser;
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
    private GithubProvider githubProvider=null;
    @Autowired
    private UserService userService=null;
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSC;
    @Value("${github.redirect.url}")
    private String redirectURL;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(clientID);
        accessTokenDto.setClient_secret(clientSC);
        accessTokenDto.setRedirect_uri(redirectURL);
        String accesstaken=githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser =githubProvider.getGithubUser(accesstaken);
        if(githubUser!=null) {
//            登陆成功
            User user = new User();
            String token=UUID.randomUUID().toString();
            user.setUser_token(token);
            System.out.println(githubUser.getName());
            user.setUser_name(githubUser.getName());
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setUser_img(githubUser.getAvatar_url());
            userService.Insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
//            登陆失败
            return "redirect:/";

        }

    }
}
