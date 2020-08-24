package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.SubCommand;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

public class Kick extends SubCommand {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();

    @Override
    public void onCommand(Player player, String[] args) {
        Team team = teamManager.getTeamByMemberId(player.getUniqueId());

        if (team != null) {
            if (team.isLeader(player.getUniqueId())) {
                if (args.length > 1) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(String.format("玩家 %s 不存在", ChatColor.GREEN + args[1] + ChatColor.RESET));
                    } else if (player.equals(target)) {
                        player.sendMessage("你不能踢出自己");
                    } else if (team.isMember(target.getUniqueId())) {
                            teamManager.kickTeamMember(team, target.getUniqueId());
                    } else {
                        player.sendMessage("該玩家不是隊伍成員");
                    }
                } else {
                    player.sendMessage("請輸入欲踢出成員名稱");
                }
            } else {
                player.sendMessage("必須是隊長才能踢出隊伍成員");
            }
        } else {
            player.sendMessage("你沒有隊伍");
        }
    }

    @Override
    public String name() {
        return "kick";
    }

    @Override
    public String info() {
        return "踢出成員";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
