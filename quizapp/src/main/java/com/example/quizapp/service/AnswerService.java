package com.example.quizapp.service;

import com.example.quizapp.dao.AnswerRepository;
import com.example.quizapp.models.Answer;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    public void saveAnswer(User user, Long questionId, String selectedAnswer) {
        Question question = questionService.getQuestionById(questionId);
        if (question != null) {
            Answer answer = new Answer();
            answer.setUser(user);
            answer.setQuestion(question);
            answer.setSelectedAnswer(selectedAnswer);
            answerRepository.save(answer);
        }
    }

    public int calculateScore(User user) {
        List<Answer> answers = answerRepository.findByUser(user);
        int score = 0;
        for (Answer answer : answers) {
            if (answer.getSelectedAnswer().equals(answer.getQuestion().getCorrectAnswer())) {
                score++;
            }
        }
        return score;
    }
}