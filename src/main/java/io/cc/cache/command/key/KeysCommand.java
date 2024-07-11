package io.cc.cache.command.key;

import io.cc.cache.core.Cache;
import io.cc.cache.core.Command;
import io.cc.cache.core.Reply;
import io.cc.cache.reply.ArrayReply;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author nhsoft.lsd
 */
public class KeysCommand implements Command {
    @Override
    public String getName() {
        return "KEYS";
    }

    @Override
    public Reply<?> execute(final Cache cache, final String[] args) {

        Set<String> keys = cache.getKeys();

        if (keys == null || keys.isEmpty()) {
            return new ArrayReply(Collections.emptyList());
        }

        // 定义带有通配符的模式字符串
        String patternString = args[4];

        // 将通配符转换为正则表达式
        // 替换星号 (*) 为 .* (匹配任意字符任意次数)
        String regex = patternString.replace("*", ".*");

        // 创建Pattern对象
        Pattern pattern = Pattern.compile(regex);

        List<String> matchedKeys = new ArrayList<>();
        keys.forEach(key -> {
            if (pattern.matcher(key).matches()) {
                matchedKeys.add(key);
            }
        });

        return new ArrayReply(matchedKeys);
    }
}
