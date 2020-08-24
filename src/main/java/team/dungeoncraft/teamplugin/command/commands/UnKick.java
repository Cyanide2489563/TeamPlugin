package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.SubCommand;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

public class UnKick extends SubCommand {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();

    @Override
    public void onCommand(Player player, String[] args) {
        Team team = teamManager.getTeamByMemberId(player.getUniqueId());

        if (team != null) {
            if (team.isLeader(player.getUniqueId())) {
                if (args.length > 1) {
                    Player kicked = Bukkit.getPlayer(args[1]);
                    if (team.isKicked(kicked.getUniqueId())) {
                        teamManager.unKickPlayer(team, kicked);
                        player.sendMessage(String.format("已將 %s 從黑名單移出", ChatColor.GREEN + kicked.getName() + ChatColor.RESET));
                    } else {
                        player.sendMessage("該玩家不在黑名單中");
                    }
                } else {
                    player.sendMessage("請輸入玩家名稱");
                }
            } else {
                player.sendMessage("必須是隊長才能解除隊伍黑名單");
            }
        } else {
            player.sendMessage("你沒有隊伍");
        }
    }

    @Override
    public String name() {
        return "unKick";
    }

    @Override
    public String info() {
        return "解除玩家黑名單";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
