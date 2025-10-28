package nl.miwnn.ch17.svdkooij.chorescheduler.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalTime;

/**
 * @author Simon van der Kooij
 * These are the familymembers that can do the chores
 */

@Entity
public class Familymember {

    @Id @GeneratedValue
    Long memberID;
    String memberName;
    LocalTime memberTime;

    public Familymember(Long memberID, String memberName, LocalTime memberTime) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.memberTime = memberTime;
    }

    public Familymember() {

    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long choreID) {
        this.memberID = choreID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String choreName) {
        this.memberName = choreName;
    }

    public LocalTime getMemberTime() {
        return memberTime;
    }

    public void setMemberTime(LocalTime choreTime) {
        this.memberTime = choreTime;
    }
}
