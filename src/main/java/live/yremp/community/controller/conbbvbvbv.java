package live.yremp.community.controller;

import live.yremp.community.entity.Question;
import live.yremp.community.service.QuesService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class conbbvbvbv {
    @Autowired
    QuesService quesService;
    @RequestMapping("/query")
    @ResponseBody
    public List<Question> qurey(){
        return quesService.QueryAll();
    }


}
