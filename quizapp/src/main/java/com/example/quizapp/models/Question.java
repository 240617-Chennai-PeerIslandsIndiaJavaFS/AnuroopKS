package com.example.quizapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="question")
    private String questionText;

    @Column(name="option_a")
    private String optionA;

    @Column(name="option_b")
    private String optionB;

    @Column(name="option_c")
    private String optionC;

    @Column(name="option_d")
    private String optionD;

    @Column(name="correct_answer")
    private String correctAnswer;
}