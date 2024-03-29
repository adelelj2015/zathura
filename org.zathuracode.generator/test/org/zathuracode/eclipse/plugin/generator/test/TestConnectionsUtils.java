package org.zathuracode.eclipse.plugin.generator.test;

import java.util.HashMap;
import java.util.List;

import org.zathuracode.eclipse.plugin.generator.utilities.ConnectionModel;
import org.zathuracode.eclipse.plugin.generator.utilities.ConnectionsUtils;
import org.zathuracode.generator.utilities.GeneratorUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class TestConnectionsUtils.
 */
public class TestConnectionsUtils {

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {

		GeneratorUtil.setFullPath("D:\\Workspaces\\workspaceZathura\\org.zathuracode.eclipse.plugin.generator\\");

		List<String> names = ConnectionsUtils.getConnectionNames();
		for (String name : names) {
			System.out.println(name);
			// ConnectionModel
			// connectionModel=ConnectionsUtils.getTheZathuraConnectionModel(name);
			// System.out.println(connectionModel.getUrl());
		}

		saveConnection();
		HashMap<String, ConnectionModel> theZathuraConnections = ConnectionsUtils.getTheZathuraConnections();
		System.out.println(theZathuraConnections.size());
		removeConnection("as/400");

	}

	/**
	 * Save connection.
	 */
	private static void saveConnection() {
		// ConnectionModel connectionModel=new ConnectionModel("as/400",
		// "urlas400", "dgomez", "sadjasdk","com.vortexbird.Connection",
		// "d:\\");
		try {
			// ConnectionsUtils.saveConnectionModel(connectionModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes the connection.
	 *
	 * @param name the name
	 */
	private static void removeConnection(String name) {
		try {
			ConnectionsUtils.removeConnectionModel(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
