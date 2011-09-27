package co.edu.usbcali.lidis.zathura.generator.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * Zathura Generator.
 *
 * @author Diego Armando Gomez Mosquera (dgomez@vortexbird.com)
 * @version 1.0
 */
public class GeneratorUtil {

	/** The log. */
	private static Logger log = Logger.getLogger(GeneratorUtil.class);

	/** The slash. */
	public static String slash = System.getProperty("file.separator");
	
	/** The File name. */
	public static String FileName = "zathura-generator-factory-config.xml";

	/** The full path. */
	private static String fullPath = "";
	
	/** The xml config factory path. */
	private static String xmlConfigFactoryPath = "config" + slash + FileName;
	
	/** The xml config. */
	private static String xmlConfig = "config" + slash;

	// JavaEEJPAWebCentric
	/** The web centric templates. */
	private static String webCentricTemplates = "generatorTemplates" + GeneratorUtil.slash + "zathura-JavaEE-Web-Centric" + GeneratorUtil.slash;
	
	/** The generator libraries zathura java ee web centric. */
	private static String generatorLibrariesZathuraJavaEEWebCentric = "generatorLibraries" + GeneratorUtil.slash;
	
	/** The generator ext zathura java ee web centric. */
	private static String generatorExtZathuraJavaEEWebCentric = "generatorExt" + GeneratorUtil.slash + "zathura-JavaEE-Web-Centric" + GeneratorUtil.slash;

	// JavaEE-HibernateCore-Spring-WebCentric
	/** The spring centric templates. */
	private static String springCentricTemplates = "generatorTemplates" + GeneratorUtil.slash + "zathura-JavaEE-hibernateCore-Spring-Centric"
			+ GeneratorUtil.slash;
	
	/** The generator libraries zathura java ee spring web centric. */
	private static String generatorLibrariesZathuraJavaEESpringWebCentric = "generatorLibraries" + GeneratorUtil.slash;
	
	/** The generator ext zathura java ee spring web centric. */
	private static String generatorExtZathuraJavaEESpringWebCentric = "generatorExt" + GeneratorUtil.slash + "zathura-JavaEE-hibernateCore-Spring-Centric"
			+ GeneratorUtil.slash;

	// JavaEE-HibernateCore-WebCentric
	/** The templates zathura java ee hibernate core web centric. */
	private static String templatesZathuraJavaEEHibernateCoreWebCentric = "generatorTemplates" + GeneratorUtil.slash
			+ "zathura-JavaEE-hibernateCore-Web-Centric" + GeneratorUtil.slash;
	
	/** The generator libraries zathura java ee hibernate core web centric. */
	private static String generatorLibrariesZathuraJavaEEHibernateCoreWebCentric = "generatorLibraries" + GeneratorUtil.slash;
	
	/** The generator ext zathura java ee hibernate core web centric. */
	private static String generatorExtZathuraJavaEEHibernateCoreWebCentric = "generatorExt" + GeneratorUtil.slash + "zathura-JavaEE-hibernateCore-Web-Centric"
			+ GeneratorUtil.slash;

	// JavaEEGwtCentric
	/** The gwt centric templates. */
	private static String gwtCentricTemplates = "generatorTemplates" + GeneratorUtil.slash + "zathura-JavaEE-GWT-Centric" + GeneratorUtil.slash;
	
	/** The generator libraries zathura java ee gwt centric. */
	private static String generatorLibrariesZathuraJavaEEGwtCentric = "generatorLibraries";
	
	/** The generator ext zathura java ee gwt centric. */
	private static String generatorExtZathuraJavaEEGwtCentric = "generatorExt" + GeneratorUtil.slash + "zathura-JavaEE-GWT-Centric" + GeneratorUtil.slash;

	// SP Store Procedure
	/** The store procedure templates. */
	private static String storeProcedureTemplates = "generatorTemplates" + GeneratorUtil.slash + "amazilia-storeProcedure" + GeneratorUtil.slash;

