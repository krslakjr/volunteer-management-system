import React, { useState, useEffect } from 'react';
import VolunteerService from '../services/volunteer.service';
import './VolunteerManagement.css';

function VolunteerManagement() {
    const [volunteers, setVolunteers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    
    const [newVolunteer, setNewVolunteer] = useState({
        name: '',     
        contactInfo: '', 
    });

    const fetchVolunteers = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await VolunteerService.getAllVolunteers();
            console.log("Fetched Volunteers:", response.data); 
            setVolunteers(response.data); 
        } catch (err) {
            const resError =
                (err.response &&
                    err.response.data &&
                    err.response.data.message) ||
                err.message ||
                err.toString();
            setError(`Failed to load volunteers: ${resError}`);
            console.error("Error fetching volunteers:", err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchVolunteers();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewVolunteer({ ...newVolunteer, [name]: value });
    };

    const handleAddVolunteer = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setSuccessMessage('');

        if (!newVolunteer.name || !newVolunteer.contactInfo) {
            setError('Name and Contact Info are required.');
            setLoading(false);
            return;
        }

        try {
            await VolunteerService.createVolunteer(newVolunteer);
            setSuccessMessage('Volunteer added successfully!');
            setNewVolunteer({
                name: '',
                contactInfo: '',
            });
            fetchVolunteers(); 
        } catch (err) {
            const resError =
                (err.response &&
                    err.response.data &&
                    err.response.data.message) ||
                err.message ||
                err.toString();
            setError(`Failed to add volunteer: ${resError}`);
            console.error("Error adding volunteer:", err);
        } finally {
            setLoading(false);
            setTimeout(() => {
                setSuccessMessage('');
                setError('');
            }, 5000);
        }
    };

    const handleEditVolunteer = (volunteerId) => {
        console.log("Edit volunteer with volunteerId:", volunteerId);
    };

    const handleDeleteVolunteer = async (volunteerId, volunteerName) => {
        if (window.confirm(`Are you sure you want to delete ${volunteerName}?`)) {
            setLoading(true);
            setError(null);
            setSuccessMessage('');
            try {
                await VolunteerService.deleteVolunteer(volunteerId); 
                setSuccessMessage('Volunteer deleted successfully!');
                fetchVolunteers(); 
            } catch (err) {
                const resError =
                    (err.response && err.response.data && err.response.data.message) ||
                    err.message ||
                    err.toString();
                setError(`Failed to delete volunteer: ${resError}`);
                console.error("Error deleting volunteer:", err);
            } finally {
                setLoading(false);
                setTimeout(() => {
                    setSuccessMessage('');
                    setError('');
                }, 5000);
            }
        }
    };

    return (
        <div className="volunteer-management-container content-area">
            <h1>Volunteer Management</h1>

            {successMessage && <div className="alert alert-success">{successMessage}</div>}
            {error && <div className="alert alert-danger">{error}</div>}

            <div className="volunteer-list">
                <h2>All Volunteers</h2>
                {loading ? (
                    <p className="loading-message">Loading volunteers...</p>
                ) : error ? (
                    <p className="error-message">{error}</p>
                ) : volunteers.length > 0 ? (
                    <table>
                        <thead>
                            <tr>
                                <th>Name</th> 
                                <th>Contact Info</th> 
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {volunteers.map((volunteer) => (
                                <tr key={volunteer.volunteerId}>
                                    <td>{volunteer.name}</td>
                                    <td>{volunteer.contactInfo}</td> 
                                    <td>
                                        <button 
                                            className="action-icon edit-icon"
                                            onClick={() => handleEditVolunteer(volunteer.volunteerId)}
                                        >
                                            ✏️
                                        </button>
                                        <button 
                                            className="action-icon delete-icon"
                                            onClick={() => handleDeleteVolunteer(volunteer.volunteerId, volunteer.name)} 
                                        >
                                            🗑️
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p className="no-volunteers-message">No volunteers found.</p>
                )}
            </div>

            <div className="add-new-volunteer-form">
                <h2>Add New Volunteer</h2>
                <form onSubmit={handleAddVolunteer}>
                    <div className="form-group"> 
                        <label htmlFor="name">Name</label> 
                        <input type="text" id="name" name="name" value={newVolunteer.name} onChange={handleInputChange} placeholder="Enter full name" required />
                    </div>
                    <div className="form-group">
                        <label htmlFor="contactInfo">Contact Info (Email/Phone)</label> 
                        <input type="text" id="contactInfo" name="contactInfo" value={newVolunteer.contactInfo} onChange={handleInputChange} placeholder="Enter email or phone" required />
                    </div>
                    <div className="form-actions">
                        <button type="button" className="cancel-button" onClick={() => setNewVolunteer({ name: '', contactInfo: '' })}>Cancel</button>
                        <button type="submit" className="add-volunteer-submit-button" disabled={loading}>
                            {loading ? 'Adding...' : 'Add Volunteer'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default VolunteerManagement;