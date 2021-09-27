package mainServer.java;

import java.io.Serializable;

public class FilenameHolder implements Serializable {
    private static final long serialVersionUID = 33L;
    private static String filename;

    public static void setFilename(String filename) {
        FilenameHolder.filename = filename;
    }

    public static String getFilename() {
        return filename;
    }
}
