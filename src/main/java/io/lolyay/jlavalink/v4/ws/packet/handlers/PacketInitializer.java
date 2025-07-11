package io.lolyay.jlavalink.v4.ws.packet.handlers;


import io.lolyay.jlavalink.v4.ws.packet.S2CPacket;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utility class for initializing and registering all packet classes.
 */
public class PacketInitializer {
    private static final String PACKAGE_NAME = "io.lolyay.jlavalink.v4.ws.packet.packets.S2C";
    
    /**
     * Scans for all packet classes and registers them.
     */
    public static void initialize() {
        try {
            // Get all classes in the packets package
            Class<?>[] classes = getClasses(PACKAGE_NAME);
            
            // Register all packet classes
            for (Class<?> clazz : classes) {
                if (S2CPacket.class.isAssignableFrom(clazz) && !clazz.isInterface() && !clazz.isAnnotationPresent(NoPacket.class)) {
                    try {
                        // This will trigger the static initializer if present
                        Class.forName(clazz.getName());
                        PacketRegistry.registerPacket((Class<S2CPacket>) clazz);
                    } catch (ClassNotFoundException e) {
                        System.err.println("Failed to initialize packet class: " + clazz.getName());
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to initialize packets: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     */
    public static Class<?>[] getClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<Class<?>> classes = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("jar")) {
                // Handle JAR files
                String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
                try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"))) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        String entryName = entries.nextElement().getName();
                        if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                            String className = entryName.replace('/', '.').substring(0, entryName.length() - 6);
                            try {
                                classes.add(Class.forName(className));
                            } catch (NoClassDefFoundError | ExceptionInInitializerError e) {
                                System.err.println("Could not load class: " + className);
                            }
                        }
                    }
                }
            } else {
                // Handle file system (development)
                File directory = new File(resource.getFile());
                if (directory.exists()) {
                    classes.addAll(findClasses(directory, packageName));
                }
            }
        }
        
        return classes.toArray(new Class[0]);
    }
    
    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        
        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }
        
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class") && !file.getName().contains("$")) {
                try {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                } catch (NoClassDefFoundError e) {
                    System.err.println("Could not load class: " + packageName + '.' + file.getName());
                }
            }
        }
        
        return classes;
    }
}
