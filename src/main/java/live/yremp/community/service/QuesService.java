package live.yremp.community.service;

import live.yremp.community.entity.Question;
import live.yremp.community.mapper.QuesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuesService {
    @Resource
    private QuesMapper quesMapper;
    @Transactional(rollbackFor = Exception.class)
    public Integer count(){return quesMapper.count();}
    public Integer countByUserId(Integer user_id){return quesMapper.countByUserId(user_id);}
    public void Insert(Question ques){ quesMapper.Insert(ques);}
    public List<Question> QueryAll(Integer offset, Integer size){return quesMapper.QueryAll(offset,size);};
    public List<Question> QueryAllByUserId(Integer user_id,Integer offset, Integer size){return quesMapper.QueryAllByUserId(user_id,offset,size);};
}
