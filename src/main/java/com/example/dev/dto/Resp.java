package com.example.dev.dto;

public class Resp <T>{
    public int status;
    public String msg;
    public T data;

    public  Resp(){
        this.status = 200;
    }

    public Resp(T data) {
        this.status = 200;
        if(data instanceof String)
            this.msg = (String) data;
        else
            this.data = data;
    }

    public Resp( String msg, T data) {
        this.status = 200;
        this.msg = msg;
        this.data = data;
    }

    public Resp(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
