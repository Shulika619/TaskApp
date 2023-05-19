package dev.shulika.adminpanel.controller;

import dev.shulika.adminpanel.model.Department;
import dev.shulika.adminpanel.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.webjars.NotFoundException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public String findAllDepartments(
            Model model,
            @PageableDefault(size = 15) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.info("+++++ IN DepartmentController :: findAllDepartments :: START +++++");
        var departments = departmentService.findAllPage(pageable);
        model.addAttribute("title", "Список отделов :: X-Tel");
        model.addAttribute("h1", "Список отделов");
        model.addAttribute("departments", departments);
        log.info("+++++ IN DepartmentController :: findAllDepartments :: COMPLETE +++++");
        return "departments";
    }

    @GetMapping("/departments/new")
    public String newDepartment(Model model) {
        log.info("+++++ IN DepartmentController :: newDepartment :: START +++++");
        model.addAttribute("title", "Добавить отдел :: X-Tel");
        model.addAttribute("h1", "Добавить отдел");
        model.addAttribute("department", new Department());
        log.info("+++++ IN DepartmentController :: newDepartment :: COMPLETE +++++");
        return "department-form";
    }

    @PostMapping("/departments/save")
    public String saveDepartment(Department department, RedirectAttributes redirectAttr) {
        log.info("+++++ IN DepartmentController :: saveDepartment :: START +++++");
        departmentService.saveDepartment(department);
        redirectAttr.addFlashAttribute("messageOk", "Отдел успешно сохранен!");
        log.info("+++++ IN DepartmentController :: saveDepartment :: COMPLETE +++++");
        return "redirect:/departments";
    }

    @GetMapping("/departments/{id}/edit")
    public String editDepartment(
            Model model,
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttr
    ) {
        log.info("+++++ IN DepartmentController :: editDepartment :: START +++++");
        try {
            var department = departmentService.findById(id);
            model.addAttribute("title", "Редактировать отдел :: X-Tel");
            model.addAttribute("h1", "Редактировать отдел");
            model.addAttribute("department", department);
            log.info("+++++ IN DepartmentController :: editDepartment :: COMPLETE +++++");
            return "department-form";
        } catch (NotFoundException e) {
            redirectAttr.addFlashAttribute("messageFail", e.getMessage());
            log.error("---- IN DepartmentController :: saveDepartment :: FAIL ----");
            return "redirect:/departments";
        }
    }
}
