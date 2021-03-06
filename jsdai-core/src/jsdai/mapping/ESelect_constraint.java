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

// Java interface for entity select_constraint

package jsdai.mapping;
import jsdai.lang.*;

public interface ESelect_constraint extends EConstraint_attribute {

	// methods for attribute: data_type, base type: LIST OF ENTITY
	public boolean testData_type(ESelect_constraint type) throws SdaiException;
	public jsdai.dictionary.ADefined_type getData_type(ESelect_constraint type) throws SdaiException;
	public jsdai.dictionary.ADefined_type createData_type(ESelect_constraint type) throws SdaiException;
	public void unsetData_type(ESelect_constraint type) throws SdaiException;

	// constants and methods for SELECT attribute: attribute
	boolean testAttribute(ESelect_constraint type) throws SdaiException;

	jsdai.lang.EEntity getAttribute(ESelect_constraint type) throws jsdai.lang.SdaiException; // case 1

	void setAttribute(ESelect_constraint type, jsdai.lang.EEntity value) throws jsdai.lang.SdaiException; // case 1

	void unsetAttribute(ESelect_constraint type) throws SdaiException;

}
