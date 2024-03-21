package com.techpixe.getphoto.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface StoregeServie {
    public String uploadFile(MultipartFile file) ;
    public byte[] downloadFile(String fileName) ;
    public String deleteFile(String fileName) ;

}
