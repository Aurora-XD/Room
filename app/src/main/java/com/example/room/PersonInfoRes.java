package com.example.room;

public class PersonInfoRes<T> {
    private T data;

    public PersonInfoRes(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
