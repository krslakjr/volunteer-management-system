import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; 
import EventsService from '../services/events.service'; 
import './CreateEventPages.css'; 

function CreateEventPages() {
    const navigate = useNavigate(); 
    const [eventData, setEventData] = useState({
        description: '', 
        date: '',       
        location: '',   
        volunteersNeeded: '', 
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEventData(prevData => ({ ...prevData, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setSuccessMessage('');

        if (!eventData.description || !eventData.date || !eventData.location || eventData.volunteersNeeded === '') {
            setError('Please fill in all required fields, including Volunteers Needed.');
            setLoading(false);
            return;
        }


        const dataToSend = {
            description: eventData.description,
            date: eventData.date, 
            location: eventData.location,
            volunteersNeeded: parseInt(eventData.volunteersNeeded, 10), 
        };

        try {
            await EventsService.createEvent(dataToSend); 
            setSuccessMessage('Event created successfully!');
            // Resetuj formu
            setEventData({
                description: '',
                date: '',
                location: '',
                volunteersNeeded: '',
            });
            setTimeout(() => {
                navigate('/events');
            }, 1500);
        } catch (err) {
            const resError =
                (err.response &&
                    err.response.data &&
                    err.response.data.message) ||
                err.message ||
                err.toString();
            setError(`Failed to create event: ${resError}`);
            console.error("Error creating event:", err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="create-event-container content-area">
            <h1 className="page-title">Create New Event</h1>
            {successMessage && <div className="alert alert-success">{successMessage}</div>}
            {error && <div className="alert alert-danger">{error}</div>}
            
            <form onSubmit={handleSubmit} className="create-event-form">
                <div className="form-group">
                    <label htmlFor="description">Event Name (Description):</label> 
                    <input
                        type="text"
                        id="description"
                        name="description" 
                        value={eventData.description}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="date">Date & Time:</label>
                    <input
                        type="datetime-local"
                        id="date"
                        name="date" 
                        value={eventData.date}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="location">Location:</label>
                    <input
                        type="text"
                        id="location"
                        name="location"
                        value={eventData.location}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="volunteersNeeded">Volunteers Needed:</label> 
                    <input
                        type="number"
                        id="volunteersNeeded"
                        name="volunteersNeeded" 
                        value={eventData.volunteersNeeded}
                        onChange={handleChange}
                        min="1" 
                        required
                    />
                </div>
                
                <button type="submit" className="submit-button" disabled={loading}>
                    {loading ? 'Creating...' : 'Create Event'}
                </button>
                <button type="button" className="cancel-button" onClick={() => navigate('/events')}>
                    Cancel
                </button>
            </form>
        </div>
    );
}

export default CreateEventPages;