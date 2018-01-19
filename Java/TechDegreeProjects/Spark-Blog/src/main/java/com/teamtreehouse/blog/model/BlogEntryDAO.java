package com.teamtreehouse.blog.model;

import com.teamtreehouse.blog.model.BlogEntry;

import java.util.List;

public interface BlogEntryDAO {
    boolean add(BlogEntry blogEntry);
    boolean remove(BlogEntry blogEntry);
    List<BlogEntry> findAll();
    BlogEntry findTitleBySlug(String slug);
}
