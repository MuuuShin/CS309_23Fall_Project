package cse.ooad.project.utils;

public enum RoomStatus {
    SELECTED(1), UNSELECTED(0);


    public final int statusCode;
    RoomStatus(int statusCode){
        this.statusCode = statusCode;
    }
}