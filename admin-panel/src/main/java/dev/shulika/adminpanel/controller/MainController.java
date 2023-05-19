package dev.shulika.adminpanel.controller;

import dev.shulika.adminpanel.service.DepartmentService;
import dev.shulika.adminpanel.service.EmployeeService;
import dev.shulika.adminpanel.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.time.Instant;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final PostService postService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping("/")
    public String mainPage(Model model) {
        log.info("+++++ IN MainController :: mainPage :: START +++++");
        var countPostsToday = postService.countPostsToday();
        var countEmployees = employeeService.countEmployees();
        var countDepartments = departmentService.countDepartments();
        var lastPosts = postService.findLastPostsToday(PageRequest.of(0, 9));
        model.addAttribute("title", "Главная :: X-Tel");
        model.addAttribute("h1", "Главная");
        model.addAttribute("dateToday", Timestamp.from(Instant.now()));
        model.addAttribute("countPostsToday", countPostsToday);
        model.addAttribute("countEmployee", countEmployees);
        model.addAttribute("countDepartments", countDepartments);
        model.addAttribute("lastPosts", lastPosts);
        log.info("+++++ IN MainController :: mainPage :: COMPLETE +++++");
        return "index";
    }
}
