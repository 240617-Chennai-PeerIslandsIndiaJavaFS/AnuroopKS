package com.example.quizapp.service;

import com.example.quizapp.models.Leaderboard;
import com.example.quizapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    private UserService userService;

    public List<Leaderboard> getTopUsers(int limit) {
        List<User> topUsers = userService.getTopUsers(limit);
        return topUsers.stream()
                .map(user -> new Leaderboard(user.getUsername(), user.getScore()))
                .collect(Collectors.toList());
    }
}