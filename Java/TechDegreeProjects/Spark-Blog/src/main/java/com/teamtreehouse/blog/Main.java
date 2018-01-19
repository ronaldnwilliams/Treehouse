package com.teamtreehouse.blog;

import com.teamtreehouse.blog.model.BlogEntryDAO;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comments;
import com.teamtreehouse.blog.model.SimpleBlogEntryDAO;

import java.util.*;

import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;
import static spark.Spark.halt;

public class Main {

    public static void main(String[] args) {
        staticFileLocation("/public");
        BlogEntryDAO dao = new SimpleBlogEntryDAO();
        //Add three default blogs
        Map<String, String> defaultBlogs = new HashMap<>();
        defaultBlogs.put("The best day I’ve ever had", "The best day I’ve ever had");
        defaultBlogs.put("That time at the mall", "That time at the mall");
        defaultBlogs.put("The absolute worst day I’ve ever had", "The absolute worst day I’ve ever had");
        for(Map.Entry<String, String> entry : defaultBlogs.entrySet()) {
            List<String> tags = new ArrayList<>();
            tags.add("defaultblog,");
            tags.add(" thisisatag");
            String title = entry.getKey();
            String body = entry.getValue();
            BlogEntry blogEntry = new BlogEntry(title, body, tags);
            dao.add(blogEntry);
        }


        before("/edit/:slug", (req, res) -> {
            if (req.cookie("password") == null) {
                res.redirect("/password");
                halt();
            }
        });

        before("/new", (req, res) -> {
            if (req.cookie("password") == null) {
                res.redirect("/password");
                halt();
            }
        });

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntry", dao.findAll());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/index", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntry", dao.findAll());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/detail/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntry", dao.findTitleBySlug(req.params("slug")));
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        post("/detail/:slug/comment", (req, res) -> {
            BlogEntry blogEntry = dao.findTitleBySlug(req.params("slug"));
            String name = "Name: " + req.queryParams("name");
            String nameComment = "Comment: " + req.queryParams("comment");
            Comments comment = new Comments(name, nameComment);
            blogEntry.addComment(comment);
            res.redirect("/index");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/edit/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntry", dao.findTitleBySlug(req.params("slug")));
            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/edit/:slug", (req, res) -> {
            BlogEntry blogEntry = dao.findTitleBySlug(req.params("slug"));
            String title = req.queryParams("title");
            if (req.queryParams("tags") != "") {
                List<String> tags = new ArrayList<>(Arrays.asList(req.queryParams("tags").split(",")));
                blogEntry.setTags(tags);
            }
            String body = req.queryParams("entry");
            blogEntry.setTitle(title);
            blogEntry.setBody(body);
            res.redirect("/index");
            return null;
        });

        post("/delete/:slug", (req, res) -> {
            BlogEntry blogEntry = dao.findTitleBySlug(req.params("slug"));
            dao.remove(blogEntry);
            res.redirect("/index");
            return null;
        });

        get("/new", (req, res) -> {
            return new ModelAndView(null, "new.hbs");
        }, new HandlebarsTemplateEngine());

        post("/new", (req, res) -> {
            String title = req.queryParams("title");
            String body = req.queryParams("body");
            if (req.queryParams("tags") != "") {
                List<String> tags = new ArrayList<>(Arrays.asList(req.queryParams("tags").split(",")));
                BlogEntry blogEntry = new BlogEntry(title, body, tags);
                dao.add(blogEntry);
                res.redirect("/index");
                return null;
            } else {
                BlogEntry blogEntry = new BlogEntry(title, body);
                dao.add(blogEntry);
                res.redirect("/index");
                return null;
            }
        });

        get("/password", (req, res) -> {
            return new ModelAndView(null, "password.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sign-in", (req, res) -> {
            String password = req.queryParams("password");
            if (password.equals("admin")) {
                res.cookie("password", password);
                res.redirect("/");
                return null;
            } else {
                res.redirect("/password");
                return null;
            }
        }, new HandlebarsTemplateEngine());
    }
}

