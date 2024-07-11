package org.example;

import org.example.controller.UserController;
import org.example.dao.UserDAO;
import org.example.service.*;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService(new UserDAO());
        ClientsService clientsService = new ClientsService();
        ProjectTeamsService projectTeamsService = new ProjectTeamsService();
        ProjectTeamMembersService projectTeamMembersService= new ProjectTeamMembersService();
        ProjectService projectService= new ProjectService();
        MilestoneService milestoneService=new MilestoneService();
        EffortCalculationService effortCalculationService= new EffortCalculationService();
        TaskService taskService= new TaskService();
        MessageService messageService=new MessageService();
        UserController userController = new UserController(userService, clientsService, projectTeamsService, projectTeamMembersService, projectService, milestoneService, effortCalculationService, taskService,messageService,scanner);

        userController.run();
    }
}