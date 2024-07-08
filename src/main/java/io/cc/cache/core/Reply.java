package io.cc.cache.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

/**
 * @author nhsoft.lsd
 */
public abstract class Reply<T> {
    protected T content;

    public Reply(final T value) {
        this.content = value;
    }

    public abstract void execute(ChannelHandlerContext ctx);

    protected void integer(final ChannelHandlerContext ctx, final int content) {

        writeByteBuf(ctx, ":" + content + "\r\n");
    }

    protected void simpleString(final ChannelHandlerContext ctx, final String content) {
        if (content == null) {
            error(ctx);
            return;
        } else if (content.isEmpty()) {
            empty(ctx);
            return;
        }

        writeByteBuf(ctx, "+" + content + "\r\n");
    }

    protected void bulkString(final ChannelHandlerContext ctx, final String content) {
        // $ + 长度 + \r\n + 内容 + \r\n
        if (content == null) {
            error(ctx);
            return;
        } else if (content.isEmpty()) {
            empty(ctx);
            return;
        }
        writeByteBuf(ctx, "$" + content.getBytes().length + "\r\n" + content + "\r\n");
    }

    protected void array(final ChannelHandlerContext ctx, final List<String> contents) {
        // * + 长度 + \r\n
        // $ + 长度 + \r\n + 内容 + \r\n
        // example:
        // *3\r\n
        // $3\r\nfoo\r\n
        // $3\r\nbar\r\n
        // $5\r\nhello\r
        ByteBuf buffer = Unpooled.buffer(128);
        buffer.writeBytes(("*" + contents.size() + "\r\n").getBytes());
        for (String content : contents) {
            buffer.writeBytes(("$" + content.getBytes().length + "\r\n" + content + "\r\n").getBytes());
        }
        ctx.writeAndFlush(buffer);
    }

    protected void error(final ChannelHandlerContext ctx) {
        writeByteBuf(ctx, "$-1\r\n");
    }

    protected void error(final ChannelHandlerContext ctx, final String content) {
        writeByteBuf(ctx, "-" + content + "\r\n");
    }

    protected void empty(final ChannelHandlerContext ctx) {
        writeByteBuf(ctx, "$0\r\n");
    }

    protected void writeByteBuf(final ChannelHandlerContext ctx, final String content) {
        ByteBuf buffer = Unpooled.buffer(128);
        buffer.writeBytes(content.getBytes());
        ctx.writeAndFlush(buffer);
    }
}
