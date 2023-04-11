package edu.ntnu.idatt2001;


import javax.security.auth.login.CredentialNotFoundException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a link in a text adventure game.
 * A link has a text, a reference to another passage, and a list of actions.
 */
public class Link {
    private String text;
    private String reference;
    private ArrayList<Action> actions;


    /**
     * Creates a new link with the given text and reference.
     * @param text String text with a description of the link
     * @param reference String reference
     */
    public Link(String text, String reference, ArrayList<Action> actions) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be empty");
        }
        this.text = text;
        this.reference = reference;
        this.actions = actions;
    }

    /**
     * Returns the text of the link.
     * @return String text
     */

    public String getReference() {
        return reference;
    }
    /**
     * Returns the reference of the link.
     * @return String reference
     */

    public String getText() {

        return text;
    }
    /**
     * Returns the actions of the link.
     * @return List actions
     */

    public List<Action> getActions() {

        return actions;
    }
    /**
     * Adds an action to the link.
     * @param action actions
     */

    public void addAction(Action action) {
        if (action == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }
        actions.add(action);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(text, link.text) && Objects.equals(reference, link.reference) && Objects.equals(actions, link.actions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, reference, actions);
    }

    /**
     * Returns a string representation of the link.
     * @return String with text, reference and actions
     */


    @Override
    public String toString() {
        return "Button: " + getText() + "\n"
                + "Reference: " + getReference() + "\n"
                + "Actions: " + getActions();
    }
}
