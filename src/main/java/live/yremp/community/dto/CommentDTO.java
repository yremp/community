package live.yremp.community.dto;

import live.yremp.community.entity.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer comm_id;
    private Integer comm_user_id;
    private Integer comm_parent_id;
    private Integer comm_like;
    private Integer comm_count;
    private String comm_info;
    private Integer comm_type;
    private Long gmt_create;
    private Long gmt_modified;
    private User user;
}
