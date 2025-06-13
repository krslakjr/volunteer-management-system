import React, { useState } from 'react';
import './AvailableActivities.css'; // Create this CSS file
import './AvailableActivities.css'; // Uvezite CSS modul
function AvailableActivities() {
  const [activities] = useState([
    { id: 1, category: 'Environmental', title: 'Park Cleanup', description: 'Help clean up Central Park. All supplies provided. 3 hours commitment.' },
    { id: 2, category: 'Education', title: 'Literacy Tutoring', description: 'Tutor elementary students in reading and writing. Weekly 2-hour sessions for 3 months.' },
    { id: 3, category: 'Community', title: 'Food Bank Helper', description: 'Sort and distribute food at the local food bank. Flexible hours available.' },
    { id: 4, category: 'Healthcare', title: 'Hospital Volunteer', description: 'Assist hospital staff with non-medical tasks. Training provided. Background check required.' },
    { id: 5, category: 'Animal Welfare', title: 'Animal Shelter', description: 'Help care for animals at the local shelter. Tasks include walking dogs and cleaning cats.' },
    { id: 6, category: 'Senior Care', title: 'Senior Companion', description: 'Provide companionship to seniors at assisted living facilities. Weekly visits of 1-2 hours.' },
  ]);

  const [appliedActivities] = useState([
    { id: 101, name: 'Beach Cleanup', category: 'Environmental', appliedOn: 'May 15, 2023', status: 'Pending Approval' },
    { id: 102, name: 'Homeless Shelter', category: 'Community', appliedOn: 'May 13, 2023', status: 'Approved' },
  ]);

  const [upcomingSchedule] = useState([
    { id: 201, name: 'Homeless Shelter Assistance', date: 'May 28, 2023', time: '8:00 AM - 12:00 PM', location: 'Downtown Community Center' },
    { id: 202, name: 'River Cleanup Project', date: 'June 5, 2023', time: '9:00 AM - 11:00 AM', location: 'Riverside Park' },
  ]);

  return (
    <div className="available-activities-container">
      <div className="breadcrumb">Home &gt; Volunteer Activities</div>
      <h1>Available Volunteer Activities</h1>

      <div className="search-bar">
        <input type="text" placeholder="Search by keyword, location..." />
        <button className="filter-button">Filter</button>
      </div>

      <div className="activity-categories">
        <button className="category-button active">All Activities</button>
        <button className="category-button">Environmental</button>
        <button className="category-button">Education</button>
        <button className="category-button">Community</button>
        <button className="category-button">Healthcare</button>
        {/* Add more categories as needed */}
      </div>

      <div className="activity-cards-grid">
        {activities.map((activity) => (
          <div className="activity-card" key={activity.id}>
            <div className="activity-icon">💡</div> {/* Replace with actual icons */}
            <h3>{activity.title}</h3>
            <p className="activity-category">{activity.category}</p>
            <p className="activity-description">{activity.description}</p>
            <div className="activity-actions">
              <button className="apply-now-button">Apply Now</button>
              <button className="learn-more-button">Learn More</button>
            </div>
          </div>
        ))}
      </div>

      <div className="recently-applied-activities">
        <h2>Recently Applied Activities</h2>
        <table>
          <thead>
            <tr>
              <th>Activity</th>
              <th>Category</th>
              <th>Applied on</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {appliedActivities.map((activity) => (
              <tr key={activity.id}>
                <td>{activity.name}</td>
                <td>{activity.category}</td>
                <td>{activity.appliedOn}</td>
                <td>{activity.status}</td>
                <td><button className="action-icon">⚙️</button></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="upcoming-volunteer-schedule">
        <h2>Upcoming Volunteer Schedule</h2>
        {upcomingSchedule.map((event) => (
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
        ))}
      </div>
    </div>
  );
}

export default AvailableActivities;