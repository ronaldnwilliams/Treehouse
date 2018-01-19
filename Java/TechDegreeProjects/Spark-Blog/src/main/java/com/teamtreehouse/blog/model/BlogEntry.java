package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.util.*;

public class BlogEntry {
    private String slug;
    private String title;
    private Date date;
    private String body;
    private List<Comments> comments;
    private List<String> tags;

    public BlogEntry(String title, String body) {
        this.title = title;
        this.body = body;
        comments = new ArrayList<>();
        date = new Date();
        Slugify slugify = new Slugify();
        slug = slugify.slugify(title);
    }

    public BlogEntry(String title, String body, List<String> tags) {
        this.title = title;
        this.body = body;
        comments = new ArrayList<>();
        date = new Date();
        Slugify slugify = new Slugify();
        slug = slugify.slugify(title);
        this.tags = tags;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void addComment(Comments comment) {
        comments.add(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogEntry blogEntry = (BlogEntry) o;

        if (slug != null ? !slug.equals(blogEntry.slug) : blogEntry.slug != null) return false;
        if (title != null ? !title.equals(blogEntry.title) : blogEntry.title != null) return false;
        if (date != null ? !date.equals(blogEntry.date) : blogEntry.date != null) return false;
        if (body != null ? !body.equals(blogEntry.body) : blogEntry.body != null) return false;
        if (comments != null ? !comments.equals(blogEntry.comments) : blogEntry.comments != null) return false;
        return tags != null ? tags.equals(blogEntry.tags) : blogEntry.tags == null;
    }

    @Override
    public int hashCode() {
        int result = slug != null ? slug.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
