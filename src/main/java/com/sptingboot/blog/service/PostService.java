package com.sptingboot.blog.service;

import com.sptingboot.blog.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDot);
    List<PostDto> getAllPosts();
    PostDto getPostById(long id) ;
    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);

}
