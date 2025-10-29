package nl.miwnn.ch17.svdkooij.chorescheduler.repositories;


import nl.miwnn.ch17.svdkooij.chorescheduler.model.Chore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekplanRepository extends JpaRepository<Chore, Long> {

}
