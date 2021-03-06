/*
 * $Id$
 *
 * JSDAI(TM), a way to implement STEP, ISO 10303
 * Copyright (C) 1997-2008, LKSoftWare GmbH, Germany
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License
 * version 3 as published by the Free Software Foundation (AGPL v3).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * JSDAI is a registered trademark of LKSoftWare GmbH, Germany
 * This software is also available under commercial licenses.
 * See also http://www.jsdai.net/
 */

package jsdai.express_doc.wizards;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import jsdai.common.CommonPlugin;
import jsdai.common.utils.IsolatedRunnableThread;
import jsdai.express_doc.ExpressDocPlugin;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiSession;
import jsdai.runtime.RuntimePlugin;
import jsdai.tools.ExpressDoc;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;

import jsdai.common.utils.*;

/**
 * @author Mantas Balnys
 * 
 */
public class ExportDocumentation extends Wizard implements IExportWizard {
	private IStructuredSelection selection = null;
//	private IWorkbench workbench = null;
	
	private ExpressDocSettingPage expressDocSettingPage = null;

 	static IProject fProject = null;
	static IResource fDictionaryResource = null;
	
	/**
	 * 
	 */
	public ExportDocumentation() {
		super();
		/* - we no longer need this - RR (see bug #4149)
		IDialogSettings workbenchSettings = ExpressDocPlugin.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection("RepositoryFileWizard");//$NON-NLS-1$
		if(section == null)
			section = workbenchSettings.addNewSection("RepositoryFileWizard");//$NON-NLS-1$
		setDialogSettings(section);
    */	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		super.addPages();

//		expressDocSettingPage = new ExpressDocSettingPage(ResourcesPlugin.getWorkspace().getRoot().getLocation(), "expressDocExport");
		expressDocSettingPage = new ExpressDocSettingPage(ResourcesPlugin.getWorkspace().getRoot().getLocation(), "expressDocExport", fProject);
		expressDocSettingPage.setTitle("Create Express Data documentation");
//			page_from.setDescription("Select Express Data file to export its contents to html documentation.");
		addPage(expressDocSettingPage);

//			page_to.setDescription("Select directory to export documentation.");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		boolean ok = false;
		Runnable op =
			new Runnable(expressDocSettingPage.getDestinationDirectory(),
					expressDocSettingPage.getSourceFileName(), expressDocSettingPage.isGenerateJavaDocPart(),expressDocSettingPage.isEnableIncremental(),expressDocSettingPage.getDocumentTitle());
//					expressDocSettingPage.getSourceFileName(), expressDocSettingPage.isGenerateJavaDocPart(),expressDocSettingPage.isEnableIncremental());
//					expressDocSettingPage.getSourceFileName(), expressDocSettingPage.isGenerateJavaDocPart());
		try {
			getContainer().run(true, false, op);
			ok = op.isOk();
			try {
				IPath path = new Path(expressDocSettingPage.getDestinationDirectory());
				IResource file = ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(path);
				if (file != null) {
					file.refreshLocal(IResource.DEPTH_INFINITE, null);
				} // else - destination location is not in eclipse workspace, therefore no refreshing can be done
			} catch (Throwable t) {
//				t.printStackTrace();
//					System.out.println("problems with ExpressDoc 1, see log: " + t);
					ExpressDocPlugin.log("problems with ExpressDoc export 1: " + t,1);				
					ExpressDocPlugin.log(t);				
			}
		} catch (InterruptedException e) {
//System.err.println("Export to html CANCELED: " + e);
			MessageDialog.openError(getShell(), "Export Documentation Error", "Interupted by user");
//					System.out.println("problems with ExpressDoc export 2, see log: " + e);
					// e2.printStackTrace();
					ExpressDocPlugin.log("ExpressDoc interrupted by user 2",1);				
					//ExpressDocPlugin.log(e);				
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
System.err.println("ExportToHtmlAction excption: " + e);
System.err.println("ExportToHtmlAction real excption: " + realException);
			String message = "Exporting failed, " + e.getLocalizedMessage();
			if (realException != null) {
				message = "Exporting failed, " + realException.getLocalizedMessage();
				if (realException instanceof SdaiException && 
						((SdaiException)realException).getErrorId() == SdaiException.RP_DUP) {
					message = "Exporting failed, file is opened. Close " + expressDocSettingPage.getSourceFileName() + " file and try again.";
				}
			}
			MessageDialog.openError(getShell(), "Export Documentation Error", message);
			System.out.println("problems with ExpressDoc 4, see log: " + e);
					// e2.printStackTrace();
			ExpressDocPlugin.log("problems with ExpressDoc export 4: " + e,1);				
			ExpressDocPlugin.log(e);				
		}
		expressDocSettingPage.saveWidgetValues();
		expressDocSettingPage.setDefaultDocumentTitle();
		return ok;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	public boolean canFinish() {
		return expressDocSettingPage != null && expressDocSettingPage.determinePageCompletion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
//		this.workbench = workbench;
		setWindowTitle("Export");
		setNeedsProgressMonitor(true);
	}
	
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);

