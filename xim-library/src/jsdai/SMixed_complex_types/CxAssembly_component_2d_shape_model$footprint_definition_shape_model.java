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

package jsdai.SMixed_complex_types;

/**
* @author Giedrius Liutkus
* @version $$
* $$
*/

import jsdai.lang.*;
import jsdai.libutil.*;
import jsdai.SFootprint_definition_xim.CxFootprint_definition_shape_model;
import jsdai.SPhysical_unit_2d_design_view_xim.*;
import jsdai.SProduct_property_representation_schema.CShape_representation;
import jsdai.SRepresentation_schema.ERepresentation;

public class CxAssembly_component_2d_shape_model$footprint_definition_shape_model extends CAssembly_component_2d_shape_model$footprint_definition_shape_model implements EMappedXIMEntity
{


	/// methods for attribute: name, base type: STRING
/*	public boolean testName(ERepresentation type) throws SdaiException {
		return test_string(a5);
	}
	public String getName(ERepresentation type) throws SdaiException {
		return get_string(a5);
	}*/
	public void setName(ERepresentation type, String value) throws SdaiException {
		a6 = set_string(value);
	}
	public void unsetName(ERepresentation type) throws SdaiException {
		a6 = unset_string();
	}
	public static jsdai.dictionary.EAttribute attributeName(ERepresentation type) throws SdaiException {
		return a6$;
	}
	public int attributeState = ATTRIBUTES_MODIFIED;	

	public void createAimData(SdaiContext context) throws SdaiException {
		if (attributeState == ATTRIBUTES_MODIFIED) {
			attributeState = ATTRIBUTES_UNMODIFIED;
		} else {
			return;
		}

		setTemp("AIM", CShape_representation.definition);

		setMappingConstraints(context, this);

      setShape_characterized_component(context, this);

      setShape_characterized_definition(context, this);
      setShape_environment(context, this);
      setShape_material_condition(context, this);
      setReference_shape(context, this);

      // clean ARM
      
      unsetShape_characterized_definition(null);
      unsetShape_environment(null);
      unsetShape_material_condition(null);
      unsetReference_shape(null);
      
      unsetShape_characterized_component(null);
		
	}

	public void removeAimData(SdaiContext context) throws SdaiException {
		unsetMappingConstraints(context, this);

		unsetShape_characterized_component(context, this);

      unsetShape_characterized_definition(context, this);
      unsetShape_environment(context, this);
      unsetShape_material_condition(context, this);
      unsetReference_shape(context, this);
	}
	
	
	/**
	* Sets/creates data for mapping constraints.
	*
	* <p>
	* mapping_constraints;
	* 	{shape_representation &lt;=
	*  representation				
	*  [representation.id = 'ac2ds']
	*  [representation.name = 'planar projected shape']
	*  [representation &lt;-
	*  property_definition_representation.used_representation
	*  property_definition_representation
	*  property_definition_representation.definition -&gt;
	*  {property_definition =&gt;
	*  product_definition_shape}
	*  property_definition
	*  property_definition.definition -&gt;
	*  characterized_definition
	*  characterized_definition = characterized_product_definition
	*  characterized_product_definition
	*  characterized_product_definition = product_definition
	*  product_definition =&gt;
	*  component_definition =&gt;
	*  assembly_component]}	
	* end_mapping_constraints;
	* </p>
	* @param context SdaiContext.
	* @param armEntity arm entity.
	* @throws SdaiException
	*/
	public static void setMappingConstraints(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException
	{
		unsetMappingConstraints(context, armEntity);
		//	Order is importatnt as AC2DS is subtype of FSD and only in this way magic strings can be synchronized		
		CxFootprint_definition_shape_model.setMappingConstraints(context, armEntity);
		CxAssembly_component_2d_shape_model.setMappingConstraints(context, armEntity);
	}

	public static void unsetMappingConstraints(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException
	{
		//	Order is importatnt as AC2DS is subtype of FSD and only in this way magic strings can be synchronized
		CxFootprint_definition_shape_model.unsetMappingConstraints(context, armEntity);
		CxAssembly_component_2d_shape_model.unsetMappingConstraints(context, armEntity);
	}

	//**********assembly_component_2d_shape attributes
	/**
	* Sets/creates data for shape_characterized_component attribute.
	* @param context SdaiContext.
	* @param armEntity arm entity.
	*
	*  attribute_mapping shape_characterized_component_assembly_component (shape_characterized_component
	* , (*PATH*), assembly_component);
	* 	shape_representation <=
	* 	representation <-
	* 	property_definition_representation.used_representation
	* 	property_definition_representation
	* 	property_definition_representation.definition ->
	* 	({property_definition =>
	* 	product_definition_shape}
	* 	property_definition
	* 	property_definition.definition ->
	* 	characterized_definition
	* 	characterized_definition = characterized_product_definition
	* 	characterized_product_definition
	* 	characterized_product_definition = product_definition
	* 	product_definition =>
	* 	component_definition)
	* 	(property_definition
	* 	property_definition.definition ->
	* 	characterized_definition
	* 	characterized_definition = shape_definition
	* 	shape_definition
	* 	shape_definition = shape_aspect
	* 	shape_aspect =>
	* 	component_shape_aspect)
	*  end_attribute_mapping;
	*
	*/
  // SR <- PDR -> PD -> CD (or CSA)
	public static void setShape_characterized_component(SdaiContext context, EAssembly_component_2d_shape_model armEntity) throws SdaiException {
		CxAssembly_component_2d_shape_model.setShape_characterized_component(context, armEntity);		
	}
	
	/**
	* unSets/deletes data for shape_characterized_component attribute.
	* @param context SdaiContext.
	* @param armEntity arm entity.
	*
	*/
  // SR <- PDR -> PD -> CD (or CSA)
	public static void unsetShape_characterized_component(SdaiContext context, EAssembly_component_2d_shape_model armEntity) throws SdaiException {
		CxAssembly_component_2d_shape_model.unsetShape_characterized_component(context, armEntity);		
	}	

	// Footprint_definition_shape
	public static void setShape_characterized_definition(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException {
		CxFootprint_definition_shape_model.setShape_characterized_definition(context, armEntity);		
	}
	
	public static void unsetShape_characterized_definition(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException {
		CxFootprint_definition_shape_model.unsetShape_characterized_definition(context, armEntity);		
	}


	public static void setShape_environment(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException {
		CxFootprint_definition_shape_model.setShape_environment(context, armEntity);		
	}

	public static void unsetShape_environment(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException {
		CxFootprint_definition_shape_model.unsetShape_environment(context, armEntity);		
	}


	public static void setShape_material_condition(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException {
		CxFootprint_definition_shape_model.setShape_material_condition(context, armEntity);		
	}

	public static void unsetShape_material_condition(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException {
		CxFootprint_definition_shape_model.unsetShape_material_condition(context, armEntity);		
	}

	public static void setReference_shape(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException {
		CxFootprint_definition_shape_model.setReference_shape(context, armEntity);		
	}
	
	public static void unsetReference_shape(SdaiContext context, CAssembly_component_2d_shape_model$footprint_definition_shape_model armEntity) throws SdaiException {
		CxFootprint_definition_shape_model.unsetReference_shape(context, armEntity);		
	}
	
	
}
