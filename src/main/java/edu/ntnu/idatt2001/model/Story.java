package edu.ntnu.idatt2001.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A Story is a collection of passages. A story has a title and an opening passage.
 * A story can be played by a player.
 */
public class Story {

  private final String title;
  private Passage openingPassage;
  private final Map<String, Passage> passages;

  /**
   * Creates a new story with the given title and opening passage.
   *
   * @param title          String title of the story
   * @param openingPassage Passage opening passage of the story
   */

  public Story(String title, Passage openingPassage) {
    if (title == null || title.isEmpty()) {
      throw new NullPointerException("Title cannot be empty");
    }
    if (openingPassage == null) {
      throw new NullPointerException("Opening passage cannot be null");
    }
    this.title = title;
    this.openingPassage = openingPassage;
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

  /**
   * Adds a passage to the story.
   *
   * @param newPassage Passage to be added to the story
   */
  public void addPassage(Passage newPassage) {
    if (newPassage == null) {
      throw new NullPointerException("Passage cannot be null");
    }
    passages.put(newPassage.getTitle(), newPassage);
  }

  /**
   * Returns the passage that is linked to the given link.
   *
   * @param link Link containing the link to the passage
   * @return Passage containing the passage that is linked to the given link
   */
  public Passage getPassage(Link link) {
    Passage passage = passages.get(link.getReference());
    if (passage == null) {
      throw new IllegalArgumentException("Link not found");
    }
    return passage;
  }

  /**
   * Removes the passage that is linked to the given link.
   *
   * @param link Link containing the link to the passage
   */
  public void removePassage(Link link) {
    if (link == null) {
      throw new NullPointerException("Link cannot be null");
    }

    boolean isLinkInUse = passages.values()
            .stream()
            .anyMatch(passage -> passage.getLinks().contains(link))
            || openingPassage.getLinks().contains(link);

    if (isLinkInUse) {
      throw new IllegalArgumentException("Link is in use");
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
            .toList();
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
