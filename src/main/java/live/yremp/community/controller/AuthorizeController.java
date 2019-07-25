package live.yremp.community.controller;

import live.yremp.community.dto.AccessTokenDto;
import live.yremp.community.dto.GithubUser;
import live.yremp.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AuthorizeController {
    @Autowired

    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSC;
    @Value("${github.redirect.url}")
    private String redirectURL;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){
       AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(clientID);
        accessTokenDto.setClient_secret(clientSC);
        accessTokenDto.setRedirect_uri(redirectURL);
        String accesstaken=githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser =githubProvider.getGithubUser(accesstaken);
            System.out.println(githubUser.toString());
        return "index";
    }
}
