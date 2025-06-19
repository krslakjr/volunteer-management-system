import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import './EngagementStatistics.css';
import EventsService from '../services/events.service';
import VolunteerService from '../services/volunteer.service';

const AnimatedCounter = ({ endValue, duration = 1000 }) => {
    const [count, setCount] = useState(0);
    const ref = useRef(null);
    const started = useRef(false);

    useEffect(() => {
        const observer = new IntersectionObserver(
            (entries) => {
                entries.forEach((entry) => {
                    if (entry.isIntersecting && !started.current) {
                        started.current = true;
                        let startTimestamp;
                        const animate = (timestamp) => {
                            if (!startTimestamp) startTimestamp = timestamp;
                            const progress = Math.min((timestamp - startTimestamp) / duration, 1);
                            const currentCount = (typeof endValue === 'number' && !Number.isInteger(endValue))
                                ? parseFloat((progress * endValue).toFixed(1))
                                : Math.floor(progress * endValue);
                            setCount(currentCount);

                            if (progress < 1) {
                                requestAnimationFrame(animate);
                            }
                        };
                        requestAnimationFrame(animate);
                    }
                });
            },
            { threshold: 0.5 }
        );

        if (ref.current) {
            observer.observe(ref.current);
        }

        return () => {
            if (ref.current) {
                observer.unobserve(ref.current);
            }
        };
    }, [endValue, duration]);

    const formattedCount = typeof endValue === 'string' && endValue.includes(',')
        ? count.toLocaleString()
        : (typeof endValue === 'number' && !Number.isInteger(endValue))
            ? count.toFixed(1)
            : count.toString();

    return <span ref={ref}>{formattedCount}</span>;
};

const EngagementStatisticsCard = ({ icon, title, valueComponent, subtitle, onClick, isClickable }) => {
    return (
        <div className="stat-card" onClick={isClickable ? onClick : null} style={{ cursor: isClickable ? 'pointer' : 'default' }}>
            <div className="stat-icon">{icon}</div>
            <h3>{title}</h3>
            <p className="stat-value">{valueComponent}</p>
            <span>{subtitle}</span>
            {isClickable && <button className="view-details-button">View Details</button>}
        </div>
    );
};


