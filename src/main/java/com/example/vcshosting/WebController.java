package com.example.vcshosting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @Autowired
    private VcsHostingService vcsHostingService;

    @GetMapping("/")
    public String home() {
        return "index"; // Returns the homepage template
    }

    @PostMapping("/search")
    public String searchRepos(@RequestParam String orgLink, @RequestParam String accessToken, Model model) {
        model.addAttribute("repositories", vcsHostingService.getRepositories(orgLink, accessToken));
        return "repoList";
    }
}
