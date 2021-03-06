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

// %modified: 1016210368845 %

/* Generated By:JJTree: Do not edit this line. X_Element.java */

/*
NOTE: because element is expression, to be safe it is needed to support additional java statements,
and pass them over out. Because in practice I saw only strings or string concatenations, 
it is not implemented here - low priority
 */
package jsdai.expressCompiler;

import java.util.*;


public class X_Element
  extends ExpressionNode { // needed?
  int repetition = 1;
	String repetition_count = ""; 

  // public class X_Element extends SimpleNode {
  public X_Element(int id) {
    super(id);
    repetition = 1;
  }

  public X_Element(Compiler2 p, int id) {
    super(p, id);
    repetition = 1;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(Compiler2Visitor visitor, Object data)
                   throws jsdai.lang.SdaiException {
    return visitor.visit(this, data);
  }

  public Object childrenAccept(Compiler2Visitor visitor, Object data)
                        throws jsdai.lang.SdaiException {
    JavaClass jc = ( JavaClass )data;
    String element_str = "";

    if (children != null) {
      if (jc != null) {
        if (jc.active) {
          statements = new Vector();
          variable_names = new Vector();
          variable_declarations = new Vector();
          initializing_code = new Vector();

          for (int i = 0; i < children.length; ++i) {
            children[i].jjtAccept(visitor, data);

            if ((( SimpleNode )children[i]).java_contains_statements) {
              java_contains_statements = true;

              // variable_declaration += "\n" + ((SimpleNode)children[i]).variable_declaration;
              for (int j = 0; j < (( SimpleNode )children[i]).variable_names.size(); j++) {
                variable_names.add((( SimpleNode )children[i]).variable_names.elementAt(j));
              }

              for (int j = 0; j < (( SimpleNode )children[i]).variable_declarations.size(); j++) {
                variable_declarations.add((( SimpleNode )children[i]).variable_declarations.elementAt(j));
              }

              for (int j = 0; j < (( SimpleNode )children[i]).statements.size(); j++) {
                statements.add((( SimpleNode )children[i]).statements.elementAt(j));
              }

              // initializing_code += "\n" + ((SimpleNode)children[i]).initializing_code;
              for (int j = 0; j < (( SimpleNode )children[i]).initializing_code.size(); j++) {
                initializing_code.add((( SimpleNode )children[i]).initializing_code.elementAt(j));
              }
            } // if contains statements


            if (i == 0) {
              element_str = jc.generated_java;
            } else
            if (i == 1) {
            	repetition_count = jc.generated_java;
            }
          }

          jc.generated_java = element_str;
        }
      }
    }

    return data;
  }
}