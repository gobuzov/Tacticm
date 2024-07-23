package ru.tacticm;

/**
 * Pair - visible name, path. See res.txt
 */
public class Resource {
    public String caption; // Example: рис. 1
    public String path;    // Example: image-005.png
    public Resource(String caption, String path){
        this.caption = caption;
        this.path = path;
    }
}
