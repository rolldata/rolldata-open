package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * @Title: EmailAttachmentPojo
 * @Description: EmailAttachmentPojo
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2024-04-09
 * @version: V1.0
 */
public class EmailAttachmentPojo implements Serializable {

    private static final long serialVersionUID = -6066827604990434210L;

    private String filePath;

    private String fileName;

    public EmailAttachmentPojo() {
    }

    public EmailAttachmentPojo(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public EmailAttachmentPojo setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public EmailAttachmentPojo setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public String toString() {
        return "EmailAttachmentPojo{" +
                "filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
