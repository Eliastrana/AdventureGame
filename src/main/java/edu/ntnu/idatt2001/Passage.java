package edu.ntnu.idatt2001;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Passage {

    private String title, content;

    private ArrayList links = new ArrayList<>();


    public Passage(String title, String content) {
        if (title == null || title.isEmpty()) throw new IllegalArgumentException("Title cannot be empty");
        if (content == null || content.isEmpty()) throw new IllegalArgumentException("Content cannot be empty");

        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;

    }

    public String getContent() {
        return content;

    }

    public boolean addLink(Link link) {
        if (link == null) {
            throw new IllegalArgumentException("Link cannot be null");
        }
        links.add(link);
        return true;
    }

    public ArrayList<Link> getLinks() {
        if (links == null) throw new IllegalArgumentException("Links cannot be empty");
        return links;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passage passage = (Passage) o;
        return Objects.equals(title, passage.title) && Objects.equals(content, passage.content) && Objects.equals(links, passage.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, links);
    }
    public boolean hasLink() {
        return !links.isEmpty();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
