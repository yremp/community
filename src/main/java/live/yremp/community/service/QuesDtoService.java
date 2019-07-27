package live.yremp.community.service;

import live.yremp.community.dto.PageDto;
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
    public PageDto list( Integer page, Integer size){
        PageDto pageDto = new PageDto();
        Integer totalCount=quesService.count();
        pageDto.setPagenation(totalCount,page,size);
        if(page<1){
            page=1;
        }
        if(page>pageDto.getTotalPage()){
            page=pageDto.getTotalPage();
        }
        Integer offset =  size*(page-1);
        List<Question> ques=quesService.QueryAll(offset,size);
        List<QuesDto> quesDtoList=new ArrayList<>();

            for(Question question:ques){
                User user =userService.findById(question.getQues_userid());
                QuesDto quesDto = new QuesDto();
                BeanUtils.copyProperties(question,quesDto);
                quesDto.setUser(user);
                quesDtoList.add(quesDto);
            }
            pageDto.setQuestions(quesDtoList);


            return pageDto;
    }


    public PageDto list(Integer user_id, Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        Integer totalCount=quesService.countByUserId(user_id);
        pageDto.setPagenation(totalCount,page,size);
        if(page<1){
            page=1;
        }
        if(page>pageDto.getTotalPage()){
            page=pageDto.getTotalPage();
        }
        Integer offset =  size*(page-1);
        List<Question> ques=quesService.QueryAllByUserId(user_id,offset,size);
        List<QuesDto> quesDtoList=new ArrayList<>();

        for(Question question:ques){
            User user =userService.findById(question.getQues_userid());
            QuesDto quesDto = new QuesDto();
            BeanUtils.copyProperties(question,quesDto);
            quesDto.setUser(user);
            quesDtoList.add(quesDto);
        }
        pageDto.setQuestions(quesDtoList);


        return pageDto;
    }
}
