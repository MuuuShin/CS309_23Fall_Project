package cse.ooad.project.controller;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin("*")
public class SelectionTimeController {

    //@Autowired
    //private SelectionTimeService selectionTimeService;

    @GetMapping("/selection-time")
    public Result<String> getSelectionTime() {
        log.info("get selection time");
        //TODO: get selection time
        String selectionTime = "";

        return Result.success("get selection time", selectionTime);
    }


    @PostMapping("/selection-time")
    public Result<String> setSelectionTime() {
        log.info("set selection time");

        return Result.success("set selection time", null);
    }



}
