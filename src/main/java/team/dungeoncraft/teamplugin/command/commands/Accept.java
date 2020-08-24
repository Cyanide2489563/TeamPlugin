package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.SubCommand;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

import java.util.UUID;

public class Accept extends SubCommand {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            Team targetTeam = teamManager.getTeamByName(args[1]);
            UUID playerUUID = player.getUniqueId();

            if (teamManager.getTeamByMemberId(player.getUniqueId()) == null) {
                if (targetTeam == null) {
                    player.sendMessage("該隊伍不存在");
                } else if (targetTeam.isMember(playerUUID)) {
                    player.sendMessage("你已是該隊伍成員");
                } else if (targetTeam.isInvited(playerUUID)) {
                    teamManager.acceptInvite(player, targetTeam);
                } else {
                    player.sendMessage("你沒有被邀請");
                }
            } else {
                player.sendMessage("你已有隊伍");
            }
        } else {
            player.sendMessage("請輸入隊伍名稱");
        }
    }

    @Override
    public String name() {
        return "accept";
    }

    @Override
    public String info() {
        return "接受隊伍邀請";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
