package com.example.quizapp.dao;

import com.example.quizapp.models.Answer;
import com.example.quizapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUser(User user);
}