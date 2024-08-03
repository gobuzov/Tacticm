package ru.tacticm;

import java.util.ArrayList;
import java.util.Hashtable;

public class ResourceManager {
    private static Hashtable resources = new Hashtable(); // for access to pictures & video
    private static void initStore(){
        String json = Tools.loadAssetString("res.txt");
        Jlen jl = new Jlen(json);
        resources = (Hashtable)jl.parse();
    }
    /**
     * @param s [фото 8–10; рис. 1; фото 11, 12]
     * @return array of resources[caption + path]
     */
    public static ArrayList<Resource> getResources(String s) throws ResourceException{
        if (resources.isEmpty())
            initStore();
        String caption = c.RIS;
        String ch = "r";
        if (s.startsWith(c.PHOTO)){
            caption = c.PHOTO;
            ch = "f";
        }else if (s.startsWith(c.VIDEO)){
            caption = c.VIDEO;
            ch = "v";
        }else if (false==s.startsWith(c.RIS)){
            throw new ResourceException("Unsupported resource name, must to be 'фото' or 'рис.' или 'видео'");
        }
        s = s.substring(caption.length()).trim();
        int id = s.indexOf('-');
        if (-1==id)
            id = s.indexOf('–');// '–' != '-'
        ArrayList<Resource> list = new ArrayList<>();
        if (-1!=id){ // фото 8–10
            try {
                int min = Integer.parseInt(s.substring(0, id).trim());
                int max = Integer.parseInt(s.substring(id+1).trim());
                if (max<=min){
                    throw new ResourceException("wrong numbers range: first must to be less than second");
                }
                for (int i=min; i<=max; i++){
                    String path = (String)(resources.get(ch.concat(Integer.toString(i))));
                    if (null!=path){
                        list.add(new Resource(caption.concat(" ").concat(Integer.toString(i)), path));
                    }
                }
                return list;
            }catch (NumberFormatException nfe){ throw new ResourceException("wrong numbers for range");}
        }
        String[] array = s.split(",");
        for (int i=0; i<array.length; i++){
            String path = (String)(resources.get(ch.concat(array[i].trim())));
            if (null!=path){
                list.add(new Resource(caption.concat(" ").concat(array[i].trim()), path));
            }
        }
        return list;
    }
}
