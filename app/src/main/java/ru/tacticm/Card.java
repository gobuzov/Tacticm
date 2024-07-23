package ru.tacticm;

import com.google.gson.Gson;

public class Card {
    String i = "";  // id
    String h = "";  // header
    String[] t = {};// tags
    String c = "";  // content

    boolean mark = false;
    String prev, next; // ids
    public static Card fromJson(String json){
        Card cd = null;
        try {
             cd = new Gson().fromJson(json, Card.class);
        }catch (Exception exc){
            exc.printStackTrace();
        }
        return cd;
    }

    public Card(String h, String id, String[] t, String content){ // not used while
        this.h = h;
        this.i = id;
        this.t = t;
        this.c = content;
    }
    public String getTags(){
        StringBuilder sb = new StringBuilder("[");
        for (int i=0; i<t.length; i++){
            sb.append(t[i]);
            if (i!=t.length-1)
                sb.append(", ");
        }
        sb.append(']');
        return sb.toString();
    }
}
