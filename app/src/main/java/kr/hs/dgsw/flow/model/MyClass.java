package kr.hs.dgsw.flow.model;

import com.google.gson.annotations.SerializedName;

public class MyClass {
    @SerializedName("grade")
    private int grade;

    @SerializedName("class_room")
    private int classRoom;

    @SerializedName("class_number")
    private int classNumber;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(int classRoom) {
        this.classRoom = classRoom;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }
}
