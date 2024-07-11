package io.cc.cache.command.set;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author nhsoft.lsd
 */
public class SdiffCommand implements Command {
    @Override
    public String getName() {
        return "SDIFF";
    }

    @Override
    public Reply<?> execute(final CcCache cache, final String[] args) {

        List<String> keys = new ArrayList<>();
        for (int i = 4; i < args.length; i += 2) {
            keys.add(args[i]);
        }
        Set<String> diffs = cache.sdiff(keys.toArray(new String[0]));
        return new ArrayReply(new ArrayList<>(diffs));
    }
}
