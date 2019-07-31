package live.yremp.community.entity;

import lombok.Data;

@Data
public class Notice {
    private Integer notice_id;
    private Integer notice_senderid;
    private Integer notice_receiverid;
    private Integer notice_qcid;
    private Integer notice_type;
    private Long gmt_create;
    private Integer status;
    private String notice_sendername;
    private String notice_title;
}
