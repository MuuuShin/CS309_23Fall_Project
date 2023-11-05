package cse.ooad.project.utils;

public enum StudentType {
    MASTER_MALE("MASTER_MALE", 1),DOCTOR_MALE("DOCTOR-MALE", 3),
    MASTER_FEMALE("MASTER_FEMALE", 2), DOCTOR_FEMALE("DOCTOR_FEMALE", 4);

    public final String name;
    public final int type;
    private StudentType(String name, int type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + name;
    }
}
