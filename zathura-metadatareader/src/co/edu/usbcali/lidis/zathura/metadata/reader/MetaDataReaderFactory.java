package co.edu.usbcali.lidis.zathura.metadata.reader;

import co.edu.usbcali.lidis.zathura.metadata.exceptions.MetaDataReaderNotFoundException;

public class MetaDataReaderFactory {

	public final static int JPAEntityLoaderEngine = 1;

	private MetaDataReaderFactory() {
	}

	public static IMetaDataReader createMetaDataReader(int metaDataReader)throws MetaDataReaderNotFoundException {

		switch (metaDataReader) {
			case 1: {
				return new co.edu.usbcali.lidis.zathura.metadata.engine.JPAEntityLoaderEngine();
			}
			default:{
				throw new MetaDataReaderNotFoundException();
			}
		}
	}

}