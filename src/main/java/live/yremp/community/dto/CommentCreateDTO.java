package live.yremp.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {

    private Integer comm_parent_id;
    private String comm_info;
    private Integer comm_type;

}
