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

package jsdai.SFabrication_technology_xim;

import jsdai.lang.*;
import jsdai.libutil.*;
import jsdai.SCharacteristic_xim.ELength_tolerance_characteristic;
import jsdai.SFabrication_technology_mim.CPassage_technology;
import jsdai.SProduct_property_definition_schema.ECharacterized_object;
import jsdai.SProduct_property_definition_schema.EProperty_definition;

public class CxDefault_tapered_blind_via_definition extends CDefault_tapered_blind_via_definition implements EMappedXIMEntity
{

	public int attributeState = ATTRIBUTES_MODIFIED;	

	// From CProperty_definition.java
	/// methods for attribute: name, base type: STRING
/*	public boolean testName(EProperty_definition type) throws SdaiException {
		return test_string(a0);
	}
	public String getName(EProperty_definition type) throws SdaiException {
		return get_string(a0);
	}*/
	public void setName(EProperty_definition type, String value) throws SdaiException {
		a0 = set_string(value);
	}
	public void unsetName(EProperty_definition type) throws SdaiException {
		a0 = unset_string();
	}
	public static jsdai.dictionary.EAttribute attributeName(EProperty_definition type) throws SdaiException {
		return a0$;
	}
	// ENDOF From CProperty_definition.java
	
	// FROM Characterized_object
	/// methods for attribute: description, base type: STRING
/*	public boolean testDescription(ECharacterized_object type) throws SdaiException {
		return test_string(a5);
	}
	public String getDescription(ECharacterized_object type) throws SdaiException {
		return get_string(a5);
	}*/
	public void setDescription(ECharacterized_object type, String value) throws SdaiException {
		a5 = set_string(value);
	}

	public void unsetDescription(ECharacterized_object type) throws SdaiException {
		a5 = unset_string();
	}
	public static jsdai.dictionary.EAttribute attributeDescription(ECharacterized_object type) throws SdaiException {
		return a5$;
	}
	// ENDOF FROM Characterized_object	
	
	public void createAimData(SdaiContext context) throws SdaiException {
		if (attributeState == ATTRIBUTES_MODIFIED) {
			attributeState = ATTRIBUTES_UNMODIFIED;
		} else {
			return;
		}

		setTemp("AIM", CPassage_technology.definition);

		setMappingConstraints(context, this);
		
		// as_finished_inter_stratum_extent 		
		// setAs_finished_inter_stratum_extent(context, this);

		// EX Parameters
      // plated_passage
		setPlated_passage(context, this);
      
		// as_finished_passage_extent
		setAs_finished_passage_extent(context, this);
		
      // as_finished_deposition_thickness
		setAs_finished_deposition_thickness(context, this);
		
		// maximum_aspect_ratio
		setMaximum_aspect_ratio(context, this);

		// as_finished_interior_passage_extent(context, this);
		setAs_finished_interior_passage_extent(context, this);		
		
		setPassage_terminus_condition(context, this);		
		
		// Clean ARM
		
		// as_finished_inter_stratum_extent 		
		// unsetAs_finished_inter_stratum_extent(null);

		// EX Parameters
      // plated_passage
		unsetPlated_passage(null);
      
		// as_finished_passage_extent
		unsetAs_finished_passage_extent(null);
		
      // as_finished_deposition_thickness
		unsetAs_finished_deposition_thickness(null);
		
		// maximum_aspect_ratio
		unsetMaximum_aspect_ratio(null);
		
		// As_finished_interior_passage_extent
		unsetAs_finished_interior_passage_extent(null);
		
		unsetPassage_terminus_condition(null);
	}

	public void removeAimData(SdaiContext context) throws SdaiException {
		unsetMappingConstraints(context, this);

		// as_finished_inter_stratum_extent 		
		// unsetAs_finished_inter_stratum_extent(context, this);

		// EX Parameters
      // plated_passage
		unsetPlated_passage(context, this);
      
		// as_finished_passage_extent
		unsetAs_finished_passage_extent(context, this);
		
      // as_finished_deposition_thickness
		unsetAs_finished_deposition_thickness(context, this);
		
		// maximum_aspect_ratio
		unsetMaximum_aspect_ratio(context, this);
		
		unsetAs_finished_interior_passage_extent(context, this);
	
		unsetPassage_terminus_condition(context, this);
		
		CxPassage_technology_armx.cleanAIM_stuff(context, this);
	}
	
	
	/**
	* Sets/creates data for mapping constraints.
	*
	* <p>
	mapping_constraints;
		passage_technology <=
		characterized_object
		{characterized_object
		characterized_object.description = 'default tapered blind via definition'}
	end_mapping_constraints;
	* </p>
	* @param context SdaiContext.
	* @param armEntity arm entity.
	* @throws SdaiException
	*/
	public static void setMappingConstraints(SdaiContext context, EDefault_via_definition armEntity) throws SdaiException
	{
		unsetMappingConstraints(context, armEntity);
		CxDefault_via_definition.setMappingConstraints(context, armEntity);
		armEntity.setDescription((ECharacterized_object)null, "default tapered blind via definition");
	}

