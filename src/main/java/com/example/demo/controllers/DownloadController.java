package com.example.demo.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import com.example.demo.config.MediaTypeConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class DownloadController {

    private static final String DEFAULT_FILE_NAME = "D:\\proga\\проэкты java\\final_project_spring\\src\\main\\resources\\static\\tarifs.txt";

    private MediaTypeConfig mediaTypeConfig;

    @Autowired
    public DownloadController(MediaTypeConfig mediaTypeConfig) {
        this.mediaTypeConfig = mediaTypeConfig;
    }

    @GetMapping("/user/download")
    @PreAuthorize("hasAnyAuthority('READ')")
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {

        MediaType mediaType = mediaTypeConfig.getMediaTypeForFileName(DEFAULT_FILE_NAME);
        File file = new File(DEFAULT_FILE_NAME);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}
