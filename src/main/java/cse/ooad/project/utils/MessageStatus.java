package cse.ooad.project.utils;

public enum MessageStatus {

    Read(0), UNREAD(1);


    final int statusCode;
    MessageStatus(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }
}
