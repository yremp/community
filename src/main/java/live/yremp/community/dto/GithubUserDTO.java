package live.yremp.community.dto;

import lombok.Data;

@Data
public class GithubUserDTO {
    private String name;
    private long id;
    private String bio;
    private String avatarUrl;
    private String html_url;
    private String blog;


}
