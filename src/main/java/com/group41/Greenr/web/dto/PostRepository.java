package com.group41.Greenr.web.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group41.Greenr.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}