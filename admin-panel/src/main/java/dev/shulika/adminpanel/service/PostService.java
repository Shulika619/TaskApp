package dev.shulika.adminpanel.service;

import dev.shulika.adminpanel.model.Post;
import dev.shulika.adminpanel.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> findAll(Pageable pageable) {
        log.info("+++++ IN PostService :: findAll :: START +++++");
        var posts = postRepository.findAllPosts(pageable);
        log.info("+++++ IN PostService :: findAll :: FINISHED SUCCESSFULLY +++++");
        return posts;
    }

    public Page<Post> findByDepartment(Pageable pageable, Long departmentId) {
        log.info("+++++ IN PostService :: findByDepartment :: START +++++");
        var posts = postRepository.findByDepartmentId(pageable, departmentId);
        log.info("+++++ IN PostService :: findByDepartment :: FINISHED SUCCESSFULLY +++++");
        return posts;
    }

    public Page<Post> findBySearchTxt(Pageable pageable, String searchTxt) {
        log.info("+++++ IN PostService :: findBySearchTxt :: START +++++");
        var posts = postRepository.findBySearchText(pageable, searchTxt);
        log.info("+++++ IN PostService :: findBySearchTxt :: FINISHED SUCCESSFULLY +++++");
        return posts;
    }

    public Long countPostsToday() {
        log.info("+++++ IN PostService :: countPostsToday :: START +++++");
        var count = postRepository.findCountPostsToday();
        log.info("+++++ IN PostService :: countPostsToday :: FINISHED SUCCESSFULLY +++++");
        return count;
    }

    public List<Post> findLastPostsToday(Pageable pageable) {
        log.info("+++++ IN PostService :: findLastPostsToday :: START +++++");
        var last10PostsToday = postRepository.findLastPostsToday(pageable);
        log.info("+++++ IN PostService :: findLastPostsToday :: FINISHED SUCCESSFULLY +++++");
        return last10PostsToday;
    }
}
