package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.SubCommand;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

public class Reject extends SubCommand {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            if (teamManager.getTeamByMemberId(player.getUniqueId()) == null) {
                Team targetTeam = teamManager.getTeamByName(args[1]);
                if (targetTeam != null) {
                    if (targetTeam.isInvited(player.getUniqueId())) {
                        teamManager.rejectInvite(player, targetTeam);
                    } else {
                        player.sendMessage("你沒有被邀請");
                    }
                } else {
                    player.sendMessage("已拒絕隊伍邀請");
                }
            }
        } else {
            player.sendMessage("請輸入隊伍名稱");
        }
    }

    @Override
    public String name() {
        return "reject";
    }

    @Override
    public String info() {
        return "拒絕隊伍邀請";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
