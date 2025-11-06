package nl.miwnn.ch17.svdkooij.chorescheduler.service;


import nl.miwnn.ch17.svdkooij.chorescheduler.model.FamilyMember;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.FamilyMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Simon van der Kooij
 */

@Service
public class FamilyMemberService implements UserDetailsService {

    private final FamilyMemberRepository familyMemberRepository;
    private final PasswordEncoder passwordEncoder;

    public FamilyMemberService(FamilyMemberRepository familyMemberRepository, PasswordEncoder passwordEncoder) {
        this.familyMemberRepository = familyMemberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return familyMemberRepository.findByMemberName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s was not found.", username)));
    }

    public void saveUser(FamilyMember familyMember) {
        familyMember.setPassword(passwordEncoder.encode(familyMember.getPassword()));
        familyMemberRepository.save(familyMember);
    }

}
