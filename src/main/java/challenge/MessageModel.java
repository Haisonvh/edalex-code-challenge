package challenge;

// Note: This is just a bare bones model, you are welcome to change it if required for your implementation.
public class MessageModel {
    private final long id;
    private final String message;

    public MessageModel(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
