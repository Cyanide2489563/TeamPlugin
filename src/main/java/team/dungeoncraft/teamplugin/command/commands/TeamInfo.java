package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.SubCommand;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

public class TeamInfo extends SubCommand {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();

    @Override
    public void onCommand(Player player, String[] args) {
        Team team = teamManager.getTeamByMemberId(player.getUniqueId());
        if (team != null) {
            player.sendMessage(teamManager.getTeamInfo(team));
        } else {
            player.sendMessage("你沒有隊伍");
        }
    }

    @Override
    public String name() {
        return "info";
    }

    @Override
    public String info() {
        return "隊伍資訊";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
