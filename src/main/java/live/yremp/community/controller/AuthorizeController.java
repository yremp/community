package live.yremp.community.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import live.yremp.community.dto.*;
import live.yremp.community.entity.User;
import live.yremp.community.exception.PeculiarException;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import live.yremp.community.provider.GithubProvider;
import live.yremp.community.service.QuesDtoService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    QuesDtoService quesDtoService;
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
                    user.setUser_bio(githubUserDTO.getBio());
                    user.setUser_blog(githubUserDTO.getBlog());
                    user.setUser_github(githubUserDTO.getHtml_url());
                    userService.Insert(user);
                    request.getSession().setAttribute("user", user);
                    response.addCookie(new Cookie("token", token));
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


//更新用户数据
    @RequestMapping(value = "/user/editinfo",method = RequestMethod.POST)
    @ResponseBody
    public Object editInfo(@RequestBody UserDTO userDTO,
                           HttpServletRequest request){
        User user1 = (User) request.getSession().getAttribute("user");
//从session中获取user判断当前用户是否登录
        if (user1 == null) {
            return ResultDTO.ERROROF(PeculiarExceptionCodeAndMessage.USER_NOT_LOGIN);
        }
        if(user1.getUser_id()!=userDTO.getUser_id()){
            return  ResultDTO.ERROROF(PeculiarExceptionCodeAndMessage.USER_PAGE_NOT_BELANG_TO_YOU);
        }
        User user = userService.findById(userDTO.getUser_id());
        user.setUser_name(userDTO.getUser_name());
        user.setUser_img(userDTO.getUser_img());
        user.setUser_blog(userDTO.getUser_blog());
        user.setUser_github(userDTO.getUser_github());
        user.setUser_bio(userDTO.getUser_bio());
        userService.Update(user);
        ResultDTO resultDTO=ResultDTO.OKOF(100,"操作成功");
        return resultDTO;
    }

//用户公共页面
    @RequestMapping("/user/public/")
    public String userPublicInfo(@RequestParam(value = "user_id") Integer user_id,
                                 Model model,
                                 @RequestParam(value = "page",defaultValue = "1",required = false)Integer page,
                                 @RequestParam(value = "size", defaultValue = "8") Integer size){
//返回用户基本信息
        User selected_user = userService.findById(user_id);
        model.addAttribute("user",selected_user);
//返回查看的用户发起的提问等
        PageDTO pagenation;
        pagenation = quesDtoService.list2(user_id,page, size);
        model.addAttribute("pagenation",pagenation);
        return "userpublic";
    }
}