	public static void unsetMappingConstraints(SdaiContext context, EDefault_via_definition armEntity) throws SdaiException
	{
		CxDefault_via_definition.unsetMappingConstraints(context, armEntity);
		armEntity.unsetDescription((ECharacterized_object)null);
	}

   //********** "passage_technology" attributes

   /**
    * Sets/creates data for as_finished_inter_stratum_extent attribute.
    *
    * @param context SdaiContext.
    * @param armEntity arm entity.
    * @throws SdaiException
    */
   // PT <- SAR -> | SA -> PDS -> PDR (specific | reusable)
/* Removed	
   public static void setAs_finished_inter_stratum_extent(SdaiContext context, EPassage_technology_armx armEntity) throws
      SdaiException {
   	CxPassage_technology_armx.setAs_finished_inter_stratum_extent(context, armEntity);   	
   }
*/
 /**
  * Unsets/deletes data for as_finished_inter_stratum_extent attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
/* Removed	
 public static void unsetAs_finished_inter_stratum_extent(SdaiContext context, EPassage_technology_armx armEntity) throws
    SdaiException {
 	CxPassage_technology_armx.unsetAs_finished_inter_stratum_extent(context, armEntity);
 }
*/	
 /**
  * Sets/creates data for Plated_passage attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 // PT <- PD <- PDR -> R
 public static void setPlated_passage(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.setPlated_passage(context, armEntity);
 }

 /**
  * Unsets/deletes data for plated_passage attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 public static void unsetPlated_passage(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.unsetPlated_passage(context, armEntity);
 }

 /**
  * Sets/creates data for As_finished_passage_extent attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 // PT <- PD <- PDR -> R
 public static void setAs_finished_passage_extent(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.setAs_finished_passage_extent(context, armEntity);
 }

 /**
  * Unsets/deletes data for As_finished_passage_extent attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 public static void unsetAs_finished_passage_extent(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.unsetAs_finished_passage_extent(context, armEntity);
 }

 /**
  * Sets/creates data for As_finished_deposition_thickness attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 // PT <- PD <- PDR -> R
 public static void setAs_finished_deposition_thickness(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.setAs_finished_deposition_thickness(context, armEntity);
 }

 /**
  * Unsets/deletes data for As_finished_deposition_thickness attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 public static void unsetAs_finished_deposition_thickness(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.unsetAs_finished_deposition_thickness(context, armEntity);
 }

 /**
  * Sets/creates data for maximum_aspect_ratio attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 // PT <- PD <- PDR -> R
 public static void setMaximum_aspect_ratio(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.setMaximum_aspect_ratio(context, armEntity);
 }

 /**
  * Unsets/deletes data for Maximum_aspect_ratio attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 public static void unsetMaximum_aspect_ratio(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.unsetPlated_passage(context, armEntity);
 }

 /**
  * Sets/creates data for as_finished_interior_passage_extent attribute.
  *
	attribute_mapping as_finished_interior_passage_extent(as_finished_interior_passage_extent, $PATH, Length_tolerance_characteristic);
		passage_technology <=
		characterized_object
		characterized_definition = characterized_object
		characterized_definition <-
		property_definition.definition
		{property_definition.name = 'as finished interior passage extent'}
		property_definition <-
		property_definition_representation.definition
		property_definition_representation
		property_definition_representation.used_representation ->
		representation
	end_attribute_mapping;
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 // PT <- PD <- PDR -> R
 public static void setAs_finished_interior_passage_extent(SdaiContext context, EDefault_tapered_blind_via_definition armEntity) throws SdaiException {
 	unsetAs_finished_interior_passage_extent(context, armEntity);
 	if(armEntity.testAs_finished_interior_passage_extent(null)){
 		ELength_tolerance_characteristic characteristic = armEntity.getAs_finished_interior_passage_extent(null);
 		CxAP210ARMUtilities.setProperty_definitionToRepresentationPath(context, armEntity, null, "as finished interior passage extent", characteristic);
 	}
 }

 /**
  * Unsets/deletes data for as_finished_interior_passage_extent attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 public static void unsetAs_finished_interior_passage_extent(SdaiContext context, EDefault_tapered_blind_via_definition armEntity) throws SdaiException {
	 CxAP210ARMUtilities.unsetProperty_definitionToRepresentationPath(context, armEntity, "as finished interior passage extent");
 }
 
 /**
  * Sets/creates data for Passage_terminus_condition attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 // PT <- PD <- PDR -> R
 public static void setPassage_terminus_condition(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.setPassage_terminus_condition(context, armEntity);
 }

 /**
  * Unsets/deletes data for Passage_terminus_condition attribute.
  *
  * @param context SdaiContext.
  * @param armEntity arm entity.
  * @throws SdaiException
  */
 public static void unsetPassage_terminus_condition(SdaiContext context, EPassage_technology_armx armEntity) throws SdaiException {
 	CxPassage_technology_armx.unsetPassage_terminus_condition(context, armEntity);
 }
  
}
