package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.UserTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTopicRepository extends JpaRepository<UserTopic, Long> {

    boolean existsByUserIdAndTopicId(Long userId, Long topicId);

    void deleteByUserIdAndTopicId(Long userId, Long topicId);

    List<UserTopic> findByUserId(Long userId);
}
