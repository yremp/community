package live.yremp.community.mapper;

import live.yremp.community.entity.Notice;

import java.util.List;

public interface NoticeMapper {
    public void Insert(Notice notice);
    public Notice SelectById(Integer id);
    public List<Notice> SelectByRecriverId(Integer current_user_id);
    public void UpDateStatus(Notice notice);
    public List<Notice> SelectUnReadByRecriverId(Integer current_user_id);
    public  void deleteByParentId(Integer notice_qcparentid);
    public void deleteById(Integer notice_id);
}
