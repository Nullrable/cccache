package io.cc.cache.command.string;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.StringReply;

/**
 * @author nhsoft.lsd
 */
public class MsetCommand implements Command {

    @Override
    public String getName() {
        return "MSET";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        for (int i = 4; i < args.length; i += 4) {
            String key = args[i];
            String value = args[i + 2];
            cache.set(key, value);
        }
        return new StringReply("OK");
    }
}
