package team.dungeoncraft.teamplugin.team;

import java.util.*;

public final class Team {

    private String name;
    private UUID leader;
    private final List<UUID> members = new ArrayList<>();
    private final List<UUID> kicked = new ArrayList<>();
    private final Map<UUID, Long> invites = new HashMap<>();
    private final HashMap<UUID, Long> disconnectionList = new HashMap<>();

    public Team(String name, UUID leader) {
        this.name = name;
        this.leader = leader;
        addMember(leader);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public int getMembersCount() {
        return members.size();
    }

    public void addMember(UUID member) {
        members.add(member);
    }

    public void removeMember(UUID member) {
        members.remove(member);
    }

    public void addKicked(UUID player) {
        kicked.add(player);
    }

    public void removeKicked(UUID player) {
        kicked.remove(player);
    }

    public int getInvitationCount() {
        return invites.size();
    }

    public void addInvite(UUID target, long expire) {
        invites.put(target, expire);
    }

    public void removeInvite(UUID target) {
        invites.remove(target);
    }

    public List<UUID> getMembers() {
        return members;
    }

    public List<UUID> getKickedList() {
        return kicked;
    }

    void clearMembers() {
        members.clear();
    }

    void clearInvitations() {
        invites.clear();
    }

    public boolean isLeader(UUID id) {
        return leader.equals(id);
    }

    public boolean isMember(UUID id) {
        return members.contains(id);
    }

    public boolean isKicked(UUID id) {
        return kicked.contains(id);
    }

    public boolean isInvited(UUID id) {
        return  invites.containsKey(id);
    }
}
