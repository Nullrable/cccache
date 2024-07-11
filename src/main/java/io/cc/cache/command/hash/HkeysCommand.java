package io.cc.cache.command.hash;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public class HkeysCommand implements Command {
    @Override
    public String getName() {
        return "HKEYS";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];
        List<String> keys = cache.hkeys(key);
        return new ArrayReply(keys);
    }
}
