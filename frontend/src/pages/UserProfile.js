import React, { useState } from 'react';
import './UserProfile.css'; // Create this CSS file

function UserProfile() {
  const [profile] = useState({
    name: 'Sarah Johnson',
    role: 'Environmental Advocate',
    email: 'sarahjohnson@email.com',
    phone: '+1 (555) 123-4567',
    address: '123 Volunteer St. Portland, OR 97204',
  });

  const [skills] = useState([
    'Photography', 'First Aid', 'Community Outreach', 'Translation', 'Event Coordination', 'Project Management',
    'Public Speaking', 'Data Entry', 'Graphic Design', 'Social Media Management', 'Fundraising', 'Mentoring'
  ]);

  const [completedActivities] = useState([
    { id: 1, name: 'Beach Cleanup', category: 'Environmental', date: 'May 15, 2023', hours: 4 },
    { id: 2, name: 'Literacy Program', category: 'Education', date: 'April 22, 2023', hours: 5 },
    { id: 3, name: 'Food Bank Assistance', category: 'Community', date: 'March 10, 2023', hours: 6 },
    { id: 4, name: 'Senior Center Visit', category: 'Healthcare', date: 'February 8, 2023', hours: 3 },
  ]);

  const [registeredActivities] = useState([
    { id: 1, name: 'Park Restoration', category: 'Environmental', date: 'June 12, 2023', status: 'Pending' },
    { id: 2, name: 'Homeless Shelter', category: 'Community', date: 'June 26, 2023', status: 'Confirmed' },
  ]);

  const [emailNotifications, setEmailNotifications] = useState(true);
  const [smsNotifications, setSmsNotifications] = useState(false);

  return (
    <div className="user-profile-container">
      <div className="breadcrumb">Dashboard &gt; User Profile</div>
      <div className="profile-header">
        <div className="profile-card">
          <div className="card-icon">👤</div>
          <h3>Profile Information</h3>
          <p>Manage your personal details and preferences</p>
          <button className="edit-profile-button">Edit Profile</button>
        </div>
        <div className="profile-card">
          <div className="card-icon">✨</div>
          <h3>Skills & Interests</h3>
          <p>12 skills added, 5 interest areas</p>
          <button className="manage-skills-button">Manage Skills</button>
          <button className="add-interests-button">Add Interests</button>
        </div>
        <div className="profile-card">
          <div className="card-icon">📈</div>
          <h3>Impact Summary</h3>
          <p>42 volunteer hours, 8 completed activities</p>
          <button className="view-certificate-button">View Certificate</button>
        </div>
      </div>

      <div className="personal-information-section">
        <h2>Personal Information</h2>
        <div className="profile-detail">
          <div className="avatar-placeholder"></div>
          <div>
            <h3>{profile.name}</h3>
            <p>{profile.role}</p>
          </div>
          <button className="edit-icon">✏️</button>
        </div>
        <p className="contact-info">📧 {profile.email}</p>
        <p className="contact-info">📞 {profile.phone}</p>
        <p className="contact-info">🏠 {profile.address}</p>
      </div>

      <div className="notification-preferences-section">
        <h2>Notification Preferences</h2>
        <div className="notification-toggle">
          <span>Email Notifications</span>
          <label className="switch">
            <input type="checkbox" checked={emailNotifications} onChange={() => setEmailNotifications(!emailNotifications)} />
            <span className="slider round"></span>
          </label>
        </div>
        <p className="notification-description">Receive activity updates via email</p>

        <div className="notification-toggle">
          <span>SMS Alerts</span>
          <label className="switch">
            <input type="checkbox" checked={smsNotifications} onChange={() => setSmsNotifications(!smsNotifications)} />
            <span className="slider round"></span>
          </label>
        </div>
        <p className="notification-description">Get text reminders for upcoming activities</p>
      </div>

      <div className="activity-history-section">
        <div className="tabs">
          <button className="tab-button active">Activity History</button>
          <button className="tab-button">Upcoming Activities</button>
          <button className="tab-button">Saved Activities</button>
        </div>

        <h3>Completed Activities</h3>
        <table>
          <thead>
            <tr>
              <th>Activity</th>
              <th>Environmental</th>
              <th>Date</th>
              <th>Hours</th>
              <th></th> {/* For actions */}
            </tr>
          </thead>
          <tbody>
            {completedActivities.map((activity) => (
              <tr key={activity.id}>
                <td>{activity.name}</td>
                <td>{activity.category}</td>
                <td>{activity.date}</td>
                <td>{activity.hours} hours</td>
                <td><button className="action-icon">⭐</button></td>
              </tr>
            ))}
          </tbody>
        </table>

        <h3>Registered Activities</h3>
        <table>
          <thead>
            <tr>
              <th>Activity</th>
              <th>Environmental</th>
              <th>Date</th>
              <th>Status</th>
              <th></th> {/* For actions */}
            </tr>
          </thead>
          <tbody>
            {registeredActivities.map((activity) => (
              <tr key={activity.id}>
                <td>{activity.name}</td>
                <td>{activity.category}</td>
                <td>{activity.date}</td>
                <td>{activity.status}</td>
                <td><button className="action-icon">⬆️</button></td>
              </tr>
            ))}
          </tbody>
        </table>

        <button className="view-all-activities-button">View All Activities</button>
      </div>
    </div>
  );
}

export default UserProfile;