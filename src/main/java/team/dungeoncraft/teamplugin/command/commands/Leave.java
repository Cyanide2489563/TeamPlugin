package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.SubCommand;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

public class Leave extends SubCommand {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();

    @Override
    public void onCommand(Player player, String[] args) {
        if (teamManager.isPlayerHasTeam(player.getUniqueId())) {
            Team team = teamManager.getTeamByMemberId(player.getUniqueId());
            if (team != null) {
                if (team.getMembersCount() > 1 && team.isLeader(player.getUniqueId())) {
                    player.sendMessage("請先轉移隊長給其他成員");
                } else {
                    teamManager.leaveTeam(team, player.getUniqueId());
                    player.sendMessage(String.format("已離開隊伍 %s", ChatColor.GOLD + team.getName()));
                }
            } else {
                player.sendMessage("隊伍不存在");
            }
        } else {
            player.sendMessage("你沒有隊伍");
        }
    }

    @Override
    public String name() {
        return "leave";
    }

    @Override
    public String info() {
        return "離開隊伍";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
