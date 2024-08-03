package ru.tacticm;

import java.util.*;

public class CardManager {
    private static ArrayList<Card> list = new ArrayList<>(); // all cards
    private static Hashtable<String, Card> table = new Hashtable<>(); // for access by id

    public static void initStore(){
        if (table.isEmpty()) {
            String json = Tools.loadAssetString("cards.txt");
            //
            Jlen jl = new Jlen(json);
            Vector v = (Vector)jl.parse();
            for (int i=0; i<v.size(); i++) {
                Card cd = new Card((Hashtable) v.get(i));
                list.add(cd);
                table.put(cd.i, cd);
            }
            int last = v.size()-1;
            for (int i=0; i<list.size(); i++) {
                Card cd = list.get(i);
                cd.prev = 0==i ? null : list.get(i-1).i;
                cd.next = last==i ? null : list.get(i+1).i;
            }
            String[] bookmarks = App.getInstance().getStrings(c.BOOKMARKS); /// mark cards
            if (null!=bookmarks && 0!=bookmarks.length){
                for (String id : bookmarks){
                    Card card = table.get(id);
                    card.mark = true;
                }
            }
        }
    }
    public static Card get(String id){
        if (table.isEmpty())
            initStore();
        return null==id ? null : table.get(id);
    }
    public static ArrayList<Card> find(String s){
        if (table.isEmpty())
            initStore();
        if ("".equals(s))
            return list;
        s = s.toLowerCase();
        ArrayList<Card> result = new ArrayList<>();
        Enumeration<Card> en = table.elements();
        while (en.hasMoreElements()) {
            Card cd = en.nextElement();
            for (int j = 0; j < cd.t.length; j++) {
                if (cd.t[j].startsWith(s)) {
                    result.add(cd);
                    break;
                }
            }
        }
        return result;
    }
    public static void saveBookmarks(){
        ArrayList<Card> bookmarks = getBookmarks();
        String[] array = new String[bookmarks.size()];
        for (int i=0; i<bookmarks.size(); i++){
            array[i] = bookmarks.get(i).i;
        }
        App.getInstance().putStrings(c.BOOKMARKS, array);
    }
    public static ArrayList<Card> getBookmarks(){
        if (table.isEmpty())
            initStore();
        ArrayList<Card> result = new ArrayList<>();
        for (Card card : list){
            if (card.mark)
                result.add(card);
        }
        return result;
    }
}
