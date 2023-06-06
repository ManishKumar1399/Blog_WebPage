package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comments;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private ModelMapper mapper;
    private CommentRepository CommentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository CommentRepository,PostRepository postRepository,ModelMapper mapper){
        this.CommentRepository=CommentRepository;
        this.postRepository=postRepository;
        this.mapper=mapper;
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

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        //retrieve Post By Id
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));
        //retrieve Comment by Id
        Comments comments=CommentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comments","id",commentId)
        );

        if(!comments.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to the post");
        }
        return mapToDto(comments);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
        //retrieve Post By Id
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));
        //retrieve Comment by Id
        Comments comments=CommentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comments","id",commentId)
        );

        if(!comments.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to the post");
        }
        comments.setName(commentRequest.getName());
        comments.setEmail(commentRequest.getEmail());
        comments.setBody(commentRequest.getBody());

        Comments updatedComment =CommentRepository.save(comments);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));
        //retrieve Comment by Id
        Comments comments=CommentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comments","id",commentId)
        );

        if(!comments.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to the post");
        }

        CommentRepository.delete(comments);

    }

    private CommentDto mapToDto(Comments comment){
        CommentDto commentDto=mapper.map(comment,CommentDto.class);
//        CommentDto commentDto=new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comments mapToEntity(CommentDto commentDto){
        Comments comments=mapper.map(commentDto,Comments.class);
//        Comments comments=new Comments();
//        comments.setId(commentDto.getId());
//        comments.setName(commentDto.getName());
//        comments.setEmail(commentDto.getEmail());
//        comments.setBody(commentDto.getBody());

        return comments;
    }
}
