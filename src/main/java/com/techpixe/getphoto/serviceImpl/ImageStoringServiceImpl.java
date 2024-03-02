package com.techpixe.getphoto.serviceImpl;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.entity.ImageStoring;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.EventRepository;
import com.techpixe.getphoto.repository.ImageStoringRepository;
import com.techpixe.getphoto.service.ImageStoringService;
import com.techpixe.getphoto.util.ImageUtils;

@Service
public class ImageStoringServiceImpl implements ImageStoringService {
	@Autowired
	private ImageStoringRepository imageStoringRepository;

	@Autowired
	private EventRepository eventRepository;

	@Override
	public ResponseEntity<?> uploadImage(Long event, MultipartFile image) throws IOException {
		Event eventId = eventRepository.findById(event)
				.orElseThrow(() -> new RuntimeException("Event with ID " + event + " not found"));

		PhotoGrapher photographer = eventId.getPhotoGrapher();
		long totalImagesUploaded = photographer.getEvent().stream().mapToLong(ev -> ev.getImageStoring().size()).sum();
		System.out.println(totalImagesUploaded+"totalImagesUploaded");
		System.out.println(photographer.getTotalImages()+"photographer.getTotalImages");
		if (totalImagesUploaded >= photographer.getTotalImages()) {
			return ResponseEntity.internalServerError()
					.body("Internal Server Error: Please upgrade your plan.");
		}

		if (eventId != null) {
			System.out.println("event ID: " + eventId.getEvent_Id());
			byte[] compressedImage = ImageUtils.compressImage(image.getBytes());
			ImageStoring imageData = ImageStoring.builder().type(image.getContentType()).image(compressedImage)
					.event(eventId).build();

			ImageStoring savedImage = imageStoringRepository.save(imageData);

			if (savedImage != null) {
				return ResponseEntity.ok(savedImage);
			}
		}
		return ResponseEntity.internalServerError().body("Event with ID " + event + " not found");

	}

	@Override
	public void deleteimage(long id) {
		imageStoringRepository.deleteById(id);
	}

	@Override
	public ImageStoring fetchById(Long id) {

		return imageStoringRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Image Storing Id '" + id + "' is not present "));
	}

}
