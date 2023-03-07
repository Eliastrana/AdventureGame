package edu.ntnu.idatt2001;

import javax.security.auth.login.CredentialNotFoundException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Link {
    private String text;
    private String reference;
    private ArrayList<Action> actions = new ArrayList<>();


    public Link(String text, String reference) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be empty");
        }
        this.text = text;
        this.reference = reference;
    }

    public String getReference() {

        return reference;
    }

    public String getText() {

        return text;
    }

    public List<Action> getActions() {

        return actions;
    }

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

    @Override
    public String toString() {
        return "Tekst: " + getText() + "\n"
                + "Reference: " + getReference() + "\n"
                + "Actions: " + getActions();
    }
}
