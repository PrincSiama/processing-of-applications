package dev.sosnovsky.applications.repository;

import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.StatusOfApplications;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findAllByCreatorsId(int creatorsId, Pageable pageable);

    List<Application> findAllByStatusInAndNameContainsIgnoreCase(List<StatusOfApplications> statuses, String text,
                                                                 Pageable pageable);

}

