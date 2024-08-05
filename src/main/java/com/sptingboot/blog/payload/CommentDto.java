package com.sptingboot.blog.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentDto {

    private long id;
    @NotEmpty
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 5, message = "Body should have at least 5 characters")
    private String body;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
