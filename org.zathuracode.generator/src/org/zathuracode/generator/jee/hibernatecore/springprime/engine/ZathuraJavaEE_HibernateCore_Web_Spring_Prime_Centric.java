package org.zathuracode.generator.jee.hibernatecore.springprime.engine;
/**
 * Zathura Generator
 * @author Andr�s Mauricio C�rdenas P�rez (mauriciocardenasp@gmail.com)
 * @version 1.0
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.zathuracode.eclipse.plugin.generator.utilities.EclipseGeneratorUtil;
import org.zathuracode.generator.jee.hibernatecore.springprime.utils.IStringBuilder;
import org.zathuracode.generator.jee.hibernatecore.springprime.utils.IStringBuilderForId;
import org.zathuracode.generator.jee.hibernatecore.springprime.utils.StringBuilder;
import org.zathuracode.generator.jee.hibernatecore.springprime.utils.StringBuilderForId;
import org.zathuracode.generator.jee.hibernatecore.springprime.utils.Utilities;
import org.zathuracode.generator.model.IZathuraGenerator;
import org.zathuracode.generator.utilities.GeneratorUtil;
import org.zathuracode.generator.utilities.JalopyCodeFormatter;
import org.zathuracode.metadata.model.MetaData;
import org.zathuracode.metadata.model.MetaDataModel;


public class ZathuraJavaEE_HibernateCore_Web_Spring_Prime_Centric implements IZathuraTemplate,IZathuraGenerator{
	private static Logger log;
	private static String pathTemplates;
	private Properties properties;
	private String virginPackageInHd;
	private VelocityEngine ve;
	private String webRootPath;
	
	static{
		log= Logger.getLogger(ZathuraJavaEE_HibernateCore_Web_Spring_Prime_Centric.class);
		pathTemplates= GeneratorUtil.getSpringHibernatePrime();
	}


	@Override
	public void toGenerate(MetaDataModel metaDataModel, String projectName,
			String folderProjectPath, Properties propiedades) throws Exception{

		String jpaPckgName = propiedades.getProperty("jpaPckgName");
		String domainName = jpaPckgName.substring(0, jpaPckgName.indexOf("."));
		Integer specificityLevel = (Integer) propiedades.get("specificityLevel");
		properties = propiedades;
		webRootPath=(propiedades.getProperty("webRootFolderPath"));
		
		log.info("Begin Zathura+Primefaces+Hibernate+Spring");
		doTemplate(folderProjectPath, metaDataModel, jpaPckgName, projectName, specificityLevel, domainName);
		copyLibraries();
		log.info("End Zathura+Primefaces+Hibernate+Spring");


	}
	
	public void copyLibraries(){
		String pathIndexJsp = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringHibernateCentric()+"index.jsp";
		String pathWebXml= GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringHibernateCentric()+"WEB-INF"+GeneratorUtil.slash;
		String generatorExtZathuraJavaEEWebSpringPrimeHibernateCentricImages = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringHibernateCentric() + GeneratorUtil.slash + "images"
		+ GeneratorUtil.slash;
		
		String pathHibernate= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimeSpringHibernate()+"core-hibernate3.3"+GeneratorUtil.slash;
		String pathPrimeFaces= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimeSpringHibernate()+"primeFaces3.2"+GeneratorUtil.slash;
		String pathSpring= GeneratorUtil.getGeneratorLibrariesZathuraJavaEEPrimeSpringHibernate()+"spring3.0"+GeneratorUtil.slash;
		String pathLib= properties.getProperty("libFolderPath");
		
		String pathCss = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringHibernateCentric() + GeneratorUtil.slash + "css"
		+ GeneratorUtil.slash;
		String log4j = GeneratorUtil.getGeneratorExtZathuraJavaEEPrimeSpringHibernateCentric()+ GeneratorUtil.slash + "log4j"
				+ GeneratorUtil.slash;
		
		// Copy Css
		GeneratorUtil.createFolder(webRootPath + "css");
		GeneratorUtil.copyFolder(pathCss, webRootPath + "css" + GeneratorUtil.slash);
		
		GeneratorUtil.copyFolder(pathWebXml,webRootPath+"WEB-INF"+GeneratorUtil.slash);
		//create folder images and insert .png
		GeneratorUtil.createFolder(webRootPath + "images");
		GeneratorUtil.copyFolder(generatorExtZathuraJavaEEWebSpringPrimeHibernateCentricImages, webRootPath + "images" + GeneratorUtil.slash);
		// create index.jsp
		GeneratorUtil.copy(pathIndexJsp,webRootPath+"index.jsp" );
		
		//valida si no es un proyecto maven, para iniciar la copia de librerias
		if (!EclipseGeneratorUtil.isMavenProject) {
			//copy libraries
			log.info("Copy libraries files Zathura+Primefaces+Hibernate+Spring");
			GeneratorUtil.copyFolder(pathHibernate, pathLib);
			GeneratorUtil.copyFolder(pathPrimeFaces, pathLib);
			GeneratorUtil.copyFolder(pathSpring, pathLib);
		}

		// copy to Log4j
		String folderProjectPath = properties.getProperty("folderProjectPath");
		GeneratorUtil.copyFolder(log4j, folderProjectPath + GeneratorUtil.slash);
		
	}

	@Override
	public void doTemplate(String hdLocation, MetaDataModel metaDataModel,
			String jpaPckgName, String projectName, Integer specificityLevel,
			String domainName) {

		try {

			ve = new VelocityEngine();
			Properties propiedades = new Properties();
			propiedades.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
			propiedades.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			propiedades.setProperty("file.resource.loader.path", pathTemplates);
			propiedades.setProperty("file.resource.loader.cache", "false");
			propiedades.setProperty("file.resource.loader.modificationCheckInterval", "2");
			ve.init(propiedades);


			VelocityContext context = new VelocityContext();
			MetaDataModel dataModel = metaDataModel;
			List<MetaData> list = dataModel.getTheMetaData();

			IStringBuilderForId stringBuilderForId = new StringBuilderForId(list);
			IStringBuilder stringBuilder = new StringBuilder(list, (StringBuilderForId) stringBuilderForId);
			String packageOriginal = null;
			String virginPackage = null;
			String modelName = null;

			if (specificityLevel.intValue() == 2) {
				try {
					int lastIndexOf = jpaPckgName.lastIndexOf(".");
					packageOriginal = jpaPckgName.substring(0, lastIndexOf);

					int lastIndexOf2 = packageOriginal.lastIndexOf(".") + 1;
					modelName = packageOriginal.substring(lastIndexOf2);

					int virginLastIndexOf = packageOriginal.lastIndexOf(".");
					virginPackage = packageOriginal.substring(0, virginLastIndexOf);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			} else {
				try {
					packageOriginal = jpaPckgName;

					int lastIndexOf2 = packageOriginal.lastIndexOf(".") + 1;
					modelName = jpaPckgName.substring(lastIndexOf2);

					int virginLastIndexOf = packageOriginal.lastIndexOf(".");
					virginPackage = packageOriginal.substring(0, virginLastIndexOf);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}

			String projectNameClass = (projectName.substring(0, 1)).toUpperCase() + projectName.substring(1, projectName.length());
			context.put("packageOriginal", packageOriginal);
			context.put("virginPackage", virginPackage);
			context.put("package", jpaPckgName);
			context.put("projectName", projectName);
			context.put("modelName", modelName);
			context.put("projectNameClass", projectNameClass);
			context.put("domainName", domainName);
			
			this.virginPackageInHd = GeneratorUtil.replaceAll(virginPackage, ".", GeneratorUtil.slash);
			Utilities.getInstance().buildFolders(virginPackage, hdLocation, specificityLevel, packageOriginal, properties);
			Utilities.getInstance().biuldHashToGetIdValuesLengths(list);

			for (MetaData metaData : list) {

				List<String> imports = Utilities.getInstance().getRelatedClasses(metaData, dataModel);

				if (imports != null) {
					if (imports.size() > 0 && !imports.isEmpty()) {
						context.put("isImports", true);
						context.put("imports", imports);
					} else {
						context.put("isImports", false);
					}
				} else {
					context.put("isImports", false);
				}

				// generacion de nuevos dto
				context.put("variableDto", stringBuilder.getPropertiesDto(list, metaData));
				context.put("propertiesDto",Utilities.getInstance().dtoProperties);
				context.put("memberDto",Utilities.getInstance().nameMemberToDto);
				// generacion de la nueva logica 
				context.put("dtoConvert", stringBuilderForId.dtoConvert(list,metaData));
				context.put("dtoConvert2", stringBuilder.dtoConvert2(list, metaData));

				context.put("finalParamForView", stringBuilder.finalParamForView(list, metaData));
				context.put("finalParamForDtoUpdate", stringBuilder.finalParamForDtoUpdate(list, metaData));
				context.put("finalParamForDtoUpdateOnlyVariables", stringBuilder.finalParamForDtoUpdateOnlyVariables(list, metaData));
				context.put("finalParamForViewVariablesInList", stringBuilder.finalParamForViewVariablesInList(list, metaData));
				context.put("isFinalParamForViewDatesInList", Utilities.getInstance().isFinalParamForViewDatesInList());
				context.put("finalParamForViewDatesInList", Utilities.getInstance().dates);
				context.put("finalParamForIdForViewVariablesInList", stringBuilderForId.finalParamForIdForViewVariablesInList(list, metaData));
				context.put("isFinalParamForIdForViewDatesInList", Utilities.getInstance().isFinalParamForIdForViewDatesInList());
				context.put("finalParamForIdForViewDatesInList", Utilities.getInstance().datesId);
				context.put("finalParamForIdForViewClass", stringBuilderForId.finalParamForIdForViewClass(list, metaData));
				context.put("finalParamForIdClassAsVariablesAsString", stringBuilderForId.finalParamForIdClassAsVariablesAsString(list, metaData));
				context.put("finalParamForViewForSetsVariablesInList", stringBuilder.finalParamForViewForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdForDtoForSetsVariablesInList", stringBuilderForId.finalParamForIdForDtoForSetsVariablesInList(list, metaData));
				context.put("finalParamForDtoForSetsVariablesInList", stringBuilder.finalParamForDtoForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdForDtoInViewForSetsVariablesInList", stringBuilderForId.finalParamForIdForDtoInViewForSetsVariablesInList(list,
						metaData));
				context.put("finalParamForDtoInViewForSetsVariablesInList", stringBuilder.finalParamForDtoInViewForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdForViewForSetsVariablesInList", stringBuilderForId.finalParamForIdForViewForSetsVariablesInList(list, metaData));
				context.put("finalParamForIdVariablesAsList", stringBuilderForId.finalParamForIdVariablesAsList(list, metaData));

				String finalParam = stringBuilder.finalParam(list, metaData);
				context.put("finalParam", finalParam);
				metaData.setFinamParam(finalParam);

				String finalParamVariables = stringBuilder.finalParamVariables(list, metaData);
				context.put("finalParamVariables", finalParamVariables);
				metaData.setFinamParamVariables(finalParamVariables);

				String finalParamForId = stringBuilderForId.finalParamForId(list, metaData);
				context.put("finalParamForId", stringBuilderForId.finalParamForId(list, metaData));
				metaData.setFinalParamForId(finalParamForId);

				String finalParamForIdVariables = stringBuilderForId.finalParamForIdVariables(list, metaData);
				context.put("finalParamForIdVariables", stringBuilderForId.finalParamForIdVariables(list, metaData));
				metaData.setFinalParamForIdVariables(finalParamForIdVariables);

				context.put("finalParamVariablesAsList", stringBuilder.finalParamVariablesAsList(list, metaData));
				context.put("finalParamVariablesAsList2", stringBuilder.finalParamVariablesAsList2(list, metaData));
				context.put("finalParamVariablesDatesAsList2", stringBuilder.finalParamVariablesDatesAsList2(list, metaData));
				context.put("isFinalParamDatesAsList", Utilities.getInstance().isFinalParamDatesAsList());
				context.put("finalParamDatesAsList", Utilities.getInstance().datesJSP);

				context.put("finalParamForIdClassAsVariables", stringBuilderForId.finalParamForIdClassAsVariables(list, metaData));
				context.put("finalParamForIdClassAsVariables2", stringBuilderForId.finalParamForIdClassAsVariables2(list, metaData));
				context.put("isFinalParamForIdClassAsVariablesForDates", Utilities.getInstance().isFinalParamForIdClassAsVariablesForDates());
				context.put("finalParamForIdClassAsVariablesForDates", Utilities.getInstance().datesIdJSP);

				context.put("finalParamForVariablesDataTablesAsList", stringBuilder.finalParamForVariablesDataTablesAsList(list, metaData));
				context.put("finalParamForVariablesDataTablesForIdAsList", stringBuilderForId.finalParamForVariablesDataTablesForIdAsList(list, metaData));

				if (metaData.isGetManyToOneProperties()) {
					context.put("getVariableForManyToOneProperties", stringBuilder.getVariableForManyToOneProperties(metaData.getManyToOneProperties(), list));
					context.put("getStringsForManyToOneProperties", stringBuilder.getStringsForManyToOneProperties(metaData.getManyToOneProperties(), list));
				}

				context.put("composedKey", false);

				if (metaData.getPrimaryKey().isPrimiaryKeyAComposeKey()) {
					context.put("composedKey", true);
					context.put("finalParamForIdClass", stringBuilderForId.finalParamForIdClass(list, metaData));
				}
				context.put("metaData", metaData);
				context.put("dataModel", dataModel);


				doDaoSpringXMLHibernate(metaData, context, hdLocation);
				doBackingBeans(metaData, context, hdLocation, dataModel);
				doJsp(metaData, context, hdLocation, dataModel);
				doLogicSpringXMLHibernate(metaData, context, hdLocation, dataModel, modelName);
				doDto(metaData, context, hdLocation, dataModel, modelName);
				

			}

			if (EclipseGeneratorUtil.isMavenProject) {
				GeneratorUtil.doPomXml(context, ve);
			}
	
			doUtilites(context, hdLocation, dataModel, modelName);
			doExceptions(context, hdLocation);
			doBusinessDelegator(context, hdLocation, dataModel);
			doJspInitialMenu(dataModel, context, hdLocation);
			doFacesConfig(dataModel, context, hdLocation);
			doJspFacelets(context, hdLocation);
			doSpringContextConfFiles(context, hdLocation, dataModel, modelName);
	

		} catch (Exception e) {
			log.error(e.getMessage());
		}


	}


	@Override
	public void doBusinessDelegator(VelocityContext context, String hdLocation,
			MetaDataModel dataModel) {
		try {
			String path = hdLocation + virginPackageInHd + GeneratorUtil.slash + "presentation"+ GeneratorUtil.slash + "businessDelegate" +GeneratorUtil.slash;
			
			log.info("Begin IBusinesDelegate ");
			Template templateIBusinessDelegate = ve.getTemplate("IBusinessDelegatorView.vm");
			StringWriter swIBusinessDelegate = new StringWriter();
			templateIBusinessDelegate.merge(context, swIBusinessDelegate);
			
			FileWriter fwIBusinessDelegate = new FileWriter(path+"IBusinessDelegatorView.java");
			BufferedWriter bwIBusinessDelegate = new  BufferedWriter(fwIBusinessDelegate);
			bwIBusinessDelegate.write(swIBusinessDelegate.toString());
			bwIBusinessDelegate.close();
			fwIBusinessDelegate.close();
			log.info("End IBusinesDelegate ");
			
			log.info("Begin BusinesDelegate ");
			Template templateBusinessDelegate = ve.getTemplate("BusinessDelegatorView.vm");
			StringWriter swBusinessDelegate = new StringWriter();
			templateBusinessDelegate.merge(context, swBusinessDelegate);
			FileWriter fwBusinessDelegate = new FileWriter(path + "BusinessDelegatorView.java");
			BufferedWriter bwBusinessDelegate = new BufferedWriter(fwBusinessDelegate);
			bwBusinessDelegate.write(swBusinessDelegate.toString());
			bwBusinessDelegate.close();
			fwBusinessDelegate.close();
			
			JalopyCodeFormatter.formatJavaCodeFile(path + "IBusinessDelegatorView.java");
			JalopyCodeFormatter.formatJavaCodeFile(path + "BusinessDelegatorView.java");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doDaoSpringXMLHibernate(MetaData metaData,
			VelocityContext context, String hdLocation) {

		try {

			String path=hdLocation + virginPackageInHd + GeneratorUtil.slash + "dataaccess" + GeneratorUtil.slash + "dao"+ GeneratorUtil.slash;

			log.info("Begin Idao Spring+PrimeFaces+Hibernate");
			Template idaoSpringPrimeTemplate = ve.getTemplate("IDAOSpringHibernatePrime.vm");
			StringWriter swIdaoPrime = new StringWriter();
			idaoSpringPrimeTemplate.merge(context, swIdaoPrime);
			FileWriter fwIdao = new FileWriter(path+ "I" +metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwIdao = new BufferedWriter(fwIdao);
			bwIdao.write(swIdaoPrime.toString());
			bwIdao.close();
			fwIdao.close();
			log.info("End Idao Spring+PrimeFaces+Hibernate");

			log.info("Begin Dao Spring+PrimeFaces+Hibernate");
			Template daoSpringPrime = ve.getTemplate("DAOSpringHibernatePrime.vm");
			StringWriter swDao = new StringWriter();
			daoSpringPrime.merge(context, swDao);
			FileWriter fwDao = new FileWriter(path+ metaData.getRealClassName()+"DAO.java");
			BufferedWriter bwDao = new BufferedWriter(fwDao);
			bwDao.write(swDao.toString());
			bwDao.close();
			fwDao.close();
			log.info("End Dao Spring+PrimeFaces+Hibernate");
			
			JalopyCodeFormatter.formatJavaCodeFile(path + "I" + metaData.getRealClassName() + "DAO.java");
			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "DAO.java");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doDto(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {

		try {

			Template dtoTemplate = ve.getTemplate("DtoSpringHibernatePrime.vm");
			StringWriter swDto = new StringWriter();
			dtoTemplate.merge(context, swDto);

			String path=hdLocation + virginPackageInHd + GeneratorUtil.slash + modelName + GeneratorUtil.slash + "dto"+ GeneratorUtil.slash;
			FileWriter fwDto = new FileWriter(path+metaData.getRealClassName()+"DTO.java");
			BufferedWriter bwDto = new BufferedWriter(fwDto);
			bwDto.write(swDto.toString());
			bwDto.close();
			swDto.close();
			JalopyCodeFormatter.formatJavaCodeFile(path+metaData.getRealClassName()+"DTO.java");

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doExceptions(VelocityContext context, String hdLocation) {
		try {
			String path = hdLocation + virginPackageInHd + GeneratorUtil.slash + "exceptions" + GeneratorUtil.slash;
			log.info("Begin ZMessManager");
			Template templateZmessManager = ve.getTemplate("ZMessManager.vm");
			StringWriter swZmessManager = new StringWriter();
			templateZmessManager.merge(context, swZmessManager);
			
			FileWriter fwZmessManager = new FileWriter(path+ "ZMessManager.java");
			BufferedWriter bwZmessManager = new BufferedWriter(fwZmessManager);
			bwZmessManager.write(swZmessManager.toString());
			bwZmessManager.close();
			fwZmessManager.close();
			log.info("Begin ZMessManager");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doFacesConfig(MetaDataModel dataModel, VelocityContext context,
			String hdLocation) {
		try {
			
			String path =properties.getProperty("webRootFolderPath")+"WEB-INF"+ GeneratorUtil.slash;
			
			log.info("Begin FacesConfig");
			Template templateFacesConfig = ve.getTemplate("faces-configSpringPrimeHibernate.xml.vm");
			StringWriter swFacesConfig = new StringWriter();
			templateFacesConfig.merge(context, swFacesConfig);
			FileWriter fwFacesConfig = new FileWriter(path+"faces-config.xml");
			BufferedWriter bwFacesConfig = new BufferedWriter(fwFacesConfig);
			bwFacesConfig.write(swFacesConfig.toString());
			bwFacesConfig.close();
			fwFacesConfig.close();
			log.info("End FacesConfig");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doJsp(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {
		try {
			
			String path=properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			log.info("Begin Crud XHTML");
			Template templateXhtml = ve.getTemplate("XHTMLSpringHibernatePrime.vm");
			StringWriter swXhtml= new StringWriter();
			templateXhtml.merge(context, swXhtml);
			FileWriter fwXhtml = new FileWriter(path+metaData.getRealClassNameAsVariable()+".xhtml");
			BufferedWriter bwXhtml = new BufferedWriter(fwXhtml);
			bwXhtml.write(swXhtml.toString());
			bwXhtml.close();
			fwXhtml.close();
			log.info("End Crud XHTML");
			
			log.info("Begin DataTable XHTML");
			Template templateDataTable = ve.getTemplate("XHTMLdataTablesHibernatePrime.vm");
			StringWriter swDataTable = new StringWriter();
			templateDataTable.merge(context, swDataTable);
			FileWriter fwDataTable = new FileWriter(path+metaData.getRealClassNameAsVariable()+"ListDataTable.xhtml");
			BufferedWriter bwDataTable = new BufferedWriter(fwDataTable);
			bwDataTable.write(swDataTable.toString());
			bwDataTable.close();
			fwDataTable.close();
			Utilities.getInstance().datesJSP = null;
			Utilities.getInstance().datesIdJSP = null;
			log.info("End DataTable XHTML");
			
			log.info("Begin DataTableEditable XHTML");
			Template templateDataTableEditable = ve.getTemplate("XHTMLdataTableEditablePrime.vm");
			StringWriter swDataTableEditable = new StringWriter();
			templateDataTableEditable.merge(context, swDataTableEditable);
			FileWriter fwDataTableEditable = new FileWriter(path+ metaData.getRealClassNameAsVariable()+"ListDataTableEditable.xhtml");
			BufferedWriter bwDataTableEditable = new BufferedWriter(fwDataTableEditable);
			bwDataTableEditable.write(swDataTableEditable.toString());
			bwDataTableEditable.close();
			fwDataTableEditable.close();
			log.info("Begin DataTableEditable XHTML");
			
			Utilities.getInstance().datesJSP = null;
			Utilities.getInstance().datesIdJSP = null;
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doJspFacelets(VelocityContext context, String hdLocation) {
		try {
			log.info("Begin Header");
			String pathFacelets = properties.getProperty("webRootFolderPath") + "WEB-INF" + GeneratorUtil.slash + "facelets" + GeneratorUtil.slash;
			Template templateHeader = ve.getTemplate("JSPheader.vm");
			StringWriter swHeader = new StringWriter();
			templateHeader.merge(context, swHeader);
			FileWriter fwHeader = new FileWriter(pathFacelets+"header.jspx");
			BufferedWriter bwHeader = new BufferedWriter(fwHeader);
			bwHeader.write(swHeader.toString());
			bwHeader.close();
			fwHeader.close();
			log.info("End Header");
			
			log.info("Begin Footer");
			Template templateFooter = ve.getTemplate("JSPfooter.vm");
			StringWriter swFooter = new StringWriter();
			templateFooter.merge(context, swFooter);
			FileWriter fwFooter = new FileWriter(pathFacelets+"footer.jspx");
			BufferedWriter bwFooter = new BufferedWriter(fwFooter);
			bwFooter.write(swFooter.toString());
			bwFooter.close();
			fwFooter.close();
			log.info("End Footer");
			
			log.info("Begin Footer InitialMenu");
			Template footerInitialMenu = ve.getTemplate("footerInitialMenu.vm");
			StringWriter swFooterInitialMenu = new StringWriter();
			footerInitialMenu.merge(context, swFooterInitialMenu);
			FileWriter fwFooterInitialMenu = new FileWriter(pathFacelets+"footerInitialMenu.jspx");
			BufferedWriter bwFooterInitialMenu = new BufferedWriter(fwFooterInitialMenu);
			bwFooterInitialMenu.write(swFooterInitialMenu.toString());
			bwFooterInitialMenu.close();
			fwFooterInitialMenu.close();
			log.info("End Footer InitialMenu");
			
			String pathCommon= properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			log.info("Begin CommonColumnsContens");
			Template templateCommonsColumns = ve.getTemplate("CommonColumnsContent.vm");
			StringWriter swCommonColumns = new StringWriter();
			templateCommonsColumns.merge(context, swCommonColumns);
			FileWriter fwCommonColumns = new FileWriter(pathCommon+"CommonColumnsContent.xhtml");
			BufferedWriter bwCommonColumns = new BufferedWriter(fwCommonColumns);
			bwCommonColumns.write(swCommonColumns.toString());
			bwCommonColumns.close();
			swCommonColumns.close();
			log.info("End CommonColumnsContens");
			
			log.info("Begin CommonLayout");
			Template templateCommonLayout = ve.getTemplate("CommonLayout.vm");
			StringWriter swCommonLayout = new StringWriter();
			templateCommonLayout.merge(context, swCommonLayout);
			FileWriter fwCommonLayout = new FileWriter(pathCommon+"CommonLayout.xhtml");
			BufferedWriter bwCommonLayout = new BufferedWriter(fwCommonLayout);
			bwCommonLayout.write(swCommonLayout.toString());
			bwCommonLayout.close();
			fwCommonLayout.close();
			log.info("End CommonLayout");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doJspInitialMenu(MetaDataModel dataModel,
			VelocityContext context, String hdLocation) {
		try {
			String path=properties.getProperty("webRootFolderPath") + "XHTML" + GeneratorUtil.slash;
			log.info("Begin Initial  XHTML");
			Template templateInitialMenu = ve.getTemplate("XHTMLinitialMenu.vm");
			StringWriter swInitialMenu = new StringWriter();
			templateInitialMenu.merge(context, swInitialMenu);
			FileWriter fwInitialMenu = new FileWriter(path+"initialMenu.xhtml");
			BufferedWriter bwInitialMenu = new BufferedWriter(fwInitialMenu);
			bwInitialMenu.write(swInitialMenu.toString());
			bwInitialMenu.close();
			fwInitialMenu.close();
			log.info("End Initial  XHTML");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doLogicSpringXMLHibernate(MetaData metaData,
			VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) {
		try {
			String path=hdLocation + virginPackageInHd + GeneratorUtil.slash + modelName + GeneratorUtil.slash +"control" + GeneratorUtil.slash;
			
			log.info("Begin Ilogic PrimeFaces+Spring+Hibernate");
			Template iLogicPrimeFaces = ve.getTemplate("ILogicSpringHibernatePrimeFaces.vm");
			StringWriter swIlogic = new StringWriter();
			iLogicPrimeFaces.merge(context, swIlogic);
			FileWriter fwIlogic = new FileWriter(path+"I"+ metaData.getRealClassName()+"Logic.java");
			BufferedWriter bwIlogic = new BufferedWriter(fwIlogic);
			bwIlogic.write(swIlogic.toString());
			bwIlogic.close();
			fwIlogic.close();
			log.info("End Ilogic PrimeFaces+Spring+Hibernate");
			
			log.info("Begin Logic PrimeFaces+Spring+Hibernate");
			Template LogicTemplate = ve.getTemplate("LogicSpringHibernatePrimeFaces.vm");
			StringWriter swLogic = new StringWriter();
			LogicTemplate.merge(context, swLogic);
			
			FileWriter fwLogic = new FileWriter(path+ metaData.getRealClassName() + "Logic.java");
			BufferedWriter bwLogic = new BufferedWriter(fwLogic);
			bwLogic.write(swLogic.toString());
			bwLogic.close();
			fwLogic.close();
			log.info("End Logic PrimeFaces+Spring+Hibernate");
			
			JalopyCodeFormatter.formatJavaCodeFile(path+"I" + metaData.getRealClassName() + "Logic.java");
			JalopyCodeFormatter.formatJavaCodeFile(path+metaData.getRealClassName() + "Logic.java");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public void doSpringContextConfFiles(VelocityContext context,
			String hdLocation, MetaDataModel dataModel, String modelName) {
		try {
			
			log.info("Begin ApplicationContext.xml");
			Template applicationContext = ve.getTemplate("applicationContext.xml.vm");
			StringWriter swApplicationContext = new StringWriter();
			applicationContext.merge(context, swApplicationContext);
			FileWriter fwApplicationContext = new  FileWriter(hdLocation+"applicationContext.xml");
			BufferedWriter bwApplicationContext = new BufferedWriter(fwApplicationContext);
			bwApplicationContext.write(swApplicationContext.toString());
			bwApplicationContext.close();
			fwApplicationContext.close();
			log.info("End ApplicationContext.xml");
			
			log.info("Begin aopContext.xml");
			Template aopContext= ve.getTemplate("aopContext.xml.vm");
			StringWriter swAopContext = new StringWriter();
			aopContext.merge(context, swAopContext);
			FileWriter fwAopContext = new FileWriter(hdLocation+"aopContext.xml");
			BufferedWriter bwAopContext = new BufferedWriter(fwAopContext);
			bwAopContext.write(swAopContext.toString());
			bwAopContext.close();
			fwAopContext.close();
			log.info("End aopContext.xml");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}



	@Override
	public void doUtilites(VelocityContext context, String hdLocation,
			MetaDataModel dataModel, String modelName) {
		try {
			String path =hdLocation+virginPackageInHd+GeneratorUtil.slash+"utilities"+GeneratorUtil.slash;
			
			log.info("Begin Utilities Spring+Hibernate+PrimeFaces");
			Template utilitiesTemplate = ve.getTemplate("Utilities.vm");
			StringWriter swUtilities = new StringWriter();
			utilitiesTemplate.merge(context, swUtilities);
			FileWriter fwUtilities = new FileWriter(path+"Utilities.java");
			BufferedWriter bwUtilities = new BufferedWriter(fwUtilities);
			bwUtilities.write(swUtilities.toString());
			bwUtilities.close();
			fwUtilities.close();
			log.info("End Utilities Spring+Hibernate+PrimeFaces");
			
			log.info("Begin FacesUtils Spring+Hibernate+PrimeFaces");
			Template templateFacesUtils = ve.getTemplate("FacesUtils.vm");
			StringWriter swFacesUtils = new StringWriter();
			templateFacesUtils.merge(context, swFacesUtils);
			FileWriter fwFacesUtils = new FileWriter(path+"FacesUtils.java");
			BufferedWriter bfFacesUtils = new BufferedWriter(fwFacesUtils);
			bfFacesUtils.write(swFacesUtils.toString());
			bfFacesUtils.close();
			fwFacesUtils.close();
			log.info("End FacesUtils Spring+Hibernate+PrimeFaces");
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}


	@Override
	public void doBackingBeans(MetaData metaData, VelocityContext context,
			String hdLocation, MetaDataModel dataModel) {
		try {
			
			String path =hdLocation + virginPackageInHd + GeneratorUtil.slash + "presentation" + GeneratorUtil.slash + "backingBeans" + GeneratorUtil.slash;
			
			log.info("Begin BackEndBean");
			Template templateBakcEndBean= ve.getTemplate("BackingBeansSpringHibernatePrime.vm");
			StringWriter swBackEndBean = new StringWriter();
			templateBakcEndBean.merge(context, swBackEndBean);
			
			FileWriter fwBackEndBean = new FileWriter(path+ metaData.getRealClassName() + "View.java");
			BufferedWriter bwBackEndBean = new BufferedWriter(fwBackEndBean);
			bwBackEndBean.write(swBackEndBean.toString());
			bwBackEndBean.close();
			fwBackEndBean.close();
			log.info("Begin BackEndBean");
			JalopyCodeFormatter.formatJavaCodeFile(path + metaData.getRealClassName() + "View.java");
			Utilities.getInstance().dates = null;
			Utilities.getInstance().datesId = null;
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
	
	/* (non-Javadoc)
	 * @see org.zathuracode.generator.jee.hibernatecore.springprime.engine.IZathuraTemplate#doFacesConfig(org.apache.velocity.VelocityContext, java.lang.String)
	 */
	/*public void doPomXml(VelocityContext context){
		log.info("Begin doPomXml");
		
		Template pomTemplate = null;
		StringWriter swPom = new StringWriter();
		
		try {
			pomTemplate = ve.getTemplate("pom.xml.vm");
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			
			pomTemplate.merge(context, swPom);
			
			String pomLocation = EclipseGeneratorUtil.fullPathProject + GeneratorUtil.slash + GeneratorUtil.pomFile;
			FileWriter fstream = new FileWriter(pomLocation);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(swPom.toString());
			// Close the output stream
			out.close();
			
			JalopyCodeFormatter.formatJavaCodeFile(pomLocation);

			log.info("End doPomXml");
			
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
		}
		
	}*/

}
