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

    @Id
    @GeneratedValue
    private Long choreID;
    private String choreName;
    private LocalTime choreDuration;

    @ManyToOne
    private FamilyMember familyMember;

    @ManyToMany(mappedBy = "chores")
    private Set<Schedule> schedules;

    public Chore(Long choreID, String choreName, LocalTime choreDuration) {
        this.choreID = choreID;
        this.choreName = choreName;
        this.choreDuration = choreDuration;
    }

    public Chore() {
    }

    public LocalTime getTotalChoreTimeOutOfAllSchedules(FamilyMember familyMember) {
        LocalTime totalChoreTime = LocalTime.of(0, 0);

        for (Schedule schedule : schedules) {

            totalChoreTime = totalChoreTime.plusHours(schedule.getTotalChoreTime(familyMember).getHour())
                    .plusMinutes(schedule.getTotalChoreTime(familyMember).getMinute());
        }

        return totalChoreTime;
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

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

}
