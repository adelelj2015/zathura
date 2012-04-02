package com.vortexbird.amazilia.plugin.sp.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.zathuracode.eclipse.plugin.generator.utilities.ConfigEclipsePluginPath;


import com.vortexbird.amazilia.plugin.sp.gui.WizardMainStoreProcedure;

// TODO: Auto-generated Javadoc
/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class AmaziliaStoreProcedureAction implements IWorkbenchWindowActionDelegate {
	
	/** The window. */
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public AmaziliaStoreProcedureAction() {
		ConfigEclipsePluginPath.getInstance();
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 *
	 * @param action the action
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		WizardMainStoreProcedure wizardMainStoreProcedure = new WizardMainStoreProcedure();
		WizardDialog dialog = new WizardDialog(window.getShell(), wizardMainStoreProcedure);
		dialog.create();
		dialog.open();
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 *
	 * @param action the action
	 * @param selection the selection
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 *
	 * @param window the window
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}