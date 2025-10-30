package nl.miwnn.ch17.svdkooij.chorescheduler.model;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Set;

/**
 * @author Simon van der Kooij
 * These are the chores that can be added to the planning and can be done by the familymembers
 */

@Entity
public class Chore {

    @Id @GeneratedValue
    private Long choreID;
    private String choreName;
    private LocalTime choreDuration;

    @ManyToOne
    @JoinColumn(name = "familymember_member_id")
    private FamilyMember familymember;

    @ManyToMany
    private Set<Schedule> schedules;

    public FamilyMember getFamilymember() {
        return familymember;
    }

    public void setFamilyMember(FamilyMember familymember) {
        this.familymember = familymember;
    }

    public Chore(Long choreID, String choreName, LocalTime choreDuration) {
        this.choreID = choreID;
        this.choreName = choreName;
        this.choreDuration = choreDuration;
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

    public LocalTime getChoreDuration() {
        return choreDuration;
    }

    public void setChoreDuration(LocalTime choreDuration) {
        this.choreDuration = choreDuration;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }
}
