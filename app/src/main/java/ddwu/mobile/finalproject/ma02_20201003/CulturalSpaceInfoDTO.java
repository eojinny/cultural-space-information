package ddwu.mobile.finalproject.ma02_20201003;

public class CulturalSpaceInfoDTO {

    int id;
    int num;
    String SubjectCode;
    String Fac_Name;
    String Addr;
    Double X_Coord;
    Double Y_Coord;
    String Phne;
    String Homapage;
    String Fac_Desc;

    public CulturalSpaceInfoDTO() {
    }

    public CulturalSpaceInfoDTO(int id, String fac_Name, String addr) {
        this.id = id;
        Fac_Name = fac_Name;
        Addr = addr;
    }

    public CulturalSpaceInfoDTO(int num, String subjectCode, String fac_Name, String addr, Double x_Coord, Double y_Coord, String phne, String homapage, String fac_Desc) {
        this.num = num;
        SubjectCode = subjectCode;
        Fac_Name = fac_Name;
        Addr = addr;
        X_Coord = x_Coord;
        Y_Coord = y_Coord;
        Phne = phne;
        Homapage = homapage;
        Fac_Desc = fac_Desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSubjectCode() {
        return SubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        SubjectCode = subjectCode;
    }

    public String getFac_Name() {
        return Fac_Name;
    }

    public void setFac_Name(String fac_Name) {
        Fac_Name = fac_Name;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public Double getX_Coord() {
        return X_Coord;
    }

    public void setX_Coord(Double x_Coord) {
        X_Coord = x_Coord;
    }

    public Double getY_Coord() {
        return Y_Coord;
    }

    public void setY_Coord(Double y_Coord) {
        Y_Coord = y_Coord;
    }

    public String getPhne() {
        return Phne;
    }

    public void setPhne(String phne) {
        Phne = phne;
    }

    public String getHomapage() {
        return Homapage;
    }

    public void setHomapage(String homapage) {
        Homapage = homapage;
    }

    public String getFac_Desc() {
        return Fac_Desc;
    }

    public void setFac_Desc(String Fac_Desc) {
        this.Fac_Desc = Fac_Desc;
    }

    @Override
    public String toString() {
        return "CulturalSpaceInfoDTO{" +
                "num=" + num +
                ", SubjectCode='" + SubjectCode + '\'' +
                ", Fac_Name='" + Fac_Name + '\'' +
                ", Addr='" + Addr + '\'' +
                ", X_Coord=" + X_Coord +
                ", Y_Coord=" + Y_Coord +
                ", Phne='" + Phne + '\'' +
                ", Homapage='" + Homapage + '\'' +
                ", FAC_DESC='" + Fac_Desc + '\'' +
                '}';
    }
}
