package ru.tacticm;

import java.util.*;
/**
 * Created with IntelliJ IDEA.
 * User: ago
 * Date: 21.10.14
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public class Jlen {
    String str;
    int i;
    public Jlen (String str){
        this.str = str;
        i = 0;
    }
    public Object parse(){
        String s = null;
        Hashtable table = null;
        Vector vector = null;
        while (i <str.length() && null==table && null==vector && null==s){
            char ch = str.charAt(i++);
            if ('{'==ch){
                table = new Hashtable();
            } else if ('['==ch){
                vector = new Vector(3, 1);
            } else if (']'==ch){
                s = "]";
            } else if ('}'==ch){
                s = "}";
            } else if ('"'==ch){
                int start = i;
                while ('"'!= str.charAt(i++)|| str.charAt(i -2)=='\\');
                s = str.substring(start, i -1);
            } else if ((ch>='0' && ch<='9')||'t'==ch || 'f'==ch || 'n'==ch){// true, false, null
                int start = i -1;
                do{
                    ch = str.charAt(i++);
                } while ('}'!=ch && ']'!=ch && ','!=ch);
                s = str.substring(start, --i);
            }
        }
        if (null!=table){
            while (true){
                Object key = parse();
                if ("}".equals(key))
                    return table;
                table.put(key, parse());
            }
        }else if (null!=vector){
            while (true){
                Object obj = parse();
                if ("]".equals(obj))
                    return vector;
                vector.addElement(obj);
            }
        }
        return s;
    }
}
