package dev.sosnovsky.applications.repository;

import dev.sosnovsky.applications.model.PhoneDetails;
import dev.sosnovsky.applications.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PhoneDetailsRepository extends JpaRepository<PhoneDetails, Integer> {
}

