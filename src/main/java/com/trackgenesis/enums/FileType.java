// SID: 2408078
package com.trackgenesis.enums;

public enum FileType {
    TEXT_PLAIN("text/plain"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_MSWORD("application/msword"),
    APPLICATION_DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    private final String mimeType;

    FileType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static FileType fromMimeType(String mimeType) {
        for (FileType fileType : FileType.values()) {
            if (fileType.getMimeType().equalsIgnoreCase(mimeType)) {
                return fileType;
            }
        }
        return null; // Or throw an exception if you prefer
    }
}