package com.techpixe.getphoto.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.service.PhotoGrapherService;

@Service
public class PhotoGrapherServiceImpl implements PhotoGrapherService
{
	@Autowired
	private PhotoGrapherRepository PhotoGrapherRepository;

	@Override
	public PhotoGrapher registration(PhotoGrapher photoGrapher)
	{
		return PhotoGrapherRepository.save(photoGrapher);
	}

}
