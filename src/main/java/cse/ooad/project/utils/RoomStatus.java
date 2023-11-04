package cse.ooad.project.utils;

public enum RoomStatus {
    SELECTED(0), UNSELECTED(1);


    final int statusCode;
    RoomStatus(int statusCode){
        this.statusCode = statusCode;
    }
}
