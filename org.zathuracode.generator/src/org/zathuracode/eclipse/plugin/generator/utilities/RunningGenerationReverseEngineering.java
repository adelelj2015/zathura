package org.zathuracode.eclipse.plugin.generator.utilities;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.zathuracode.reverse.utilities.ZathuraReverseEngineeringUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class RunningGenerationReverseEngineering.
 */
public class RunningGenerationReverseEngineering implements IRunnableWithProgress {
	
	

	/** The shell. */
	private org.eclipse.swt.widgets.Shell shell;
	
	/**
	 * RunningGeneration constructor.
	 *
	 * @param shell the shell
	 */
	public RunningGenerationReverseEngineering(Shell shell) {
		this.shell=shell;
	}

	/**
	 * Runs the long running operation.
	 *
	 * @param monitor the progress monitor
	 * @throws InvocationTargetException the invocation target exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		monitor.setTaskName("Generating Entity Artifacts");
		monitor.beginTask("Generation in progress...", IProgressMonitor.UNKNOWN);
		try {

			// Genera los entity originales
			EclipseGeneratorUtil.generateJPAReverseEngineering();

			// OJO
			// Se generan los temporales y de estos se lee la metaData siempre
			EclipseGeneratorUtil.generateJPAReverseEngineeringTMP();

			// Cierra todas la conexiones
			ZathuraReverseEngineeringUtil.closeAll();

			// copia los Drivers de la base de datos a la carpeta del proyecto
			EclipseGeneratorUtil.copyDriverJars();

			monitor.subTask("Refresh " + EclipseGeneratorUtil.projectName + " project...");
			EclipseGeneratorUtil.project.refreshLocal(IResource.DEPTH_INFINITE, monitor);

			monitor.subTask("Building " + EclipseGeneratorUtil.projectName + " project...");
			EclipseGeneratorUtil.project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
			
			//TODO Hacer el componentes para formatear el codigo con el componente de eclipse http://www.eclipsezone.com/eclipse/forums/t88960.html
			

		} catch (CoreException e) {
			monitor.setCanceled(true);
			e.printStackTrace();
			ZathuraGeneratorLog.logError(e);
			
			MessageDialog.openError(getShell(), "Error",e.getMessage());
			throw new InterruptedException(e.getMessage());
		}catch (Exception e) {
			monitor.setCanceled(true);
			e.printStackTrace();
			ZathuraGeneratorLog.logError(e);
			
			MessageDialog.openError(getShell(), "Error",e.getMessage());
			throw new InterruptedException(e.getMessage());
		}
		monitor.done();
		if (monitor.isCanceled()) {
			throw new InterruptedException("The generation was cancelled");
		}
	}

	/**
	 * Gets the shell.
	 *
	 * @return the shell
	 */
	public org.eclipse.swt.widgets.Shell getShell() {
		return shell;
	}

	/**
	 * Sets the shell.
	 *
	 * @param shell the shell
	 */
	public void setShell(org.eclipse.swt.widgets.Shell shell) {
		this.shell = shell;
	}
	
	
}
