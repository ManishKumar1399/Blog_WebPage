package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comments;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository CommentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository CommentRepository,PostRepository postRepository){
        this.CommentRepository=CommentRepository;
        this.postRepository=postRepository;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
       Comments comment=mapToEntity(commentDto);
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));
        //set post to comment entity;
        comment.setPost(post);

        //comment entity to DB
        Comments newComment=CommentRepository.save(comment);


        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        //retrieve comments by postId
        List<Comments> comments=CommentRepository.findByPostId(postId);
        //convert List of comment entity
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comments comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comments mapToEntity(CommentDto commentDto){
        Comments comments=new Comments();
        comments.setId(commentDto.getId());
        comments.setName(commentDto.getName());
        comments.setEmail(commentDto.getEmail());
        comments.setBody(commentDto.getBody());

        return comments;
    }
}
