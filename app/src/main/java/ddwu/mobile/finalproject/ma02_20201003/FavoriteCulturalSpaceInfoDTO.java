package ddwu.mobile.finalproject.ma02_20201003;

public class FavoriteCulturalSpaceInfoDTO {

    long id;
    String Fac_Name;
    String Desc;


    public FavoriteCulturalSpaceInfoDTO() {
    }

    public FavoriteCulturalSpaceInfoDTO(int id, String fac_Name, String desc) {
        this.id = id;
        Fac_Name = fac_Name;
        Desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getFac_Name() {
        return Fac_Name;
    }

    public void setFac_Name(String fac_Name) {
        Fac_Name = fac_Name;
    }


    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", Fac_Name='" + Fac_Name + '\'' +
                ", Desc='" + Desc + '\'' +
                '}';
    }
}
