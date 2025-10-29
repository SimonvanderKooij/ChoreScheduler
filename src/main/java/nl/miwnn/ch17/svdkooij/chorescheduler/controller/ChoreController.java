package nl.miwnn.ch17.svdkooij.chorescheduler.controller;

import nl.miwnn.ch17.svdkooij.chorescheduler.model.Chore;
import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.ChoreRepository;
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

    public ChoreController(ChoreRepository choreRepository) {
        this.choreRepository = choreRepository;
    }

    @GetMapping({"/chore/all", "/"})
    private String showChoreOverview(Model datamodel) {

        datamodel.addAttribute("chores", choreRepository.findAll());

        return "choreOverview";
    }

    @GetMapping("/chore/add")
    private String showChoreForm(Model datamodel) {
        datamodel.addAttribute("formChore", new Chore());

        return "choreForm";
    }

    @PostMapping("/chore/save")
    public String saveOrUpdateChore(@ModelAttribute("formChore") Chore chore, BindingResult result) {
        if (!result.hasErrors()) {
            choreRepository.save(chore);
        }

        return "redirect:/chore/all";
    }

    @GetMapping("/chore/delete/{choreID}")
    public String deleteChore(@PathVariable("choreID") Long choreID) {
        choreRepository.deleteById(choreID);
        return "redirect:/chore/all";
    }

    @GetMapping("/chore/edit/{choreID}")
    public String editChore(@PathVariable("choreID") Long choreID, Model datamodel) {
        Optional<Chore> optionalChore = choreRepository.findById(choreID);

        if (optionalChore.isPresent()) {
            datamodel.addAttribute("formChore", optionalChore.get());
            return "choreForm";
        }

        return "redirect:/chore/all";
    }
}