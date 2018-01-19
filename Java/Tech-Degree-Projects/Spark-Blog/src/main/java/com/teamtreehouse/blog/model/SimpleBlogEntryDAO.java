package com.teamtreehouse.blog.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlogEntryDAO implements BlogEntryDAO {
    private List<BlogEntry> blogEntries;

    public SimpleBlogEntryDAO() {

        blogEntries = new ArrayList<>();
    }


    @Override
    public boolean add(BlogEntry blogEntry) {

        return blogEntries.add(blogEntry);
    }

    @Override
    public boolean remove(BlogEntry blogEntry) {

        return blogEntries.remove(blogEntry);

    }

    @Override
    public List<BlogEntry> findAll() {

        return new ArrayList<>(blogEntries);
    }

    @Override
    public BlogEntry findTitleBySlug(String slug) {
        return blogEntries.stream()
                .filter(blogEntry -> blogEntry.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
