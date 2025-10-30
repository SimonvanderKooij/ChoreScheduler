package nl.miwnn.ch17.svdkooij.chorescheduler.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Simon van der Kooij
 * These are the chores that can be added to the planning and can be done by the familymembers
 */

@Entity
public class FamilyMember {

    @Id @GeneratedValue
    Long memberID;

    String memberName;

    public FamilyMember(Long memberID, String memberName) {
        this.memberID = memberID;
        this.memberName = memberName;
    }

    public FamilyMember() {
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
}
