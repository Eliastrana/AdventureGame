package edu.ntnu.idatt2001;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * A Story is a collection of passages. A story has a title and an opening passage.
 * A story can be played by a player.
 *
 */
public class Story {

    private String title;

    private Map<Link, Passage> passages;

    private Passage openingPassage;

    /**
     * Creates a new story with the given title and opening passage.
     * @param title String containing the title of the story
     * @param openingPassage Passage containing the opening passage of the story
     */

    public Story(String title, Passage openingPassage) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be empty");
        if (openingPassage == null) throw new IllegalArgumentException("Opening passage cannot be null");
        this.title = title;
        this.openingPassage = openingPassage;
        passages = new HashMap<>();
    }

    /**
     * Returns the title of the story.
     * @return String containing the title of the story
     */

    public String getTitle(){
        return title;
    }

    /**
     * Returns the opening passage of the story.
     * @return Passage containing the opening passage of the story
     */

    public Passage getOpeningPassage(){
        return openingPassage;
    }

    /**
     * Adds a passage to the story.
     * @param newPassage Passage to be added to the story
     */

    public void addPassage(Passage newPassage){
        if (newPassage == null) throw new IllegalArgumentException("Passage cannot be null");
        if (newPassage.getTitle().equals(openingPassage.getTitle())) throw new IllegalArgumentException("Passage cannot have the same title as the opening passage");
        passages.put(new Link(newPassage.getTitle(),newPassage.getContent(), new ArrayList<>()),newPassage);
    }

    /**
     * Returns the passage that is linked to the given link.
     * @param link Link containing the link to the passage
     * @return Passage containing the passage that is linked to the given link
     */

    public Passage getPassage(Link link){
        if (link == null) throw new IllegalArgumentException("Link cannot be null");
        Link newlink = new Link(link.getReference(),link.getReference(), new ArrayList<Action>());
        return passages.get(newlink);
    }

    /**
     * Removes the passage that is linked to the given link.
     * @param link Link containing the link to the passage
     */
    public void removePassage(Link link) {
        if (link == null) {
            throw new IllegalArgumentException("Link cannot be null");
        }
        // check if any passage has the given link in its links
        boolean isLinkInUse = passages.values()
                .stream()
                .anyMatch(passage -> passage.getLinks().contains(link));

        if (isLinkInUse) {
            System.out.println("Link is already in use");
            return;
        }
        // remove the passage with the given link
        passages.remove(link);
        }



    /**
     * Returns a list with all the broken links.
     * @return List containing all the broken links.
     */
    public List<Link> getBrokenLinks() {
        return passages.values().stream() // Stream of all passages
                .flatMap(passage -> passage.getLinks().stream()) // Stream of all links in all passages
                .filter(link -> !passages.containsKey(link)) // Filter out links that are not broken
                .collect(Collectors.toList()); // Collect the broken links into a list
    }

    /**
     * Returns a list with all the passages in the story.
     * @return List containing all the passages in the story.
     */
    public Collection<Passage> getPassages() {
        return passages.values();
    }

    public static void main(String[] args) {
        Story story = new Story("The story", new Passage("The beginning", "Once upon a time..."));
        System.out.println("Title: "+ story.getTitle() +"\n"+"Passages: "+ story.getPassages()+"\n"+ "Opening passage: " + story.openingPassage.toString());

    }

}
