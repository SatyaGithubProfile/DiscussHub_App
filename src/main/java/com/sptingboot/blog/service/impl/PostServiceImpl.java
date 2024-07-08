package com.sptingboot.blog.service.impl;

import com.sptingboot.blog.entity.Comment;
import com.sptingboot.blog.entity.Post;
import com.sptingboot.blog.exception.ResourceNotFoundException;
import com.sptingboot.blog.payload.CommentDto;
import com.sptingboot.blog.payload.PostDto;
import com.sptingboot.blog.repository.PostRepository;
import com.sptingboot.blog.service.CommentService;
import com.sptingboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        PostDto postRes =  mapToDTO(newPost);    //  conver entity to DTO
        return postRes;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postResult = postRepo.findAll();
        return postResult.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long  id) throws ResourceNotFoundException {
//        throw
       Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id ));
        System.out.println("post--->" + post);
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
        PostDto postRes = mapper.map(newPost, PostDto.class);
//        PostDto postRes = new PostDto();
//        postRes.setId(newPost.getId());
//        postRes.setTitle(newPost.getTitile());
//        postRes.setContent(newPost.getContent());
//        postRes.setDescription(newPost.getDescription());
//
//        Set<CommentDto> CommentDto =  newPost.getComments().stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toSet());
//
//        postRes.setComments(CommentDto);
        return postRes;
    }

//    Convert DTO to Entity -> client language to Database Language
    private Post mapToEntity(PostDto pD) {
        Post post = mapper.map(pD, Post.class);
//        Post post = new Post();
//        post.setTitile(pD.getTitle());
//        post.setDescription(pD.getDescription());
//        post.setContent(pD.getContent());
//        post.setComments(pD.getComments());
        return post;
    }
}
