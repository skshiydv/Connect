package io.github.skshiydv.connect.repository;

import io.github.skshiydv.connect.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface rolesRepository extends JpaRepository<Roles,Integer> {
    Optional<Roles> findByName(String name);
}
