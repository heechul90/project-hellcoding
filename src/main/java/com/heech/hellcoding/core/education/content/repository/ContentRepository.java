package com.heech.hellcoding.core.education.content.repository;

import com.heech.hellcoding.core.education.content.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
