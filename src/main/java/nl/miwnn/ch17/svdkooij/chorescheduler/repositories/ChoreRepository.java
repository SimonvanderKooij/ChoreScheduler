package nl.miwnn.ch17.svdkooij.chorescheduler.repositories;


import nl.miwnn.ch17.svdkooij.chorescheduler.model.Chore;
import nl.miwnn.ch17.svdkooij.chorescheduler.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChoreRepository extends JpaRepository<Chore, Long> {

    Set<Chore> findAllByFamilyMember(FamilyMember familyMember);
}
