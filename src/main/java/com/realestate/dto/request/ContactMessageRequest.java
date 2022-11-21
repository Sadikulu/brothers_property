package com.realestate.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageRequest {

    @Size(min = 1,max = 50,message = "Your name '${validatedValue}' must be between {min} and {max} chars long")
    @NotNull(message = "Please provide your name")
    private String name;

    @Size(min = 5,max = 50,message = "Your subject '${validatedValue}' must be between {min} and {max} chars long")
    @NotNull(message = "Please provide your subject")
    private String subject;

    @Size(min = 5,max = 200,message = "Your body '${validatedValue}' must be between {min} and {max} chars long")
    @NotNull(message = "Please provide your body")
    private String body;

    @Email(message = "Provide valid email")
    private String email;
}
