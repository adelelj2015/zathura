package org.zathuracode.generator.jee.jpa.primecentric.utils;

import java.util.HashMap;
import java.util.List;

import org.zathuracode.metadata.model.Member;
import org.zathuracode.metadata.model.MetaData;


// TODO: Auto-generated Javadoc
/**
 * Zathura Generator.
 *
 * @author Andr�s Mauricio C�rdenas P�rez (mauriciocardenasp@gmail.com)
 * @version 1.0
 */
public interface IStringBuilder {

	// finalParameter(List<MetaData> theMetaData, MetaData metaData) {
	/**
	 * Final param.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the string
	 */
	public String finalParam(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param variables.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the string
	 */
	public String finalParamVariables(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Gets the type andvariable for many to one properties.
	 *
	 * @param strClass the str class
	 * @param theMetaData the meta data
	 * @return the type andvariable for many to one properties
	 */
	public String[] getTypeAndvariableForManyToOneProperties(String strClass, List<MetaData> theMetaData);

	/**
	 * Final param variables as list.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the list< string>
	 */
	public List<String> finalParamVariablesAsList(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param variables as list2.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the list< string>
	 */
	public List<String> finalParamVariablesAsList2(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param variables dates as list2.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the list< string>
	 */
	public List<String> finalParamVariablesDatesAsList2(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param for variables data tables as list.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the list< string>
	 */
	public List<String> finalParamForVariablesDataTablesAsList(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param for view.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the string
	 */
	public String finalParamForView(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param for dto update.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the string
	 */
	public String finalParamForDtoUpdate(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param for dto update only variables.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the string
	 */
	public String finalParamForDtoUpdateOnlyVariables(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param for view variables in list.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the list< string>
	 */
	public List<String> finalParamForViewVariablesInList(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param for view for sets variables in list.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the list< string>
	 */
	public List<String> finalParamForViewForSetsVariablesInList(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param for dto for sets variables in list.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the list< string>
	 */
	public List<String> finalParamForDtoForSetsVariablesInList(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Final param for dto in view for sets variables in list.
	 *
	 * @param theMetaData the meta data
	 * @param metaData the meta data
	 * @return the list< string>
	 */
	public List<String> finalParamForDtoInViewForSetsVariablesInList(List<MetaData> theMetaData, MetaData metaData);

	/**
	 * Gets the variable for many to one properties.
	 *
	 * @param manyToOne the many to one
	 * @param theMetaData the meta data
	 * @return the variable for many to one properties
	 */
	public List<String> getVariableForManyToOneProperties(List<Member> manyToOne, List<MetaData> theMetaData);

	/**
	 * Gets the strings for many to one properties.
	 *
	 * @param manyToOne the many to one
	 * @param theMetaData the meta data
	 * @return the strings for many to one properties
	 */
	public List<String> getStringsForManyToOneProperties(List<Member> manyToOne, List<MetaData> theMetaData);
	
	
	/**
	 * get class member to dto Class  
	*/
	public  List<String> getPropertiesDto(List<MetaData> theMetaData , MetaData metaData);
	
	/**
	 * get properties to generate  the method getData 
	 * */
	public List<String>  dtoConvert2(List<MetaData> theMetaData, MetaData metaData);

}