package com.realestate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageSaveResponse extends REResponse{

	private String imageId;

	public ImageSaveResponse(String imageId,String message, Boolean success) {
		super(message, success);
		this.imageId = imageId;
	}
	
}
