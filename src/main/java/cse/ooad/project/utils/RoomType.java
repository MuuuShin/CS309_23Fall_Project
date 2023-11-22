package cse.ooad.project.utils;

import lombok.Getter;

@Getter
public enum RoomType {
    MASTER_MALE_ONE(1), MASTER_MALE_TWO(2), MASTER_MALE_Three(3),MASTER_MALE_Four(4),
    MASTER_FEMALE_ONE(5), MASTER_FEMALE_TWO(6), MASTER_FEMALE_THREE(7), MASTER_FEMALE_FOUR(8),
    DOCTOR_MALE_ONE(9), DOCTOR_MALE_TWO(10), DOCTOR_MALE_THREE(11), DOCTOR_MALE_FOUR(12),
    DOCTOR_FEMALE_ONE(13),DOCTOR_FEMALE_TWO(14), DOCTOR_FEMALE_THREE(15), DOCTOR_FEMALE_FOUR(16);


    public final int type;
    RoomType(int type){
        this.type = type;
    }

}
