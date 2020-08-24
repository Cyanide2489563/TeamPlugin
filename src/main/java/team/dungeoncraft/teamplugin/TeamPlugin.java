package team.dungeoncraft.teamplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import team.dungeoncraft.teamplugin.command.CommandManager;
import team.dungeoncraft.teamplugin.hook.Expansion;
import team.dungeoncraft.teamplugin.team.TeamManager;

public final class TeamPlugin extends JavaPlugin {

    public static TeamPlugin plugin;
    private TeamManager teamManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        teamManager = new TeamManager();
        new CommandManager(plugin);
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Expansion(this).register();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }
}
