package com.wcreators.todo_api.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private boolean deleted = false;

    @ManyToMany(mappedBy = "notes", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
}
