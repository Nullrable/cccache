package io.cc.cache.command.zset;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.exception.SyntaxException;
import io.cc.cache.reply.ArrayReply;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author nhsoft.lsd
 */
public class ZrangeCommand implements Command {

    private static final String WITHSCORES = "WITHSCORES";
    @Override
    public String getName() {
        return "ZRANGE";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        String key = args[4];
        int start = Integer.parseInt(args[6]);
        int end = Integer.parseInt(args[8]);

        String withscores = null;
        if (args.length > 10) {
            withscores = args[10];
        }

        Set<Cache.ZsetEntry> zsetEntries = cache.zrange(key, start, end);

        if (withscores != null && !withscores.isEmpty()) {
            if (WITHSCORES.equalsIgnoreCase(withscores)) {
                List<String> members = new ArrayList<>();
                zsetEntries.forEach(zsetEntry -> {
                    members.add(zsetEntry.getMember());
                    members.add(String.valueOf(zsetEntry.getScore()));
                });
                return new ArrayReply(members);
            } else {
                throw new SyntaxException();
            }
        } else {
            List<String> members = new ArrayList<>();
            zsetEntries.forEach(zsetEntry -> members.add(zsetEntry.getMember()));
            return new ArrayReply(members);
        }

    }
}
