package live.yremp.community.controller;

import com.alibaba.fastjson.JSONObject;
import live.yremp.community.dto.NoticeDTO;
import live.yremp.community.dto.ResultDTO;
import live.yremp.community.entity.User;
import live.yremp.community.enums.NoticeTypeEnum;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import live.yremp.community.provider.UserProvider;
import live.yremp.community.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @Autowired
    UserProvider userProvider;
//处理通知阅读状态
    @GetMapping("/notice/{notice_id}")
    public String profile(@PathVariable("notice_id") Integer notice_id,
                          Model model,
                          HttpServletRequest request) {
//判断用户是否有token，没有则返回
        User user = null;
        user = userProvider.login(request, user);
        if (user == null) {
            return "redirect:/";
        }
//用户点击问题链接，将通知设置为已读
        NoticeDTO noticeDTO = noticeService.read(user, notice_id);
        if (NoticeTypeEnum.REPLY_COMMENT.getName().equals(noticeDTO.getType())
                || NoticeTypeEnum.REPLY_QUESTION.getName().equals(noticeDTO.getType())) {
            return "redirect:/question/" + noticeDTO.getNotice_qcparentid();
        } else {
            return "redirect:/";
        }
    }
//删除用户的通知 api
    @RequestMapping(value = "/profile/removenotice", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestBody JSONObject data,
                         HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.ERROROF(PeculiarExceptionCodeAndMessage.USER_NOT_LOGIN);
        }
        noticeService.delete(data.getInteger("notice_id"));
        ResultDTO resultDTO = ResultDTO.OKOF(100, "操作成功");
        return resultDTO;
    }

}
