package team.dungeoncraft.teamplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CommandTabManager implements TabCompleter {

    private final TeamManager teamManager = TeamPlugin.plugin.getTeamManager();
    private final List<String> blank = Collections.singletonList("");
    private final List<String> commands = new ArrayList<>();

    CommandTabManager(List<SubCommand> commands) {
        for (SubCommand command : commands) {
            this.commands.add(command.name());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            UUID senderUUID = ((Player) sender).getUniqueId();
            int argsLength = args.length;
            if (argsLength > 1) {
                Team team = teamManager.getTeamByMemberId(senderUUID);
                if (team != null) {
                    if (team.isLeader(senderUUID)) {
                        if (args[0].equalsIgnoreCase("invite")) {
                            List<String> players = new ArrayList<>();
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (team.isMember(player.getUniqueId())) continue;
                                players.add(player.getName());
                            }
                            return StringUtil.copyPartialMatches(args[1], players, new ArrayList<>());
                        } else if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("changeLeader")) {
                            List<String> members = new ArrayList<>();
                            for (UUID memberUUID : team.getMembers()) {
                                if (memberUUID.equals(senderUUID)) continue;
                                members.add(Bukkit.getPlayer(memberUUID).getName());
                            }
                            return StringUtil.copyPartialMatches(args[1], members, new ArrayList<>());
                        } else if (args[0].equalsIgnoreCase("unKick")) {
                            List<String> kickedList = new ArrayList<>();
                            for (UUID kicked : team.getKickedList()) {
                                kickedList.add(Bukkit.getPlayer(kicked).getName());
                            }
                            return StringUtil.copyPartialMatches(args[1], kickedList, new ArrayList<>());
                        }
                    }
                }
            }
            return StringUtil.copyPartialMatches(args[0], commands, new ArrayList<>());
        }
        return blank;
    }
}
