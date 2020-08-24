package team.dungeoncraft.teamplugin.command.commands;

import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.command.SubCommand;

public class Help extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {

    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String info() {
        return "指令列表";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
