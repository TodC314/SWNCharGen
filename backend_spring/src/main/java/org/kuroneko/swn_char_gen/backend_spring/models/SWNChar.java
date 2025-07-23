/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Model class for SWN characters.
 */
public class SWNChar
{
	// instance variable for data
	// this is an echo of the Python dataclass swn_char_data - see SWNCharData for details
	private final SWNCharData mData;
	// instance variable for system defaults
	private final SWNSystem mSystem;

	/**
	 * Create a new SWNChar with default values.
	 */
	public SWNChar()
	{
		this.mData = new SWNCharData();
		this.mSystem = new SWNSystem();
	}

	/**
	 * Create a new SWNChar from character data.
	 *
	 * @param theCharData A Map containing character data
	 */
	public SWNChar(Map<String, Object> theCharData) throws IOException
	{
		this.mData = new SWNCharData();
		this.mSystem = new SWNSystem();

		if (theCharData != null)
		{
			this.fromMap(theCharData);
		}
	}

	/**
	 * Set a detail for the character.
	 *
	 * @param theDetail The detail to set
	 * @param theValue  The value to set
	 * @throws IllegalArgumentException if the detail is invalid
	 */
	public void setDetail(DetailEnum theDetail, String theValue)
	{
		if (DetailEnum.NAME == theDetail)
		{
			mData.mName = theValue;
		}
		else
		{
			throw new IllegalArgumentException("Invalid detail: " + theDetail);
		}
	}

	/**
	 * Roll random values for all attributes and update their modifiers.
	 */
	public void rollAttributes()
	{
		// Roll 3d6 for each attribute
		mData.mStrength = mSystem.calculateAttribute();
		mData.mDexterity = mSystem.calculateAttribute();
		mData.mConstitution = mSystem.calculateAttribute();
		mData.mIntelligence = mSystem.calculateAttribute();
		mData.mWisdom = mSystem.calculateAttribute();
		mData.mCharisma = mSystem.calculateAttribute();

		// Update modifiers based on attribute values
		mData.mStrengthModifier = mSystem.calculateModifier(mData.mStrength);
		mData.mDexterityModifier = mSystem.calculateModifier(mData.mDexterity);
		mData.mConstitutionModifier = mSystem.calculateModifier(mData.mConstitution);
		mData.mIntelligenceModifier = mSystem.calculateModifier(mData.mIntelligence);
		mData.mWisdomModifier = mSystem.calculateModifier(mData.mWisdom);
		mData.mCharismaModifier = mSystem.calculateModifier(mData.mCharisma);
	}

	/**
	 * Set an attribute to a specific value and update its modifier.
	 *
	 * @param theAttribute The attribute to set
	 * @param theValue     The value to set
	 * @return The old value of the attribute
	 */
	private int setAttribute(AttributeEnum theAttribute, int theValue)
	{
		int oldValue = -1;

		switch (theAttribute)
		{
			case STRENGTH:
				oldValue = mData.mStrength;
				mData.mStrength = theValue;
				mData.mStrengthModifier = mSystem.calculateModifier(mData.mStrength);
				break;
			case DEXTERITY:
				oldValue = mData.mDexterity;
				mData.mDexterity = theValue;
				mData.mDexterityModifier = mSystem.calculateModifier(mData.mDexterity);
				break;
			case CONSTITUTION:
				oldValue = mData.mConstitution;
				mData.mConstitution = theValue;
				mData.mConstitutionModifier = mSystem.calculateModifier(mData.mConstitution);
				break;
			case INTELLIGENCE:
				oldValue = mData.mIntelligence;
				mData.mIntelligence = theValue;
				mData.mIntelligenceModifier = mSystem.calculateModifier(mData.mIntelligence);
				break;
			case WISDOM:
				oldValue = mData.mWisdom;
				mData.mWisdom = theValue;
				mData.mWisdomModifier = mSystem.calculateModifier(mData.mWisdom);
				break;
			case CHARISMA:
				oldValue = mData.mCharisma;
				mData.mCharisma = theValue;
				mData.mCharismaModifier = mSystem.calculateModifier(mData.mCharisma);
				break;
		}

		return oldValue;
	}

	/**
	 * Change one attribute to a special value (14) and restore any previously changed attribute.
	 *
	 * @param theAttribute The attribute to change
	 */
	public void changeOneAttribute(AttributeEnum theAttribute)
	{
		if (mData.mChangedAttribute != AttributeEnum.NONE)
		{
			setAttribute(mData.mChangedAttribute, mData.mChangedAttributeOriginalValue);
		}

		mData.mChangedAttributeOriginalValue = setAttribute(theAttribute, mSystem.getChangedAttributeValue());
		mData.mChangedAttribute = theAttribute;
	}

	/**
	 * Convert the SWNChar object to a Map for JSON serialization.
	 *
	 * @return A Map representation of the SWNChar object
	 */
	public Map<String, Object> toMap() throws IllegalArgumentException
	{
		// TODO: Move the mapper functionality somewhere better.
		ObjectMapper mapper = new ObjectMapper();
		// null means don't change member variable names - we want it to match the Python output
		mapper.setPropertyNamingStrategy(null);
		// The next two lines are in case any accessors are defined in our "data only" class, since Java doesn't enforce that.
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

		return mapper.convertValue(this.mData, new TypeReference<Map<String,Object>>() {});
	}

	/**
	 * Load character data from a Map.
	 *
	 * @param theData A Map containing character data
	 */
	public void fromMap(Map<String, Object> theData) throws IOException
	{
		// TODO: Move the mapper functionality somewhere better.
		ObjectMapper mapper = new ObjectMapper();
		// null means don't change member variable names - we want it to match the Python output
		mapper.setPropertyNamingStrategy(null);
		// The next two lines are in case any accessors are defined in our "data only" class, since Java doesn't enforce that.
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

		// Convert Map to JsonNode
		JsonNode node = mapper.valueToTree(theData);

		// Update existing object with Map data
		mapper.readerForUpdating(this.mData).readValue(node);
	}
}