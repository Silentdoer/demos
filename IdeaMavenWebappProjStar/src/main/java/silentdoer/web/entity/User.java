package silentdoer.web.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer fId;

    private String fName;

    private String fSrcUid;

    private String fPasswd;

    private String fPayPassword;

    private String fCellphone;

    private String fSalt;

    private String fIdCard;

    private String fRealName;

    private Byte fStatus;

    private String fRemark;

    private Date fRegisterTime;

    private Date fUpdateTime;

    private Byte fIdcardVerified;

    private Byte fSex;

    private Integer fBirthday;

    private Integer fProvinceId;

    private Integer fCityId;

    private Integer fAreaId;

    private String fAddress;

    private String fUserImg;

    private Byte fCellphoneTimes;

    private Byte fFailLoginTimes;

    private Byte fPaypwdTimes;

    private Byte fPayFailTimes;

    private BigDecimal fWithdrawLimit;

    private Byte fInvestFlag;

    private Date fVerifyTime;

    private String fTaofen8UserId;

    private Integer fSmsType;

    private Integer fFromType;

    private String fPromotionQueryStr;

    private Integer fInvestType;

    private String fInviteCode;

    private Byte fTreasureFlag;

    private String fRegisterIp;

    private Byte fXwActivate;

    private Integer fFinancialInvestType;

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfSrcUid() {
        return fSrcUid;
    }

    public void setfSrcUid(String fSrcUid) {
        this.fSrcUid = fSrcUid;
    }

    public String getfPasswd() {
        return fPasswd;
    }

    public void setfPasswd(String fPasswd) {
        this.fPasswd = fPasswd;
    }

    public String getfPayPassword() {
        return fPayPassword;
    }

    public void setfPayPassword(String fPayPassword) {
        this.fPayPassword = fPayPassword;
    }

    public String getfCellphone() {
        return fCellphone;
    }

    public void setfCellphone(String fCellphone) {
        this.fCellphone = fCellphone;
    }

    public String getfSalt() {
        return fSalt;
    }

    public void setfSalt(String fSalt) {
        this.fSalt = fSalt;
    }

    public String getfIdCard() {
        return fIdCard;
    }

    public void setfIdCard(String fIdCard) {
        this.fIdCard = fIdCard;
    }

    public String getfRealName() {
        return fRealName;
    }

    public void setfRealName(String fRealName) {
        this.fRealName = fRealName;
    }

    public Byte getfStatus() {
        return fStatus;
    }

    public void setfStatus(Byte fStatus) {
        this.fStatus = fStatus;
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark;
    }

    public Date getfRegisterTime() {
        return fRegisterTime;
    }

    public void setfRegisterTime(Date fRegisterTime) {
        this.fRegisterTime = fRegisterTime;
    }

    public Date getfUpdateTime() {
        return fUpdateTime;
    }

    public void setfUpdateTime(Date fUpdateTime) {
        this.fUpdateTime = fUpdateTime;
    }

    public Byte getfIdcardVerified() {
        return fIdcardVerified;
    }

    public void setfIdcardVerified(Byte fIdcardVerified) {
        this.fIdcardVerified = fIdcardVerified;
    }

    public Byte getfSex() {
        return fSex;
    }

    public void setfSex(Byte fSex) {
        this.fSex = fSex;
    }

    public Integer getfBirthday() {
        return fBirthday;
    }

    public void setfBirthday(Integer fBirthday) {
        this.fBirthday = fBirthday;
    }

    public Integer getfProvinceId() {
        return fProvinceId;
    }

    public void setfProvinceId(Integer fProvinceId) {
        this.fProvinceId = fProvinceId;
    }

    public Integer getfCityId() {
        return fCityId;
    }

    public void setfCityId(Integer fCityId) {
        this.fCityId = fCityId;
    }

    public Integer getfAreaId() {
        return fAreaId;
    }

    public void setfAreaId(Integer fAreaId) {
        this.fAreaId = fAreaId;
    }

    public String getfAddress() {
        return fAddress;
    }

    public void setfAddress(String fAddress) {
        this.fAddress = fAddress;
    }

    public String getfUserImg() {
        return fUserImg;
    }

    public void setfUserImg(String fUserImg) {
        this.fUserImg = fUserImg;
    }

    public Byte getfCellphoneTimes() {
        return fCellphoneTimes;
    }

    public void setfCellphoneTimes(Byte fCellphoneTimes) {
        this.fCellphoneTimes = fCellphoneTimes;
    }

    public Byte getfFailLoginTimes() {
        return fFailLoginTimes;
    }

    public void setfFailLoginTimes(Byte fFailLoginTimes) {
        this.fFailLoginTimes = fFailLoginTimes;
    }

    public Byte getfPaypwdTimes() {
        return fPaypwdTimes;
    }

    public void setfPaypwdTimes(Byte fPaypwdTimes) {
        this.fPaypwdTimes = fPaypwdTimes;
    }

    public Byte getfPayFailTimes() {
        return fPayFailTimes;
    }

    public void setfPayFailTimes(Byte fPayFailTimes) {
        this.fPayFailTimes = fPayFailTimes;
    }

    public BigDecimal getfWithdrawLimit() {
        return fWithdrawLimit;
    }

    public void setfWithdrawLimit(BigDecimal fWithdrawLimit) {
        this.fWithdrawLimit = fWithdrawLimit;
    }

    public Byte getfInvestFlag() {
        return fInvestFlag;
    }

    public void setfInvestFlag(Byte fInvestFlag) {
        this.fInvestFlag = fInvestFlag;
    }

    public Date getfVerifyTime() {
        return fVerifyTime;
    }

    public void setfVerifyTime(Date fVerifyTime) {
        this.fVerifyTime = fVerifyTime;
    }

    public String getfTaofen8UserId() {
        return fTaofen8UserId;
    }

    public void setfTaofen8UserId(String fTaofen8UserId) {
        this.fTaofen8UserId = fTaofen8UserId;
    }

    public Integer getfSmsType() {
        return fSmsType;
    }

    public void setfSmsType(Integer fSmsType) {
        this.fSmsType = fSmsType;
    }

    public Integer getfFromType() {
        return fFromType;
    }

    public void setfFromType(Integer fFromType) {
        this.fFromType = fFromType;
    }

    public String getfPromotionQueryStr() {
        return fPromotionQueryStr;
    }

    public void setfPromotionQueryStr(String fPromotionQueryStr) {
        this.fPromotionQueryStr = fPromotionQueryStr;
    }

    public Integer getfInvestType() {
        return fInvestType;
    }

    public void setfInvestType(Integer fInvestType) {
        this.fInvestType = fInvestType;
    }

    public String getfInviteCode() {
        return fInviteCode;
    }

    public void setfInviteCode(String fInviteCode) {
        this.fInviteCode = fInviteCode;
    }

    public Byte getfTreasureFlag() {
        return fTreasureFlag;
    }

    public void setfTreasureFlag(Byte fTreasureFlag) {
        this.fTreasureFlag = fTreasureFlag;
    }

    public String getfRegisterIp() {
        return fRegisterIp;
    }

    public void setfRegisterIp(String fRegisterIp) {
        this.fRegisterIp = fRegisterIp;
    }

    public Byte getfXwActivate() {
        return fXwActivate;
    }

    public void setfXwActivate(Byte fXwActivate) {
        this.fXwActivate = fXwActivate;
    }

    public Integer getfFinancialInvestType() {
        return fFinancialInvestType;
    }

    public void setfFinancialInvestType(Integer fFinancialInvestType) {
        this.fFinancialInvestType = fFinancialInvestType;
    }
}