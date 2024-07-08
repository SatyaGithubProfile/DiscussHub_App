package com.sptingboot.blog.service.impl;

import com.sptingboot.blog.entity.Post;
import com.sptingboot.blog.exception.ResourceNotFoundException;
import com.sptingboot.blog.payload.PostDto;
import com.sptingboot.blog.repository.PostRepository;
import com.sptingboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper mapper;


    public PostServiceImpl(PostRepository postRepo, ModelMapper mp) {
        this.postRepo = postRepo;
        this.mapper = mp;
    }

    @Override
    public PostDto createPost(PostDto pD) {

        Post post = mapToEntity(pD);     // convert DTO to entity

        Post newPost = postRepo.save(post);  //Save the post to DB
        return  mapToDTO(newPost);  //  conver entity to DTO
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postResult = postRepo.findAll();
        return postResult.stream().map(this::mapToDTO).toList();
    }

    @Override
    public PostDto getPostById(long  id) throws ResourceNotFoundException {
//        throw
       Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id ));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id ));

        post.setTitile(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepo.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id ));
        postRepo.delete(post);
    }



    //Convert Entity to DTO -> Databased language to client Language
    private PostDto mapToDTO(Post newPost) {
        return mapper.map(newPost, PostDto.class);
    }

//    Convert DTO to Entity -> client language to Database Language
    private Post mapToEntity(PostDto pD) {
        return mapper.map(pD, Post.class);
    }
}
