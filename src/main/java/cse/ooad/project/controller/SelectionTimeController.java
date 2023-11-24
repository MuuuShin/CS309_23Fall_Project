package cse.ooad.project.controller;


import cse.ooad.project.model.Timeline;
import cse.ooad.project.service.SearchService;
import cse.ooad.project.service.TeacherService;
import cse.ooad.project.service.TimelineService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("*")
public class SelectionTimeController {

    //@Autowired
    //private SelectionTimeService selectionTimeService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    TimelineService timelineService;

    @GetMapping("/selection-time/{type}")
    public Result<Timeline> getSelectionTime(@RequestParam("type") String type) {
        log.info("get selection time");
        //TODO: get selection time
        Timeline selectionTime = timelineService.getTimelineByType(Integer.parseInt(type));

        return Result.success("get selection time", selectionTime);
    }


    @PostMapping("/selection-time")
    public Result<String> setSelectionTime(@RequestBody Timeline timeline) {
        log.info("set selection time");

        teacherService.saveTimeline(timeline);

        return Result.success("set selection time", null);
    }



}
