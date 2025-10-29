package nl.miwnn.ch17.svdkooij.chorescheduler.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Simon van der Kooij
 * The week wherein chores are planned and where they can be done by familymembers.
 */

public class Weekplan {

    @Id
    @GeneratedValue
    private Long weekplanID;

    private int weekNumber;

    private String day;


}