		String dictionaryFileName = null;
//		System.out.println("createPageControls - selection: " + selection);
		if(selection != null) {
			IProject project = null;
			List selResources = IDE.computeSelectedResources(selection);
			for(Iterator i = selResources.iterator(); i.hasNext();) {
				IResource resource = (IResource) i.next();
//				System.out.println("createPageControls - resource: " + resource);
				if (fProject == null) {
					// not sure why setting project is after continue - not reached
//					System.out.println("<I> attemptingto set fProject: " + resource);
					fProject = resource.getProject();
				}
				if (resource instanceof IFile) {
					IFile file = (IFile) resource;
//					System.out.println("createPageControls - file: " + file);
					String fext = file.getFileExtension();
					if (fext.equalsIgnoreCase("exd") || fext.equalsIgnoreCase("exg") || 
							fext.equalsIgnoreCase("sdai")) {
						if(dictionaryFileName == null) {
							dictionaryFileName = file.getLocation().toOSString();
							fDictionaryResource = resource;
						}
						continue;
					}
				}
				if(project == null) {
					project = resource.getProject();
				}
//				System.out.println("<O> attemptingto set fProject: " + resource + ", project: " + resource.getProject());
			}
			if(dictionaryFileName == null && project != null) {
				dictionaryFileName = project.getLocation().toOSString();
			}
		}
		if(dictionaryFileName != null) {
			expressDocSettingPage.setSourceFileName(dictionaryFileName);
			expressDocSettingPage.setDestinationDirectory(dictionaryFileName);
		}
		if (fDictionaryResource == null) {
			fDictionaryResource = fProject;
		}
		if (fDictionaryResource != null) {
			expressDocSettingPage.setDocumentTitle(fDictionaryResource);
		} else {
			expressDocSettingPage.setDefaultDocumentTitle();
		}
	}

	private static class Runnable implements IRunnableWithProgress {
		private boolean ok = false;
		
		private String repo_location = null;
		private String html_destination_path = null;
		private String flag_generate_java = null;
		private String flag_enable_incremental = null;

		private boolean generateJavaDocPart;
		private boolean enableIncremental;		
		private String documentTitle = null;
		/**
		 * @param html_destination_path
		 * @param repo_location
		 */
//		public Runnable(String html_destination_path, String repo_location, boolean generateJavaDocPart) {
//		public Runnable(String html_destination_path, String repo_location, boolean generateJavaDocPart, boolean enableIncremental) {
		public Runnable(String html_destination_path, String repo_location, boolean generateJavaDocPart, boolean enableIncremental, String documentTitle) {
			this.html_destination_path = html_destination_path;
			this.repo_location = repo_location;
			this.generateJavaDocPart = generateJavaDocPart;
			this.enableIncremental = enableIncremental;
			this.documentTitle = documentTitle;
			
			if (generateJavaDocPart) 
				this.flag_generate_java = "true";
			else	
				this.flag_generate_java = "false";
				
		 if (enableIncremental)
		 		this.flag_enable_incremental = "true";		
			else
		 		this.flag_enable_incremental = "false";		
				
		}

		public boolean isOk() {
			return ok;
		}

	
		/* (non-Javadoc)
		 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
		 */
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
//ExpressDocPlugin.log("in run - 01",1);				
			monitor.beginTask("Generating html documentation", IProgressMonitor.UNKNOWN);


//  this is when iso_db is called iso_db and is located in the root of the express_doc plugin
//			String iso_db_path = RuntimePlugin.getFragmentResourcePath(Platform.getBundle("net.jsdai.express_doc"), "iso_db");
// new specification requires iso_db to be called document_reference.txt and be used when present in the corresponding project root,
// if not found, do not use it (btw, perhaps no need to check if present, if an unsuccessful attempt is made to use it from ExpressDoc itself - ok)
			String iso_db_path = fProject.getLocation().toOSString() + File.separator + "document_reference.txt";


//      System.out.println("ISO_DB: " + iso_db_path);

// need to add one more command line switch:  -non_incremental

     if (documentTitle == null) {
     	 documentTitle = "Express Data";
     } else
     if (documentTitle.equals("")) {
     	 documentTitle = "Express Data";
     }
     if (html_destination_path == null) {
     		html_destination_path = fProject.getLocation().toOSString() + File.separator + "HTML";
     } else
     if (html_destination_path.equals("")) {
     		html_destination_path = fProject.getLocation().toOSString() + File.separator + "HTML";
     }
     if (flag_generate_java == null) {
				flag_generate_java = "false";	
     } else
     if (flag_generate_java.equals("")) {
				flag_generate_java = "false";	
     }
     if (flag_enable_incremental == null) {
				flag_enable_incremental = "false";	
     } else
     if (flag_enable_incremental.equals("")) {
				flag_enable_incremental = "false";	
     }
		 
     
	 //System.out.println("Will try saving, resource: " + fDictionaryResource + ", title: " + documentTitle);
	 try {

      /*
				   the following things currently in the export dialog:
				   exd file (path)                  - on this we are invoking, so selected, probably no need to save
				   destination directory           [destinationPath]
				   the title                       [documentTitle]
				   flag generate java or not       [flagGenerateJava]
				   flag enable incremental or not  [flagEnableIncremental]
      */

		 //fProject.setPersistentProperty(new QualifiedName(ExpressDocPlugin.ID_EXPRESS_DOC,".documentTitle"), documentTitle);
			fDictionaryResource.setPersistentProperty(new QualifiedName(ExpressDocPlugin.ID_EXPRESS_DOC,".documentTitle"), documentTitle);
			fDictionaryResource.setPersistentProperty(new QualifiedName(ExpressDocPlugin.ID_EXPRESS_DOC,".destinationPath"), html_destination_path);
			fDictionaryResource.setPersistentProperty(new QualifiedName(ExpressDocPlugin.ID_EXPRESS_DOC,".flagGenerateJava"), flag_generate_java);
			fDictionaryResource.setPersistentProperty(new QualifiedName(ExpressDocPlugin.ID_EXPRESS_DOC,".flagEnableIncremental"), flag_enable_incremental);
		 //System.out.println("DONE");
	 } catch (CoreException e1) {
		 System.out.println("PROBLEM");
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

			String [] args;
      int argument_number = 17;

			if (true) {  // if not a single schema (how to know if a single schema, and
							// its name?
//				args = new String[generateJavaDocPart ? 15  : 16];
//				args = new String[generateJavaDocPart ? 17  : 18];
			 // after adding -non_incremental

				// also take into account enableIncremental

				if (!enableIncremental) argument_number++;
				if (!generateJavaDocPart) argument_number++;

//				args = new String[generateJavaDocPart ? 18  : 19];
				args = new String[argument_number];
	
				
			} else {
//				args = new String[9];
//				args = new String[11];
			 // after adding -non_incremental
//				args = new String[12];
				args = new String[argument_number-6];
				
			}

			int ii = 0;

//System.out.println("TITLE: " + documentTitle);

			if (!enableIncremental) {
	      args[ii++] = "-non_incremental";
//System.out.println("NON-INCREMENTAL");
			}
			args[ii++] = "-location";
//			 args[ii++] = "ExpressCompilerRepo";
			args[ii++] = repo_location;
//			 args[ii++] = repo_location + File.separator + "ExpressCompilerRepo.sdai";
			args[ii++] = "-output";
			args[ii++] = html_destination_path;
			args[ii++] = "-title";
			args[ii++] = documentTitle; //  default - Expresss Data or rather the project name or what?
//			args[ii++] = "Express Data"; // TODO project name
			args[ii++] = "-iso_db_path";
			args[ii++] = iso_db_path;
			if(!generateJavaDocPart) {
				args[ii++] = "-noJava";
			}
			if (true) {
//			 if (fSchema_name == null) {

//System.out.println("<ALL>");	
				args[ii++] = "-generate_summary";
				args[ii++] = "-complex_schema";
				args[ii++] = "-complex_index";
				args[ii++] = "Express Data Index"; // TODO project name
				args[ii++] = "-exclude";
				args[ii++] = "SDAI_DICTIONARY_SCHEMA_DICTIONARY_DATA";
//			 args[ii++] = "-exclude";
				args[ii++] = "SDAI_MAPPING_SCHEMA_DICTIONARY_DATA";
				args[ii++] = "EXTENDED_DICTIONARY_SCHEMA_DICTIONARY_DATA";
				args[ii++] = "MAPPING_SCHEMA_DICTIONARY_DATA";
			} else {
				args[ii++] = "-include";
//			 args[ii++] = fSchema_name.toUpperCase() + "_DICTIONARY_DATA";
//System.out.println("<1ONLY>: " + args[ii-1]);	
			}

//			System.setProperty("jsdai.properties", ExpressDocPlugin.class.getResource("jsdai.properties").getFile());

//ExpressDocPlugin.log("in run - 02 - after args before try",1);				


			try {
// ##				try {
				Properties prop = new Properties();
				File repoDir = ExpressDocPlugin.getDefault().getStateLocation().append("sdairepos").toFile();
				if (!repoDir.exists()) repoDir.mkdirs();
//System.out.println("<>JSDAI-PROPERTIES: " + repoDir.getAbsolutePath());
				prop.setProperty("repositories", repoDir.getAbsolutePath());

// try without this
//				SdaiSession.setSessionProperties(prop);


//ExpressDocPlugin.log("in run - 03 - after repodir",1);				

/* ##

				} catch (SdaiException sex) {
//					ExpressDocPlugin.log(,1);
					System.out.println("problems with ExpressDoc 5, see log: " + sex);
					// e2.printStackTrace();
					ExpressDocPlugin.log("problems with ExpressDoc export 5: " + sex,1);				
					ExpressDocPlugin.log(sex);				
				};

## */
				
/*
				File repoDir = ExpressDocPlugin.getDefault().getStateLocation().append("sdairepos").toFile();
				if (!repoDir.exists()) repoDir.mkdirs();
				System.setProperty("repositories", repoDir.getAbsolutePath());
/**/
				
//				System.out.println("Invoking ExpressDoc with arguments");
//				System.out.println("---------------------------------------");
//				for (int ia = 0; ia < args.length; ia++) {
//					System.out.println("argument " + ia + ": " + args[ia]);	
//				}
//
//				System.out.println("-----------------------------------end-");


// let's try using JarFileClassLoader --------------------



//	public static IsolatedRunnableThread newInstance(final String className, final String methodName,
//			final Class[] parameterTypes, final Object[] parameters, final File[] classPathJars, String[] exceptions)


       	 String [] exceptions = null;
       	 

       	 
//       final File[] classPathJars;

// it is null, unfortunately       	 
//System.out.println("<KUKU> fProject: " + fProject);       	 

/*       	 
	    	 	String jar_path =  fProject.getProject().getLocation().toOSString();
    			if (jar_path.endsWith("\\") || jar_path.endsWith("/")) {
    				jar_path += fProject.getName() + ".jar";
    			} else {
    				jar_path += File.separator + fProject.getName() + ".jar";
		    	}
*/

//ExpressDocPlugin.log("in run - 05 - before classpaths",1);				

	    	 	File[] classPathJars = {
	    				new File(getClassPath(
	    						ExpressDocPlugin.getDefault().getBundle(), "jsdai_doc.jar")),
	    				new File(getClassPath(
	    						RuntimePlugin.getDefault().getBundle(), "jsdai_runtime.jar")),
	    				new File(getClassPath(
	    						Platform.getBundle("net.jsdai.ext_dict_lib"), "SExtended_dictionary_schema.zip"))
						// new File(jar_path),
	    			};

// ExpressDocPlugin.log("in run - 06 - after classpaths",1);				

//				ClassLoader loader = new JarFileClassLoader(...);

//	    	 ClassLoader loader =

				try {

//					System.out.println("classPathJars: " + classPathJars);					
                    for (int ihi = 0; ihi < classPathJars.length; ihi++) {
                    	
//    					System.out.println("classPathJars - i: " + ihi + " - " + classPathJars[ihi]);					
                    }


    	IsolatedRunnableThread expressdocThread = null;

//		File repoDir = ExpressDocPlugin.getDefault().getStateLocation().append("sdairepos").toFile();
		String repoDirStr = ExpressDocPlugin.getDefault().getStateLocation().append("sdairepos").toOSString();
		// if (!repoDir.exists()) repoDir.mkdirs();
    	
	    	 	expressdocThread =
	    	 		IsolatedRunnableThread.newInstance("jsdai.tools.ExpressDoc", "initAsRunnable",
	    	 				new Class[] { String.class, String[].class },
	    	 				new Object[] { repoDirStr, args },
	    	 				classPathJars, null);
// ExpressDocPlugin.log("in run - 07 - before thread start",1);				

	    		expressdocThread.start();
// ExpressDocPlugin.log("in run - 08 - after thread start",1);				

		if (true) {

//					p.waitFor();

			boolean is_cancel = false;
//			int exit_code = -555;
			for (;;) {
				if(!Thread.interrupted()) {
						try {
							expressdocThread.join(1000);
						} catch (InterruptedException e) {
							//System.out.println("problems with ExpressDoc 2, see log: " + e2);
							// e2.printStackTrace();
							ExpressDocPlugin.log("ExpressDoc interrupted by user 1", 1);				
						}
						if(!expressdocThread.isAlive()) {
							break;
						}
				}	
//				is_cancel = monitor.isCanceled();
				if (is_cancel) {
//					fValidateOutput += "Validation canceled";
						expressdocThread.interrupt();
					throw (new InterruptedException());
				}
			}	// for	
		} // if true (OS)



/*

					JarFileClassLoader loader =
						new JarFileClassLoader(classPathJars, ExportDocumentation.class.getClassLoader(), exceptions);

//		    	 	Class express_doc_class = loader.loadClass("ExpressDoc", true);
//System.out.println("<> before class - loader: "  + loader);
		    	 	Class express_doc_class = loader.loadClass("jsdai.tools.ExpressDoc", false);
//		    	 	Class express_doc_class = Class.forName("jsdai.tools.ExpressDoc", true, loader);
//System.out.println("<> after class");

//System.out.println("loader: " + express_doc_class.getClassLoader());
//		    	 	Method main = express_doc_class.getMethod("main", new Class[] {String[].class});
                    Class[] m_pars = new Class [2];
                    m_pars[0] = String.class;
                    m_pars[1] = String[].class;
                    
//		    	 	Method init_runnable = express_doc_class.getMethod("initAsRunnable", new Class[] {String.class, String[].class});
		    	 	Method init_runnable = express_doc_class.getMethod("initAsRunnable", m_pars);
//System.out.println("after getMethod, before invoke");
//					int modifiers = main.getModifiers();
//					if (Modifier.isPublic

//						main.invoke(null, new Object[] {new String[] {} }};
//						main.invoke(null, new Object[] {args  });
                        String repo_str = "G:\\REPO";
///                        Object arg1_obj = new Object(repo_str);
                        Object [] arguments = new Object[2];
                        arguments[0] = repo_str;
                        arguments[1] = args;
//						init_runnable.invoke(null, new Object[] {args  });
						java.lang.Runnable the_runnable = (java.lang.Runnable)init_runnable.invoke(null, arguments);
//System.out.println("after invoke - DONE");
			            the_runnable.run();
//System.out.println("after run - DONE");

*/

				} catch (Exception e2) {
					System.out.println("problems with ExpressDoc 6, see log: " + e2);
					// e2.printStackTrace();
					ExpressDocPlugin.log("problems with ExpressDoc export 6: " + e2,1);				
					ExpressDocPlugin.log(e2);				
				}

// -------- end of using JarFileClassLoader ---------------------------

//				ExpressDoc.main(args);

				ok = true;
//			} catch (SdaiException e) {
			} catch (Exception e) {
				System.out.println("problems with ExpressDoc 3, see log: " + e);
//				e.printStackTrace();
				ExpressDocPlugin.log("problems with ExpressDoc export 3: " + e,1);				
				ExpressDocPlugin.log(e);				
				throw new InvocationTargetException(e);
			}
			
			
			monitor.done();
		}
		
		
		
	}
	
	static String getClassPath(Bundle bundle, String jarFile) {
		try {
			URL classpath = Platform.asLocalURL(bundle.getEntry(jarFile));
			String classpath_string = classpath.getFile().toString();

		if (Platform.getOS().equals("win32")) {
			classpath_string = classpath_string.substring(1);
		}
			return classpath_string;
		} catch (IOException e) {
				// ExpressCompilerPlugin.log(e);
				// e.printStackTrace();
				System.out.println("problems with ExpressDoc 7, see log: " + e);
				// e2.printStackTrace();
				ExpressDocPlugin.log("problems with ExpressDoc export 7: " + e,1);				
				ExpressDocPlugin.log(e);				
				// ExpressDocPlugin.log(e.toString(),1);				
				return null;
		}
	}

	public static IProject getProject() {
		return fProject;
	}
	
	
}
