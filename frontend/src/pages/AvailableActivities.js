// src/pages/AvailableActivities.js
import React, { useState, useEffect } from 'react';
import './AvailableActivities.css';
import ActivityService from '../services/activity.service'; 
import ParticipationService from '../services/participation.service'; 
import AuthService from '../services/auth.service'; 

function AvailableActivities() {
    const [activities, setActivities] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentUserId, setCurrentUserId] = useState(null);
    const [applicationStatus, setApplicationStatus] = useState({}); 
    const [appliedActivities, setAppliedActivities] = useState([]); 

    const [upcomingSchedule] = useState([
        { id: 201, name: 'Homeless Shelter Assistance', date: 'May 28, 2023', time: '8:00 AM - 12:00 PM', location: 'Downtown Community Center' },
        { id: 202, name: 'River Cleanup Project', date: 'June 5, 2023', time: '9:00 AM - 11:00 AM', location: 'Riverside Park' },
    ]);

    const fetchAppliedActivities = async (volunteerId) => {
        if (!volunteerId) {
            console.warn("No volunteer ID provided for fetching applied activities.");
            setAppliedActivities([]); 
            return;
        }
        try {
            const response = await ParticipationService.getParticipationsByVolunteerId(volunteerId);
            const formattedApplied = response.data.map(p => ({
                id: p.id, 
                activityId: p.activity.activityId, 
                name: p.activity.description,
                appliedOn: new Date(p.timestamp).toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' }),
                status: p.status || 'Pending Approval'
            }));
            setAppliedActivities(formattedApplied);
            console.log("Successfully fetched applied activities:", formattedApplied);
        } catch (err) {
            console.warn("Could not fetch applied activities from backend. Using mock data for 'Recently Applied Activities' section. Error:", err);
            const resError = (err.response && err.response.data) || err.message || err.toString();

            setAppliedActivities([
                { id: 101, name: 'Beach Cleanup (Mock)', category: 'Environmental', appliedOn: 'May 15, 2023', status: 'Pending Approval' },
                { id: 102, name: 'Homeless Shelter (Mock)', category: 'Community', appliedOn: 'May 13, 2023', status: 'Approved' },
            ]);
        }
    };


    useEffect(() => {
        const user = AuthService.getCurrentUser();
        if (user && user.id) {
            setCurrentUserId(user.id);
            fetchAppliedActivities(user.id);
        } else {
            setError("You must be logged in to view and apply for activities. Please log in.");
            setLoading(false);
            return;
        }

        const fetchActivities = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await ActivityService.getAllActivities();
                setActivities(response.data.content || response.data);
            } catch (err) {
                const resError =
                    (err.response &&
                        err.response.data &&
                        (err.response.data.message || err.response.data.error)) ||
                    err.message ||
                    err.toString();
                setError(`Error fetching activities: ${resError}. Please check if backend services are running.`);
                console.error('Error fetching activities:', err);
            } finally {
                setLoading(false);
            }
        };

        fetchActivities();

    }, []); 


    const handleApplyNow = async (activityId) => {
        if (!currentUserId) {
            alert("You must be logged in to apply for an activity.");
            return;
        }

        const alreadyApplied = appliedActivities.some(p => p.activityId === activityId && p.status !== 'Cancelled');
        if (alreadyApplied) {
            setApplicationStatus(prev => ({ ...prev, [activityId]: { status: 'info', message: 'You have already applied for this activity.' } }));
            setTimeout(() => { setApplicationStatus(prev => ({ ...prev, [activityId]: null })); }, 3000);
            return;
        }


        setApplicationStatus(prev => ({ ...prev, [activityId]: { status: 'loading', message: 'Applying...' } }));
        try {
            const response = await ParticipationService.applyForActivity(activityId, currentUserId);
            console.log("Apply response:", response);

            if (response.status === 200 || response.status === 201) {
                setApplicationStatus(prev => ({ ...prev, [activityId]: { status: 'success', message: 'Successfully applied!' } }));
                await fetchAppliedActivities(currentUserId); 
            } else {
                setApplicationStatus(prev => ({ ...prev, [activityId]: { status: 'error', message: `Application failed: ${response.data || 'Unknown error'}` } }));
            }
        } catch (err) {
            const resError =
                (err.response && err.response.data &&
                    (typeof err.response.data === 'string' ? err.response.data : (err.response.data.message || err.response.data.error))) ||
                err.message ||
                err.toString();

            let displayMessage = `Application failed: ${resError}`;

            if (resError.includes("User not authenticated")) {
                displayMessage = "You are not authenticated. Please log in.";
            } else if (resError.includes("already exists for this activity")) {
                 displayMessage = "You have already applied for this activity.";
            }

            setApplicationStatus(prev => ({ ...prev, [activityId]: { status: 'error', message: displayMessage } }));
            console.error('Error applying for activity:', err);
        } finally {
            setTimeout(() => {
                setApplicationStatus(prev => ({ ...prev, [activityId]: null }));
            }, 5000);
        }
    };

    if (loading) {
        return (
            <div className="available-activities-container loading-message-container">
                <p className="loading-message">Loading available activities...</p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="available-activities-container error-message-container">
                <p className="error-message">{error}</p>
            </div>
        );
    }

    return (
        <div className="available-activities-container">
            <h1>Available Volunteer Activities</h1>

            <div className="activity-cards-grid">
                {activities.length === 0 ? (
                    <div className="info-message">No available activities at the moment.</div>
                ) : (
                    activities.map((activity) => (
                        <div className="activity-card" key={activity.activityId}>
                            <div className="activity-icon">💡</div>
                            <h3>{activity.description}</h3>
                            <p className="activity-description">{activity.description}</p>
                            <p><strong>Date:</strong> {new Date(activity.date).toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })}</p>
                            <p><strong>Location:</strong> {activity.location}</p>
                            <p><strong>Volunteers Needed:</strong> {activity.volunteersNeeded}</p>
                            <p><strong>Available Spots:</strong> {activity.availableSpots !== null ? activity.availableSpots : 'N/A'}</p>

                            <div className="activity-actions">
                                <button
                                    className="apply-now-button"
                                    onClick={() => handleApplyNow(activity.activityId)}
                                    disabled={
                                        applicationStatus[activity.activityId]?.status === 'loading' ||
                                        applicationStatus[activity.activityId]?.status === 'success' ||
                                        appliedActivities.some(p => p.activityId === activity.activityId && p.status !== 'Cancelled')
                                    }
                                >
                                    {applicationStatus[activity.activityId]?.status === 'loading' ? 'Applying...' : 'Apply Now'}
                                </button>
                                {applicationStatus[activity.activityId] && (
                                    <p className={`application-message ${applicationStatus[activity.activityId].status}`}>
                                        {applicationStatus[activity.activityId].message}
                                    </p>
                                )}
                                {appliedActivities.some(p => p.activityId === activity.activityId && p.status !== 'Cancelled') &&
                                 !applicationStatus[activity.activityId] && (
                                    <p className="application-message info">Already Applied</p>
                                )}
                            </div>
                        </div>
                    ))
                )}
            </div>

            <div className="recently-applied-activities">
                <h2>Recently Applied Activities</h2>
                {appliedActivities.length === 0 && !loading ? (
                    <div className="info-message">No recently applied activities.</div>
                ) : (
                    <table>
                        <thead>
                            <tr>
                                <th>Activity</th>
                                <th>Application Date</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {appliedActivities.map((activity) => (
                                <tr key={activity.id}>
                                    <td>{activity.name}</td>
                                    <td>{activity.appliedOn}</td>
                                    <td><span className={`status-badge ${activity.status.toLowerCase().replace(' ', '-')}`}>{activity.status}</span></td>
                                    <td><button className="action-icon">⚙️</button></td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
            </div>

            <div className="upcoming-volunteer-schedule">
                <h2>Upcoming Volunteer Schedule</h2>
                {upcomingSchedule.length === 0 ? (
                    <div className="info-message">No upcoming scheduled activities.</div>
                ) : (
                    upcomingSchedule.map((event) => (
                        <div className="schedule-item" key={event.id}>
                            <div className="schedule-details">
                                <h4>{event.name}</h4>
                                <p>{event.date} • {event.time} • {event.location}</p>
                            </div>
                            <div className="schedule-actions">
                                <button className="action-icon">📅</button>
                                <button className="action-icon">ℹ️</button>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}

export default AvailableActivities;