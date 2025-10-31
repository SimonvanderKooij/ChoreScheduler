package nl.miwnn.ch17.svdkooij.chorescheduler.controller;


import nl.miwnn.ch17.svdkooij.chorescheduler.model.Chore;
import nl.miwnn.ch17.svdkooij.chorescheduler.model.FamilyMember;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.FamilyMemberRepository;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.ScheduleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Simon van der Kooij
 * handles everything in respect of familymembers who do the chores
 */

@Controller
@RequestMapping({"/familymember", "/familymembers"})
public class familyMemberController {

    private static final String NOT_YET_ASSIGNED_NAME = "Not yet assigned";
    private final FamilyMemberRepository familyMemberRepository;
    private final ScheduleRepository scheduleRepository;

    public familyMemberController(FamilyMemberRepository familyMemberRepository, ScheduleRepository scheduleRepository) {
        this.familyMemberRepository = familyMemberRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping({"/all", "/"})
    public String showAllFamilyMembers(Model datamodel) {
        datamodel.addAttribute("allFamilyMembers", familyMemberRepository.findAll());
        datamodel.addAttribute("allScheduledChores", scheduleRepository.findAll());

        return "familyMemberOverview";
    }

    @GetMapping("/add")
    public String addNewFamilyMember(Model datamodel) {
        datamodel.addAttribute("formMember", familyMemberRepository.findAll());

        return showFamilyMemberForm(datamodel, new FamilyMember());
    }

    @GetMapping("/edit/{name}")
    public String editFamilyMember(@PathVariable("name") String name, Model datamodel) {
        Optional<FamilyMember> optionalFamilyMember = familyMemberRepository.findByMemberName(name);

        if (!name.equals(NOT_YET_ASSIGNED_NAME)) {
            if (optionalFamilyMember.isPresent()) {
                return showFamilyMemberForm(datamodel, optionalFamilyMember.get());
            }
        }

        return "redirect:/familymember/all";
    }

    @PostMapping("/save")
    public String saveOrUpdatePlanning(@ModelAttribute("formMember") FamilyMember memberToBeSaved,
                                       BindingResult result, Model datamodel) {

        Optional<FamilyMember> optionalMemberWithSameName =
                familyMemberRepository.findByMemberName(memberToBeSaved.getMemberName());

        if (optionalMemberWithSameName.isPresent() && !optionalMemberWithSameName.get().getMemberID()
                .equals(memberToBeSaved.getMemberID())) {
            result.addError(new FieldError("familyMember", "name",
                    "this name is already in use by another familymember"));
        }

        if (result.hasErrors()) {
            return showFamilyMemberForm(datamodel, memberToBeSaved);
        }

        familyMemberRepository.save(memberToBeSaved);
        return "redirect:/familymember/";
    }

    @GetMapping("/delete/{id}")
    public String deleteFamilyMember(@PathVariable("id") Long memberID, Model datamodel) {

        Optional<FamilyMember> familyMember = familyMemberRepository.findById(memberID);
        Optional<FamilyMember> newFamilyMember = familyMemberRepository.findByMemberName(NOT_YET_ASSIGNED_NAME);

        if (familyMember.isPresent() && newFamilyMember.isPresent()) {
            FamilyMember familyMemberToBeDeleted = familyMember.get();
            FamilyMember familyMemberToAssignChoresTo = newFamilyMember.get();

            if (!familyMemberToBeDeleted.getMemberName().equals(NOT_YET_ASSIGNED_NAME)) {
                for (Chore chore : familyMemberToBeDeleted.getChores() ) {
                    chore.setFamilyMember(familyMemberToAssignChoresTo);
                }

                familyMemberRepository.deleteById(memberID);
            }
        }

        return "redirect:/familymember/";
    }


    private String showFamilyMemberForm(Model datamodel, FamilyMember familyMember) {
        datamodel.addAttribute("formMember", familyMember);
        datamodel.addAttribute("allFamilyMembers", familyMemberRepository.findAll());

        return "familyMemberForm";
    }


}
