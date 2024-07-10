package com.sptingboot.blog.service.impl;

import com.sptingboot.blog.entity.Comment;
import com.sptingboot.blog.entity.Post;
import com.sptingboot.blog.exception.BlogApiException;
import com.sptingboot.blog.exception.ResourceNotFoundException;
import com.sptingboot.blog.payload.CommentDto;
import com.sptingboot.blog.repository.CommenRepository;
import com.sptingboot.blog.repository.PostRepository;
import com.sptingboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommenRepository commentRepo;
    private PostRepository postRepo;
    private ModelMapper  mapper;
    @Autowired
    public CommentServiceImpl(CommenRepository commentRepo, PostRepository postRepo, ModelMapper mp) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.mapper = mp;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto); // user input to database(Entity) Structure
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment newComment = commentRepo.save(comment);  // Save the comment entity to the database
        return mapToDTO(newComment);  // database structure to DTO(Client) language
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
         List<Comment> comments = commentRepo.findByPostId(postId);
         return comments.stream().map(this::mapToDTO).toList();
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId)); // to check the post is existed
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId)); // to get the comment

        if (!comment.getPost().getId().equals(post.getId())) { // if the comment does not belong to the post
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
              throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepo.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public String deleteComment(Long postId, Long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepo.delete(comment);
        return "Comment deleted successfully";
    }

    //    MapToDto  : -> Databased language to client Language
    private CommentDto mapToDTO(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }

//    MapToEntity : -> Client language to database structure
    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }


}
