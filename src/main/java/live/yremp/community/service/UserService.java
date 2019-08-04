package live.yremp.community.service;

import live.yremp.community.entity.User;
import live.yremp.community.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {
@Resource
    private UserMapper userMapper;

    public User findById(Integer user_id){return userMapper.findById(user_id);}

    public User findByGithubId(String user_id){return userMapper.findByGithubId(user_id);}

    public User findByToken(String token){return  userMapper.findByToken(token);}

    @Transactional(rollbackFor = Exception.class)
    public void upTokenById(String token,Integer user_id){ userMapper.upTokenById(token,user_id);}
    @Transactional(rollbackFor = Exception.class)
    public void Insert(User user){userMapper.Insert(user);}

    public void Update(User user){
        userMapper.Update(user);
    }
}
