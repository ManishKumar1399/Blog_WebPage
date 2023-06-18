package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PostDtoV2 {


        private long id;
        @NotEmpty
        @Size(min = 2, message = "Post title should have at least 2 character")
        private String title;

        @NotEmpty
        @Size(min = 10, message = "Post description should have at least 10 character")
        private String description;

        @NotEmpty
        private String content;
        private Set<CommentDto> comments;

        private Long categoryId;
        List<String> tags;

}
