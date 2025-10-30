package nl.miwnn.ch17.svdkooij.chorescheduler.model;


import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Simon van der Kooij
 * The schedule wherein chores are planned.
 */

@Entity
public class Schedule {

    @Id
    @GeneratedValue
    private Long scheduleID;

    @Column(unique = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @ManyToMany
    private Set<Chore> chores;

    public Schedule(Long scheduleID, LocalDate dueDate, Set<Chore> chores) {
        this.scheduleID = scheduleID;
        this.dueDate = dueDate;
        this.chores = chores;
    }

    public Schedule() {
    }

    public DayOfWeek getDayOfWeek() {
        return dueDate.getDayOfWeek();
    }

    public Long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Set<Chore> getChores() {
        return chores;
    }

    public void setChores(Set<Chore> chores) {
        this.chores = chores;
    }

    @Override
    public String toString() {
        return String.format("scheduleID: %s, dueDate: %s, chores: %s", this.scheduleID, this.dueDate, this.chores);
    }
}
