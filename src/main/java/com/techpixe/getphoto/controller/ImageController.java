package com.techpixe.getphoto.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.getphoto.entity.Images;
import com.techpixe.getphoto.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController 
{
	@Autowired
	private ImageService imageService;
	
	@PostMapping("/upload/{event}")
	public ResponseEntity<Images> uploadImage(@RequestParam("image")MultipartFile file,@PathVariable Long event) throws IOException {
		Images uploadImage = imageService.uploadImageToFileSystem(file,event);
		return new ResponseEntity<Images>(uploadImage,HttpStatus.CREATED);
	}

	
	@GetMapping(value = "/getImage/{image_Id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable("image_Id") Long id)
	{
        try 
        {
            byte[] imageData = imageService.getImageById(id);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
        }
        catch (IOException e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve image", e);
        }
    }
	
//	@GetMapping("/getAll/{eventId}")
//	public ResponseEntity<List<Images>> getAll(@PathVariable("eventId") Long id)
//	{
//		List<Images> getAll= imageService.getAll(id);
//		return ResponseEntity.ok(getAll);
//	}
	
	
//	@GetMapping(value = "/getAll",produces = MediaType.IMAGE_JPEG_VALUE)
//    public List<byte[]> getAllImages()
//	{
//        try {
//            return imageService.getAllImages();
//        } catch (IOException e) {
//            // Handle the IOException, possibly by returning an appropriate HTTP error response
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<Void> deleteById(@PathVariable("imageId") Long id) throws IOException
    {
    	byte[] image = imageService.getImageById(id);
    	if (image==null) 
    	{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	else
    	{
    		imageService.deleteByImageId(id);
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    }
}
