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

/* Generated By:JJTree: Do not edit this line. X_SubtypeBindingHeader.java */

package jsdai.expressCompiler;


import java.util.*;
import java.io.*;
import jsdai.lang.*;
import jsdai.SExtended_dictionary_schema.*;

public class X_SubtypeBindingHeader extends SimpleNode {

	static boolean flag_generate_execute_in_nodes = true;

	EMap_or_view_partition partition = null;

  public X_SubtypeBindingHeader(int id) {
    super(id);
  }

  public X_SubtypeBindingHeader(Compiler2 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(Compiler2Visitor visitor, Object data) throws jsdai.lang.SdaiException {
    return visitor.visit(this, data);
  }

  public Object childrenAccept(Compiler2Visitor visitor, Object data) throws jsdai.lang.SdaiException {
    JavaClass jc = ( JavaClass )data;
		PrintWriter pw = jc.pw;

		int ind = jc.ind;

	pw.println("// YES YES YES - in SubtypeBindingHeader!!!!!!!!!!!!!!, secondary: " + jc.secondary);


		if (jc.secondary == 1) {

			 if (children != null) {
					for (int i = 0; i < children.length; i++) {
						if (children[i] instanceof X_WhereClause) {
							children[i].jjtAccept(visitor, data);
					  } else {

pw.println("\t\t// EXPERIMENT 2 - SubtypeBindingHeader - intermediate supertype - START ----");

				tabByIndex(ind, pw); pw.println("\tbinding_inst_extent.addElement(binding_inst); // not where clause");
				// needed for non-in-node-version, at least
				if (!flag_generate_execute_in_nodes) {
					tabByIndex(ind, pw); pw.println("\t}");
				}
				//								children[i].jjtAccept(visitor, data);
				while(ind > 0){
					tabByIndex(ind, pw); pw.println("}");
					ind--;
				}
				if (children[i] instanceof X_IdentifiedByClause) {
					pw.println("\t\t// IDENTIFIED_BY clause");
					children[i].jjtAccept(visitor, data);

					pw.println("\t\t// Evaluating expression parts for the bindings of the same eq class and setting appopriate values");
					pw.println("\t\tIterator iter = alist.iterator();");
					pw.println("\t\tfor(int i = 0; i < alist.size() ; i++) {");
					pw.println("\t\t\tint inst_num = 0;");
					// don't do checking, rely on the size
					pw.println("\t\t\tVector eq_class = (Vector)iter.next();");

					jc.identified_by = true;

//				} else
//				if (children[i] instanceof X_OrderedByClause) {
				} else {
					// should not occur
				} 

pw.println("\t\t// EXPERIMENT 2 - SubtypeBindingHeader - intermediate supertype - END ----");


					  }
		  		}
			 }
		} else
		if (jc.secondary == 0) {
			// this implementation for case 0 is temporary, because IDENTIFIEND_BY clause has to be supported, etc.
			// at least it will have to be extended
			 if (children != null) {
					for (int i = 0; i < children.length; i++) {
						if (children[i] instanceof X_WhereClause) {
							children[i].jjtAccept(visitor, data);
					  } else {

pw.println("\t\t// EXPERIMENT 3 - SubtypeBindingHeader - final subtype - START ----");

				tabByIndex(ind, pw); pw.println("\tbinding_inst_extent.addElement(binding_inst); // not where clause");
				// needed for non-in-node-version, at least
				if (!flag_generate_execute_in_nodes) {
					tabByIndex(ind, pw); pw.println("\t}");
				}
				//								children[i].jjtAccept(visitor, data);
				while(ind > 0){
					tabByIndex(ind, pw); pw.println("}");
					ind--;
				}
				if (children[i] instanceof X_IdentifiedByClause) {
					pw.println("\t\t// IDENTIFIED_BY clause");
					children[i].jjtAccept(visitor, data);

					pw.println("\t\t// Evaluating expression parts for the bindings of the same eq class and setting appopriate values");
					pw.println("\t\tIterator iter = alist.iterator();");
					pw.println("\t\tfor(int i = 0; i < alist.size() ; i++) {");
					pw.println("\t\t\tint inst_num = 0;");
					// don't do checking, rely on the size
					pw.println("\t\t\tVector eq_class = (Vector)iter.next();");

					jc.identified_by = true;

//				} else
//				if (children[i] instanceof X_OrderedByClause) {
				} else {
					// should not occur
				} 

pw.println("\t\t// EXPERIMENT 3 - SubtypeBindingHeader - final subtype - END ----");


					  }
		  		}
			 }
		} else
		if (jc.secondary == 2) {
			// it will not be invoked on attribute mapping, hopefully, if it is, it is a bug
			System.out.println("INTERNAL ERROR - in SubtypeBindingHeader node invoked as secondary = 2 (MapAttributeDeclaration)");
		} else {

 if (children != null) {

		
		for (int i = 0; i < children.length; i++) {
//			if (children[i] instanceof X_FromClause) { // currently no such clause
//			} else 	
//			if (children[i] instanceof X_LocalDecl) {
//			} else
			if (children[i] instanceof X_WhereClause) {
				pw.println("\t\t\t// WHERE clause");
				tabByIndex(ind, pw); pw.print("\tif(");
				children[i].jjtAccept(visitor, data);
				pw.println("){"); 
				tabByIndex(ind, pw); pw.println("\t\tbinding_inst_extent.addElement(binding_inst); // where clause");
				tabByIndex(ind, pw); pw.println("\t}");
				// i++; // generate next clause
				while(ind > 0){
					tabByIndex(ind, pw); pw.println("}");
					ind--;
				}
			} else {
				tabByIndex(ind, pw); pw.println("\tbinding_inst_extent.addElement(binding_inst); // not where clause");
				// needed for non-in-node-version, at least
				if (!flag_generate_execute_in_nodes) {
					tabByIndex(ind, pw); pw.println("\t}");
				}
				//								children[i].jjtAccept(visitor, data);
				while(ind > 0){
					tabByIndex(ind, pw); pw.println("}");
					ind--;
				}
				if (children[i] instanceof X_IdentifiedByClause) {
					pw.println("\t\t// IDENTIFIED_BY clause");
					children[i].jjtAccept(visitor, data);

					pw.println("\t\t// Evaluating expression parts for the bindings of the same eq class and setting appopriate values");
					pw.println("\t\tIterator iter = alist.iterator();");
					pw.println("\t\tfor(int i = 0; i < alist.size() ; i++) {");
					pw.println("\t\t\tint inst_num = 0;");
					// don't do checking, rely on the size
					pw.println("\t\t\tVector eq_class = (Vector)iter.next();");

					jc.identified_by = true;

//				} else
//				if (children[i] instanceof X_OrderedByClause) {
				} else {
					// should not occur
				} 
			}
		} // for - children
		if (children.length == 0) {
			tabByIndex(ind, pw); pw.println("\tbinding_inst_extent.addElement(binding_inst); // no children");
			if (!flag_generate_execute_in_nodes) {
//				tabByIndex(ind, pw); pw.println("\t}");
			}
			//children[i].jjtAccept(visitor, data);
			while(ind > 0){
				tabByIndex(ind, pw); pw.println("}");
				ind--;
			}
		}
	} else {
		
		// perhaps all that above business with various children is from old implementation moved here,
		// with introducing of the special node for binding header, everything is different
		
		tabByIndex(ind, pw); pw.println("\tbinding_inst_extent.addElement(binding_inst); // the rest");
		if (!flag_generate_execute_in_nodes) {
			
			ASource_parameter asp = jc.asp;
			if (asp.getMemberCount() > 0) {

				tabByIndex(ind, pw); pw.println("\t}");
			}
		}
		//children[i].jjtAccept(visitor, data);
		while(ind > 0){
			tabByIndex(ind, pw); pw.println("}");
			ind--;
		}
	
	} // if children not NULL	
	
	} // secondary not 1, so normal processing
	
    return data;
	} // end of method


	void tabByIndex(int ind, PrintWriter pw){
		pw.print("\t");
		for(int tt=0; tt < ind; tt++) pw.print("\t");
	}


  String getEntityPackage(EEntity_or_view_definition eds, SdaiModel current_model)
		throws jsdai.lang.SdaiException {

    // return an empty string if ed and eds in the same schema, and the package of eds otherwise
    String entity_package = "";
    SdaiModel entity_model = eds.findEntityInstanceSdaiModel();
		
    if (entity_model != current_model) {
      String entity_schema_name = getSchema_definitionFromModel(entity_model).getName(null);

      if (entity_schema_name.equalsIgnoreCase("Sdai_dictionary_schema")) {
        entity_package = "jsdai.dictionary.";
      } else {
        entity_package = "jsdai.S" + entity_schema_name.substring(0, 1).toUpperCase() + entity_schema_name.substring(1).toLowerCase() + ".";
      }
    }

    return entity_package;
  }

  EGeneric_schema_definition getSchema_definitionFromModel(SdaiModel sm) throws jsdai.lang.SdaiException {
    jsdai.lang.Aggregate ia = sm.getEntityExtentInstances(EGeneric_schema_definition.class);
    jsdai.lang.SdaiIterator iter_inst = ia.createIterator();

    while (iter_inst.next()) {
      EGeneric_schema_definition inst = ( EGeneric_schema_definition )ia.getCurrentMemberObject(iter_inst);
      return inst;
    }

    return null;
  }


}
