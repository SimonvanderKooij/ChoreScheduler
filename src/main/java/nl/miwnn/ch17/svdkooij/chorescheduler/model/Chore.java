package nl.miwnn.ch17.svdkooij.chorescheduler.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalTime;

/**
 * @author Simon van der Kooij
 * These are the chores that can be added to the planning and can be done by the familymembers
 */

@Entity
public class Chore {

    @Id @GeneratedValue
    Long choreID;
    String choreName;
    LocalTime choreDuration;

    public Chore(Long choreID, String choreName, LocalTime choreTime) {
        this.choreID = choreID;
        this.choreName = choreName;
        this.choreDuration = choreTime;
    }

    public Chore() {

    }

    public Long getChoreID() {
        return choreID;
    }

    public void setChoreID(Long choreID) {
        this.choreID = choreID;
    }

    public String getChoreName() {
        return choreName;
    }

    public void setChoreName(String choreName) {
        this.choreName = choreName;
    }

    public LocalTime getChoreTime() {
        return choreDuration;
    }

    public void setChoreTime(LocalTime choreTime) {
        this.choreDuration = choreTime;
    }
}
