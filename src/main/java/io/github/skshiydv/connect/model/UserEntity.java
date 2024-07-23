package io.github.skshiydv.connect.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "connect_user")
@Table(name = "connect_user", uniqueConstraints = {@UniqueConstraint(name = "user_email", columnNames = "email")})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "username", nullable = false, columnDefinition = "TEXT")
    private String username;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")

    private String name;
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")

    private String email;
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private List<Roles> roles= new ArrayList<>();

    public UserEntity(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
