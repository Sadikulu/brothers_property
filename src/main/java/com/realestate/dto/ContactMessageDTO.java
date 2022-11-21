package com.realestate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageDTO {
	
    private Long id;

    private String name;

    private String subject;

    private String body;

    private String email;

}
