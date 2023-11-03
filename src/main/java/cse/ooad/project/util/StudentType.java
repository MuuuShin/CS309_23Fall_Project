package cse.ooad.project.util;

public enum StudentType {
    MASTER("MASTER", 0),DOCTOR("DOCTOR", 1);

    public String name;
    public int type;
    private StudentType(String name, int type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + name;
    }
}
