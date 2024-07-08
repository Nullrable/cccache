package io.cc.cache.core;

import io.cc.cache.command.CommandCommand;
import io.cc.cache.command.DecrByCommand;
import io.cc.cache.command.DecrCommand;
import io.cc.cache.command.DelCommand;
import io.cc.cache.command.ExistsCommand;
import io.cc.cache.command.GetCommand;
import io.cc.cache.command.InfoCommand;
import io.cc.cache.command.InrcByCommand;
import io.cc.cache.command.InrcCommand;
import io.cc.cache.command.PingCommand;
import io.cc.cache.command.SetCommand;
import io.cc.cache.command.StrlenCommand;
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
