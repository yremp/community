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

    @Transactional(rollbackFor = Exception.class)
    public void Insert(User user){userMapper.Insert(user);}
    public User findById(Integer user_id){return userMapper.findById(user_id);}
    public User findByToken(String token){return  userMapper.findByToken(token);}
}
