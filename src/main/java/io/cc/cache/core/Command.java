package io.cc.cache.core;


/**
 * @author nhsoft.lsd
 */
public interface Command {

    String getName();

    Reply<?> execute(Cache cache, String[] args);
}
