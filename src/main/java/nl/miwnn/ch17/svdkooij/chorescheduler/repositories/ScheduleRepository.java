package nl.miwnn.ch17.svdkooij.chorescheduler.repositories;


import nl.miwnn.ch17.svdkooij.chorescheduler.model.Chore;
import nl.miwnn.ch17.svdkooij.chorescheduler.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByDueDate(LocalDate dueDate);
}
