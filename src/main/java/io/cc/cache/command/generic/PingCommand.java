package io.cc.cache.command.generic;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.StringReply;

/**
 * @author nhsoft.lsd
 */
public class PingCommand implements Command {

    public static final String NAME = "PING";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply<String> execute(final Cache cache, final String[] args) {
        String ret = "PONG";
        if (args.length > 4) {
            ret = args[4];
        }
        return new StringReply(ret);
    }
}