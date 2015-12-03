package com.ra.dissection.protocol.mvc;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author lukaszkaleta
 * @since 03.07.13 08:32
 */
public class UploadItem {

    private long id;

    private CommonsMultipartFile file;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }
}
