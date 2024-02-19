package com.techpixe.getphoto.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.entity.Images;
import com.techpixe.getphoto.repository.EventRepository;
import com.techpixe.getphoto.repository.ImagesRepository;
import com.techpixe.getphoto.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService
{
	@Autowired
    private ImagesRepository imagesRepository;
	
	@Autowired
	private EventRepository eventRepository;

    private final String FOLDER_PATH="C:\\GP\\";
    
    
    
    
    @Override
	public Images uploadImageToFileSystem(MultipartFile file, Long event) throws IOException
    {
    	String filePath=FOLDER_PATH+file.getOriginalFilename();
    	
    	
    	Event eventId= eventRepository.findById(event).orElseThrow(()-> new RuntimeException(" Event id is not present"));
    	if (eventId!=null)
    	{
    		Images images = new Images();
        	images.setType(file.getContentType());
        	images.setFilePath(filePath);
        	images.setEvent(eventId);
        	
        	file.transferTo(new File(filePath));
        	
        	return imagesRepository.save(images);
		}
    	else
    	{
    		System.out.println("Event Id is not present");
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Event with this Id is not present");
					
    	}
    	
	}

    
    @Override
    public byte[] getImageById(Long id) throws IOException 
    {
        Optional<Images> imageOptional = imagesRepository.findById(id);
        Images image = imageOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                                    "Image with this Id is not present"));
        String filePath = image.getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }


//	@Override
//	public List<byte[]> getAll(Long id) 
//	{
//		
////		List<Images> all = imagesRepository.findAll();
////		if (all.isEmpty())
////		{
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Images are not present");
////		}
////		return all;
//		Event event= eventRepository.findById(id).orElseThrow(()-> new RuntimeException("Event Id is not present"));
//		if (event!=null) 
//		{
//			Optional<Images> imageOptional = imagesRepository.findAll(id);
//	        Images image = imageOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//	                                                                                    "Image with this Id is not present"));
//	        String filePath = image.getFilePath();
//	        return Files.readAllBytes(new File(filePath).toPath());
//		}
//		
//	}
    
    
    
//    @Override
//    public List<byte[]> getAllImages() throws IOException 
//    {
//        List<Images> allImages = imagesRepository.findAll();
//        if (allImages.isEmpty())
//        {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No images found in the database");
//        }
//        return allImages.stream()
//                        .map(image -> {
//                            try {
//                                return Files.readAllBytes(new File(image.getFilePath()).toPath());
//                            } catch (IOException e) {
//                                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
//                                                                   "Error reading image file", e);
//                            }
//                        })
//                        .toList();
//    }


	@Override
	public void deleteByImageId(Long id) 
	{
		imagesRepository.deleteById(id);
		
	}


//	@Override
//	public List<byte[]> getAll(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

    


    

	

}
