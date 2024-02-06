package com.techpixe.getphoto.serviceImpl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.io.ByteArrayOutputStream;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.EventRepository;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.service.EventService;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private PhotoGrapherRepository photoGrapherRepository;

	@Override
	public Event save(String eventName, String eventAddress, Long photoGrapher) throws IOException {
	    PhotoGrapher photoGrapherId = photoGrapherRepository.findById(photoGrapher)
	            .orElseThrow(() -> new RuntimeException("Id is not present" + photoGrapher));
	    if (photoGrapherId != null) {
	        Event event = new Event();
	        event.setEventName(eventName);
	        event.setEventAddress(eventAddress);
	        event.setPhotoGrapher(photoGrapherId);

	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        Map<EncodeHintType, Object> hintsMap = new HashMap<>();
	        hintsMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	        BitMatrix bitMatrix;
	        int width = 300; 
	        int height = 300;
	        try {
	            String qrContent = eventName + "\n" + eventAddress;
	            bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height, hintsMap);
	        } catch (WriterException e) {
	            throw new RuntimeException("Failed to generate QR code image.", e);
	        }

	        BufferedImage qrImage = toBufferedImage(bitMatrix);

	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        try {
	            ImageIO.write(qrImage, "png", outputStream);
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to write QR code image to output stream.", e);
	        }
	        event.setQrCode(outputStream.toByteArray());

	        return eventRepository.save(event);
	    } else {
	        System.out.println("id is not present");
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
	                "photoGrapher with this Id is not present" + photoGrapher);
	    }
	}
	private BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.BLACK);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (matrix.get(x, y)) {
					graphics.fillRect(x, y, 1, 1);
				}
			}
		}
		return image;
	}

}
