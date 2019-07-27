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
    public void Insert(Question ques){ quesMapper.Insert(ques);}
    public List<Question> QueryAll(Integer offset, Integer size){return quesMapper.QueryAll(offset,size);};
    public Integer count(){return quesMapper.count();}
}
