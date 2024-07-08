package com.sptingboot.blog.service;

import com.sptingboot.blog.payload.CommentDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest);

    String deleteComment(Long postId, Long commentId);
}
