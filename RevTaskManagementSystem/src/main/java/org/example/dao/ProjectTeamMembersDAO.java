package org.example.dao;

import org.example.connection.DBConnection;
import org.example.models.ProjectTeamMembers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.dao.UserDAO.connection;

public class ProjectTeamMembersDAO {

    public void addMemberByName(String memberName, String teamName) throws SQLException {
        String getUserIdQuery = "SELECT user_id FROM User WHERE user_name = ?";
        String getTeamIdQuery = "SELECT team_id FROM ProjectTeams WHERE team_name = ?";
        String insertQuery = "INSERT INTO ProjectTeamMembers (member_id, team_id, member_name) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(getUserIdQuery);
             PreparedStatement teamStmt = conn.prepareStatement(getTeamIdQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Get the user ID
            userStmt.setString(1, memberName);
            ResultSet userRs = userStmt.executeQuery();
            if (!userRs.next()) {
                throw new SQLException("User not found: " + memberName);
            }
            int memberId = userRs.getInt("user_id");

            // Get the team ID
            teamStmt.setString(1, teamName);
            ResultSet teamRs = teamStmt.executeQuery();
            if (!teamRs.next()) {
                throw new SQLException("Team not found: " + teamName);
            }
            int teamId = teamRs.getInt("team_id");

            // Insert into ProjectTeamMembers
            insertStmt.setInt(1, memberId);
            insertStmt.setInt(2, teamId);
            insertStmt.setString(3, memberName);
            insertStmt.executeUpdate();
        }
    }

    public void removeMemberByName(String memberName) throws SQLException {
        String deleteQuery = "DELETE FROM ProjectTeamMembers WHERE member_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
            deleteStmt.setString(1, memberName);
            deleteStmt.executeUpdate();
        }
    }

    public ProjectTeamMembers getMemberByName(String memberName) throws SQLException {
        String query = "SELECT * FROM ProjectTeamMembers WHERE member_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, memberName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int memberId = rs.getInt("member_id");
                int teamId = rs.getInt("team_id");
                String name = rs.getString("member_name");
                return new ProjectTeamMembers(memberId, teamId, name);
            } else {
                return null;
            }
        }
    }

    public int getTeamIdByName(String teamName) throws SQLException {
        String query = "SELECT team_id FROM projectteams WHERE team_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, teamName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("team_id");
            } else {
                throw new SQLException("Team not found with name: " + teamName);
            }
        }
    }

    public List<String> getAllMembersByTeamId(int teamId) throws SQLException {
        List<String> members = new ArrayList<>();
        String query = "SELECT member_name FROM projectteammembers WHERE team_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String memberName = resultSet.getString("member_name");
                members.add(memberName);
            }
        }
        return members;
    }


}
