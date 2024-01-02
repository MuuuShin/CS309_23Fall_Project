package cse.ooad.project.repository;

import cse.ooad.project.model.Timeline;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {


    Timeline getTimelineByType(int type);
}