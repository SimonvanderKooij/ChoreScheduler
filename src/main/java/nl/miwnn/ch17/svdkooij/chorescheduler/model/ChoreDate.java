package nl.miwnn.ch17.svdkooij.chorescheduler.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

/**
 * @author Simon van der Kooij
 * The week wherein chores are planned and where they can be done by familymembers.
 */

public class ChoreDate {

    @Id
    @GeneratedValue
    private Long choreDateID;

    private LocalDate dueDate;

}
