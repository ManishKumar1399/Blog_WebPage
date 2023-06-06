package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController (CommentService commentService){
        this.commentService=commentService;
    }
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("post/{postId}/comments")
    public List<CommentDto> getCommentsByPostId( @PathVariable(value = "postId") Long postId){
        return commentService.getCommentByPostId(postId);
    }
    @GetMapping("/post/{postId}/comments/{commentsId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long id,
                                                     @PathVariable(value = "commentsId") long commentId){
        return new ResponseEntity<>(commentService.getCommentById(id,commentId), HttpStatus.OK);
    }

    @PutMapping("/post/{postId}/comments/{commentsId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "commentsId") long commentId,
                                                    @RequestBody CommentDto commentDto){
    CommentDto updateComment=commentService.updateComment(postId,commentId,commentDto);
    return new ResponseEntity<>(updateComment,HttpStatus.OK);
    }
    @DeleteMapping("/post/{postId}/comments/{commentsId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
                                                @PathVariable(value = "commentsId") long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }
}
