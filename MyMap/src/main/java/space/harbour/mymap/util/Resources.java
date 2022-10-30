package space.harbour.mymap.util;

import space.harbour.mymap.MyMapApp;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class Resources {

    public static InputStream getImage(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("images" + File.separator + name);
    }

    public static URL getUI(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name);
    }

    public static String getPath(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name)
                .toString();
    }

    public static String getkjaksd(String name) {
        return MyMapApp.class.getResource("ui" + File.separator + name)
                .toString();
    }
}
