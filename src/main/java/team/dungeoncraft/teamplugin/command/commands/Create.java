package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.SubCommand;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

public class Create extends SubCommand {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length > 1) {
            if (!teamManager.isPlayerHasTeam(player.getUniqueId())) {
                String teamName = args[1];
                if (teamManager.isTeamExists(teamName)) {
                    player.sendMessage("隊伍名稱重複");
                } else if (teamManager.checkTeamName(teamName)) {
                    Team team = teamManager.createTeam(teamName, player.getUniqueId());
                    player.sendMessage(String.format("已建立隊伍: %s", ChatColor.GOLD + team.getName()));
                } else {
                    player.sendMessage("請輸入符合規格的隊伍名稱, 中英文數字、3 字以上 10 字以下");
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
        return "create";
    }

    @Override
    public String info() {
        return "建立隊伍";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
