package dev.sosnovsky.applications.repository;

import dev.sosnovsky.applications.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
