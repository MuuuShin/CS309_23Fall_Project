package cse.ooad.project.utils;

public enum MessageType {
    MSG(0),APPLY(1), SYSTEM(2), isTran(3);

    public final int typeCode;
    MessageType(int typeCode){
        this.typeCode = typeCode;
    }
}
