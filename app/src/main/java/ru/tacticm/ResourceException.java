package ru.tacticm;

public class ResourceException extends Exception{
    String msg;
    public ResourceException(String m){
        msg = m;
    }
}
