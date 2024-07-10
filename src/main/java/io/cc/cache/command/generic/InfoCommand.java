package io.cc.cache.command.generic;

import io.cc.cache.core.CcCache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.BlukStringReply;

/**
 * @author nhsoft.lsd
 */
public class InfoCommand implements Command {

    public static final String NAME = "INFO";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Reply execute(final CcCache cache, final String[] args) {
        return new BlukStringReply("cc netty redis v0.0.1, \r\ncreated at 2024-07-08 on beijing.\r\n");
    }
}
