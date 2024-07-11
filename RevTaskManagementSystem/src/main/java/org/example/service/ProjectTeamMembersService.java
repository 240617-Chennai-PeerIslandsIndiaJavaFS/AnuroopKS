package org.example.service;

import org.example.dao.ProjectTeamMembersDAO;
import org.example.models.ProjectTeamMembers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectTeamMembersService {
    private ProjectTeamMembersDAO projectTeamMembersDAO;

    public ProjectTeamMembersService() {
        this.projectTeamMembersDAO = new ProjectTeamMembersDAO();
    }

    public void addMemberToTeam(String memberName, String teamName) {
        try {
            projectTeamMembersDAO.addMemberByName(memberName, teamName);
            System.out.println("Member added successfully: " + memberName + " to team: " + teamName);
        } catch (SQLException e) {
            System.err.println("Error adding member: " + e.getMessage());
        }
    }

    public void removeMemberFromTeam(String memberName) {
        try {
            projectTeamMembersDAO.removeMemberByName(memberName);
            System.out.println("Member removed successfully: " + memberName);
        } catch (SQLException e) {
            System.err.println("Error removing member: " + e.getMessage());
        }
    }

    public boolean isMemberByName(String memberName) {
        try {
            ProjectTeamMembers member = projectTeamMembersDAO.getMemberByName(memberName);
            return member != null; // Returns true if member exists, false otherwise
        } catch (SQLException e) {
            System.err.println("Error checking member: " + e.getMessage());
            return false; // Return false in case of exception or no member found
        }
    }


    public List<String> getAllMembersForTeam(String teamName) {
        List<String> members = new ArrayList<>();
        try {
            int teamId = projectTeamMembersDAO.getTeamIdByName(teamName);
            members = projectTeamMembersDAO.getAllMembersByTeamId(teamId);
        } catch (SQLException e) {
            System.err.println("Error retrieving members for team " + teamName + ": " + e.getMessage());
        }
        return members;
    }
}
