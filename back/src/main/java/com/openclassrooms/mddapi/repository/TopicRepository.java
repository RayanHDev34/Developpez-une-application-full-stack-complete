package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.mddapi.model.Topic;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByName(String name);

    boolean existsByName(String name);
}
