package nl.miwnn.ch17.svdkooij.chorescheduler.controller;

import nl.miwnn.ch17.svdkooij.chorescheduler.model.Chore;
import nl.miwnn.ch17.svdkooij.chorescheduler.model.Schedule;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.ChoreRepository;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.FamilyMemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Optional;

/**
 * @author Simon van der Kooij
 * handles everything regarding chores
 */

@Controller
public class ChoreController {

    private final ChoreRepository choreRepository;
    private final FamilyMemberRepository familyMemberRepository;

    public ChoreController(ChoreRepository choreRepository, FamilyMemberRepository familyMemberRepository) {
        this.choreRepository = choreRepository;
        this.familyMemberRepository = familyMemberRepository;
    }

    @GetMapping({"/chore/all", "/", "/chore", "/chores"})
    private String showChoreOverview(Model datamodel) {

        datamodel.addAttribute("chores", choreRepository.findAll());

        return "choreOverview";
    }

    @GetMapping("/chore/add")
    private String addChore(Model datamodel) {
        showChoreForm(datamodel, new Chore());

        return "choreForm";
    }

    @PostMapping("/chore/save")
    public String saveOrUpdateChore(@ModelAttribute("formChore") Chore chore, BindingResult result) {

        System.err.println(chore);

        if (!result.hasErrors()) {
            choreRepository.save(chore);
        }

        return "redirect:/chore/all";
    }

    @GetMapping("/chore/delete/{choreID}")
    public String deleteChore(@PathVariable("choreID") Long choreID) {
        Optional<Chore> chore = choreRepository.findById(choreID);

        if (chore.isPresent()) {
            Chore choreToBeDeleted = chore.get();

            for (Schedule schedule : choreToBeDeleted.getSchedules() ) {
                schedule.getChores().remove(choreToBeDeleted);
            }

            choreRepository.deleteById(choreID);
        }




        return "redirect:/chore/all";
    }

    @GetMapping("/chore/edit/{choreID}")
    public String editChore(@PathVariable("choreID") Long choreID, Model datamodel) {
        Optional<Chore> optionalChore = choreRepository.findById(choreID);


        if (optionalChore.isPresent()) {
            return showChoreForm(datamodel, optionalChore.get());
        }

        return "redirect:/chore/all";
    }

    private String showChoreForm(Model datamodel, Chore chore) {
        datamodel.addAttribute("formChore", chore);
        datamodel.addAttribute("allMembers", familyMemberRepository.findAll());
        return "choreForm";
    }
}