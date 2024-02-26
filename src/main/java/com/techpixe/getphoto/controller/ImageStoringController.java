package com.techpixe.getphoto.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techpixe.getphoto.entity.ImageStoring;
import com.techpixe.getphoto.service.ImageStoringService;

@RestController
@RequestMapping("/images")
public class ImageStoringController {
	@Autowired
	private ImageStoringService imageStoringService;

	@PostMapping("/upload/{event}")
	public ResponseEntity<?> uploadImage(@PathVariable Long event, @RequestParam("image") MultipartFile image)
			throws IOException {
		String uploadImage = imageStoringService.uploadImage(event, image);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
	}

	@DeleteMapping("/delete/{Id}")
	public ResponseEntity<Void> deleteImgaes(@PathVariable Long Id) {
		ImageStoring imageStoring = imageStoringService.fetchById(Id);
		if (imageStoring == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			imageStoringService.deleteimage(Id);
			return new ResponseEntity<>(HttpStatus.OK);
		}

	}
}
