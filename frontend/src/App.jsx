/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

import {useState, useEffect, Fragment, JSX} from 'react'
import axios from 'axios'
import './App.css'
import Attributes from './components/Attributes'
import Details from './components/Details'

/**
 * Main application component for the SWN Character Generator.
 * Manages character data state and provides functionality for creating, modifying,
 * downloading, and uploading character data.
 * 
 * @returns {JSX.Element} The rendered App component
 */
function App()
{
	/**
	 * State variables for managing character data and UI state
	 */
	// Character data from the API
	const [character, setCharacter] = useState(null)
	// Loading state for API operations
	const [loading, setLoading] = useState(true)
	// Error message state
	const [error, setError] = useState('')

	/**
	 * Fetch character data when component mounts
	 */
	useEffect(() =>
	{
		handleFetchCharacter()
	}, [])

	/**
	 * Fetches character data from the API
	 * Sets loading state during fetch and updates character data or error state based on response
	 */
	const handleFetchCharacter = () =>
	{
		setLoading(true)
		axios.get('/api/character')
			.then(response =>
			{
				setCharacter(response.data)
				setLoading(false)
				setError('')
			})
			.catch(error =>
			{
				console.error('Error fetching character:', error)
				setLoading(false)
				setError('Failed to load character data')
			})
	}

	/**
	 * Changes a character attribute to a specified value (14)
	 * 
	 * @param {string} newAttributeName - The name of the attribute to change
	 */
	const handleChangedAttribute = (newAttributeName) =>
	{
		setLoading(true)
		axios.post('/api/change-attribute', {attribute: newAttributeName})
			.then(response =>
			{
				setCharacter(response.data)
				setLoading(false)
				setError('')
			})
			.catch(error =>
			{
				console.error('Error changing attribute:', error)
				setError('Failed to change attribute')
			})
	}

	/**
	 * Creates a new character by calling the API
	 * Updates the character state with the new character data
	 */
	const handleNewCharacter = () =>
	{
		setLoading(true)
		axios.get('/api/new-character')
			.then(response =>
			{
				setCharacter(response.data)
				setLoading(false)
				setError('')
			})
			.catch(error =>
			{
				console.error('Error on new character:', error)
				setLoading(false)
				setError('Failed to load new character data')
			})
	}

	/**
	 * Rolls new random attributes for the character
	 * Updates the character state with the new attribute values
	 */
	const handleRollAttributes = () =>
	{
		setLoading(true)
		axios.get('/api/roll-attributes')
			.then(response =>
			{
				setCharacter(response.data)
				setLoading(false)
				setError('')
			})
			.catch(error =>
			{
				console.error('Error rolling attributes:', error)
				setLoading(false)
				setError('Failed to roll attributes')
			})
	}

	/**
	 * Updates a character detail with a new value
	 * 
	 * @param {string} detailId - The identifier of the detail to change
	 * @param {string} detailValue - The new value for the detail
	 */
	const handleChangedDetails = (detailId, detailValue) =>
	{
		setLoading(true)
		axios.post('/api/set-detail', {detail: detailId, value: detailValue})
			.then(response =>
			{
				setCharacter(response.data)
				setLoading(false)
				setError('')
			})
			.catch(error =>
			{
				console.error('Error setting detail:', error)
				setError('Failed to set detail')
			})
	}

	/**
	 * Downloads the current character data as a JSON file
	 * Creates a blob from the API response and triggers a file download
	 */
	const handleDownloadCharacter = async () =>
	{
		setLoading(true);
		try
		{
			const response = await axios.get('/api/download-character', {
				responseType: 'blob',
				onDownloadProgress: (progressEvent) =>
				{
					// Optional: Handle download progress
					const percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
					console.log(`Download Progress: ${percentCompleted}%`);
				},
			});

			const blob = new Blob([response.data], {
				type: response.headers['content-type']
			});
			const url = window.URL.createObjectURL(blob);
			const link = document.createElement('a');
			link.href = url;

			const contentDisposition = response.headers['content-disposition'];
			const filename = contentDisposition
				? contentDisposition.split('filename=')[1].replace(/['"]/g, '')
				: 'character.json';

			link.setAttribute('download', filename);
			document.body.appendChild(link);
			link.click();
			document.body.removeChild(link);
			window.URL.revokeObjectURL(url);

			setError('');
		}
		catch (error)
		{
			console.error('Error downloading character:', error);
			setError('Failed to download character data');
		}
		finally
		{
			setLoading(false);
		}
	};

	/**
	 * Uploads a character JSON file and updates the character state
	 * Validates that the file is a JSON file before uploading
	 * 
	 * @param {React.ChangeEvent<HTMLInputElement>} event - The file input change event
	 */
	const handleUploadCharacter = async (event) =>
	{
		const file = event.target.files[0];
		if (!file)
		{
			return;
		}

		// Check if the file is a JSON file
		if (!file.name.endsWith('.json'))
		{
			setError('File must be a JSON file');
			return;
		}

		setLoading(true);

		// Create a FormData object to send the file
		const formData = new FormData();
		formData.append('file', file);

		try
		{
			const response = await axios.post('/api/upload-character', formData, {
				headers: {'Content-Type': 'multipart/form-data'}
			});
			setCharacter(response.data);
			setError('');
		}
		catch (error)
		{
			console.error('Error uploading character:', error);
			setError(error.response?.data?.error || 'Failed to upload character data');
		}
		finally
		{
			setLoading(false);
			// Reset the file input
			event.target.value = '';
		}
	};


	return (
		<div className="App">
			<h1>Character Management</h1>

			<div className="character-actions">
				<button onClick={handleNewCharacter}>New Character</button>
				{character && <button onClick={handleDownloadCharacter}>Download Character</button>}
				<div className="upload-container">
					<label htmlFor="upload-character" className="upload-label">
						Upload Character
					</label>
					<input
						type="file"
						id="upload-character"
						accept=".json"
						onChange={handleUploadCharacter}
						style={{display: 'none'}}
					/>
				</div>
			</div>

			{loading ? (
				<p>Loading character data...</p>
			) : error ? (
				<div>
					<p className="error">{error}</p>
					<button onClick={handleFetchCharacter}>Retry</button>
				</div>
			) : (
				<Fragment>
					<Details
						character={character}
						onChangeDetails={handleChangedDetails}
					/>
					<Attributes
						character={character}
						onRollAttributes={handleRollAttributes}
						onChangeAttribute={handleChangedAttribute}
					/>
				</Fragment>
			)}
		</div>
	)
}

export default App
