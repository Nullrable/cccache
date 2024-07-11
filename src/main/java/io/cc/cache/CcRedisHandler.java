package io.cc.cache;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Commands;
import io.cc.cache.core.Reply;
import io.cc.cache.exception.SyntaxException;
import io.cc.cache.reply.ErrorReply;
import io.cc.cache.reply.StringReply;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nhsoft.lsd
 */
@Slf4j
public class CcRedisHandler extends SimpleChannelInboundHandler<String> {

    private final Cache cache = new Cache();

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final String string) {

        String[] args = string.split("\r\n");

        String command = args[2];

        Command commander = Commands.get(command);

        try {
            if (commander != null) {
                Reply<?> reply = commander.execute(cache, args);
                reply.execute(ctx);
            } else {
                new StringReply("OK").execute(ctx);
            }
        } catch (NumberFormatException e) {
            new ErrorReply("ERR value is not an integer or out of range").execute(ctx);
        } catch (ClassCastException e) {
            new ErrorReply("WRONGTYPE Operation against a key holding the wrong kind of value").execute(ctx);
        } catch (IndexOutOfBoundsException e) {
            new ErrorReply("ERR wrong number of arguments for '" + command + "' command").execute(ctx);
        } catch (SyntaxException e) {
            new ErrorReply("ERR syntax error").execute(ctx);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            new ErrorReply(e.getMessage()).execute(ctx);
        }
    }
}
