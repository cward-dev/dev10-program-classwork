package learn.pets.domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    private ResultType type = ResultType.SUCCESS;
    private T payload;
    private ArrayList<String> messages = new ArrayList<>();

    public ResultType getType() {
        return type;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public void addMessage(String message, ResultType type) {
        messages.add(message);
        this.type = type;
    }
}