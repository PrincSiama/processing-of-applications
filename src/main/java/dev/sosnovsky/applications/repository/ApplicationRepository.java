package dev.sosnovsky.applications.repository;

import dev.sosnovsky.applications.model.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findAllByCreatorsIdOrderByCreateDateDesc(int creatorsId, Pageable pageable);

/*    @Query("SELECT a FROM Applications a" +
            "WHERE a.status = 'SENT'" +
            "ORDER BY a.createDate desc")
    List<Application> sentApplications(Pageable pageable);*/

    /*@Query("SELECT a FROM Applications a left join users u on a.creatorsId = u.id" +
            "WHERE a.status = 'SENT' and u.name like ?" + // todo уточнить запрос в части поиска по имени
            "ORDER BY a.createDate desc")
    List<Application> sentApplicationsWithName(String text);*/
}
