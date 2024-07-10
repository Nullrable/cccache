package io.cc.cache.command.generic;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import io.cc.cache.core.Commands;
import java.util.List;

/**
 * @author nhsoft.lsd
 */

public class CommandCommand implements Command {

    public static final String NAME = "COMMAND";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        return new ArrayReply(List.of(Commands.getCommandNames()));
    }
}
