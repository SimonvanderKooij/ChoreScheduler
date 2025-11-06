package nl.miwnn.ch17.svdkooij.chorescheduler.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.lang.Math.abs;

/**
 * @author Simon van der Kooij
 * These are the familymembers who do the chores. These are also the users that can log in.
 */

@Entity
public class FamilyMember implements UserDetails {

    @Id
    @GeneratedValue
    private Long memberID;

    @Column(unique = true)
    private String memberName;

    private String password;

    @OneToMany(mappedBy = "familyMember")
    private Set<Chore> chores;

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

    public String returnAheadOrBehind(Set<FamilyMember> familyMembers) {
        LocalTime totalOfAllMembers = LocalTime.of(0, 0);

        for (FamilyMember familyMember : familyMembers) {
            if (!Objects.equals(familyMember.memberName, "Not yet assigned")) {
                totalOfAllMembers = totalOfAllMembers.plusHours(familyMember.getTotalChoreTimeOutOfAllChores().getHour())
                        .plusMinutes(familyMember.getTotalChoreTimeOutOfAllChores().getMinute());
            }
        }
        int nrOfMinutes = (totalOfAllMembers.getHour() * 60) + (totalOfAllMembers.getMinute());

        int meanNrOfMinutes = nrOfMinutes / ( familyMembers.size() - 1);

        int selfNrOfMinutes = (this.getTotalChoreTimeOutOfAllChores().getHour() * 60) +
                (this.getTotalChoreTimeOutOfAllChores().getMinute());

        int difference = selfNrOfMinutes - meanNrOfMinutes;
        String returnString;

        if (difference < 0) {
            returnString = "-";
            difference = abs(difference);
        } else {
            returnString = "+";
        }

        return returnString + LocalTime.of(difference / 60, difference % 60).toString();
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return memberName;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
