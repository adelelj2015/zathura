package org.zathuracode.reverse.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @author William Altuzarra Noriega Noriega (williamaltu@gmail.com)
 * @version 1.0
 */
public class ZathuraReverseJarLoader {
	
	private static Logger log= Logger.getLogger(ZathuraReverseJarLoader.class);

	/**
	 * Load jar.
	 *
	 * @param jarLocation the jar location
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException the IO exception
	 */
	public static void loadJar(String jarLocation) throws FileNotFoundException, IOException {

		String jarName = jarLocation;

		URLClassLoader urlLoader = getURLClassLoader(new URL("file", null, jarName));

		JarInputStream jis = new JarInputStream(new FileInputStream(jarName));
		JarEntry entry = jis.getNextJarEntry();
		int loadedCount = 0, totalCount = 0;

		while (entry != null) {
			String name = entry.getName();
			if (name.endsWith(".class")) {
				totalCount++;
				name = name.substring(0, name.length() - 6);
				name = name.replace('/', '.');
				log.info("> " + name);

				try {
					urlLoader.loadClass(name);
					log.info("\t- loaded");
					loadedCount++;
				} catch (Throwable e) {
					log.info("\t- not loaded");
					log.info("\t " + e.getClass().getName() + ": " + e.getMessage());
				}

			}
			entry = jis.getNextJarEntry();
		}

		log.info("\n---------------------");
		log.info("Summary:");
		log.info("\tLoaded:\t" + loadedCount);
		log.info("\tFailed:\t" + (totalCount - loadedCount));
		log.info("\tTotal:\t" + totalCount);
		// ZathuraReverseMappingTool.callAntProcess();
	}

	/**
	 * Gets the url class loader.
	 *
	 * @param jarURL the jar url
	 * @return the URL class loader
	 */
	private static URLClassLoader getURLClassLoader(URL jarURL) {
		return new URLClassLoader(new URL[] { jarURL });
	}

	/**
	 * Load jar2.
	 *
	 * @param jarLocation the jar location
	 * @throws Exception the exception
	 */
	public static void loadJar2(String jarLocation) throws Exception {
		Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		addURL.setAccessible(true);// you're telling the JVM to override the
		// default visibility
		File[] files = getExternalJars(jarLocation);// some method returning the
		// jars to add
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		for (int i = 0; i < files.length; i++) {
			URL url = files[i].toURL();
			addURL.invoke(cl, new Object[] { url });
			// System.out.println("\n---------------------");
			// System.out.println("Summary:");
			// System.out.println("\tLoaded:\t" + files[i].getName());
		}
		// at this point, the default class loader has all the jars you
		// indicated
	}

	/**
	 * Gets the external jars.
	 *
	 * @param jarLocation the jar location
	 * @return the external jars
	 */
	private static File[] getExternalJars(String jarLocation) {
		File[] files = new File[1];
		files[0] = new File(jarLocation);
		return files;
	}
}