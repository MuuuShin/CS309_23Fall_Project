package cse.ooad.project.service;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;


@Slf4j
public class StudentTimeMatch {


    public static Long TimeMatch(Time awakeTime1, Time sleepTime1, Time awakeTime2, Time sleepTime2) {
        //先把Time转换为Long，如果睡觉时间在起床时间之后，则给睡觉时间减去上24小时的Long的修正，然后计算两个时间段的重合度
        long awakeTime1Long = awakeTime1.getTime();
        long sleepTime1Long = sleepTime1.getTime();
        long sleepTime2Long = sleepTime2.getTime();
        long awakeTime2Long = awakeTime2.getTime();
        awakeTime1Long += 86400000;
        awakeTime2Long += 86400000;

        Time noon = new Time(12, 0, 0);

        if (sleepTime2Long <= noon.getTime()) {
            sleepTime2Long += 86400000;
        }
        if (sleepTime1Long <= noon.getTime()) {
            sleepTime1Long += 86400000;
        }


        long startTime1 = sleepTime1Long; // 第一个时间段的开始时间
        long endTime1 = awakeTime1Long;   // 第一个时间段的结束时间
        long startTime2 = sleepTime2Long; // 第二个时间段的开始时间
        long endTime2 = awakeTime2Long;   // 第二个时间段的结束时间

        // 找到最晚开始时间
        long latestStart = Math.max(startTime1, startTime2);

        // 找到最早结束时间
        long earliestEnd = Math.min(endTime1, endTime2);

        // 检查是否有交集
        if(latestStart < earliestEnd) {
            // 存在交集
            long intersectionStart = latestStart; // 交集的开始时间
            long intersectionEnd = earliestEnd;   // 交集的结束时间

            log.warn(intersectionEnd - intersectionStart + "");

            return intersectionEnd - intersectionStart;
            // 这里可以根据需要处理交集
        }



        return 0L;
    }

}


