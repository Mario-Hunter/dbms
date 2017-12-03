package eg.edu.alexu.csd.oop.Utilities.reflections;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectionUtils {
    public static List<Class> loadClassesFromJar(File file) {
        JarFile jarFile = null;
        List<Class> classes = new ArrayList<>();

        try {
            jarFile = new JarFile(file);
            Enumeration<JarEntry> e = jarFile.entries();
            URL[] urls;
            urls = new URL[]{new URL("jar:file:" + file.getPath() + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                // -6 because of .class
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                Class c = cl.loadClass(className);
                classes.add(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return classes;
        }
    }
}
