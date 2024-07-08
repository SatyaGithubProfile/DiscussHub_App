package com.sptingboot.blog.repository;

import com.sptingboot.blog.entity.Comment;
import com.sptingboot.blog.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommenRepository extends JpaRepository<Comment, Long>{

    List<Comment> findByPostId(long postId);
}
