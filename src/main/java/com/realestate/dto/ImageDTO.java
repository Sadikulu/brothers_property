package com.realestate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private String name;
	
    private String uri;
    
	private String type;
	
	private long length;
	
	
}
