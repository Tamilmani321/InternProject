package com.user.util;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.user.entity.Attachments;
@Component
public class ImageUtil {
	
	
	public Attachments imageCompressor(MultipartFile file) throws IOException {
		Attachments ima= new Attachments();
		byte [] imageArr=file.getBytes();
		long size=file.getSize();
		 long kilobytes = (size / 1024);
		long megabytes = (kilobytes / 1024);
		System.out.println("KB "+kilobytes);
		ima.setImageSizeInKb(kilobytes);
		ima.setType(file.getContentType());
		String imageAsString= Base64.getEncoder().encodeToString(imageArr);
		ima.setImage(imageAsString);
		return ima;
	}

}
