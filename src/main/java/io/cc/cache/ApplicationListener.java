package io.cc.cache;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author nhsoft.lsd
 */
@Component
public class ApplicationListener implements org.springframework.context.ApplicationListener<ApplicationEvent> {

    @Autowired
    List<Plugin> plugins;

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        if(event instanceof ApplicationReadyEvent are) {
            for (Plugin plugin : plugins) {
                plugin.init();
                plugin.startup();
            }
        } else if (event instanceof ContextClosedEvent cce) {
            for (Plugin plugin : plugins) {
                plugin.shutdown();
            }
        }
    }
}
