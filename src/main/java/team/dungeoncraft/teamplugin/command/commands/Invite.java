package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.SubCommand;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

public class Invite extends SubCommand {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();

    @Override
    public void onCommand(Player player, String[] args) {
        Team team = teamManager.getTeamByMemberId(player.getUniqueId());
        if (team != null) {
            if (team.isLeader(player.getUniqueId())) {
                if (!teamManager.isTeamInviteFull(team)) {
                    if (!teamManager.isTeamFull(team)) {
                        if (args.length > 1) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target != null) {
                                if (team.isMember(target.getUniqueId())) {
                                    player.sendMessage("該玩家已是隊伍成員");
                                } else if (teamManager.getTeamByMemberId(target.getUniqueId()) != null) {
                                    player.sendMessage("該玩家已有隊伍");
                                } else if (team.isInvited(target.getUniqueId())) {
                                    player.sendMessage("已邀請過該玩家");
                                } else {
                                    teamManager.invite(team, player, target);
                                }
                            } else {
                                player.sendMessage(String.format("玩家 %s 不存在", ChatColor.GREEN + args[1] + ChatColor.RESET));
                            }
                        } else {
                            player.sendMessage("請輸入玩家名稱");
                        }
                    } else {
                        player.sendMessage("隊伍人數已達上限");
                    }
                } else {
                    player.sendMessage("隊伍邀請超過上限");
                }
            } else {
                player.sendMessage("必須是隊長才能邀請玩家");
            }
        } else {
            player.sendMessage("你沒有隊伍");
        }
    }

    @Override
    public String name() {
        return "invite";
    }

    @Override
    public String info() {
        return "邀請玩家";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
