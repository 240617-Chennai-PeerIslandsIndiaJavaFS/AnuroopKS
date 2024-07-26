package com.example.quizapp.controller;

import com.example.quizapp.models.User;
import com.example.quizapp.service.AnswerService;
import com.example.quizapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> saveAnswer(@RequestBody AnswerRequest answerRequest) {
        User user = userService.loginUser(answerRequest.getUsername(), answerRequest.getPassword()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        answerService.saveAnswer(user, answerRequest.getQuestionId(), answerRequest.getSelectedAnswer());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/score")
    public ResponseEntity<Integer> calculateScore(@RequestParam String username, @RequestParam String password) {
        User user = userService.loginUser(username, password).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        int score = answerService.calculateScore(user);
        userService.updateScore(user, score);
        return ResponseEntity.ok(score);
    }
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class AnswerRequest {
    private String username;
    private String password;
    private Long questionId;
    private String selectedAnswer;

}