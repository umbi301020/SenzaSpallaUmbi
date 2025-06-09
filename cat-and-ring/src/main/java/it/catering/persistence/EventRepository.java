// Pattern Singleton - Un solo repository per gli eventi
public class EventRepository {
    private static EventRepository instance;
    private final Map<String, Event> events;
    
    private EventRepository() {
        this.events = new ConcurrentHashMap<>();
    }
    
    public static EventRepository getInstance() {
        if (instance == null) {
            synchronized (EventRepository.class) {
                if (instance == null) {
                    instance = new EventRepository();
                }
            }
        }
        return instance;
    }
    
    public void save(Event event) {
        events.put(event.getEventId(), event);
    }
    
    public Event findById(String eventId) {
        return events.get(eventId);
    }
    
    public List<Event> findAll() {
        return new ArrayList<>(events.values());
    }
}
