package cse.ooad.project.service;

import cse.ooad.project.model.Timeline;
import cse.ooad.project.repository.TimelineRepository;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TimelineService {

    @Autowired
    TimelineRepository timelineRepository;

    public int getStage(int type){
        Timeline timeline = timelineRepository.getTimelineByType(type);

        Timestamp nowTime = new Timestamp(new Date().getTime());

        if (nowTime.before(timeline.getBeginTime1()))
            return 0;
        if (nowTime.after(timeline.getBeginTime1()) && nowTime.after(timeline.getEndTime1()))
            return 1;
        if (nowTime.after(timeline.getBeginTime2()) && nowTime.before(timeline.getEndTime2()))
            return 2;
        if (nowTime.after(timeline.getBeginTime3()) && nowTime.before(timeline.getEndTime3()))
            return 3;
        if (nowTime.after(timeline.getBeginTime4()) && nowTime.before(timeline.getEndTime4()))
            return 4;
        return 5;
    }
}
