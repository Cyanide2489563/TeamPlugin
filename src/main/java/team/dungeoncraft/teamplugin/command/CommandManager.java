package team.dungeoncraft.teamplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import team.dungeoncraft.teamplugin.TeamPlugin;
import team.dungeoncraft.teamplugin.command.commands.*;

import java.util.ArrayList;
import java.util.Objects;

public class CommandManager implements CommandExecutor {

    private final ArrayList<SubCommand> commands = new ArrayList<>();
    private final String command = "team";

    public CommandManager(TeamPlugin plugin) {
        setCommands();
        Objects.requireNonNull(plugin.getCommand(command)).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand(command)).setTabCompleter(new CommandTabManager(commands));
    }

    public void setCommands() {
        commands.add(new Create());
        commands.add(new Invite());
        commands.add(new Accept());
        commands.add(new Reject());
        commands.add(new Leave());
        commands.add(new Kick());
        commands.add(new UnKick());
        commands.add(new Disband());
        commands.add(new ChangeLeader());
        commands.add(new TeamInfo());
        commands.add(new Help());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (this.command.equalsIgnoreCase(command.getName())) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("[team] 無效的指令發送者");
                return false;
            }

            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage("請輸入 /team help 獲得指令列表");
                return true;
            }

            SubCommand target = this.get(args[0]);
            if (target == null) {
                player.sendMessage("無效的子命令");
                return true;
            }

            try {
                target.onCommand(player, args);
            } catch (Exception e) {
                player.sendMessage("發生不可預期的錯誤");
                e.printStackTrace();
            }
        }
        return false;
    }

    private SubCommand get(String name) {
        for (SubCommand sc : this.commands) {
            if (sc.name().equalsIgnoreCase(name)) return sc;

            String[] aliases = sc.aliases();
            for (int i = 0, length = sc.aliases().length; i < length; i++) {
                if (name.equalsIgnoreCase(aliases[i])) return sc;
            }
        }
        return null;
    }
}
