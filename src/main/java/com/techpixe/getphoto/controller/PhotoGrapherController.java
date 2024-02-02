package com.techpixe.getphoto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.service.PhotoGrapherService;

@RestController
@RequestMapping("/photographer")
public class PhotoGrapherController 
{
	
	private PhotoGrapherService photoGrapherService;
}
