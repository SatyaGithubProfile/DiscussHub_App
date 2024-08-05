package com.sptingboot.blog.entity;

import lombok.*;
import jakarta.persistence.*;

    import java.util.HashSet;
import java.util.Set;

@Data   //lambok annotation helps to not to create getters and setters
@Getter
@Setter
@AllArgsConstructor  //lambok annotation to crete constructor
@NoArgsConstructor //Lambok annotation to create no argument constructor

@Entity
@Table(
        name = "posts", uniqueConstraints =  {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
}
