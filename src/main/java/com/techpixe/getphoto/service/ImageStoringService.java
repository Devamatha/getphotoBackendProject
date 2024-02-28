package com.techpixe.getphoto.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.techpixe.getphoto.entity.ImageStoring;


public interface ImageStoringService {
	ResponseEntity<?> uploadImage(Long event, MultipartFile image) throws IOException;

	public void deleteimage(long id);
	ImageStoring fetchById(Long id);

}
