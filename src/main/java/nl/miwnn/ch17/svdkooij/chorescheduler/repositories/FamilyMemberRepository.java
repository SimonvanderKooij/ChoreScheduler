package nl.miwnn.ch17.svdkooij.chorescheduler.repositories;


import nl.miwnn.ch17.svdkooij.chorescheduler.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    Optional<FamilyMember> findByMemberName(String name);

}
