package live.yremp.community.dto;

import live.yremp.community.entity.User;
import lombok.Data;

@Data
public class NoticeDTO
{
    private Integer notice_receiverid;
    private String type;
    private User sender;
    private Long gmt_create;
    private String title;
    private Integer notice_qcparentid;
    private Integer status;
    private Integer notic_id;

}
