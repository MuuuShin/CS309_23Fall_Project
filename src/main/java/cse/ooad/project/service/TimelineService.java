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


    //测试用的
    public static int STATUS = 0;
    public static int timestamp = 0;

    public int getStage(int type){
        Timeline timeline = timelineRepository.getTimelineByType(type);

        //Timestamp nowTime = new Timestamp(new Date().getTime());
        //测试的时候用的
        if (timestamp == 0){
            return STATUS;
        }
        Timestamp nowTime = new Timestamp(timestamp);
        //return STATUS;
        if (nowTime.before(timeline.getBeginTime1()))
            return 0;
        if (nowTime.after(timeline.getBeginTime1()) && nowTime.before(timeline.getEndTime1()))
            return 1;
        if (nowTime.after(timeline.getBeginTime2()) && nowTime.before(timeline.getEndTime2()))
            return 2;
        if (nowTime.after(timeline.getBeginTime3()) && nowTime.before(timeline.getEndTime3()))
            return 3;
        if (nowTime.after(timeline.getBeginTime4()) && nowTime.before(timeline.getEndTime4()))
            return 4;
        return 5;
    }

    public Timeline getTimelineByType(int type){
        return timelineRepository.getTimelineByType(type);
    }
}
