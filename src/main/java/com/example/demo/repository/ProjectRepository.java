package com.example.demo.repository;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Project;

public interface ProjectRepository extends JpaRepository<Project,Long>{
}
