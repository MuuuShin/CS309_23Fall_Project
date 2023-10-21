package cse.ooad.project.service;


import cse.ooad.project.repository.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimelineService {
    @Autowired
    TimelineRepository timelineRepository;
}
