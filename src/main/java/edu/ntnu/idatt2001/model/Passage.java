package edu.ntnu.idatt2001.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a passage in a text adventure game.
 * A passage has a title, a content, and a list of links to other passages.
 */
public class Passage {

  private String title;
  private String content;

  private ArrayList<Link> links = new ArrayList<>();

  /**
   * Creates a new passage with the given title and content.
   *
   * @param title   String title of the passage
   * @param content String content of the passage
   */
  public Passage(String title, String content) {
    if (title == null || title.isEmpty()) {
      throw new IllegalArgumentException("Title cannot be empty");
    }
    if (content == null || content.isEmpty()) {
      throw new IllegalArgumentException("Content cannot be empty");
    }

    this.title = title;
    this.content = content;
  }

  /**
   * Returns the title of the passage.
   *
   * @return String title
   */

  public String getTitle() {
    return title;

  }

  public void setLinks(List<Link> links) {
    this.links = (ArrayList<Link>) links;
  }

  /**
   * Returns the content of the passage.
   *
   * @return String content
   */

  public String getContent() {
    return content;

  }


  /**
   * Adds a link to the passage.
   *
   * @param link Link to the Passage
   * @return boolean true/false
   */

  public boolean addLink(Link link) {
    if (link == null) {
      throw new IllegalArgumentException("Link cannot be null");
    }

    return links.add(link);
  }


  /**
   * Returns the list of links of the passage.
   *
   * @return ArrayList<Link> links
   */

  public List<Link> getLinks() {
    if (links == null) {
      throw new IllegalArgumentException("Links cannot be empty");
    }
    return links;
  }

  /**
   * Compare two passages.
   *
   * @param o Link
   * @return boolean true/false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Passage passage = (Passage) o;
    return Objects.equals(title, passage.title)
            && Objects.equals(content, passage.content)
            && Objects.equals(links, passage.links);
  }

  /**
   * Returns the hashcode of the passage.
   *
   * @return int hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(title, content, links);
  }

  /**
   * Returns true if the passage has a link.
   *
   * @return boolean true/false
   */
  public boolean hasLink() {
    return !links.isEmpty();
  }


  @Override
  public String toString() {
    return "Title: " + getTitle() + "\n"
            + "Content: " + getContent() + "\n"
            + "Links: " + getLinks() + "\n";
  }
}


