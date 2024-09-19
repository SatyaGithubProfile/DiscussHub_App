package com.sptingboot.blog.controller;

import com.sptingboot.blog.entity.Comment;
import com.sptingboot.blog.payload.CommentDto;
import com.sptingboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {


    private  CommentService commenerServ;
    public CommentController(CommentService commenerServ) {
        this.commenerServ = commenerServ;
    }
    @PostMapping("/posts/{postId}/comments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId") long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commenerServ.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value="postId") long postId) {
//         return new ResponseEntity(commenerServ.getCommentsByPostId(postId), HttpStatus.OK);
         return commenerServ.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{comment_id}")
    public CommentDto getCommentById(@PathVariable(value="postId") long postId, @PathVariable(value="comment_id") long commentId) {
        return commenerServ.getCommentById(postId, commentId);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postId") Long postId,
                                                     @PathVariable(value="id") Long commentId,
                                                     @Valid @RequestBody CommentDto commentRequest) {
        CommentDto updatedComment = commenerServ.updateComment(postId, commentId, commentRequest);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteComment(@PathVariable(value="postId") Long postId,
                                @PathVariable(value="id") Long commentId) {
       String s = commenerServ.deleteComment(postId, commentId);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

}
