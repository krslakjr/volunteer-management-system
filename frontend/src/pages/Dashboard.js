import React, { useEffect, useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

import EngagementStatisticsCard from '../pages/EngagementStatisticsCard';
import RecentActivityCompletion from '../pages/RecentActivityCompletion';
import EventsService from '../services/events.service'; 
import VolunteerService from '../services/volunteer.service';

import './Dashboard.css';

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


const Dashboard = () => {
    const navigate = useNavigate();
    const [completedActivities, setCompletedActivities] = useState([]); 
    const [loadingData, setLoadingData] = useState(true);
    const [errorData, setErrorData] = useState(null); 

    const [stats, setStats] = useState([
        { title: 'Total Registered Volunteers', value: 'Loading...', rawValue: 0, detailsLink: '/volunteers/manage' },
        { title: 'Completed Activities', value: 'Loading...', rawValue: 0, detailsLink: '/activities/available' }, 
        { title: 'Active Events', value: 'Loading...', rawValue: 0, detailsLink: '/events' }, 
        { title: 'Average Volunteer Hours', value: 'Loading...', rawValue: 12.5, subtitle: 'per Month', detailsLink: '/reports/engagement' },
    ]);

    useEffect(() => {
        const fetchAllDashboardData = async () => {
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
                    activity: event.description, 
                    volunteers: event.volunteersNeeded, 
                    date: new Date(event.date).toLocaleDateString('en-US', {
                        year: 'numeric',
                        month: 'short',
                        day: 'numeric'
                    }),
                    status: 'Completed'
                }));

                setCompletedActivities(formattedCompletedEvents); // Postavljamo completed events

                // Dohvaćanje broja volontera pomoću VolunteerService
                const volunteerResponse = await VolunteerService.getAllVolunteers();
                const totalVolunteers = volunteerResponse.data.length; 

                // Ažuriranje svih statistika
                setStats(prevStats => {
                    const newStats = [...prevStats]; 

                    const volunteerStatIndex = newStats.findIndex(s => s.title === 'Total Registered Volunteers');
                    if (volunteerStatIndex !== -1) {
                        newStats[volunteerStatIndex] = { 
                            ...newStats[volunteerStatIndex], 
                            value: totalVolunteers.toString(), 
                            rawValue: totalVolunteers 
                        };
                    }

                    // Ažuriranje 'Completed Activities' (koje su Completed Events)
                    const completedStatIndex = newStats.findIndex(s => s.title === 'Completed Activities');
                    if (completedStatIndex !== -1) {
                        newStats[completedStatIndex] = { 
                            ...newStats[completedStatIndex], 
                            value: formattedCompletedEvents.length.toString(), 
                            rawValue: formattedCompletedEvents.length 
                        };
                    }

                    // Ažuriranje 'Active Events'
                    const activeEventsStatIndex = newStats.findIndex(s => s.title === 'Active Events');
                    if (activeEventsStatIndex !== -1) {
                        newStats[activeEventsStatIndex] = { 
                            ...newStats[activeEventsStatIndex], 
                            value: upcomingEvents.length.toString(), 
                            rawValue: upcomingEvents.length 
                        };
                    }

                    return newStats;
                });

            } catch (err) {
                const resError =
                    (err.response &&
                        err.response.data &&
                        err.response.data.message) ||
                    err.message ||
                    err.toString();
                setErrorData(`Greška prilikom dohvaćanja podataka za dashboard: ${resError}`);
                console.error('Greška pri dohvaćanju dashboard podataka:', err);

                setStats(prevStats => prevStats.map(stat => ({ ...stat, value: 'Error', rawValue: 0 })));
            } finally {
                setLoadingData(false);
            }
        };

        fetchAllDashboardData();
    }, []);

    return (
        <div className="dashboard-container">
            <h1 className="dashboard-title">Welcome to Your Dashboard</h1>

            <div className="stats-grid">
                {stats.map((stat, index) => (
                    <EngagementStatisticsCard
                        key={index}
                        title={stat.title}
                        valueComponent={
                            loadingData && stat.rawValue === 0 ? (
                                <span>Loading...</span>
                            ) : errorData ? (
                                <span>Error</span>
                            ) : (
                                <AnimatedCounter endValue={stat.rawValue} />
                            )
                        }
                        subtitle={stat.subtitle}
                        onClick={() => navigate(stat.detailsLink)}
                        isClickable={true}
                    />
                ))}
            </div>

            <h2 className="section-title">Volunteer Engagement Trends</h2>
            <div className="chart-area-wrapper">
                <div className="chart-placeholder-improved">
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

            <h2 className="section-title">Recent Activity Completion</h2>
            {loadingData ? (
                <p>Učitavanje završenih aktivnosti...</p>
            ) : errorData ? (
                <p className="error-message">{errorData}</p>
            ) : completedActivities.length === 0 ? (
                <p>Nema nedavno završenih aktivnosti.</p>
            ) : (
                <RecentActivityCompletion activities={completedActivities} />
            )}
        </div>
    );
};

export default Dashboard;