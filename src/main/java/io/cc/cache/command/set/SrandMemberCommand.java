package io.cc.cache.command.set;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author nhsoft.lsd
 */
public class SrandMemberCommand implements Command {
    @Override
    public String getName() {
        return "SRANDMEMBER";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {
        String key = args[4];
        int count = Integer.parseInt(args[6]);
        Set<String> vals = cache.srandmember(key, count);
        return new ArrayReply(new ArrayList<>(vals));
    }
}