function EngagementStatistics() {
    const navigate = useNavigate();
    const [loadingData, setLoadingData] = useState(true);
    const [errorData, setErrorData] = useState(null);

    const [stats, setStats] = useState([
        { title: 'Total Registered Volunteers', icon: '👥', subtitle: 'Registered Volunteers', rawValue: 0, detailsLink: '/volunteers/manage' },
        { title: 'Completed Activities', icon: '✅', subtitle: 'Completed Activities', rawValue: 0, detailsLink: '/activities/available' },
        { title: 'Active Events', icon: '🎉', subtitle: 'Active Events', rawValue: 0, detailsLink: '/events' },
        { title: 'Average Volunteer Hours', icon: '⏰', subtitle: 'per Month', rawValue: 12.5, detailsLink: '/reports/engagement' },
    ]);

    const [recentActivities, setRecentActivities] = useState([]);
    const [topVolunteers, setTopVolunteers] = useState([]);

    useEffect(() => {
        const fetchAllEngagementData = async () => {
            setLoadingData(true);
            setErrorData(null);

            try {
                const eventsResponse = await EventsService.getAllEvents();
                const allEvents = eventsResponse.data.content || eventsResponse.data;

                const today = new Date();
                today.setHours(0, 0, 0, 0);

                const pastEvents = allEvents.filter(event => {
                    const eventDate = new Date(event.date);
                    eventDate.setHours(0, 0, 0, 0);
                    return eventDate < today;
                });

                const upcomingEvents = allEvents.filter(event => {
                    const eventDate = new Date(event.date);
                    eventDate.setHours(0, 0, 0, 0);
                    return eventDate >= today;
                });

                const formattedCompletedEvents = pastEvents.map(event => ({
                    id: event.activityId,
                    name: event.description,
                    category: 'N/A', 
                    volunteers: event.volunteersNeeded, 
                    date: new Date(event.date).toLocaleDateString('en-US', {
                        year: 'numeric',
                        month: 'short',
                        day: 'numeric'
                    }),
                    status: 'Completed'
                }));
                setRecentActivities(formattedCompletedEvents);

                const volunteerResponse = await VolunteerService.getAllVolunteers();
                const allVolunteers = volunteerResponse.data;

                const volunteersWithRandomHours = allVolunteers.map(vol => ({
                    id: vol.volunteerId,
                    name: vol.name, 
                    hours: Math.floor(Math.random() * (150 - 20 + 1)) + 20
                }));

                const sortedVolunteers = volunteersWithRandomHours.sort((a, b) => b.hours - a.hours);

                const top5Volunteers = sortedVolunteers.slice(0, 5);
                setTopVolunteers(top5Volunteers);


                setStats(prevStats => {
                    const newStats = [...prevStats];

                    const updateStat = (title, rawValue, newValue) => {
                        const index = newStats.findIndex(s => s.title === title);
                        if (index !== -1) {
                            newStats[index] = {
                                ...newStats[index],
                                value: newValue !== undefined ? newValue.toString() : newStats[index].value,
                                rawValue: rawValue
                            };
                        }
                    };

                    updateStat('Total Registered Volunteers', allVolunteers.length, allVolunteers.length);
                    updateStat('Completed Activities', pastEvents.length, pastEvents.length);
                    updateStat('Active Events', upcomingEvents.length, upcomingEvents.length);
                    
                    return newStats;
                });

            } catch (err) {
                const resError =
                    (err.response &&
                        err.response.data &&
                        err.response.data.message) ||
                    err.message ||
                    err.toString();
                setErrorData(`Error fetching engagement data: ${resError}`);
                console.error('Error fetching engagement data:', err);

                setStats(prevStats => prevStats.map(stat => ({ ...stat, value: 'Error', rawValue: 0 })));
                setRecentActivities([]);
                setTopVolunteers([]);
            } finally {
                setLoadingData(false);
            }
        };

        fetchAllEngagementData();
    }, []); 

    const renderStatValue = (stat) => {
        if (loadingData && stat.rawValue === 0) {
            return <span>Loading...</span>;
        }
        if (errorData) {
            return <span>Error</span>;
        }
        return <AnimatedCounter endValue={stat.rawValue} />;
    };


    return (
        <div className="engagement-statistics-container">
            <h1>Engagement Statistics</h1>

            <div className="statistics-grid">
                {stats.map((stat, index) => (
                    <EngagementStatisticsCard
                        key={index}
                        icon={stat.icon}
                        title={stat.title}
                        valueComponent={renderStatValue(stat)}
                        subtitle={stat.subtitle}
                        onClick={() => navigate(stat.detailsLink)}
                        isClickable={true}
                    />
                ))}
            </div>

            <div className="volunteer-engagement-trends">
                <h2>Volunteer Engagement Trends</h2>
                <div className="chart-placeholder">
                    <p className="chart-title-text">Annual Volunteer Engagement Overview</p>
                    <div className="chart-grid">
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-jan"></div>
                            <span className="bar-label">Jan</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-feb"></div>
                            <span className="bar-label">Feb</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-mar"></div>
                            <span className="bar-label">Mar</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-apr"></div>
                            <span className="bar-label">Apr</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-may"></div>
                            <span className="bar-label">May</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-jun"></div>
                            <span className="bar-label">Jun</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-jul"></div>
                            <span className="bar-label">Jul</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-aug"></div>
                            <span className="bar-label">Aug</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-sep"></div>
                            <span className="bar-label">Sep</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-oct"></div>
                            <span className="bar-label">Oct</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-nov"></div>
                            <span className="bar-label">Nov</span>
                        </div>
                        <div className="chart-bar-group">
                            <div className="chart-bar bar-dec"></div>
                            <span className="bar-label">Dec</span>
                        </div>
                    </div>
                    <p className="chart-footer-text">Visualizing over 1200 volunteer hours.</p>
                </div>
            </div>

            <div className="recent-activity-completion">
                <h2>Recent Activity Completion</h2>
                {loadingData ? (
                    <p>Učitavanje završenih aktivnosti...</p>
                ) : errorData ? (
                    <p className="error-message">{errorData}</p>
                ) : recentActivities.length === 0 ? (
                    <p>Nema nedavno završenih aktivnosti.</p>
                ) : (
                    <table>
                        <thead>
                            <tr>
                                <th>Activity</th>
                                <th>Category</th>
                                <th>Volunteers</th>
                                <th>Date</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {recentActivities.map((activity) => (
                                <tr key={activity.id}>
                                    <td>{activity.name}</td>
                                    <td>{activity.category}</td>
                                    <td>{activity.volunteers} volunteers</td>
                                    <td>{activity.date}</td>
                                    <td><span className="status-badge completed">{activity.status}</span></td>
                                    <td><button className="action-icon">...</button></td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
                <button className="view-all-activities-button" onClick={() => navigate('/activities/available')}>View All Activities</button>
            </div>

            <div className="top-volunteers-section">
                <h2>Top Volunteers</h2>
                {loadingData ? (
                    <p>Učitavanje top volontera...</p>
                ) : errorData ? (
                    <p className="error-message">{errorData}</p>
                ) : topVolunteers.length === 0 ? (
                    <p>Nema top volontera za prikaz.</p>
                ) : (
                    <div className="top-volunteers-list">
                        {topVolunteers.map((volunteer) => (
                            <div className="top-volunteer-item" key={volunteer.id}>
                                <div className="volunteer-avatar-medium"></div>
                                <div className="volunteer-details">
                                    <h3>{volunteer.name}</h3>
                                    <p>{volunteer.hours} hours this month</p>
                                </div>
                                <button className="action-icon">⭐</button>
                            </div>
                        ))}
                    </div>
                )}
                <button className="view-all-volunteers-button" onClick={() => navigate('/volunteers/manage')}>View All Volunteers</button>
            </div>
        </div>
    );
}

export default EngagementStatistics;