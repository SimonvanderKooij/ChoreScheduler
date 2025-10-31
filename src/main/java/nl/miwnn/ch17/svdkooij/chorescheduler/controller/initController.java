package nl.miwnn.ch17.svdkooij.chorescheduler.controller;


import nl.miwnn.ch17.svdkooij.chorescheduler.model.Chore;
import nl.miwnn.ch17.svdkooij.chorescheduler.model.FamilyMember;
import nl.miwnn.ch17.svdkooij.chorescheduler.model.Schedule;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.ChoreRepository;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.FamilyMemberRepository;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.ScheduleRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Simon van der Kooij
 *
 */
@Controller
public class initController {
    private final FamilyMemberRepository familyMemberRepository;
    private final ChoreRepository choreRepository;
    private final ScheduleRepository scheduleRepository;

    public initController(FamilyMemberRepository familyMemberRepository, ChoreRepository choreRepository, ScheduleRepository scheduleRepository) {
        this.familyMemberRepository = familyMemberRepository;
        this.choreRepository = choreRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (scheduleRepository.count() == 0 && familyMemberRepository.count() == 0 && choreRepository.count() == 0) {
            makeFamilyMember("Simon");
            makeFamilyMember("Carolien");
            makeFamilyMember("Lilian");
            makeFamilyMember("Nova");
            makeFamilyMember("Joas");
            makeFamilyMember("Not yet assigned");

            Chore stofzuigen = makeChore("Stofzuigen", LocalTime.of(0,30), "Joas");
            Chore vaatwasser = makeChore("Vaatwasser uitruimen", LocalTime.of(0,15), "Nova");
            Chore was = makeChore("Was opruimen", LocalTime.of(0,45), "Lilian");
            Chore koken = makeChore("Koken", LocalTime.of(1,0), "Carolien");
            Chore keuken = makeChore("Keuken schoonmaken", LocalTime.of(0,45), "Simon");

            makeSchedule(stofzuigen, vaatwasser, was);
            makeSchedule(koken, keuken, was);
            makeSchedule(stofzuigen, koken, vaatwasser);

        }
    }

    private void makeFamilyMember(String name) {
        FamilyMember familyMember = new FamilyMember();

        familyMember.setMemberName(name);

        familyMemberRepository.save(familyMember);
    }

    private Chore makeChore(String name, LocalTime duration, String familyMember) {
        Chore chore = new Chore();

        chore.setChoreName(name);

        chore.setChoreDuration(duration);

        chore.setFamilyMember(familyMemberRepository.findByMemberName(familyMember).get());

        choreRepository.save(chore);

        return chore;
    }

    private void makeSchedule(Chore ... chores) {
        Schedule schedule = new Schedule();

        schedule.setDueDate(LocalDate.ofEpochDay(ThreadLocalRandom.current().
                nextLong(LocalDate.of(2025, 1, 1).toEpochDay(), LocalDate.now().toEpochDay())));

        Set<Chore> choreSet = new HashSet<>(Arrays.asList(chores));
        schedule.setChores(choreSet);

        scheduleRepository.save(schedule);
    }

}
