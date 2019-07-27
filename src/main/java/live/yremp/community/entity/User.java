package live.yremp.community.entity;

import lombok.Data;

@Data
public class User {
    private Integer user_id;
    private String account_id;
    private String user_name;
    private String user_token;
    private String user_img;
    private Long gmt_create;
    private Long gmt_modified;
}
