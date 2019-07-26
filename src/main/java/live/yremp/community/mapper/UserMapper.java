package live.yremp.community.mapper;

import live.yremp.community.entity.User;

public interface UserMapper {
    public void Insert(User user);
    public User findByToken(String token);
    public User findById(String account_id);
}
