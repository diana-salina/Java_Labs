package ru.nsu.salina.model.message;

public class Message {
    private final MessageType type;
    private final String name;
    private final String content;
    public Message(String name, String content, MessageType type) {
        this.name = name;
        //this.length = length;
        this.content = content;
        this.type = type;
    }
    public MessageType getType() {
        return type;
    }
    public String getName() {
        return  name;
    }
    public String getMessage() {
        switch (type) {
            case MASSAGE -> {
                return name + ": " + content;
            }
            case LOGIN -> {
                return name + "connected";
            }
            case LOGOUT -> {
                return name + "disconnected";
            }
        }
        return null;
    }
}
