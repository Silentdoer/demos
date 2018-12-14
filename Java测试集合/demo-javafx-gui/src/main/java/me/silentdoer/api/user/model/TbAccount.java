package me.silentdoer.api.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * tb_account
 * @author 
 */
public class TbAccount implements Serializable {
    private Long fId;

    private LocalDateTime createTime;

    private String gender;

    private LocalDateTime modifyTime;

    private String name;

    private static final long serialVersionUID = 1L;

    public Long getfId() {
        return fId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}