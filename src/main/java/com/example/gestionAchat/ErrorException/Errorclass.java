package com.example.gestionAchat.ErrorException;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;


public class Errorclass {

    private Timestamp timestamp ;
    private String error;
    private String message;
    private String className;
    private String exceptiopnClassName;
    private String status;
    private String trace;



    public Errorclass(Exception e, Object o, HttpStatus status) {
        long date= new Date().getTime();
        this.setTimestamp(new Timestamp(date));
        this.setStatus(status.value() + " " + status.name());
        if (e.getCause()==null) {this.setError("no value");}
        else {this.setError(e.getCause().getMessage());}
        this.setMessage(e.getMessage());
        String stack = Arrays.toString(e.getStackTrace()).replaceAll(",", System.lineSeparator());
        this.setTrace(stack);
        this.setClassName(o.getClass().getName());
        this.setExceptiopnClassName(e.getClass().getName());
    }


    public Errorclass(Exception e, Object o, String msg, HttpStatus status) {
        long date= new Date().getTime();
        this.setTimestamp(new Timestamp(date));
        this.setMessage(msg);
        if (e.getCause()==null) {this.setError("no value");}
        else {this.setError(e.getCause().getMessage());}
        this.setStatus(status.value() + " " + status.name());
        String stack = Arrays.toString(e.getStackTrace()).replaceAll(",", System.lineSeparator());
        this.setTrace(stack);
        this.setClassName(o.getClass().getName());
        this.setExceptiopnClassName(e.getClass().getName());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public String getExceptiopnClassName() {
        return exceptiopnClassName;
    }


    public void setExceptiopnClassName(String exceptiopnClassName) {
        this.exceptiopnClassName = exceptiopnClassName;
    }


    public String getError() {
        return error;
    }


    public void setError(String error) {
        this.error = error;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }




}
