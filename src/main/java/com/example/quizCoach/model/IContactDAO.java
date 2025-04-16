package com.example.quizCoach.model;

import java.util.List;

/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface IContactDAO {
    /**
     * Adds a new contact to the database.
     * @param quiz The contact to add.
     */
    public void addContact(Quiz quiz);
    /**
     * Updates an existing contact in the database.
     * @param quiz The contact to update.
     */
    public void updateContact(Quiz quiz);
    /**
     * Deletes a contact from the database.
     * @param quiz The contact to delete.
     */
    public void deleteContact(Quiz quiz);
    /**
     * Retrieves a contact from the database.
     * @param id The id of the contact to retrieve.
     * @return The contact with the given id, or null if not found.
     */
    public Quiz getContact(int id);
    /**
     * Retrieves all contacts from the database.
     * @return A list of all contacts in the database.
     */
    public List<Quiz> getAllContacts();
}

