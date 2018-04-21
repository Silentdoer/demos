package silentdoer.web.entity;

public class Student {
    private Long stdId;

    private String stdName;

    private String stdCode;

    private String stdSex;

    private String stdPhone;

    private Long schoolId;

    private Long gradeId;

    private Long clsId;

    public Long getStdId() {
        return stdId;
    }

    public void setStdId(Long stdId) {
        this.stdId = stdId;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName == null ? null : stdName.trim();
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode == null ? null : stdCode.trim();
    }

    public String getStdSex() {
        return stdSex;
    }

    public void setStdSex(String stdSex) {
        this.stdSex = stdSex == null ? null : stdSex.trim();
    }

    public String getStdPhone() {
        return stdPhone;
    }

    public void setStdPhone(String stdPhone) {
        this.stdPhone = stdPhone == null ? null : stdPhone.trim();
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Long getClsId() {
        return clsId;
    }

    public void setClsId(Long clsId) {
        this.clsId = clsId;
    }

    @Override
    public String toString(){
        return String.format("Student : %s %s %s %s %s %s %s %s", stdId, stdName, stdCode, stdSex, stdPhone, schoolId, gradeId, clsId);
    }
}