package com.springboot.blog.payload;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class CommentDto {

    private long id;
    @NotEmpty(message = "Name Should not be null or empty")
    private String name;
    @NotEmpty(message = "Email Should not be null or empty")
    @Email(message = "Email Should be Valid")
    private String email;
    @NotEmpty
    @Size(min = 10,message = "Description Should not be null or empty")
    private String body;
}
