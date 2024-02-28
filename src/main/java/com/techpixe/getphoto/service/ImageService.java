package com.techpixe.getphoto.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.techpixe.getphoto.entity.Images;

public interface ImageService {
	public Images uploadImageToFileSystem(MultipartFile file, Long event) throws IOException;

	public byte[] getImageById(Long id) throws IOException;

	// public List<byte[]> getAll(Long id);

	// List<byte[]> getAllImages() throws IOException;

	public void deleteByImageId(Long id);

}
