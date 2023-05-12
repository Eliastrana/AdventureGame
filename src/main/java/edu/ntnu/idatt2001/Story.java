package edu.ntnu.idatt2001;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Story is a collection of passages. A story has a title and an opening passage.
 * A story can be played by a player.
 */
public class Story {

    private final String title;
    private Passage openingPassage;
    private final Map<String, Passage> passages;

    public Story(String title, Passage openingPassage) {
        this.title = Objects.requireNonNull(title, "Title cannot be empty");
        this.openingPassage = Objects.requireNonNull(openingPassage, "Opening passage cannot be null");
        this.passages = new HashMap<>();
    }

    /**
     * Returns the title of the story.
     *
     * @return String containing the title of the story
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the opening passage of the story.
     *
     * @return Passage containing the opening passage of the story
     */
    public Passage getOpeningPassage() {
        return openingPassage;
    }



    public void setOpeningPassage(Passage openingPassage) {
        this.openingPassage = openingPassage;
    }

    /**
     * Adds a passage to the story.
     *
     * @param newPassage Passage to be added to the story
     */
    public void addPassage(Passage newPassage) {
        Objects.requireNonNull(newPassage, "Passage cannot be null");
        passages.put(newPassage.getTitle(), newPassage);
    }

    /**
     * Returns the passage that is linked to the given link.
     *
     * @param link Link containing the link to the passage
     * @return Passage containing the passage that is linked to the given link
     */
    public Passage getPassage(Link link) {
        Objects.requireNonNull(link, "Link cannot be null");
        Passage passage = passages.get(link.getReference());
        if (passage == null) {
            System.out.println("Link reference not found: " + link.getReference());
        }
        return passage;
    }

    /**
     * Removes the passage that is linked to the given link.
     *
     * @param link Link containing the link to the passage
     */
    public void removePassage(Link link) {
        Objects.requireNonNull(link, "Link provided cannot be null");

        boolean isLinkInUse = passages.values()
                .stream()
                .anyMatch(passage -> passage.getLinks().contains(link));

        if (isLinkInUse) {
            System.out.println("Link is already in use");
            // Add debug information if needed
            System.out.println("Link: " + link);
            return;
        }
        passages.remove(link.getReference());
    }


    /**
     * Returns a list with all the broken links.
     *
     * @return List containing all the broken links.
     */
    public List<Link> getBrokenLinks() {
        return passages.values().stream()
                .flatMap(passage -> passage.getLinks().stream())
                .filter(link -> !passages.containsKey(link.getReference()))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list with all the passages in the story.
     *
     * @return List containing all the passages in the story.
     */
    public Collection<Passage> getPassages() {

        return passages.values();
    }

}
