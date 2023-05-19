package dev.shulika.adminpanel.service;

import dev.shulika.adminpanel.model.Employee;
import dev.shulika.adminpanel.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Page<Employee> findAllPage(Pageable pageable) {
        log.info("+++++ IN EmployeeService :: findAllPage :: START +++++");
        var employees = employeeRepository.findAll(pageable);
        log.info("+++++ IN EmployeeService :: findAllPage :: FINISHED SUCCESSFULLY +++++");
        return employees;
    }

    public Long countEmployees() {
        log.info("+++++ IN EmployeeService :: countEmployees :: START +++++");
        var count = employeeRepository.findTotalEmployeesCount();
        log.info("+++++ IN EmployeeService :: countEmployees :: FINISHED SUCCESSFULLY +++++");
        return count;
    }

}