package com.techpixe.getphoto.serviceImpl;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.entity.ImageStoring;
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
	public String uploadImage(Long event, MultipartFile image) throws IOException {
		Event eventId = eventRepository.findById(event)
				.orElseThrow(() -> new RuntimeException("Event with ID " + event + " not found"));

		if (eventId != null) {
			System.out.println("event ID: " + eventId.getEvent_Id());
			byte[] compressedImage = ImageUtils.compressImage(image.getBytes());
			ImageStoring imageData = ImageStoring.builder().type(image.getContentType()).image(compressedImage)
					.event(eventId).build();

			ImageStoring savedImage = imageStoringRepository.save(imageData);

			if (savedImage != null) {
				return "File uploaded successfully: " + image.getOriginalFilename() + " with Event ID: " + event;
			}
		}

		return null;
	}

	@Override
	public void deleteimage(long id) {
		imageStoringRepository.deleteById(id);
	}

	@Override
	public ImageStoring fetchById(Long id) {

		return imageStoringRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Image Storing Id '" + id + "' is not present "));

	}


}
