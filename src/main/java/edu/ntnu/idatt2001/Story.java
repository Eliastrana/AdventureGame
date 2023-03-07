package edu.ntnu.idatt2001;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

public class Story {

    private String title;

    private Map<Link, Passage> passages = new IdentityHashMap<>();

    private Passage openingPassage;

    public Story(String title, Passage openingPassage) {
        if (title == null || title.isEmpty()) throw new IllegalArgumentException("Title cannot be empty");
        if (openingPassage == null) throw new IllegalArgumentException("Opening passage cannot be null");
        this.title = title;
        this.openingPassage = openingPassage;
    }

    public String getTitle(){
        return title;
    }

    public Passage getOpeningPassage(){
        return openingPassage;
    }

    public void addPassage(Passage newPassage){
        if (newPassage == null) throw new IllegalArgumentException("Passage cannot be null");
        passages.put(new Link(newPassage.getTitle(),newPassage.getTitle()),newPassage);
    }

    public Passage getPassage(Link link){
        if (link == null) throw new IllegalArgumentException("Link cannot be null");
        return passages.get(link);
    }

    public Collection<Passage> getPassages() {
        return passages.values();
    }

}
