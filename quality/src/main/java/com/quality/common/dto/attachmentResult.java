package com.quality.common.dto;

import java.io.Serializable;

public class attachmentResult implements Serializable {

    String attachmentId;
    String srcWidth;
    String srcHeigh;
    String fileName;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getSrcWidth() {
        return srcWidth;
    }

    public void setSrcWidth(String srcWidth) {
        this.srcWidth = srcWidth;
    }

    public String getSrcHeigh() {
        return srcHeigh;
    }

    public void setSrcHeigh(String srcHeigh) {
        this.srcHeigh = srcHeigh;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
