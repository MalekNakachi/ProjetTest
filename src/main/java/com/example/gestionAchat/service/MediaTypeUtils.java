package com.example.gestionAchat.service;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

public class MediaTypeUtils {

    public static MediaType  getMediaType(ServletContext servletContext, String fileName) {
        String mimeType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mimeType);
            return mediaType;
        }
        catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