	// Java EE Jpa + Spring
	private static String springJpaTemplates="generatorTemplates" + GeneratorUtil.slash + "zathura-JavaEE-jpaCore-Spring-Centric" + GeneratorUtil.slash;
	private static String generatorExtZathuraJavaEESpringJpa = "generatorExt" + GeneratorUtil.slash + "zathura-JavaEE-jpaCore-Spring-Centric"+ GeneratorUtil.slash;
	private static String generatorLibrariesZathuraJavaEESpringJpa="generatorLibraries" + GeneratorUtil.slash;


	
	/**
	 * Gets the full path.
	 *
	 * @return the full path
	 */
	public static String getFullPath() {
		return fullPath;
	}

	/**
	 * Sets the full path.
	 *
	 * @param fullPath the full path
	 */
	public static void setFullPath(String fullPath) {
		// System.err.println(fullPath);
		if (fullPath != null && fullPath.startsWith("/") && System.getProperty("os.name").toUpperCase().contains("WINDOWS") == true) {
			fullPath = fullPath.substring(1, fullPath.length());
		}

		if (fullPath != null) {
			if (slash.equals("/")) {
				fullPath = replaceAll(fullPath, "\\", slash);
				if (fullPath.endsWith("\\") == true) {
					fullPath = fullPath.substring(0, fullPath.length() - 1);
					fullPath = fullPath + slash;
				}

			} else if (slash.equals("\\")) {
				fullPath = replaceAll(fullPath, "/", slash);
				if (fullPath.endsWith("/") == true) {
					fullPath = fullPath.substring(0, fullPath.length() - 1);
					fullPath = fullPath + slash;
				}
			}
		}
		// System.err.println(fullPath);

		GeneratorUtil.fullPath = fullPath;
	}

