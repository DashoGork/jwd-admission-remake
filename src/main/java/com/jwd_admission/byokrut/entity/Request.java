package com.jwd_admission.byokrut.entity;


import java.util.ArrayList;
import java.util.Objects;

public class Request extends BaseEntity {
    private int facultyId;
    private int userId;
    private int score;
    private int approved = 0;

    public Request(int userId) {
        super(-1);
        this.userId = userId;
    }

    public Request(int id, int facultyId, int userId, int score, int approved) {
        super(id);
        this.facultyId = facultyId;
        this.userId = userId;
        this.score = score;
        this.approved = approved;
    }

    public Request(int facultyId, int userId, int score) {
        super(-1);
        this.facultyId = facultyId;
        this.userId = userId;
        this.score = score;
    }

    public Request(int facultyId, int score) {
        super(-1);
        this.facultyId = facultyId;
        this.score = score;
    }


    public Request(int id, int facultyId, int score, int approved) {
        super(id);
        this.facultyId = facultyId;
        this.score = score;
        this.approved = approved;
    }

    public void setId(int id) {
        super.setId(id);
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getId() {
        return super.getId();
    }

    public int getFacultyId() {
        return facultyId;
    }

    public int getApproved() {
        return approved;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public static boolean findApprovementInArray(ArrayList<Request> array, int id){
        for (Request request:array) {
            if(request.getUserId()==id){
                return request.getApproved()==1;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return facultyId == request.facultyId && userId == request.userId && score == request.score && approved == request.approved;
    }

    @Override
    public int hashCode() {
        return Objects.hash(facultyId, userId, score, approved);
    }
}
