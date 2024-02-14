package com.techpixe.getphoto.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStoringService {
	public String uploadImage(Long event,MultipartFile image) throws IOException;

}
