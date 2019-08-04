package live.yremp.community.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer user_id;
    private String user_name;
    private String user_img;
    private String user_blog;
    private String user_github;
    private String user_bio;
}
