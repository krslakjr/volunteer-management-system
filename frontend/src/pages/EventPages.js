import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import EventsService from '../services/events.service';
import './EventPages.css';

const EventPages = () => {
    const navigate = useNavigate();
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [deleteMessage, setDeleteMessage] = useState('');

    const handleCreateEventClick = () => {
        navigate('/events/create');
    };

    const fetchEvents = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await EventsService.getAllEvents();
            const fetchedEvents = response.data.content || response.data; 

            const formattedEvents = fetchedEvents.map(event => {
                const eventDate = new Date(event.date);
                const today = new Date();
                today.setHours(0, 0, 0, 0);

                eventDate.setHours(0, 0, 0, 0);

                const status = eventDate < today ? 'Completed' : 'Upcoming';

                return {
                    activityId: event.activityId, 
                    name: event.description,
                    date: new Date(event.date).toLocaleDateString('en-US', {
                        year: 'numeric',
                        month: 'short',
                        day: 'numeric'
                    }),
                    location: event.location,
                    status: status,
                    volunteersNeeded: event.volunteersNeeded,
                };
            });
            setEvents(formattedEvents);
        } catch (err) {
            const resError =
                (err.response &&
                    err.response.data &&
                    err.response.data.message) ||
                err.message ||
                err.toString();
            setError(`Failed to load events: ${resError}`);
            console.error("Error fetching events:", err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchEvents();
    }, []);

    const handleDeleteEvent = async (eventId, eventName) => {
        if (window.confirm(`Are you sure you want to delete the event "${eventName}"? This action cannot be undone.`)) {
            try {
                await EventsService.deleteEvent(eventId);
                setDeleteMessage(`Event "${eventName}" successfully deleted.`);
                fetchEvents();
            } catch (err) {
                const resError =
                    (err.response &&
                        err.response.data &&
                        err.response.data.message) ||
                    err.message ||
                    err.toString();
                setDeleteMessage(`Error deleting event "${eventName}": ${resError}`);
                console.error("Error deleting event:", err);
            }
            setTimeout(() => {
                setDeleteMessage('');
            }, 5000);
        }
    };

    return (
        <div className="events-page-container content-area">
            <div className="events-header">
                <h1 className="page-title">All Events</h1>
                <button className="create-event-button" onClick={handleCreateEventClick}>
                    Create New Event
                </button>
            </div>

            {deleteMessage && (
                <div className={`alert ${error ? 'alert-danger' : 'alert-success'}`}>
                    {deleteMessage}
                </div>
            )}

            <div className="events-list-section">
                {loading ? (
                    <p className="loading-message">Loading events...</p>
                ) : error ? (
                    <p className="error-message">{error}</p>
                ) : events.length > 0 ? (
                    <table className="events-table">
                        <thead>
                            <tr>
                                <th>Event Name</th>
                                <th>Date</th>
                                <th>Location</th>
                                <th>Status</th>
                                <th>Volunteers Needed</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {events.map(event => (
                                <tr key={event.activityId}>
                                    <td>{event.name}</td>
                                    <td>{event.date}</td>
                                    <td>{event.location}</td>
                                    <td>
                                        <span className={`status-badge status-${event.status.toLowerCase()}`}>
                                            {event.status}
                                        </span>
                                    </td>
                                    <td>{event.volunteersNeeded}</td>
                                    <td>
                                        <button
                                            className="table-action-button edit-button"
                                            onClick={() => navigate(`/events/edit/${event.activityId}`)}
                                        >
                                            Edit
                                        </button>
                                        <button
                                            className="table-action-button delete-button"
                                            onClick={() => handleDeleteEvent(event.activityId, event.name)}
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p className="no-events-message">No events found. Start by creating one!</p>
                )}
            </div>
        </div>
    );
};

export default EventPages;