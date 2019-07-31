package live.yremp.community.entity;

import lombok.Data;

@Data
public class Comment {
    private Integer comm_id;
    private Integer comm_user_id;
    private Integer comm_parent_id;
    private Integer comm_count;
    private Integer comm_like;
    private String comm_info;
    private Integer comm_type;
    private Long gmt_create;
    private Long gmt_modified;

}
