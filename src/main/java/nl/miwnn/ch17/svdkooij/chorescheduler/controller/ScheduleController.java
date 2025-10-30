package nl.miwnn.ch17.svdkooij.chorescheduler.controller;


import nl.miwnn.ch17.svdkooij.chorescheduler.model.FamilyMember;
import nl.miwnn.ch17.svdkooij.chorescheduler.model.Schedule;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.ChoreRepository;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.ScheduleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Simon van der Kooij
 * handles everything according the dates of chores
 */

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    public final ScheduleRepository scheduleRepository;
    public final ChoreRepository choreRepository;

    public ScheduleController(ScheduleRepository scheduleRepository, ChoreRepository choreRepository) {
        this.scheduleRepository = scheduleRepository;
        this.choreRepository = choreRepository;
    }

    @GetMapping({"/all", "/"})
    public String showAllScheduledChores(Model datamodel) {
        datamodel.addAttribute("allScheduledChores", scheduleRepository.findAll());
        return "scheduleOverview";
    }

    @GetMapping("/plan")
    public String addNewPlanning(Model datamodel) {
        datamodel.addAttribute("formPlanning", scheduleRepository.findAll());
        return showPlanningForm(datamodel, new Schedule());
    }

    @GetMapping("/edit/{date}")
    public String editPlanning(@PathVariable("date") LocalDate date, Model datamodel) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findByDueDate(date);

        if (optionalSchedule.isPresent()) {
            return showPlanningForm(datamodel, optionalSchedule.get());
        }

        return "redirect:/schedule/";
    }

    @GetMapping("/delete/{id}")
    public String deletePlanning(@PathVariable("id") Long scheduleID, Model datamodel) {
        scheduleRepository.deleteById(scheduleID);
        return "redirect:/schedule/";
    }

    @GetMapping("/detail/{dueDate}")
    public String showDetailView(@PathVariable("dueDate") String dueDate, Model datamodel) {
        Optional<Schedule> scheduleToShow = scheduleRepository.findByDueDate(LocalDate.parse(dueDate));

        if (scheduleToShow.isEmpty()) {
            return "redirect:/schedule/all";
        }

        datamodel.addAttribute("schedule", scheduleToShow.get());
        return "scheduleDetailView";
    }

    @PostMapping("/save")
    public String saveOrUpdateSchedule(@ModelAttribute("formPlanning") Schedule scheduleToBeSaved,
                                       BindingResult result,
                                       Model datamodel) {

        Optional<Schedule> scheduleWithSameDate = scheduleRepository.findByDueDate(scheduleToBeSaved.getDueDate());

        if (scheduleWithSameDate.isPresent() &&
                !scheduleWithSameDate.get().getScheduleID().equals(scheduleToBeSaved.getScheduleID())) {
            result.addError(new FieldError("schedule", "dueDate",
                    "this date has previously been planned."));
        }

        if (result.hasErrors()) {
            return showPlanningForm(datamodel, scheduleToBeSaved);
        }

        scheduleRepository.save(scheduleToBeSaved);

        return "redirect:/schedule/";

    }


    private String showPlanningForm(Model datamodel, Schedule schedule) {
        datamodel.addAttribute("formPlanning", schedule);
        datamodel.addAttribute("allChores", choreRepository.findAll());

        return "planningForm";
    }

}
