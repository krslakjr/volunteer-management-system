import React, { useState } from 'react';
import './Notification.css'; // Create this CSS file

function Notifications() {
  const [notifications, setNotifications] = useState({
    today: [
      { id: 1, type: 'opportunity', message: 'New volunteer opportunity: Beach Cleanup needs 5 more volunteers', time: 'Just now' },
      { id: 2, type: 'schedule', message: 'Schedule change: Community Garden event moved to 2PM', time: '1 hour ago' },
      { id: 3, type: 'reminder', message: 'Activity reminder: Teaching workshop starts tomorrow at 10AM', time: '2 hours ago' },
    ],
    yesterday: [
      { id: 4, type: 'milestone', message: 'Impact milestone reached! You’ve completed 50 volunteer hours!', time: 'Yesterday' },
      { id: 5, type: 'badge', message: 'New skill badge earned! You’ve earned the "Team Leader" badge', time: 'Yesterday' },
    ],
    earlierThisWeek: [
      { id: 6, type: 'feedback', message: 'Feedback requested: Please rate your experience at Food Bank event', time: '3 days ago' },
      { id: 7, type: 'message', message: 'New message: Event coordinator sent you a message', time: '4 days ago' },
    ],
  });

  const [notificationPreferences, setNotificationPreferences] = useState(true); // Example for a general setting

  const handleMarkAllAsRead = () => {
    // Implement logic to mark all notifications as read
    console.log('Marking all notifications as read');
    setNotifications({
      today: notifications.today.map(n => ({ ...n, read: true })),
      yesterday: notifications.yesterday.map(n => ({ ...n, read: true })),
      earlierThisWeek: notifications.earlierThisWeek.map(n => ({ ...n, read: true })),
    });
    alert('All notifications marked as read (check console)');
  };

  return (
    <div className="notifications-container">
      <div className="breadcrumb">Home &gt; Notifications</div>
      <h1>Notifications</h1>

      <div className="notifications-header-actions">
        <div className="notification-filters">
          <button className="filter-button active">All</button>
          <button className="filter-button">Unread</button>
          <button className="filter-button">Activities</button>
          <button className="filter-button">Reminders</button>
          <button className="filter-button">System</button>
        </div>
        <div className="search-and-mark">
          <input type="text" placeholder="Search notifications" />
          <button className="mark-all-read-button" onClick={handleMarkAllAsRead}>Mark all as read</button>
        </div>
      </div>

      {notifications.today.length > 0 && (
        <div className="notification-group">
          <h2>Today</h2>
          {notifications.today.map((notification) => (
            <div className="notification-item" key={notification.id}>
              <div className="notification-icon">💡</div> {/* Replace with specific icons */}
              <div className="notification-content">
                <p>{notification.message}</p>
                <span className="notification-time">{notification.time}</span>
              </div>
              <button className="notification-action">...</button> {/* Example action button */}
            </div>
          ))}
        </div>
      )}

      {notifications.yesterday.length > 0 && (
        <div className="notification-group">
          <h2>Yesterday</h2>
          {notifications.yesterday.map((notification) => (
            <div className="notification-item" key={notification.id}>
              <div className="notification-icon">✅</div> {/* Replace with specific icons */}
              <div className="notification-content">
                <p>{notification.message}</p>
                <span className="notification-time">{notification.time}</span>
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
            <div className="notification-item" key={notification.id}>
              <div className="notification-icon">💬</div> {/* Replace with specific icons */}
              <div className="notification-content">
                <p>{notification.message}</p>
                <span className="notification-time">{notification.time}</span>
              </div>
              <button className="notification-action">...</button>
            </div>
          ))}
        </div>
      )}

      <button className="load-more-button">Load More</button>

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