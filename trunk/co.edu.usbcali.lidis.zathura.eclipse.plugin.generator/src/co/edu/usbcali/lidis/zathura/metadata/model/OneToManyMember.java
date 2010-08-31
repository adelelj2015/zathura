package co.edu.usbcali.lidis.zathura.metadata.model;

/**
 * 
 * @author Diego Armando Gomez Mosquera
 *
 */
public class OneToManyMember extends Member{

	private Class collectionType;
	private String mappedBy;
	
	public OneToManyMember(String name,String showName, Class type,Class collectionType,int order) {
		super(name,showName, type,order);
		this.collectionType=collectionType;
	}

	/**
	 * 
	 * @return
	 */
	public Class getCollectionType() {
		return collectionType;
	}

	/**
	 * 
	 * @param collectionType
	 */
	public void setCollectionType(Class collectionType) {
		this.collectionType = collectionType;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}
	

}