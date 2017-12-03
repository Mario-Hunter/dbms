package eg.edu.alexu.csd.oop.Utilities.reflections;


import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.ShapeFX;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionInterfaceScanner<T> {


    private final Class<T> type;

    public ReflectionInterfaceScanner(Class<T> superType) {
        type = superType;
    }

    public List<Class<? extends T>> loadClassFrom(String packageToLookIn,
                                                  String... excludedPackages) {

        List<Class<? extends T>> clsses = new ArrayList<>();
        ArrayList<String> excludedPackagesList = getExcludedPackageList
                (excludedPackages);

        String classPackage = packageToLookIn.replace("/", ".")
                .replace("\\", ".")
                .replace("src.", "");

        File file = new File(".");


        File folder = new File(file
                .listFiles()[0]
                .getAbsolutePath() + "\\"
                + packageToLookIn);
        List<Class> cles = ReflectionUtils.loadClassesFromJar(file.listFiles()[0]);
        for (Class cls : cles) {
            if (isImplementingInterface(cls)) {
                //class implements the interface, not abstract nor
                // interface
                clsses.add(cls
                        .<T>asSubclass(type));
            }
        }
        return clsses;
//        File[] contenuti = folder.listFiles();
//        System.out.println(folder.getAbsolutePath());
//        if (contenuti == null || contenuti.length == 0) {
//            return clsses;
//        }
//
//        String entryName;
//
//        for (File actual : contenuti) {
//            entryName = actual.getName();
//            if (entryName.contains(".java") || entryName.contains(".class")) {
//                //it's a class alright
//                try {
//                    Class cls = Class.forName(classPackage + "." +
//                            entryName.replace(".java", "").replace(".class", ""));
//                    if (isImplementingInterface(cls)) {
//                        //class implements the interface, not abstract nor
//                        // interface
//                        clsses.add(Class.forName
//                                (classPackage + "." +
//                                        entryName.replace(".java", "").replace(".class", ""))
//                                .<T>asSubclass(type));
//                    }
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//            } else if (actual != null && actual.listFiles().length > 0
//                    && !excludedPackagesList.contains(entryName)) {
//                //recursively search subfolders
//                for (Class<? extends T> clas : loadClassFrom
//                        (packageToLookIn + "/" +
//                                entryName, excludedPackages)) {
//                    clsses.add(clas);
//                }
//
//            }
//        }
//        return clsses;
    }

    private boolean isImplementingInterface(final Class cls) {
        return type.isAssignableFrom(cls)
                && cls.getModifiers() != (Modifier.PUBLIC | Modifier.ABSTRACT)
                && !cls.isInterface();
    }

    private ArrayList<String> getExcludedPackageList(final String[] excludedPackages) {

        ArrayList<String> excludedPackagesList = new ArrayList<>();
        for (String pkg : excludedPackages) {
            excludedPackagesList.add(pkg);
        }
        return excludedPackagesList;
    }

    public List<Class<? extends Shape>> loadClassesFrom(final List<Class> classes) {
        List<Class<? extends Shape>> shapeClasses = new ArrayList<>();
        for (Class cls : classes) {
            if (isImplementingInterface(cls)) {
                shapeClasses.add(cls.<T>asSubclass(type));
            }
        }
        return shapeClasses;
    }
}
