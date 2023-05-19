package dev.shulika.adminpanel.controller;

import dev.shulika.adminpanel.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public String findAllEmployees(
            Model model,
            @PageableDefault(size = 15) @SortDefault(sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.info("+++++ IN EmployeeController :: findAllEmployees :: START +++++");
        var employees = employeeService.findAllPage(pageable);
        model.addAttribute("title", "Список сотрудников :: X-Tel");
        model.addAttribute("h1", "Список сотрудников");
        model.addAttribute("employees", employees);
        log.info("+++++ IN EmployeeController :: findAllEmployees :: COMPLETE +++++");
        return "employees";
    }
}
