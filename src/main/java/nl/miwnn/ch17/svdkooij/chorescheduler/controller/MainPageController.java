package nl.miwnn.ch17.svdkooij.chorescheduler.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

/**
 * @author Simon van der Kooij
 *
 */

@Controller
public class MainPageController {

    @GetMapping({"/"})
    private String showHomePage(Model datamodel) {

        return "redirect:/schedule/detail/" + LocalDate.now();
    }
}
