package com.pororo.testcode01.repository;

import com.pororo.testcode01.model.StudentPass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPassRepository extends JpaRepository<StudentPass, Long> {}
