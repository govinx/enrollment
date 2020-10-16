package com.demo.enrollment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.enrollment.entity.Dependent;
import com.demo.enrollment.entity.Enrollee;



@Repository
public interface DependentRepository extends JpaRepository<Dependent, Long>{
	List<Dependent> findByEnrollee(Enrollee enrollee);
}
