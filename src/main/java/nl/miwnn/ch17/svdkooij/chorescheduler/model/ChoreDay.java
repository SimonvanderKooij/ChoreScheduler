package nl.miwnn.ch17.svdkooij.chorescheduler.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

/**
 * @author Simon van der Kooij
 * The name that can be given to a certain date.
 */

public class ChoreDay {

    @Id
    @GeneratedValue
    private Long choreDayID;

    private String dayName;

}
