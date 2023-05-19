package dev.shulika.adminpanel.repository;

import dev.shulika.adminpanel.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT COUNT(e) FROM Employee e")
    long findTotalEmployeesCount();
}