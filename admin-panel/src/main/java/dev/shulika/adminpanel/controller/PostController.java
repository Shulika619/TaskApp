package dev.shulika.adminpanel.controller;

import dev.shulika.adminpanel.service.DepartmentService;
import dev.shulika.adminpanel.service.FileService;
import dev.shulika.adminpanel.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final DepartmentService departmentService;
    private final FileService fileService;

    @GetMapping("/posts")
    public String findAllPosts(
            Model model,
            @PageableDefault(size = 15) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.info("+++++ IN PostController :: findAllPosts :: START +++++");
        var posts = postService.findAll(pageable);
        var departments = departmentService.findAll();
        model.addAttribute("title", "Список сообщений :: X-Tel");
        model.addAttribute("h1", "Список всех сообщений");
        model.addAttribute("posts", posts);
        model.addAttribute("departments", departments);
        log.info("+++++ IN PostController :: findAllPosts :: COMPLETE +++++");
        return "posts";
    }

    @GetMapping("/posts/{departmentId}")
    public String findPostsByDepartment(
            @PathVariable("departmentId") Long departmentId,
            Model model,
            @PageableDefault(size = 15) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.info("+++++ IN PostController :: findPostsByDepartment :: START +++++");
        var posts = postService.findByDepartment(pageable, departmentId);
        var departments = departmentService.findAll();
        var departmentName = departments.stream().filter(d -> d.getId().equals(departmentId))
                .findFirst().get().getName();
        model.addAttribute("title", "Список сообщений по отделу :: X-Tel");
        model.addAttribute("h1", "Список сообщений - " + departmentName);
        model.addAttribute("posts", posts);
        model.addAttribute("departments", departments);
        log.info("+++++ IN PostController :: findPostsByDepartment :: COMPLETE +++++");
        return "posts";
    }

    @PostMapping("/posts/search")
    public String searchPost(
            @Param("keyword") String keyword,
            Model model,
            @PageableDefault(size = 15) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.info("+++++ IN PostController :: searchPost :: START +++++");
        if (keyword.isBlank()) {
            log.info("----- IN PostController :: searchPost :: keyword NULL -----");
            return "redirect:/posts";
        }
        var posts = postService.findBySearchTxt(pageable, keyword);
        var departments = departmentService.findAll();
        model.addAttribute("title", "Результаты поиска :: X-Tel");
        model.addAttribute("h1", "Результаты поиска");
        model.addAttribute("posts", posts);
        model.addAttribute("departments", departments);
        log.info("+++++ IN PostController :: searchPost :: COMPLETE +++++");
        return "posts";
    }

    @GetMapping("/posts/files")
    public String downLoadFile(@RequestParam("id") String id) {
        log.info("+++++ IN PostController :: downLoadFile :: START +++++");
        var downLoadLink = fileService.getDownLoadLink(id);
        log.info("+++++ IN PostController :: downLoadFile :: COMPLETE +++++");
        return "redirect:"+downLoadLink;
    }
}