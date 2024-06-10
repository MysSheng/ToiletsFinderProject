package com.JavaFinal.ToiletsFinder.models;

public class tableCol {
    public double distance;
    public String name;
    public String isFree;
    public String comment;
    public String link;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public tableCol() {
        distance = 0;
        name = "";
        isFree = "";
        comment = "";
        link = "";
    }
}
