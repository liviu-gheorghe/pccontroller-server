package util;

import java.net.URI;
import java.net.URI;


public class FileInformation {

    private URI uri;
    private String name;
    private String size;
    private String type = "";



    public FileInformation(URI uri, String name, String size, String type) {
        this.uri = uri;
        this.name = name;
        this.size = size;
        this.type = type;
    }

    public FileInformation() {
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}