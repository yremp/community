package live.yremp.community.mapper;

import live.yremp.community.entity.User;

public interface UserMapper {
    public void Insert(User user);
    public User findByToken(String token);
    public User findById(Integer user_id);
    public User findByGithubId(String user_id);
    public void upTokenById(String token,Integer user_id);

    public void Update(User user);
}
