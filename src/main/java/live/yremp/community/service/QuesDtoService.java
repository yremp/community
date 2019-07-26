package live.yremp.community.service;

import live.yremp.community.dto.QuesDto;
import live.yremp.community.entity.Question;
import live.yremp.community.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuesDtoService {
    @Resource
    @Autowired
    QuesService quesService;
    @Autowired
    UserService userService;
    @Transactional(rollbackFor = Exception.class)
    public List<QuesDto> list(){
        List<Question> ques=quesService.QueryAll();
        List<QuesDto> quesDtoList=new ArrayList<>();
            for(Question question:ques){
                User user =userService.findById(question.getQues_userid());
                QuesDto quesDto = new QuesDto();
                BeanUtils.copyProperties(question,quesDto);
                quesDto.setUser(user);
                quesDtoList.add(quesDto);
            }
            return quesDtoList;
    }
}
