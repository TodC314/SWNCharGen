/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

import {useState, useEffect} from 'react'
import './Details.css'

/**
 * Enumeration of character details used in the SWN system
 * @enum {string}
 */
const DetailEnum = {
	NAME: "NAME",
	NONE: "NONE"
}

/**
 * Component for displaying and managing character details
 * Currently handles the character's name
 * 
 * @param {Object} props - Component props
 * @param {Object} props.character - Character data object
 * @param {Function} props.onChangeDetails - Function to handle changing character details
 * @returns {JSX.Element} The rendered Details component
 */
function Details({character, onChangeDetails})
{
	/**
	 * State variables for character details
	 */
	// Character name
	const [name, setName] = useState('')

	/**
	 * Updates local state when character data changes
	 * Syncs component state with the character data received from props
	 */
	useEffect(() =>
	{
		if (character)
		{
			// details
			setName(character.mName)
		}
	}, [character])

	/**
	 * Handles changing a character detail
	 * 
	 * @param {string} detailId - The identifier of the detail to change
	 * @param {string} detailValue - The new value for the detail
	 */
	const changeDetails = (detailId, detailValue) =>
	{
		onChangeDetails(detailId, detailValue)
	}

	return (
		<div className="details-form">
			<h2>Details</h2>
			<div className="details-table">
				<div className="details-header">
					<div className="detail-label">Detail</div>
					<div className="detail-value-label">Value</div>
				</div>
				<div className="detail-row">
					<label htmlFor="name">Name</label>
					<input
						id="name"
						type="text"
						defaultValue={name}
						onBlur={(eventElement) =>
						{
							if (eventElement.target.value !== name)
							{
								changeDetails(DetailEnum.NAME, eventElement.target.value)
							}
						}}
					/>
				</div>
			</div>
		</div>
	)
}

export default Details
