package cse.ooad.project.utils;

public enum MessageStatus {
    UNREAD(0), READ(1), READ_AND_ACCEPTED(2), READ_AND_REJECTED(3);


    final int statusCode;
    MessageStatus(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }
}
