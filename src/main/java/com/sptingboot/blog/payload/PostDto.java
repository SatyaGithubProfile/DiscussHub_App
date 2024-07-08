package com.sptingboot.blog.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

//@Data  //lambok annotation to help to create getter and setters
@Getter
@Setter

public class PostDto {
    private long id;

    private String title;

    private String description;
    private String content;
    private Set<CommentDto> comments;
}
