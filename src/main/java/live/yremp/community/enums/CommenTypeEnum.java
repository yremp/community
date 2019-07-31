package live.yremp.community.enums;

public enum CommenTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommenTypeEnum (Integer type){
        this.type=type;
    }

    public static boolean isExist(Integer type)
    {
       for(CommenTypeEnum commenTypeEnum:CommenTypeEnum.values()) {
           if(commenTypeEnum.getType().equals(type)){
               return  true;
           }
       }
       return false;
    }
}
