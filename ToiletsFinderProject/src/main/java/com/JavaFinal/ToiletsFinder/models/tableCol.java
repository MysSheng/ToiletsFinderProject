package com.JavaFinal.ToiletsFinder.models;

public class tableCol {
    public String distance;
    public String name;
    public String isFree;
    public String comment;
    public String link;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
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
        distance = "";
        name = "";
        isFree = "";
        comment = "";
        link = "";
    }
}
