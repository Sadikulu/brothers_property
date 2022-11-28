package com.realestate.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.realestate.domain.Image;
import com.realestate.domain.ImageData;
import com.realestate.dto.ImageDTO;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	public String saveImage(MultipartFile file) {
		Image image = null;
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		try {
			ImageData imData = new ImageData(file.getBytes());
			image = new Image(fileName, file.getContentType(), imData);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		imageRepository.save(image);
		return image.getId();

	}

	public Image getImageById(String id) {
		Image image = imageRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, id)));
		return image;
	}

	public void deleteById(String id) {
		Image image = getImageById(id);
		imageRepository.delete(image);
	}

	public List<ImageDTO> getAllImages() {
		List<Image> imageFiles = imageRepository.findAll();
		List<ImageDTO> imageDTOs = imageFiles.stream().map(imFile -> {
			String imageUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download/")
					.path(imFile.getId()).toUriString();
			return new ImageDTO(imFile.getName(), imageUri, imFile.getType(), imFile.getLength());
		}).collect(Collectors.toList());
		return imageDTOs;
	}

	public Image getOnlyById(String id) {
		Image image = imageRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, id)));
		return image;
	}
}
