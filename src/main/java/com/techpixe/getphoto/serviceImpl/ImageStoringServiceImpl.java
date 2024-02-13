package com.techpixe.getphoto.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techpixe.getphoto.repository.ImageStoringRepository;
import com.techpixe.getphoto.service.ImageStoringService;

@Service
public class ImageStoringServiceImpl implements ImageStoringService {
@Autowired
private ImageStoringRepository imageStoringRepository;
}
