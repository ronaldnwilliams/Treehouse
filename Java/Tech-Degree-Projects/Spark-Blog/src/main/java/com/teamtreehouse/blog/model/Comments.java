package com.teamtreehouse.blog.model;

import java.util.Date;

public class Comments {
    private String name;
    private String comment;
    private Date date;

    public Comments(String name, String comment) {
        this.name = name;
        this.comment = comment;
        this.date = new Date();
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comments comments = (Comments) o;

        if (name != null ? !name.equals(comments.name) : comments.name != null) return false;
        if (comment != null ? !comment.equals(comments.comment) : comments.comment != null) return false;
        return date != null ? date.equals(comments.date) : comments.date == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
