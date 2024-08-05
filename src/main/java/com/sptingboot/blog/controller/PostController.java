package com.sptingboot.blog.controller;

import com.sptingboot.blog.payload.PostDto;
import com.sptingboot.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postServ;

    public PostController(PostService postServ) {
        this.postServ = postServ;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postServ.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postServ.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postServ.getPostById(id));
    }

    @PutMapping("/{id}")
    /*
         http://localhost:8080/api/posts/2
    {
    "title" : "New First post",
    "description" : "New First Description",
    "content" : "New first Contenct"
    }
     */
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id){
        PostDto postResponse = postServ.updatePost(postDto, id);
        return new ResponseEntity<PostDto>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){
        postServ.deletePost(id);
        return new ResponseEntity<String>("Post entity deleted successfully.", HttpStatus.OK);
    }




}
