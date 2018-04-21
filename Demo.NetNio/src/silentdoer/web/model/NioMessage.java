package silentdoer.web.model;

import java.io.Serializable;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-1-28 22:13
 */
public class NioMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    //private static final long serialVersionUID = 2330354535146458439L;
    /**
     * 此消息来自哪里
     */
    private String from;
    /**
     * 此消息去往何方
     */
    private String to;
    /**
     * 消息的标题
     */
    private String title;
    /**
     * 消息的具体内容
     */
    private String content;

    private Long uid;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
