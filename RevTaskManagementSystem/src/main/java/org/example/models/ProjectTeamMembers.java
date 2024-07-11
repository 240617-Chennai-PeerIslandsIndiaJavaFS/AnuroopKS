package org.example.models;

public class ProjectTeamMembers {
    private int memberId;
    private int teamId;
    private String memberName;


    public ProjectTeamMembers(int memberId, int teamId, String memberName) {
        this.memberId = memberId;
        this.teamId = teamId;
        this.memberName = memberName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
