package nl.miwnn.ch17.svdkooij.chorescheduler.controller;


import nl.miwnn.ch17.svdkooij.chorescheduler.repositories.WeekplanRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Simon van der Kooij
 * handles everything according the weekplanning of chores and familymembers.
 */

@Controller
@RequestMapping("/weekplan")
public class WeekplanController {

    private final WeekplanRepository weekplanRepository;

    public WeekplanController(WeekplanRepository weekplanRepository) {
        this.weekplanRepository = weekplanRepository;
    }

    @GetMapping("/all")
    public String showAllWeekplans(Model datamodel) {
        datamodel.addAttribute("allWeekplans", weekplanRepository.findAll());

        return "weekplanOverview";
    }


}
