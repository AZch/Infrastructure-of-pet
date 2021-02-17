package com.wcreators.users_api.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
