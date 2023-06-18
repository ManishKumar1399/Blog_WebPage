package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostDtoV2;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(
        name = "CRUD REST APIs for the Post Resource"
)
public class PostController {
/*/*Consider a Department object with Id and name is stored in Department table
 * , Develop a controller method which gets an Id as input and returns the name
 * , if no name is present for that Id, return a default name.*/
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
     // create blog post rest api
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST APIs is used to save the post in the Database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts rest api
    @Operation(
            summary = "Get All Post REST API",
            description = "Get All Post REST APIs is used to get All post from the Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/api/v1/posts")
    public PostResponse getAllPosts(
        @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NO,required = false) int pageNo,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
        @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
        @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    // get post by id
    @Operation(
            summary = "Get Post By ID REST API",
            description = "Get Post By ID REST APIs is used to get Single post from the Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping(value = "/api/posts/{id}",params = "version=1")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @GetMapping(value = "/api/posts/{id}", params = "version=2")
    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name = "id") long id){
        PostDto postDto = postService.getPostById(id);
         PostDtoV2 postDtoV2=new PostDtoV2();
         postDtoV2.setId(postDto.getId());
         postDtoV2.setTitle(postDto.getTitle());
         postDtoV2.setDescription(postDto.getDescription());
         postDtoV2.setContent(postDto.getContent());
         List<String> tags=new ArrayList<>();
         tags.add("Java");
         tags.add("Spring Boot");
         postDtoV2.setTags(tags);

        return ResponseEntity.ok(postDtoV2);
    }

    // update post by id rest api
    @Operation(
            summary = "Update Post By ID REST API",
            description = "Update Post By ID REST APIs is used to get Update a particular post in the Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){

       PostDto postResponse = postService.updatePost(postDto, id);

       return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // delete post rest api
    @Operation(
            summary = "Delete Post By ID REST API",
            description = "Delete Post By ID REST APIs is used to get Delete a particular post from the Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
    @GetMapping("/api/v1/posts/category/{id}")
    public ResponseEntity<List<PostDto>> findPostsByCategoryId(@PathVariable("id") Long CategoryId){
       List<PostDto> postDtos= postService.getPostsByCategory(CategoryId);
       return ResponseEntity.ok(postDtos);
    }
}
