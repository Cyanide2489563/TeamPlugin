package team.dungeoncraft.teamplugin.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.team.Team;
import team.dungeoncraft.teamplugin.team.TeamManager;

public class Expansion extends PlaceholderExpansion {

    private final TeamPlugin plugin;
    private final TeamManager teamManager;

    public Expansion(TeamPlugin plugin) {
        this.plugin = plugin;
        this.teamManager = plugin.getTeamManager();
    }

    @Override
    public String getIdentifier() {
        return plugin.getDescription().getName();
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equalsIgnoreCase("current_team")) {
            Team team = teamManager.getTeamByMemberId(player.getUniqueId());
            if (team != null) {
                return team.getName();
            } else {
                return "沒有隊伍";
            }
        }
        return "";
    }
}
