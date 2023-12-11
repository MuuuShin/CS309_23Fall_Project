package cse.ooad.project.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;


@Slf4j
public class StudentTimeMatch {


    public static Long TimeMatch(Time awakeTime1, Time sleepTime1, Time awakeTime2, Time sleepTime2) {
        Collection<Long> timeIndex1 = getSleepTimeIndex(awakeTime1, sleepTime1);
        Collection<Long> timeIndex2 = getSleepTimeIndex(awakeTime2, sleepTime2);
        return (long) timeIndex1.stream().filter(timeIndex2::contains).toList().size();
    }
    //输入一个起床时间和一个睡觉时间，讲一天24小时按15个分钟划分为96个时间段，返回一个集合，如果这个时间段在起床时间和睡觉时间之间，则加入这个集合，集合中的每个元素代表一个时间段的索引，
    private static Collection<Long> getSleepTimeIndex(Time awakeTime, Time sleepTime) {
        long zeroTime = new Time(0,0,0).getTime();
        long lastTime = new Time(23,59,59).getTime();
        Collection<Long> timeIndex = new ArrayList<>();
        long awakeTimeLong = awakeTime.getTime();
        long sleepTimeLong = sleepTime.getTime();
        if (sleepTimeLong < awakeTimeLong) {
            long baseTime = sleepTimeLong - sleepTimeLong%900000;
            while (baseTime < awakeTimeLong) {
                timeIndex.add(baseTime/900000);
                baseTime += 900000;
            }
        }else {
            long baseTime = zeroTime;
            while (baseTime < awakeTimeLong) {
                timeIndex.add(baseTime/900000);
                baseTime += 900000;
            }
            baseTime = sleepTimeLong - sleepTimeLong%900000;
            while (baseTime < lastTime) {
                timeIndex.add(baseTime/900000);
                baseTime += 900000;
            }
        }
        return timeIndex;
    }

}


