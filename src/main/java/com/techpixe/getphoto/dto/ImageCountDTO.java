package com.techpixe.getphoto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageCountDTO {
	private long totalUploadedImages;
    private long totalAllowedImages;
    private long totalRemaingImages;
}
