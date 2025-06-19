import React, { useState, useEffect } from 'react';
import './Notification.css';
import NotificationService from '../services/notification.service'; 
import { formatDistanceToNow, parseISO } from 'date-fns'; 

function Notifications() {
    const [notifications, setNotifications] = useState({
        today: [],
        yesterday: [],
        earlierThisWeek: [],
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [filter, setFilter] = useState('all'); 

    const fetchNotifications = async (page = 0, currentFilter = 'all') => {
        setLoading(true);
        setError(null);
        try {
            const response = await NotificationService.getAllNotifications(page, 10);
            const fetchedNotifications = response.data.content || response.data; 

            const today = new Date();
            today.setHours(0, 0, 0, 0); 

            const yesterday = new Date(today);
            yesterday.setDate(today.getDate() - 1);

            const earlierThisWeek = new Date(today);
            earlierThisWeek.setDate(today.getDate() - (today.getDay() === 0 ? 6 : today.getDay() - 1));
            earlierThisWeek.setHours(0, 0, 0, 0);


            const grouped = {
                today: [],
                yesterday: [],
                earlierThisWeek: [],
            };

            const filteredNotifications = fetchedNotifications.filter(notification => {
                if (currentFilter === 'unread') return !notification.read;
                if (currentFilter === 'activities' && notification.type !== 'Activity') return false; 
                if (currentFilter === 'reminders' && notification.type !== 'Reminder') return false; 
                if (currentFilter === 'system' && (notification.type !== 'Announcement' && notification.type !== 'Milestone' && notification.type !== 'Badge')) return false;

                return true;
            });


            filteredNotifications.forEach(n => {
                const notificationDate = parseISO(n.timestamp); 

                if (notificationDate >= today) {
                    grouped.today.push(n);
                } else if (notificationDate >= yesterday) {
                    grouped.yesterday.push(n);
                } else if (notificationDate >= earlierThisWeek) {
                    grouped.earlierThisWeek.push(n);
                }
            });

            setNotifications(grouped);
            if (response.data.totalPages) {
                setTotalPages(response.data.totalPages);
            }

        } catch (err) {
            console.error("Error fetching notifications:", err);
            const resError =
                (err.response && err.response.data && err.response.data.message) ||
                err.message ||
                err.toString();
            setError(`Failed to load notifications: ${resError}`);
            setNotifications({ today: [], yesterday: [], earlierThisWeek: [] }); 
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchNotifications(currentPage, filter);
    }, [currentPage, filter]); 

    const handleMarkAllAsRead = async () => {
        setLoading(true);
        setError(null);
        try {
            const allFetchedNotificationsResponse = await NotificationService.getAllNotifications(0, 1000); 
            const allFetchedNotifications = allFetchedNotificationsResponse.data.content || allFetchedNotificationsResponse.data;

            const unreadNotifications = allFetchedNotifications.filter(n => !n.read);

            await Promise.all(unreadNotifications.map(n => NotificationService.markNotificationAsRead(n.notificationId)));

            alert('All unread notifications marked as read!');
            fetchNotifications(currentPage, filter);
        } catch (err) {
            console.error("Error marking all notifications as read:", err);
            const resError =
                (err.response && err.response.data && err.response.data.message) ||
                err.message ||
                err.toString();
            setError(`Failed to mark all notifications as read: ${resError}`);
        } finally {
            setLoading(false);
        }
    };

    const handleLoadMore = () => {
        if (currentPage < totalPages - 1) {
            setCurrentPage(prevPage => prevPage + 1);
        }
    };

    const getNotificationIcon = (type) => {
        switch (type) {
            case 'Reminder':
                return '⏰';
            case 'Announcement':
                return '📣';
            default:
                return '💡'; 
        }
    };


    return (
        <div className="notifications-container">
            <h1>Notifications</h1>

            <div className="notifications-header-actions">
                <div className="notification-filters">
                    <button className={`filter-button ${filter === 'all' ? 'active' : ''}`} onClick={() => setFilter('all')}>All</button>
                    <button className={`filter-button ${filter === 'unread' ? 'active' : ''}`} onClick={() => setFilter('unread')}>Unread</button>
                    <button className={`filter-button ${filter === 'activities' ? 'active' : ''}`} onClick={() => setFilter('activities')}>Activities</button>
                    <button className={`filter-button ${filter === 'reminders' ? 'active' : ''}`} onClick={() => setFilter('reminders')}>Reminders</button>
                    <button className={`filter-button ${filter === 'system' ? 'active' : ''}`} onClick={() => setFilter('system')}>System</button>
                </div>
            </div>

            {loading && <p>Loading notification...</p>}
            {error && <p className="error-message">{error}</p>}

            {!loading && !error && Object.keys(notifications).every(key => notifications[key].length === 0) && (
                <p>Zero notification to display.</p>
            )}

            {notifications.today.length > 0 && (
                <div className="notification-group">
                    <h2>Today</h2>
                    {notifications.today.map((notification) => (
                        <div className="notification-item" key={notification.notificationId} style={{ fontWeight: notification.read ? 'normal' : 'bold' }}>
                            <div className="notification-icon">{getNotificationIcon(notification.type)}</div>
                            <div className="notification-content">
                                <p>{notification.message}</p>
                                <span className="notification-time">{formatDistanceToNow(parseISO(notification.timestamp), { addSuffix: true })}</span>
                            </div>
                            <button className="notification-action">...</button> 
                        </div>
                    ))}
                </div>
            )}

            {notifications.yesterday.length > 0 && (
                <div className="notification-group">
                    <h2>Yesterday</h2>
                    {notifications.yesterday.map((notification) => (
                        <div className="notification-item" key={notification.notificationId} style={{ fontWeight: notification.read ? 'normal' : 'bold' }}>
                            <div className="notification-icon">{getNotificationIcon(notification.type)}</div>
                            <div className="notification-content">
                                <p>{notification.message}</p>
                                <span className="notification-time">{formatDistanceToNow(parseISO(notification.timestamp), { addSuffix: true })}</span>
                            </div>
                            <button className="notification-action">...</button>
                        </div>
                    ))}
                </div>
            )}

            {notifications.earlierThisWeek.length > 0 && (
                <div className="notification-group">
                    <h2>Earlier this week</h2>
                    {notifications.earlierThisWeek.map((notification) => (
                        <div className="notification-item" key={notification.notificationId} style={{ fontWeight: notification.read ? 'normal' : 'bold' }}>
                            <div className="notification-icon">{getNotificationIcon(notification.type)}</div>
                            <div className="notification-content">
                                <p>{notification.message}</p>
                                <span className="notification-time">{formatDistanceToNow(parseISO(notification.timestamp), { addSuffix: true })}</span>
                            </div>
                            <button className="notification-action">...</button>
                        </div>
                    ))}
                </div>
            )}

            {currentPage < totalPages - 1 && (
                <button className="load-more-button" onClick={handleLoadMore} disabled={loading}>Load More</button>
            )}

            <div className="notification-preferences-section">
                <div className="preference-icon">🔔</div>
                <h3>Notification Preferences</h3>
                <p>Control how and when you receive notifications from Volunteer Hub</p>
                <button className="manage-settings-button">Manage Settings</button>
            </div>
        </div>
    );
}

export default Notifications;