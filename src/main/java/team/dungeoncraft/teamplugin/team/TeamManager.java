package team.dungeoncraft.teamplugin.team;

import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TeamManager {

    private int teamInviteLimit = 20;
    private int teamMembersLimit = 5;
    private final Map<String, Team> teamMap = new HashMap<>();

    public Team createTeam(String name, UUID leaderUUID) {
        if (getTeamByName(name) == null) {
            Team team = new Team(name, leaderUUID);
            addTeam(name, team);
            return team;
        }
        return null;
    }

    public void changeTeamLeader(Team team, UUID target) {
        team.setLeader(target);
    }

    public void leaveTeam(Team team, UUID uuid) {
        team.removeMember(uuid);
        notifyTeamMembers(team, String.format("隊伍成員 %s 離開隊伍", ChatColor.GREEN + Bukkit.getPlayer(uuid).getName() + ChatColor.RESET));
    }

    public void kickTeamMember(Team team, UUID target) {
        Player player = Bukkit.getPlayer(target);
        if (player.isOnline()) {
            player.sendMessage(String.format("你已被踢出隊伍 %s", ChatColor.GOLD + team.getName() + ChatColor.RESET));
        }

        team.removeMember(target);
        notifyTeamMembers(team, String.format("隊伍成員 %s 已被剔除", ChatColor.GREEN + player.getName() + ChatColor.RESET));
    }

    public void unKickPlayer(Team team, Player player) {
        team.removeKicked(player.getUniqueId());
    }

    public void disband(Team team) {
        notifyTeamMembers(team, String.format("隊伍: %s 已解散", ChatColor.GOLD + team.getName() + ChatColor.RESET));
        team.clearInvitations();
        team.clearMembers();
        removeTeam(team.getName());
    }

    public void disbandAllTeams() {
        for (Team team : teamMap.values()) {
            disband(team);
        }
    }

    public void notifyTeamMembers(Team team, String msg) {
        for (UUID uuid : team.getMembers()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(msg);
            }
        }
    }

    public void invite(Team team, Player inviter, Player target) {
        sendInvite(team.getName(), inviter, target);
        team.addInvite(target.getUniqueId(), System.currentTimeMillis());
        inviter.sendMessage(String.format("已發送隊伍邀請給玩家 %s", ChatColor.GREEN + target.getName()));
    }

    private void sendInvite(String teamName, Player inviter, Player target) {
        JSONMessage.create(String.format("玩家 %s 邀請你加入 %s 隊伍", ChatColor.GREEN + inviter.getName() + ChatColor.RESET, ChatColor.GOLD + teamName + ChatColor.RESET))
                .then("[接受邀請]")
                .tooltip("接受邀請")
                .color(ChatColor.GREEN)
                .runCommand("/team accept " + teamName)
                .then("   ")
                .then("[拒絕邀請]")
                .tooltip("拒絕邀請")
                .color(ChatColor.RED)
                .runCommand("/team reject " + teamName)
                .send(target);
    }

    private void sendJoinMessage(Player player, Team team) {
        JSONMessage.create("已加入隊伍 " + ChatColor.GOLD + team.getName())
                .title(10, 40, 10, player);

        JSONMessage.create("隊長: " + ChatColor.GREEN + Bukkit.getPlayer(team.getLeader()).getName())
                .subtitle(player);
    }

    private void sendMemberJoinMessage(Team team, Player player) {
        for (UUID memberUUID : team.getMembers()) {
            Player member = Bukkit.getPlayer(memberUUID);
            if (member.equals(player)) continue;

            JSONMessage.create(ChatColor.GREEN + player.getName() + ChatColor.RESET + " 已加入隊伍")
                    .title(10, 40, 10, member);
        }
    }

    public void acceptInvite(Player player, Team team) {
        sendJoinMessage(player, team);
        sendMemberJoinMessage(team, player);
        team.removeInvite(player.getUniqueId());
        team.addMember(player.getUniqueId());
    }

    public void rejectInvite(Player player, Team team) {
        team.removeInvite(player.getUniqueId());
        player.sendMessage(String.format("已拒絕 %s 的隊伍邀請", ChatColor.GOLD + team.getName() + ChatColor.RESET));
        notifyTeamMembers(team, String.format("玩家 %s 拒絕了隊伍邀請", ChatColor.GREEN + player.getName() + ChatColor.RESET));
    }

    public boolean isTeamExists(String name) {
        return teamMap.containsKey(name);
    }

    public Team getTeamByName(String name) {
        return teamMap.get(name);
    }

    public Team getTeamByMemberId(UUID id) {
        for (Team team : teamMap.values()) {
            if (team.isMember(id)) {
                return team;
            }
        }
        return null;
    }

    public String getTeamInfo(Team team) {
        StringBuilder info = new StringBuilder(ChatColor.YELLOW + "====== 隊伍資訊 =====" + "\n" +
                "隊伍名稱: " + team.getName() + "\n" +
                "隊伍人數: " + team.getMembersCount() + "\n" +
                "隊長: " + Bukkit.getPlayer(team.getLeader()).getName() + "\n");

        int i = 1;
        for (UUID member : team.getMembers()) {
            if (team.isLeader(member)) continue;
            String name = Bukkit.getPlayer(member).getName();
            info.append("隊員").append(i).append(": ").append(name).append("\n");
            i++;
        }
        info.append(ChatColor.YELLOW + "===================");

        return info.toString();
    }

    public boolean isPlayerHasTeam(UUID target) {
        return getTeamByMemberId(target) != null;
    }

    public boolean isTeamLeader(UUID target) {
        Team team = getTeamByMemberId(target);
        return team != null && team.isLeader(target);
    }

    public boolean isTeamFull(Team team) {
        return team.getMembersCount() >= teamMembersLimit;
    }

    public boolean isTeamInviteFull(Team team) {
        return team.getInvitationCount() >= teamInviteLimit;
    }

    private void addTeam(String name, Team team) {
        teamMap.put(name, team);
    }

    private void removeTeam(String name) {
        teamMap.remove(name);
    }

    public boolean checkTeamName(String name) {
        int num = 0;
        boolean matches = true;
        for (int i = 0; i < name.length(); i++) {
            String temp = name.substring(i, i + 1);
            if (temp.matches("[\u4e00-\u9fa5]")) {
                num += 1;
            } else if (temp.matches("[a-zA-Z0-9]*")) {
                num += 1;
            } else {
                matches = false;
                break;
            }
        }
        return (num <= 10 & num >= 3) & matches;
    }
}
