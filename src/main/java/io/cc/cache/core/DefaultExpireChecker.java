package io.cc.cache.core;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nhsoft.lsd
 */
@Slf4j
public class DefaultExpireChecker implements ExpireChecker {

    private ScheduledExecutorService executor;

    private Cache cache;

    public DefaultExpireChecker(final Cache cache) {
        this.cache = cache;
    }

    @Override
    public void start() {
        if (executor != null && !executor.isShutdown()) {
            return;
        }
        executor = new ScheduledThreadPoolExecutor(1);

        executor.scheduleWithFixedDelay(
                () -> {
                    log.info(" ===>>> expire checker scheduled...");
                    cache.ttlKeys().forEach(key -> {
                        if (cache.delIfExpire(key)) {
                            log.info(" ===>>> key [{}] is expired and then removed...", key);
                        }
                    });
                },
                0, 1000, TimeUnit.MILLISECONDS);

        log.info(" ===>>> expire checker started...");
    }

    @Override
    public void stop() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
        log.info(" ===>>> expire checker stopped...");
    }
}
