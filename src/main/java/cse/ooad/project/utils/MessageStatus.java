package cse.ooad.project.utils;

public enum MessageStatus {
    //todo: 记得调一下这里顺序和大小写
    Read(0), UNREAD(1), READ_AND_ACCEPTED(2), READ_AND_REJECTED(3);


    final int statusCode;
    MessageStatus(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }
}
