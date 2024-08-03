package ru.tacticm;

import java.util.*;

public class Card {
    String i = "";  // id
    String h = "";  // header
    String[] t = {};// tags
    String c = "";  // content

    boolean mark = false;
    String prev, next; // ids
    public Card (Hashtable ht){
        i = (String)ht.get("i");
        h = ((String)ht.get("h")).toUpperCase();
        c = (String)ht.get("c");
        Vector v = (Vector)ht.get("t");
        t = new String[v.size()];
        for (int i=0; i<v.size();i++)
            t[i] = ((String)v.get(i)).toLowerCase();
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
