package com.demo.enrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.enrollment.entity.Enrollee;


@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long>{

}
