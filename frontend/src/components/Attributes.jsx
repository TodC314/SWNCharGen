/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

import {useState, useEffect} from 'react'
import './Attributes.css'

/**
 * Enumeration of character attributes used in the SWN system
 * @enum {string}
 */
const AttributeEnum = {
	STRENGTH: "STRENGTH",
	DEXTERITY: "DEXTERITY",
	CONSTITUTION: "CONSTITUTION",
	INTELLIGENCE: "INTELLIGENCE",
	WISDOM: "WISDOM",
	CHARISMA: "CHARISMA",
	NONE: "NONE"
}

/**
 * Component for displaying and managing character attributes
 * Allows rolling new attributes and setting one attribute to a value of 14
 *
 * @param {Object} props - Component props
 * @param {Object} props.character - Character data object
 * @param {Function} props.onRollAttributes - Function to handle rolling new attributes
 * @param {Function} props.onChangeAttribute - Function to handle changing an attribute to 14
 * @returns {JSX.Element} The rendered Attributes component
 */
function Attributes({character, onRollAttributes, onChangeAttribute})
{
	// Safely access potentially undefined properties
	// TODO: replace this by converting to TypeScript
	const getValue = (obj, path) =>
	{
		return path.split('.').reduce((acc, part) => acc && acc[part] !== undefined ? acc[part] : '', obj);
	};

	/**
	 * State variables for character attributes and modifiers
	 */
	const [strength, setStrength] = useState(() => String(getValue(character, 'mStrength')));
	const [dexterity, setDexterity] = useState(() => String(getValue(character, 'mDexterity')));
	const [constitution, setConstitution] = useState(() => String(getValue(character, 'mConstitution')));
	const [intelligence, setIntelligence] = useState(() => String(getValue(character, 'mIntelligence')));
	const [wisdom, setWisdom] = useState(() => String(getValue(character, 'mWisdom')));
	const [charisma, setCharisma] = useState(() => String(getValue(character, 'mCharisma')));
	const [changedAttribute, setChangedAttribute] = useState(() => String(getValue(character, 'mChangedAttribute')));
	const [strengthModifier, setStrengthModifier] = useState(() => String(getValue(character, 'mStrengthModifier')));
	const [dexterityModifier, setDexterityModifier] = useState(() => String(getValue(character, 'mDexterityModifier')));
	const [constitutionModifier, setConstitutionModifier] = useState(() => String(getValue(character, 'mConstitutionModifier')));
	const [intelligenceModifier, setIntelligenceModifier] = useState(() => String(getValue(character, 'mIntelligenceModifier')));
	const [wisdomModifier, setWisdomModifier] = useState(() => String(getValue(character, 'mWisdomModifier')));
	const [charismaModifier, setCharismaModifier] = useState(() => String(getValue(character, 'mCharismaModifier')));

	/**
	 * Updates local state when character data changes
	 * Syncs component state with the character data received from props
	 */
	useEffect(() =>
	{
		if (character)
		{
			// attributes - convert to string to ensure consistency with initial empty string state
			setStrength(String(getValue(character, 'mStrength')))
			setDexterity(String(getValue(character, 'mDexterity')))
			setConstitution(String(getValue(character, 'mConstitution')))
			setIntelligence(String(getValue(character, 'mIntelligence')))
			setWisdom(String(getValue(character, 'mWisdom')))
			setCharisma(String(getValue(character, 'mCharisma')))
			setChangedAttribute(String(getValue(character, 'mChangedAttribute')) || 'NONE')
			// modifiers - convert to string to ensure consistency with initial empty string state
			setStrengthModifier(String(getValue(character, 'mStrengthModifier')))
			setDexterityModifier(String(getValue(character, 'mDexterityModifier')))
			setConstitutionModifier(String(getValue(character, 'mConstitutionModifier')))
			setIntelligenceModifier(String(getValue(character, 'mIntelligenceModifier')))
			setWisdomModifier(String(getValue(character, 'mWisdomModifier')))
			setCharismaModifier(String(getValue(character, 'mCharismaModifier')))
		}
	}, [character])

	/**
	 * Handles the roll attributes button click
	 * Calls the onRollAttributes function passed from parent
	 */
	const rollAttributes = () =>
	{
		// Use the onRollAttributes function passed from App component
		onRollAttributes()
	}

	/**
	 * Handles changing an attribute to a value of 14
	 *
	 * @param {string} newAttributeName - The name of the attribute to change
	 */
	const changeAttribute = (newAttributeName) =>
	{
		onChangeAttribute(newAttributeName)
	}

	// noinspection JSValidateTypes
	return (
		<div className="attributes-form">
			<h2>Attributes</h2>
			<div className="attributes-table">
				<div className="attributes-header">
					<div className="attribute-label">Attribute</div>
					<div className="attribute-value-label">Value</div>
					<div className="attribute-modifier-label">Modifier</div>
					<div className="attribute-radio-label">Set to 14</div>
				</div>

				<div className="attribute-row">
					<label htmlFor="strength">Strength</label>
					<input id="strength" type="text" value={strength} readOnly/>
					<div
						className="attribute-modifier">{(Number(strengthModifier) > 0 ? '+' : '') + strengthModifier}</div>
					<input
						type="radio"
						name="setTo14"
						value={AttributeEnum.STRENGTH.valueOf()}
						checked={changedAttribute === AttributeEnum.STRENGTH.valueOf()}
						onChange={() => changeAttribute(AttributeEnum.STRENGTH.valueOf())}
					/>
				</div>

				<div className="attribute-row">
					<label htmlFor="dexterity">Dexterity</label>
					<input id="dexterity" type="text" value={dexterity} readOnly/>
					<div
						className="attribute-modifier">{(Number(dexterityModifier) > 0 ? '+' : '') + dexterityModifier}</div>
					<input
						type="radio"
						name="setTo14"
						value={AttributeEnum.DEXTERITY.valueOf()}
						checked={changedAttribute === AttributeEnum.DEXTERITY.valueOf()}
						onChange={() => changeAttribute(AttributeEnum.DEXTERITY.valueOf())}
					/>
				</div>

				<div className="attribute-row">
					<label htmlFor="constitution">Constitution</label>
					<input id="constitution" type="text" value={constitution} readOnly/>
					<div
						className="attribute-modifier">{(Number(constitutionModifier) > 0 ? '+' : '') + constitutionModifier}</div>
					<input
						type="radio"
						name="setTo14"
						value={AttributeEnum.CONSTITUTION.valueOf()}
						checked={changedAttribute === AttributeEnum.CONSTITUTION.valueOf()}
						onChange={() => changeAttribute(AttributeEnum.CONSTITUTION.valueOf())}
					/>
				</div>

				<div className="attribute-row">
					<label htmlFor="intelligence">Intelligence</label>
					<input id="intelligence" type="text" value={intelligence} readOnly/>
					<div
						className="attribute-modifier">{(Number(intelligenceModifier) > 0 ? '+' : '') + intelligenceModifier}</div>
					<input
						type="radio"
						name="setTo14"
						value={AttributeEnum.INTELLIGENCE.valueOf()}
						checked={changedAttribute === AttributeEnum.INTELLIGENCE.valueOf()}
						onChange={() => changeAttribute(AttributeEnum.INTELLIGENCE.valueOf())}
					/>
				</div>

				<div className="attribute-row">
					<label htmlFor="wisdom">Wisdom</label>
					<input id="wisdom" type="text" value={wisdom} readOnly/>
					<div className="attribute-modifier">{(Number(wisdomModifier) > 0 ? '+' : '') + wisdomModifier}</div>
					<input
						type="radio"
						name="setTo14"
						value={AttributeEnum.WISDOM.valueOf()}
						checked={changedAttribute === AttributeEnum.WISDOM.valueOf()}
						onChange={() => changeAttribute(AttributeEnum.WISDOM.valueOf())}
					/>
				</div>

				<div className="attribute-row">
					<label htmlFor="charisma">Charisma</label>
					<input id="charisma" type="text" value={charisma} readOnly/>
					<div
						className="attribute-modifier">{(Number(charismaModifier) > 0 ? '+' : '') + charismaModifier}</div>
					<input
						type="radio"
						name="setTo14"
						value={AttributeEnum.CHARISMA.valueOf()}
						checked={changedAttribute === AttributeEnum.CHARISMA.valueOf()}
						onChange={() => changeAttribute(AttributeEnum.CHARISMA.valueOf())}
					/>
				</div>

				<div className="attribute-row">
					<label htmlFor="none">None</label>
					<input id="none" type="text" value="" readOnly style={{visibility: 'hidden'}}/>
					<div className="attribute-modifier" style={{visibility: 'hidden'}}></div>
					<input
						type="radio"
						name="setTo14"
						value={AttributeEnum.NONE.valueOf()}
						checked={changedAttribute === AttributeEnum.NONE.valueOf()}
						onChange={() => changeAttribute(AttributeEnum.NONE.valueOf())}
					/>
				</div>
			</div>
			<button onClick={rollAttributes}>Roll Attributes</button>
		</div>
	)
}

export default Attributes
