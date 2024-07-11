package io.cc.cache.command.set;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.IntegerReply;

/**
 * @author nhsoft.lsd
 */
public class SmoveCommand implements Command {
    @Override
    public String getName() {
        return "SMOVE";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {
        String source = args[4];
        String destination = args[6];
        String member = args[8];
        int ret = cache.smove(source, destination, member);
        return new IntegerReply(ret);
    }
}
