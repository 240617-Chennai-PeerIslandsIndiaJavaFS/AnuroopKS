package com.example.quizapp.controller;

import com.example.quizapp.models.Leaderboard;
import com.example.quizapp.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @GetMapping
    public ResponseEntity<List<Leaderboard>> getTopUsers(@RequestParam(defaultValue = "3") int limit) {
        List<Leaderboard> topUsers = leaderboardService.getTopUsers(limit);
        return ResponseEntity.ok(topUsers);
    }
}