package dev.sosnovsky.applications.repository;

import dev.sosnovsky.applications.model.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findAllByCreatorsId(int creatorsId, Pageable pageable);
//todo не работает с пустым text
    @Query(value = "SELECT a FROM Application a " +
            "WHERE (a.status = 'SENT') and (:text is null or lower(a.name) like lower(concat('%', :text, '%')))")
    List<Application> sentApplications(String text, Pageable pageable);

}