	/**
	 * Gets the xml config factory path.
	 *
	 * @return the xml config factory path
	 */
	public static String getXmlConfigFactoryPath() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + xmlConfigFactoryPath;
		}
		return xmlConfigFactoryPath;
	}

	/**
	 * Gets the xml config.
	 *
	 * @return the xml config
	 */
	public static String getXmlConfig() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + xmlConfig + slash;
		}
		return xmlConfigFactoryPath;
	}

	// JavaEEWebCentric
	/**
	 * Gets the generator libraries zathura java ee web centric.
	 *
	 * @return the generator libraries zathura java ee web centric
	 */
	public static String getGeneratorLibrariesZathuraJavaEEWebCentric() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + generatorLibrariesZathuraJavaEEWebCentric;
		}
		return generatorLibrariesZathuraJavaEEWebCentric;
	}

	/**
	 * Gets the generator ext zathura java ee web centric.
	 *
	 * @return the generator ext zathura java ee web centric
	 */
	public static String getGeneratorExtZathuraJavaEEWebCentric() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + generatorExtZathuraJavaEEWebCentric;
		}
		return generatorExtZathuraJavaEEWebCentric;
	}

	/**
	 * Gets the web centric templates.
	 *
	 * @return the web centric templates
	 */
	public static String getWebCentricTemplates() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + webCentricTemplates;
		}
		return webCentricTemplates;
	}

	// JavaEESpringWebCentric
	/**
	 * Gets the generator libraries zathura java ee spring web centric.
	 *
	 * @return the generator libraries zathura java ee spring web centric
	 */
	public static String getGeneratorLibrariesZathuraJavaEESpringWebCentric() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + generatorLibrariesZathuraJavaEESpringWebCentric;
		}
		return generatorLibrariesZathuraJavaEESpringWebCentric;
	}

	/**
	 * Gets the generator ext zathura java ee spring web centric.
	 *
	 * @return the generator ext zathura java ee spring web centric
	 */
	public static String getGeneratorExtZathuraJavaEESpringWebCentric() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + generatorExtZathuraJavaEESpringWebCentric;
		}
		return generatorExtZathuraJavaEESpringWebCentric;
	}

	/**
	 * Gets the spring web centric templates.
	 *
	 * @return the spring web centric templates
	 */
	public static String getSpringWebCentricTemplates() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + springCentricTemplates;
		}
		return springCentricTemplates;
	}

	// JavaEEWebCentric
	/**
	 * Gets the templates zathura java ee hibernate core web centric.
	 *
	 * @return the templates zathura java ee hibernate core web centric
	 */
	public static String getTemplatesZathuraJavaEEHibernateCoreWebCentric() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + templatesZathuraJavaEEHibernateCoreWebCentric;
		}
		return templatesZathuraJavaEEHibernateCoreWebCentric;
	}

	/**
	 * Gets the generator libraries zathura java ee hibernate core web centric.
	 *
	 * @return the generator libraries zathura java ee hibernate core web centric
	 */
	public static String getGeneratorLibrariesZathuraJavaEEHibernateCoreWebCentric() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + generatorLibrariesZathuraJavaEEHibernateCoreWebCentric;
		}
		return generatorLibrariesZathuraJavaEEHibernateCoreWebCentric;
	}

	/**
	 * Gets the generator ext zathura java ee hibernate core web centric.
	 *
	 * @return the generator ext zathura java ee hibernate core web centric
	 */
	public static String getGeneratorExtZathuraJavaEEHibernateCoreWebCentric() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + generatorExtZathuraJavaEEHibernateCoreWebCentric;
		}
		return generatorExtZathuraJavaEEHibernateCoreWebCentric;
	}

	// JavaEEGwtCentric
	/**
	 * Gets the generator libraries zathura java ee gwt centric.
	 *
	 * @return the generator libraries zathura java ee gwt centric
	 */
	public static String getGeneratorLibrariesZathuraJavaEEGwtCentric() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + generatorLibrariesZathuraJavaEEGwtCentric;
		}
		return generatorLibrariesZathuraJavaEEGwtCentric;
	}

	/**
	 * Gets the generator ext zathura java ee gwt centric.
	 *
	 * @return the generator ext zathura java ee gwt centric
	 */
	public static String getGeneratorExtZathuraJavaEEGwtCentric() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + generatorExtZathuraJavaEEGwtCentric;
		}
		return generatorExtZathuraJavaEEGwtCentric;
	}

	/**
	 * Gets the gwt centric templates.
	 *
	 * @return the gwt centric templates
	 */
	public static String getGwtCentricTemplates() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + gwtCentricTemplates;
		}
		return gwtCentricTemplates;
	}

	
	
	//Spring + Jpa


	public static String getSpringJpaTemplates() {
		if(fullPath != null && fullPath.equals("")!= true){
			return fullPath + springJpaTemplates;
		}

		return springJpaTemplates;
	}


	public static String getGeneratorExtZathuraJavaEESpringJpa() {
		if(fullPath != null && fullPath.equals("")!=true){
			return fullPath + generatorExtZathuraJavaEESpringJpa; 
		}

		return generatorExtZathuraJavaEESpringJpa;
	}


	public static String getGeneratorLibrariesZathuraJavaEESpringJpa() {

		if(fullPath!=null && fullPath.equals("")!=true){
			return fullPath + generatorLibrariesZathuraJavaEESpringJpa;
		}
		return generatorLibrariesZathuraJavaEESpringJpa;
	}


	
	
	
	/**
	 * Getstore procedure templates.
	 *
	 * @return the store procedure templates
	 */
	public static String getstoreProcedureTemplates() {
		if (fullPath != null && fullPath.equals("") != true) {
			return fullPath + storeProcedureTemplates;
		}
		return storeProcedureTemplates;
	}

	/**
	 * Creates the folder.
	 *
	 * @param path the path
	 * @return the file
	 */
	public static File createFolder(String path) {
		File aFile = new File(path);
		aFile.mkdirs();
		return aFile;
	}

	/**
	 * Copy.
	 *
	 * @param source the source
	 * @param Target the Target
	 */
	public static void copy(String source, String Target) {
		FileInputStream fIn = null;
		FileOutputStream fOut = null;
		byte[] b;
		int l = 0;
		try {
			fIn = new FileInputStream(source);
			fOut = new FileOutputStream(Target);
			b = new byte[1024];
			while ((l = fIn.read(b)) > 0) {
				fOut.write(b, 0, l);
			}
			fOut.close();
			fIn.close();
		} catch (FileNotFoundException fnfe) {
			log.error(fnfe.toString());
		} catch (IOException ioe) {
			log.error(ioe.toString());
		}
	}

	/**
	 * Delete files.
	 *
	 * @param path the path
	 */
	public static void deleteFiles(String path) {
		File file = new File(path);
		deleteFiles(file);
	}

	/**
	 * Delete files.
	 *
	 * @param file the file
	 */
	public static void deleteFiles(File file) {
		File fileAux = null;
		File listFiles[] = null;
		int iPos = -1;

		listFiles = file.listFiles();
		for (iPos = 0; iPos < listFiles.length; iPos++) {
			fileAux = listFiles[iPos];
			if (fileAux.isDirectory())
				deleteFiles(listFiles[iPos]);
			listFiles[iPos].delete();
		}
		if (file.listFiles().length == 0)
			file.delete();
	}

	/**
	 * Delete file.
	 *
	 * @param path the path
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		deleteFile(file);
	}

	/**
	 * Delete file.
	 *
	 * @param file the file
	 */
	public static void deleteFile(File file) {
		file.delete();
	}

	/**
	 * public static List<String> copyFolder(String source,String target){
	 * List<String> filesLib =new ArrayList<String>(); try { File dir = new
	 * File(source); String[] fileNames = dir.list(); for (int i = 0; i <
	 * fileNames.length; i++) { String s=fileNames[i]; copy(source+s,target+s);
	 * filesLib.add(s); } return filesLib; } catch (Exception e) { //TODO Poner
	 * log4j System.out.println("Error copy all files of folder:"+e.toString());
	 * } return null; }
	 *
	 * @param source the source
	 * @param target the target
	 */
	public static void copyFolder(String source, String target) {
		try {
			File dir = new File(source);
			File[] listFiles = dir.listFiles();
			File fileSource = null;
			for (int i = 0; i < listFiles.length; i++) {
				fileSource = listFiles[i];
				if (fileSource.isDirectory()) {
					log.debug(fileSource.getName());
					log.debug("Source:" + fileSource.getAbsolutePath());
					log.debug("Target:" + target + fileSource.getName());
					createFolder(target + fileSource.getName());
					copyFolder(fileSource.getAbsolutePath() + slash, target + fileSource.getName() + slash);
				} else {
					copy(fileSource.getAbsolutePath(), target + fileSource.getName());
					log.debug(fileSource);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * File fileAux = null; File listFiles[] = null; int iPos = -1;
	 * 
	 * listFiles = file.listFiles(); for(iPos = 0; iPos < listFiles.length;
	 * iPos++){ fileAux = listFiles[iPos]; if(fileAux.isDirectory())
	 * deleteFiles(listFiles[iPos]); listFiles[iPos].delete(); }
	 * if(file.listFiles().length == 0) file.delete();
	 *
	 * @param path the path
	 * @param pck the pck
	 * @return the string
	 */

	public static String createFolderOfPackage(String path, String pck) {
		try {
			path = path + replaceAll(pck, ".", File.separator);
			path = path + File.separator;
			File myDirectory = new File(path);
			myDirectory.mkdirs();
			return path;
		} catch (Exception e) {
			log.error("Fallo creacion de carpetas para los paquetes:" + e.toString());
		}
		return null;
	}

	/**
	 * Validate directory.
	 *
	 * @param packageName the package name
	 * @param location the location
	 * @return true, if validate directory
	 * @throws IOException the IO exception
	 */
	public static boolean validateDirectory(String packageName, String location) throws IOException {

		if (location == null || location.equals("") == true)
			return false;

		File dirComm = new File(location);

		if (dirComm.exists()) {
			String dirsToCreate[] = packageName.split("_");

			for (int i = 0; i < dirsToCreate.length; i++) {
				location = location + slash + dirsToCreate[i];
				dirComm = new File(location);

				if (!dirComm.exists()) {
					dirComm.mkdir();
				}
			}
		}
		return true;
	}

	/**
	 * Replace all.
	 *
	 * @param cadena the cadena
	 * @param old the old
	 * @param snew the snew
	 * @return the string
	 */
	public static String replaceAll(String cadena, String old, String snew) {
		StringBuffer replace = new StringBuffer();
		String aux;

		for (int i = 0; i < cadena.length(); i++) {
			if ((i + old.length()) < cadena.length()) {
				aux = cadena.substring(i, i + old.length());
				if (aux.equals(old)) {
					replace.append(snew);
					i += old.length() - 1;
				} else {
					replace.append(cadena.substring(i, i + 1));
				}
			} else
				replace.append(cadena.substring(i, i + 1));
		}
		return replace.toString();
	}

}