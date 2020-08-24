package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.SubCommand;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

public class ChangeLeader extends SubCommand {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();

    @Override
    public void onCommand(Player player, String[] args) {
        Team team = teamManager.getTeamByMemberId(player.getUniqueId());
        if (team != null) {
            if (team.isLeader(player.getUniqueId())) {
                if (args.length > 1) {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target != null) {
                        if (!player.equals(target)) {
                            if (team.isMember(target.getUniqueId())) {
                                teamManager.changeTeamLeader(team, target.getUniqueId());
                                player.sendMessage(String.format("已轉移隊長給 %s", ChatColor.GREEN + target.getName()));
                            } else {
                                player.sendMessage("該玩家不是隊伍成員");
                            }
                        } else {
                            player.sendMessage("你不能移交隊長給自己");
                        }
                    } else {
                        player.sendMessage(String.format("玩家 %s 不存在", ChatColor.GREEN + args[1] + ChatColor.RESET));
                    }
                } else {
                    player.sendMessage("請輸入玩家名稱");
                }
            } else {
                player.sendMessage("你必須是隊長才能移交隊長職位");
            }
        } else {
            player.sendMessage("你沒有隊伍");
        }
    }

    @Override
    public String name() {
        return "changeLeader";
    }

    @Override
    public String info() {
        return "更換隊長";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
