package nl.miwnn.ch17.svdkooij.chorescheduler.model;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Set;

/**
 * @author Simon van der Kooij
 * These are the chores that can be added to the planning and can be done by the familymembers
 */

@Entity
public class FamilyMember {

    @Id
    @GeneratedValue
    Long memberID;
    String memberName;

    @OneToMany(mappedBy = "familyMember")
    private Set<Chore> chores;

    public FamilyMember(Long memberID, String memberName, Set<Chore> chores) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.chores = chores;
    }

    public FamilyMember() {
    }

    public LocalTime getTotalChoreTimeOutOfAllChores() {
        LocalTime totalChoreTime = LocalTime.of(0, 0);

        for (Chore chore : chores) {
            totalChoreTime = totalChoreTime.plusHours(chore.getTotalChoreTimeOutOfAllSchedules(this).getHour())
                    .plusMinutes(chore.getTotalChoreTimeOutOfAllSchedules(this).getMinute());

        }
        return totalChoreTime;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Set<Chore> getChores() {
        return chores;
    }
}
