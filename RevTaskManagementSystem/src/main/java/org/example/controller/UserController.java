package org.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.models.*;
import org.example.service.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private ClientsService clientsService;
    private ProjectTeamsService projectTeamsService;
    private ProjectTeamMembersService projectTeamMembersService;
    private ProjectService projectService;
    private MilestoneService milestoneService;
    private EffortCalculationService effortCalculationService;
    private TaskService taskService;
    private MessageService messageService;
    private Scanner scanner;
    private UserModels.UserRole userRole;

    public UserController(UserService userService, ClientsService clientsService, ProjectTeamsService projectTeamsService, ProjectTeamMembersService projectTeamMembersService, ProjectService projectService, MilestoneService milestoneService, EffortCalculationService effortCalculationService, TaskService taskService, MessageService messageService, Scanner scanner) throws SQLException {
        this.userService = userService;
        this.clientsService = clientsService;
        this.projectTeamsService = projectTeamsService;
        this.projectTeamMembersService = projectTeamMembersService;
        this.projectService = projectService;
        this.milestoneService = milestoneService;
        this.effortCalculationService = effortCalculationService;
        this.taskService = taskService;
        this.messageService = messageService;
        this.scanner = scanner;
        askForRole();
    }

    private void askForRole() throws SQLException {
        System.out.println("Select your role:");
        System.out.println("1. Admin");
        System.out.println("2. Project Manager");
        System.out.println("3. Team Member");
        System.out.print("Enter your choice: ");
        int userRoleChoice = scanner.nextInt();
        scanner.nextLine();
        switch (userRoleChoice) {
            case 1:
                userRole = UserModels.UserRole.Admin;
                break;
            case 2:
                userRole = UserModels.UserRole.Project_Manager;
                break;
            case 3:
                userRole = UserModels.UserRole.Team_Member;
                break;
            default:
                LOGGER.warn("Invalid choice. Try again!");
                askForRole();
                return;
        }
        run();
    }

    public void run() throws SQLException {
        while (true) {
            System.out.println("|────────────────────────────────────────────────────────────────────|");
            System.out.println("   Welcome to the Task Management System!");
            System.out.println("|────────────────────────────────────────────────────────────────────|");
            if (userRole == UserModels.UserRole.Admin) {
                System.out.println("1. Login as Admin");
                System.out.println("2. Go back");
            } else if (userRole == UserModels.UserRole.Project_Manager) {
                System.out.println("1. Login as Project Manager");
                System.out.println("2. Go back");
            } else if (userRole == UserModels.UserRole.Team_Member) {
                System.out.println("1. Login as Team Member");
                System.out.println("2. Go back");
            }
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    LOGGER.info("Going back to role selection.");
                    askForRole();
                    return;
                default:
                    LOGGER.warn("Invalid choice. Try again!");
            }
        }
    }

    private void login() throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        UserModels.User user = userService.login(username, password);
        if (user != null) {
            if (user.getUser_role() == UserModels.UserRole.Admin && userRole == UserModels.UserRole.Admin) {
                user.setStatus(UserModels.Status.Active);
                userService.updateUser(user);
                LOGGER.info("Your login as Admin is successful!");
                adminMenu(user);
            } else if (user.getUser_role() == UserModels.UserRole.Project_Manager && userRole == UserModels.UserRole.Project_Manager) {
                user.setStatus(UserModels.Status.Active);
                userService.updateUser(user);
                LOGGER.info("Your login as Project Manager is successful!");
                projectManagerMenu(user);
            } else if (user.getUser_role() == UserModels.UserRole.Team_Member && userRole == UserModels.UserRole.Team_Member) {
                user.setStatus(UserModels.Status.Active);
                userService.updateUser(user);
                LOGGER.info("Your login as Team Member is successful!");
                teamMemberMenu(user);
            } else {
                LOGGER.warn("You do not have permission to login as this role.");
            }
        } else {
            LOGGER.warn("Invalid username or password.");
        }
    }

    private void adminMenu(UserModels.User user) throws SQLException {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Create a new user");
            System.out.println("2. Delete a user");
            System.out.println("3. Update user details");
            System.out.println("4. View all users");
            System.out.println("5. View User Activity"); // New option
            System.out.println("6. Logout");
            System.out.print("Choose your option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    deleteAccount();
                    break;
                case 3:
                    updateProfile();
                    break;
                case 4:
                    viewAllUsers();
                    break;
                case 5:
                    viewUserActivity(); // New method
                    break;
                case 6:
                    logout(user);
                    return;
                default:
                    LOGGER.warn("Invalid choice. Try again!");
            }
        }
    }

    private void projectManagerMenu(UserModels.User user) throws SQLException {
        while (true) {
            System.out.println("Project Manager Menu:");
            System.out.println("1. Manage Clients");
            System.out.println("2. Manage Project Teams");
            System.out.println("3. Manage Projects");
            System.out.println("4. Update password");
            System.out.println("5. Messages"); // New option
            System.out.println("6. Logout");
            System.out.print("Choose your option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    manageClients();
                    break;
                case 2:
                    manageProjectTeams();
                    break;
                case 3:
                    manageProjects();
                    break;
                case 4:
                    updatePassword(user);
                    break;
                case 5:
                    messagesMenu(user); // New method
                    break;
                case 6:
                    logout(user);
                    return;
                default:
                    LOGGER.warn("Invalid choice. Try again!");
            }
        }
    }

    private void teamMemberMenu(UserModels.User user) throws SQLException {
        while (true) {
            System.out.println("Team Member Menu:");
            System.out.println("1. View assigned project details");
            System.out.println("2. Update Task Status");
            System.out.println("3. Messages"); // New option
            System.out.println("4. Logout");
            System.out.print("Choose your option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    viewAssignedProjectDetails(user);
                    break;
                case 2:
                    updateTaskStatus(user);
                    break;
                case 3:
                    messagesMenu(user);
                    break;
                case 4:
                    logout(user);
                    return;
                default:
                    LOGGER.warn("Invalid choice. Try again!");
            }
        }
    }

    private void viewUserActivity() throws SQLException {
        List<UserModels.User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            LOGGER.info("No users found.");
            return;
        }

        System.out.println("User Activity:");
        for (UserModels.User user : users) {
            System.out.println("Name: " + user.getUser_name());
            System.out.println("Status: " + user.getStatus());

            List<Task> tasks = taskService.getTasksByUserId(user.getUser_id());
            if (tasks.isEmpty()) {
                System.out.println("No tasks assigned.");
            } else {
                for (Task task : tasks) {
                    System.out.println("  - Task Name: " + task.getTaskName());
                    System.out.println("    Task Status: " + task.getTaskStatus());
                }
            }
            System.out.println("--------------------------------------------------");
        }
    }

    private void messagesMenu(UserModels.User user) throws SQLException {
        while (true) {
            System.out.println("Messages Menu:");
            System.out.println("1. Send a message");
            System.out.println("2. View received messages");
            System.out.println("3. View sent messages");
            System.out.println("4. Go back");
            System.out.print("Choose your option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    sendMessage(user);
                    break;
                case 2:
                    viewReceivedMessages(user);
                    break;
                case 3:
                    viewSentMessages(user);
                    break;
                case 4:
                    return;
                default:
                    LOGGER.warn("Invalid choice. Try again!");
            }
        }
    }

    private void sendMessage(UserModels.User sender) throws SQLException {
        System.out.println("Select recipient:");
        List<UserModels.User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            LOGGER.info("No available recipients to send a message.");
            return;
        }

        for (int i = 0; i < users.size(); i++) {
            UserModels.User user = users.get(i);
            LOGGER.info((i + 1) + ". " + user.getUser_name() + " (" + user.getUser_role() + ")");
        }

        System.out.print("Enter recipient number: ");
        int recipientIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        if (recipientIndex < 0 || recipientIndex >= users.size()) {
            LOGGER.warn("Invalid recipient. Try again!");
            return;
        }

        UserModels.User recipient = users.get(recipientIndex);
        System.out.print("Enter your message: ");
        String messageContent = scanner.nextLine();
        Message message = new Message(0, messageContent, new Timestamp(System.currentTimeMillis()), sender.getUser_id(), recipient.getUser_id());
        messageService.addMessage(message);
        LOGGER.info("Message sent successfully.");
    }

    private void viewReceivedMessages(UserModels.User user) throws SQLException {
        List<Message> messages = messageService.getMessagesById(user.getUser_id());
        if (messages.isEmpty()) {
            LOGGER.info("No received messages found.");
            return;
        }
        System.out.println("Received Messages:");
        for (Message message : messages) {
            UserModels.User sender = userService.getUserById(message.getSenderId());
            if (sender == null) {
                LOGGER.info("From: Unknown at " + message.getTimestamp());
            } else {
                LOGGER.info("From: " + sender.getUser_name() + " at " + message.getTimestamp());
            }
            LOGGER.info("Message: " + message.getContent());
            System.out.println("--------------------------------------------------");
        }
    }

    private void viewSentMessages(UserModels.User user) throws SQLException {
        List<Message> messages = messageService.getMessagesById(user.getUser_id());
        if (messages.isEmpty()) {
            LOGGER.info("No sent messages found.");
            return;
        }
        System.out.println("Sent Messages:");
        for (Message message : messages) {
            UserModels.User recipient = userService.getUserById(message.getReceiverId());
            LOGGER.info("To: " + (recipient != null ? recipient.getUser_name() : "Unknown") + " at " + message.getTimestamp());
            LOGGER.info("Message: " + message.getContent());
            System.out.println("--------------------------------------------------");
        }
    }

    private void viewAssignedProjectDetails(UserModels.User user) throws SQLException {
        List<Project> assignedProjects = projectService.getProjectsByTeamMember(user.getUser_id());

        if (assignedProjects == null || assignedProjects.isEmpty()) {
            LOGGER.info("No assigned projects found.");
            return;
        }

        System.out.println("Assigned Projects:");
        for (Project project : assignedProjects) {
            Clients client = clientsService.getClientById(project.getClientId());
            List<Task> tasks = taskService.getTasksByProjectId(project.getProjectId());

            System.out.println("Project Name: " + project.getProjectName());
            System.out.println("Start Date: " + project.getStartDate());
            System.out.println("Client Name: " + (client != null ? client.getClient_name() : "Unknown"));
            System.out.println("Tasks:");
            for (Task task : tasks) {
                System.out.println("  - Task Name: " + task.getTaskName());
                System.out.println("    Task Description: " + task.getTaskDescription());
                System.out.println("    Task Status: " + task.getTaskStatus());
            }
            System.out.println("--------------------------------------------------");
        }
    }

    private void updateTaskStatus(UserModels.User user) throws SQLException {
        System.out.println("Enter task name: ");
        String taskName = scanner.nextLine();
        Task task = taskService.getTaskByName(taskName);

        if (task == null) {
            LOGGER.warn("Task not found.");
            return;
        }

        if (task.getAssignedTo() != user.getUser_id()) {
            LOGGER.warn("You are not assigned to this task.");
            return;
        }

        System.out.println("Current status: " + task.getTaskStatus());
        System.out.println("Select new status:");
        for (int i = 0; i < Task.TaskStatus.values().length; i++) {
            LOGGER.info((i + 1) + ". " + Task.TaskStatus.values()[i]);
        }
        System.out.println("Enter your choice: ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();

        if (statusChoice < 1 || statusChoice > Task.TaskStatus.values().length) {
            LOGGER.warn("Invalid choice. Try again!");
            return;
        }

        Task.TaskStatus newStatus = Task.TaskStatus.values()[statusChoice - 1];
        task.setTaskStatus(newStatus);

        // Update the task_end_date if the status is set to Completed
        if (newStatus == Task.TaskStatus.Completed) {
            task.setTaskEndDate(Date.valueOf(LocalDate.now()));
        }

        taskService.updateTask(task);
        LOGGER.info("Task status updated successfully.");
    }

    private void manageClients() throws SQLException {
        while (true) {
            System.out.println("Manage Clients Menu:");
            System.out.println("1. Create a new client");
            System.out.println("2. Delete a client");
            System.out.println("3. Update client details");
            System.out.println("4. View all clients");
            System.out.println("5. Go back");
            System.out.print("Choose your option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    deleteClient();
                    break;
                case 3:
                    updateClient();
                    break;
                case 4:
                    viewAllClients();
                    break;
                case 5:
                    return;
                default:
                    LOGGER.warn("Invalid choice. Try again!");
            }
        }
    }

    private void manageProjectTeams() {
        while (true) {
            System.out.println("Manage Project Teams Menu:");
            System.out.println("1. View All Teams");
            System.out.println("2. Add a New Team");
            System.out.println("3. Update a Team");
            System.out.println("4. Delete a Team");
            System.out.println("5. Go back");
            System.out.print("Choose your option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllTeams();
                    break;
                case 2:
                    addProjectTeam();
                    break;
                case 3:
                    updateProjectTeam();
                    break;
                case 4:
                    deleteProjectTeam();
                    break;
                case 5:
                    return;
                default:
                    LOGGER.warn("Invalid choice. Try again!");
            }
        }
    }

    private void manageProjects() throws SQLException {
        while (true) {
            System.out.println("Manage Projects Menu:");
            System.out.println("1. View all projects");
            System.out.println("2. Add new project");
            System.out.println("3. Edit status of a project");
            System.out.println("4. Edit milestones of a project");
            System.out.println("5. Assign tasks to team members"); // New option
            System.out.println("6. Go back");
            System.out.print("Choose your option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    viewAllProjects();
                    break;
                case 2:
                    addNewProject();
                    break;
                case 3:
                    editProjectStatus();
                    break;
                case 4:
                    editProjectMilestones();
                    break;
                case 5:
                    assignTaskToTeamMember(); // New method
                    break;
                case 6:
                    return;
                default:
                    LOGGER.warn("Invalid choice. Try again!");
            }
        }
    }

    private void viewAllProjects() throws SQLException {
        List<Project> projects = projectService.getAllProjects(); // Assuming projectService is available
        if (projects == null || projects.isEmpty()) {
            LOGGER.info("No projects found.");
            return;
        }

        System.out.println("All projects:");
        for (Project project : projects) {
            LOGGER.info("Project Name: " + project.getProjectName());
            LOGGER.info("Description: " + project.getProjectDescription());
            LOGGER.info("Start Date: " + project.getStartDate());
            LOGGER.info("Deadline: " + project.getDeadline());
            LOGGER.info("Status: " + project.getProjectStatus());
            LOGGER.info("Client ID: " + project.getClientId());
            LOGGER.info("Team ID: " + project.getTeamId());
            LOGGER.info("--------------------------------------------------");
        }
    }

    private void addNewProject() throws SQLException {
        LOGGER.info("Starting to add a new project.");
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter project description: ");
        String description = scanner.nextLine();
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter deadline (YYYY-MM-DD): ");
        String deadline = scanner.nextLine();
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        System.out.print("Enter project team name: ");
        String teamName = scanner.nextLine();

        // Validate date format
        if (!isValidDate(startDate) || !isValidDate(deadline)) {
            LOGGER.error("Invalid date format. Please use YYYY-MM-DD.");
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        Clients client = clientsService.getClientByName(clientName);
        ProjectTeams team = projectTeamsService.getProjectTeam(teamName);

        if (client == null) {
            LOGGER.error("Client does not exist.");
            System.out.println("Client does not exist.");
            return;
        }

        if (team == null) {
            LOGGER.error("Project team does not exist.");
            System.out.println("Project team does not exist.");
            return;
        }

        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectDescription(description);
        project.setStartDate(Date.valueOf(startDate));
        project.setDeadline(Date.valueOf(deadline));
        project.setClientId(client.getClient_id());
        project.setTeamId(team.getTeamId());
        project.setProjectStatus(Project.ProjectStatus.Assigned); // Set default status

        projectService.addProject(project);
        LOGGER.info("Project added successfully with status 'Assigned'.");
        System.out.println("Project added successfully with status 'Assigned'.");

        // Fetch the project_id using MilestoneDAO
        int projectId;
        projectId = milestoneService.getProjectIdByName(projectName);

        // Create initial milestone without end date
        Milestone milestone = new Milestone(0, Milestone.MilestoneName.Project_Initiation, "Understand the client's requirements and expectations.", projectId);
        milestoneService.addMilestone(milestone);
        LOGGER.info("Initial milestone 'Project_Initiation' added successfully.");
        System.out.println("Initial milestone 'Project_Initiation' added successfully.");
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            LOGGER.error("Invalid date format: {}", date, e);
            return false;
        }
    }

    private void editProjectStatus() throws SQLException {
        LOGGER.info("Starting to edit project status.");
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        Project project = projectService.getProjectByName(projectName);

        if (project == null) {
            LOGGER.error("Project not found.");
            System.out.println("Project not found.");
            return;
        }

        System.out.println("Current status: " + project.getProjectStatus());
        System.out.println("Select new status:");
        for (int i = 0; i < Project.ProjectStatus.values().length; i++) {
            System.out.println((i + 1) + ". " + Project.ProjectStatus.values()[i]);
        }
        System.out.print("Enter your choice: ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();

        if (statusChoice < 1 || statusChoice > Project.ProjectStatus.values().length) {
            LOGGER.error("Invalid choice. Try again!");
            System.out.println("Invalid choice. Try again!");
            return;
        }

        Project.ProjectStatus newStatus = Project.ProjectStatus.values()[statusChoice - 1];
        project.setProjectStatus(newStatus);
        projectService.updateProject(project);
        LOGGER.info("Project status updated successfully.");
        System.out.println("Project status updated successfully.");
    }

    private void editProjectMilestones() {
        LOGGER.info("Starting to edit project milestones.");
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();

        // Fetch the project_id using MilestoneDAO
        int projectId = milestoneService.getProjectIdByName(projectName);

        LOGGER.info("Retrieved project ID: {}", projectId);
        System.out.println("Retrieved project ID: " + projectId);

        System.out.println("Select milestone:");
        for (int i = 0; i < Milestone.MilestoneName.values().length; i++) {
            System.out.println((i + 1) + ". " + Milestone.MilestoneName.values()[i]);
        }
        System.out.print("Enter your choice: ");
        int milestoneChoice = scanner.nextInt();
        scanner.nextLine();

        if (milestoneChoice < 1 || milestoneChoice > Milestone.MilestoneName.values().length) {
            LOGGER.error("Invalid choice. Try again!");
            System.out.println("Invalid choice. Try again!");
            return;
        }

        Milestone.MilestoneName milestoneName = Milestone.MilestoneName.values()[milestoneChoice - 1];
        System.out.print("Enter milestone description: ");
        String milestoneDescription = scanner.nextLine();

        Milestone milestone = new Milestone(0, milestoneName, milestoneDescription, projectId);

        milestoneService.addMilestone(milestone);
        LOGGER.info("Milestone updated successfully.");
        System.out.println("Milestone updated successfully.");

        if (milestoneName == Milestone.MilestoneName.Project_Closure) {
            LocalDate endDate = LocalDate.now();
            updateEffortCalculation(projectId, endDate);
        }
    }

    private void updateEffortCalculation(int projectId, LocalDate endDate) {
        LOGGER.info("Starting to update effort calculation for project ID: {}", projectId);
        try {
            Project project = projectService.getProjectById(projectId);
            if (project == null) {
                LOGGER.error("Project not found.");
                System.out.println("Project not found.");
                return;
            }

            LocalDate startDate = project.getStartDate().toLocalDate();
            int actualEffortDays = (int) java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);

            EffortCalculation effortCalculation = new EffortCalculation();
            effortCalculation.setProjectId(projectId);
            effortCalculation.setProjectEndDate(Date.valueOf(endDate));
            effortCalculation.setActualEffort(actualEffortDays); // Store the number of days

            EffortCalculation existingEffortCalculation = effortCalculationService.getEffortCalculationByProjectId(projectId);
            if (existingEffortCalculation != null) {
                effortCalculationService.updateEffortCalculation(effortCalculation);
            } else {
                effortCalculationService.addEffortCalculation(effortCalculation);
            }
            LOGGER.info("Effort calculation updated successfully.");
        } catch (SQLException e) {
            LOGGER.error("Failed to update effort calculation: {}", e.getMessage(), e);
            System.out.println("Failed to update effort calculation: " + e.getMessage());
        }
    }

    private void assignTaskToTeamMember() throws SQLException {
        LOGGER.info("Starting to assign task to team member.");
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        Project project = projectService.getProjectByName(projectName);

        if (project == null) {
            LOGGER.error("Project not found.");
            System.out.println("Project not found.");
            return;
        }

        int projectId = projectService.getProjectIdByName(projectName);

        System.out.print("Enter team member name: ");
        String teamMemberName = scanner.nextLine();
        UserModels.User teamMember = userService.getUser(teamMemberName);

        if (teamMember == null || teamMember.getUser_role() != UserModels.UserRole.Team_Member) {
            LOGGER.error("Team member not found or not a valid team member.");
            System.out.println("Team member not found or not a valid team member.");
            return;
        }

        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();
        System.out.print("Enter task description: ");
        String taskDescription = scanner.nextLine();
        System.out.print("Enter task start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();

        // Validate date format
        if (!isValidDate(startDate)) {
            LOGGER.error("Invalid date format. Please use YYYY-MM-DD.");
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        Task.TaskStatus taskStatus = Task.TaskStatus.Assigned;

        Task task = new Task();
        task.setTaskName(taskName);
        task.setTaskDescription(taskDescription);
        task.setTaskStartDate(Date.valueOf(startDate));
        task.setProjectId(projectId); // Use the fetched project ID
        task.setAssignedTo(teamMember.getUser_id());
        task.setTaskStatus(taskStatus);

        boolean added = taskService.addTask(task);
        LOGGER.info("Task assigned: {}", added);
        System.out.println("Task assigned: " + added);
    }

    private void addProjectTeam() {
        LOGGER.info("Starting to add a new project team.");
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine().trim();

        // Check if the team name already exists
        while (projectTeamsService.getProjectTeam(teamName) != null) {
            LOGGER.error("Team name already exists. Please enter another name.");
            System.out.println("Team name already exists. Please enter another name.");
            System.out.print("Enter team name: ");
            teamName = scanner.nextLine().trim();
        }

        ProjectTeams projectTeam = new ProjectTeams(teamName);
        projectTeamsService.addProjectTeam(projectTeam);
        LOGGER.info("Team added successfully.");
        System.out.println("Team added successfully.");

        System.out.println("Enter team members' names. Type 'done' to stop:");
        while (true) {
            System.out.print("Enter team member's name: ");
            String memberName = scanner.nextLine().trim();
            if (memberName.equalsIgnoreCase("done")) {
                break;
            }

            // Check if the user exists and is a team member before adding
            UserModels.User user = userService.getUser(memberName);
            if (user != null && user.getUser_role() == UserModels.UserRole.Team_Member) {
                // Check if the member is already in the team
                if (!projectTeamMembersService.isMemberByName(memberName)) {
                    projectTeamsService.addTeamMemberByName(teamName, memberName);
                } else {
                    LOGGER.error("{} is already a member of another team. Cannot add to the team.", memberName);
                    System.out.println(memberName + " is already a member of another team. Cannot add to the team.");
                }
            } else {
                LOGGER.error("User either doesn't exist or is not a team member. Cannot add to the team.");
                System.out.println("User either doesn't exist or is not a team member. Cannot add to the team.");
            }
        }
    }

    private void updateProjectTeam() {
        LOGGER.info("Starting to update project team.");
        System.out.print("Enter current team name: ");
        String currentTeamName = scanner.nextLine();

        ProjectTeams projectTeam = projectTeamsService.getProjectTeam(currentTeamName);
        if (projectTeam != null) {
            System.out.println("Team found. What would you like to do?");
            System.out.println("1. Edit Team Name");
            System.out.println("2. Add Members to Team");
            System.out.println("3. Remove Members from Team");
            System.out.println("4. Go back");
            System.out.print("Choose your option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    editTeamName(projectTeam);
                    break;
                case 2:
                    addMembersToTeam(projectTeam);
                    break;
                case 3:
                    removeMembersFromTeam(projectTeam);
                    break;
                case 4:
                    return;
                default:
                    LOGGER.error("Invalid choice. Try again!");
                    System.out.println("Invalid choice. Try again!");
            }
        } else {
            LOGGER.error("Team not found.");
            System.out.println("Team not found.");
        }
    }

    private void editTeamName(ProjectTeams projectTeam) {
        LOGGER.info("Starting to edit team name.");
        System.out.print("Enter new team name: ");
        String newTeamName = scanner.nextLine();

        // Store the old team name
        String oldTeamName = projectTeam.getTeamName();

        // Update the team name in the projectTeam object
        projectTeam.setTeamName(newTeamName);

        // Call the service to update the project team with both old and new names
        projectTeamsService.updateProjectTeam(oldTeamName, projectTeam);
        LOGGER.info("Team name updated successfully.");
        System.out.println("Team name updated successfully.");
    }

    private void addMembersToTeam(ProjectTeams projectTeam) {
        LOGGER.info("Starting to add members to team.");
        System.out.println("Enter team members' names to add. Type 'done' to stop:");

        // Get current team members
        List<String> currentMembers = projectTeamMembersService.getAllMembersForTeam(projectTeam.getTeamName());

        while (true) {
            System.out.print("Enter team member's name: ");
            String memberName = scanner.nextLine();
            if (memberName.equalsIgnoreCase("done")) {
                break;
            }

            // Check if the user exists and is a team member before adding
            UserModels.User user = userService.getUser(memberName);
            if (user != null && user.getUser_role() == UserModels.UserRole.Team_Member) {
                // Check if the member is already in the current team members list
                if (currentMembers.contains(memberName)) {
                    LOGGER.error("{} is already a member of the team.", memberName);
                    System.out.println(memberName + " is already a member of the team.");
                } else {
                    projectTeamsService.addTeamMemberByName(projectTeam.getTeamName(), memberName);
                }
            } else {
                LOGGER.error("User either doesn't exist or is not a team member. Cannot add to the team.");
                System.out.println("User either doesn't exist or is not a team member. Cannot add to the team.");
            }
        }
    }

    private void removeMembersFromTeam(ProjectTeams projectTeam) {
        LOGGER.info("Starting to remove members from team.");
        System.out.println("Enter team members' names to remove. Type 'done' to stop:");
        while (true) {
            System.out.print("Enter team member's name: ");
            String memberName = scanner.nextLine();
            if (memberName.equalsIgnoreCase("done")) {
                break;
            }

            // Check if the user exists and is a team member before removing
            UserModels.User user = userService.getUser(memberName); // Assuming UserService provides this method
            if (user != null && projectTeamMembersService.isMemberByName(memberName)) {
                projectTeamMembersService.removeMemberFromTeam(memberName); // Assuming this method exists
            } else {
                LOGGER.error("User either doesn't exist in the team or is not a team member. Cannot remove from the team.");
                System.out.println("User either doesn't exist in the team or is not a team member. Cannot remove from the team.");
            }
        }
    }

    private void deleteProjectTeam() {
        LOGGER.info("Starting to delete project team.");
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();

        projectTeamsService.deleteProjectTeam(teamName);
        LOGGER.info("Team deleted successfully.");
        System.out.println("Team deleted successfully.");
    }

    private void viewAllTeams() {
        LOGGER.info("Starting to view all teams.");
        List<ProjectTeams> teams = projectTeamsService.getAllProjectTeams();
        System.out.println("All teams:");
        for (ProjectTeams team : teams) {
            System.out.println(team.getTeamName());
        }
    }

    private void createClient() throws SQLException {
        LOGGER.info("Starting to create a new client.");
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();
        System.out.print("Enter client phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter client email: ");
        String email = scanner.nextLine();

        Clients client = new Clients();
        client.setClient_name(name);
        client.setClient_phone(phone);
        client.setClient_email(email);

        clientsService.addClient(client);
        LOGGER.info("Client created successfully.");
        System.out.println("Client created successfully.");
    }

    private void deleteClient() throws SQLException {
        LOGGER.info("Starting to delete client.");
        System.out.print("Enter client name to delete: ");
        String clientName = scanner.nextLine();

        Clients client = clientsService.getClientByName(clientName);
        if (client != null) {
            clientsService.deleteClient(clientName);
            LOGGER.info("Client deleted successfully.");
        } else {
            LOGGER.error("Client not found.");
            System.out.println("Client not found.");
        }
    }

    private void updateClient() throws SQLException {
        LOGGER.info("Starting to update client.");
        System.out.print("Enter client name to update: ");
        String clientName = scanner.nextLine();

        Clients client = clientsService.getClientByName(clientName);
        if (client != null) {
            System.out.print("Enter new client name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new client phone: ");
            String newPhone = scanner.nextLine();
            System.out.print("Enter new client email: ");
            String newEmail = scanner.nextLine();

            client.setClient_name(newName);
            client.setClient_phone(newPhone);
            client.setClient_email(newEmail);

            clientsService.updateClient(client);
            LOGGER.info("Client updated successfully.");
        } else {
            LOGGER.error("Client not found.");
            System.out.println("Client not found.");
        }
    }

    private void viewAllClients() throws SQLException {
        List<Clients> clients = clientsService.getAllClients();
        LOGGER.info("All clients:");
        for (Clients client : clients) {
            LOGGER.info("{} | {} | {}", client.getClient_name(), client.getClient_phone(), client.getClient_email());
        }
    }

    private void updatePassword(UserModels.User user) {
        System.out.println("Enter your current password: ");
        String currentPassword = scanner.nextLine();

        if (currentPassword.equals(user.getPassword())) {
            System.out.println("Enter your new password: ");
            String newPassword = scanner.nextLine();

            user.setPassword(newPassword);
            boolean updated = userService.updateUser(user);
            LOGGER.info("Password updated successfully: {}", updated);
        } else {
            LOGGER.warn("Incorrect current password. Password not updated.");
        }
    }

    private void registerUser() {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter phone: ");
        String phone = scanner.nextLine();

        UserModels.User user = new UserModels.User();
        user.setUser_name(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);

        if (userRole == UserModels.UserRole.Admin) {
            System.out.println("Select user role:");
            System.out.println("1. Admin");
            System.out.println("2. Project Manager");
            System.out.println("3. Team Member");
            System.out.println("Enter role choice: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();
            switch (roleChoice) {
                case 1:
                    user.setUser_role(UserModels.UserRole.Admin);
                    break;
                case 2:
                    user.setUser_role(UserModels.UserRole.Project_Manager);
                    break;
                case 3:
                    user.setUser_role(UserModels.UserRole.Team_Member);
                    break;
                default:
                    LOGGER.warn("Invalid role choice. Setting as Team Member by default.");
                    user.setUser_role(UserModels.UserRole.Team_Member);
            }
        } else {
            user.setUser_role(userRole);
        }

        user.setStatus(UserModels.Status.Inactive);

        boolean registered = userService.registerUser(user);
        LOGGER.info("User has been registered: {}", registered);
    }

    private void updateProfile() {
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        UserModels.User user = userService.login(username, password);
        if (user != null) {
            System.out.println("Enter your new username: ");
            String newUsername = scanner.nextLine();
            System.out.println("Enter your new email: ");
            String newEmail = scanner.nextLine();
            System.out.println("Enter your new phone: ");
            String newPhone = scanner.nextLine();

            user.setUser_name(newUsername);
            user.setEmail(newEmail);
            user.setPhone(newPhone);

            boolean updated = userService.updateUser(user);
            LOGGER.info("Your profile has been updated: {}", updated);
        } else {
            LOGGER.warn("Invalid username or password.");
        }
    }

    private void deleteAccount() throws SQLException {
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        UserModels.User user = userService.login(username, password);
        if (user != null) {
            boolean deleted = userService.deleteUser(user.getUser_id());
            LOGGER.info("The account has been deleted: {}", deleted);
            LOGGER.info("Going back to role selection.");
            askForRole();
        } else {
            LOGGER.warn("Invalid username or password.");
        }
    }

    private void viewAllUsers() {
        List<UserModels.User> users = userService.getAllUsers();
        LOGGER.info("All users:");
        for (UserModels.User u : users) {
            LOGGER.info(u.getUser_name());
        }
    }

    private void logout(UserModels.User user) throws SQLException {
        user.setStatus(UserModels.Status.Inactive);
        userService.updateUser(user);
        LOGGER.info("You have been logged out.");
        askForRole();
    }
}