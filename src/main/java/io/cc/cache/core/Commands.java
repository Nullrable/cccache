package io.cc.cache.core;

import io.cc.cache.command.generic.CommandCommand;
import io.cc.cache.command.string.AppendCommand;
import io.cc.cache.command.string.DecrByCommand;
import io.cc.cache.command.string.DecrCommand;
import io.cc.cache.command.key.DelCommand;
import io.cc.cache.command.key.ExistsCommand;
import io.cc.cache.command.string.GetCommand;
import io.cc.cache.command.generic.InfoCommand;
import io.cc.cache.command.string.InrcByCommand;
import io.cc.cache.command.string.InrcCommand;
import io.cc.cache.command.list.LlenCommand;
import io.cc.cache.command.list.LpopCommand;
import io.cc.cache.command.list.LpushCommand;
import io.cc.cache.command.list.LrangeCommand;
import io.cc.cache.command.generic.PingCommand;
import io.cc.cache.command.list.RpopCommand;
import io.cc.cache.command.list.RpushCommand;
import io.cc.cache.command.string.MgetCommand;
import io.cc.cache.command.string.MsetCommand;
import io.cc.cache.command.string.SetCommand;
import io.cc.cache.command.string.StrlenCommand;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author nhsoft.lsd
 */
public class Commands {

    private static final Map<String, Command> commands = new LinkedHashMap<>();

    static {
        init();
    }

    private static void init() {
        register(new CommandCommand());
        register(new InfoCommand());
        register(new PingCommand());

        register(new SetCommand());
        register(new GetCommand());
        register(new StrlenCommand());
        register(new ExistsCommand());
        register(new DelCommand());
        register(new InrcCommand());
        register(new InrcByCommand());
        register(new DecrCommand());
        register(new DecrByCommand());
        register(new MsetCommand());
        register(new MgetCommand());
        register(new AppendCommand());

        register(new LpushCommand());
        register(new LpopCommand());
        register(new RpushCommand());
        register(new RpopCommand());
        register(new LlenCommand());
        register(new LrangeCommand());
    }

    public static void register(Command command) {
        commands.put(command.getName(), command);
    }

    public static Command get(String name) {
        return commands.get(name.toUpperCase());
    }

    public static String[] getCommandNames() {
        return commands.keySet().toArray(new String[0]);
    }
}
