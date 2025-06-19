import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import EventsService from '../services/events.service';
import './CreateEventPages'; 

const EditEvent = () => {
    const { id } = useParams(); 
    const navigate = useNavigate();
    const [eventData, setEventData] = useState({
        activityId: null, 
        description: '',
        date: '', 
        location: '',
        volunteersNeeded: '',
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [updateMessage, setUpdateMessage] = useState('');

    useEffect(() => {
        const fetchEventDetails = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await EventsService.getEventById(id);
                const eventDetails = response.data;
                
                let formattedDateForInput = '';
                if (eventDetails.date) {
                    formattedDateForInput = new Date(eventDetails.date).toISOString().split('T')[0];
                }

                setEventData({
                    activityId: eventDetails.activityId, 
                    description: eventDetails.description || '',
                    date: formattedDateForInput,
                    location: eventDetails.location || '',
                    volunteersNeeded: eventDetails.volunteersNeeded !== undefined ? String(eventDetails.volunteersNeeded) : '',
                });
            } catch (err) {
                const resError = (err.response && err.response.data && err.response.data.message) || err.message || err.toString();
                setError(`Failed to load event details: ${resError}`);
                console.error("Error fetching event details:", err);
            } finally {
                setLoading(false);
            }
        };

        fetchEventDetails();
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEventData(prevData => ({ ...prevData, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setUpdateMessage('');
        setError(null);

        if (!eventData.description || !eventData.date || !eventData.location || eventData.volunteersNeeded === '') {
            setError('Please fill in all required fields.');
            return;
        }

        const dateForBackend = `${eventData.date}T00:00:00`; 

        const dataToSend = {
            activityId: eventData.activityId, 
            description: eventData.description,
            date: dateForBackend,
            location: eventData.location,
            volunteersNeeded: parseInt(eventData.volunteersNeeded, 10),
        };

        try {
            await EventsService.updateEvent(dataToSend); 
            setUpdateMessage('Event successfully updated!');
            setTimeout(() => {
                navigate('/events');
            }, 1500);
        } catch (err) {
            const resError = (err.response && err.response.data && err.response.data.message) || err.message || err.toString();
            setError(`Failed to update event: ${resError}`);
            console.error("Error updating event:", err);
        }
    };

    if (loading) {
        return <p className="loading-message">Loading event details...</p>;
    }

    if (error && !updateMessage) { 
        return <p className="error-message">{error}</p>;
    }

    return (
        <div className="create-event-container content-area">
            <h1 className="page-title">Edit Event</h1>
            {updateMessage && <div className="alert alert-success">{updateMessage}</div>}
            {error && <div className="alert alert-danger">{error}</div>}

            <form onSubmit={handleSubmit} className="create-event-form">
                <div className="form-group">
                    <label htmlFor="description">Event Name</label>
                    <input type="text" id="description" name="description" value={eventData.description} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="date">Date</label>
                    <input type="date" id="date" name="date" value={eventData.date} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="location">Location</label>
                    <input type="text" id="location" name="location" value={eventData.location} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="volunteersNeeded">Volunteers Needed</label>
                    <input type="number" id="volunteersNeeded" name="volunteersNeeded" value={eventData.volunteersNeeded} onChange={handleChange} min="0" required />
                </div>
                <button type="submit" className="submit-button">Update Event</button>
                <button type="button" className="cancel-button" onClick={() => navigate('/events')}>Cancel</button>
            </form>
        </div>
    );
};

export default EditEvent;