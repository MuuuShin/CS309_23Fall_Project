package cse.ooad.project.service;

import java.sql.Time;

public class StudentTimeMatch {


    public static Long TimeMatch(Time awakeTime1, Time sleepTime1, Time sleepTime2, Time sleepAwake2){
    //先把Time转换为Long，如果睡觉时间在起床时间之后，则给睡觉时间减去上24小时的Long的修正，然后计算两个时间段的重合度
        long awakeTime1Long = awakeTime1.getTime();
        long sleepTime1Long = sleepTime1.getTime();
        long sleepTime2Long = sleepTime2.getTime();
        long sleepAwake2Long = sleepAwake2.getTime();

        if(sleepTime2Long>sleepAwake2Long){
            sleepTime2Long-=86400000;
        }
        if(sleepTime1Long>awakeTime1Long){
            sleepTime1Long-=86400000;
        }
        //如果两个时间段有重合，则返回重合的时间段，否则返回0
        if(sleepTime1Long<sleepAwake2Long&&sleepTime2Long<awakeTime1Long){
            return Math.min(sleepAwake2Long,awakeTime1Long)-Math.max(sleepTime1Long,sleepTime2Long);
        }



        return 0L;
    }

}
