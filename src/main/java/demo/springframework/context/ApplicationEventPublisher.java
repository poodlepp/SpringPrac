package demo.springframework.context;

/**
 * @author lipengyi
 */
public interface ApplicationEventPublisher {
    /**
     * @param event
     */
    void publishEvent(ApplicationEvent event);
}
