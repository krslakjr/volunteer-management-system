import React, { useState } from 'react';
import './EngagementStatistics.css'; // Create this CSS file

function EngagementStatistics() {
  const [stats] = useState({
    totalVolunteers: 1248,
    completedActivities: 356,
    activeEvents: 42,
    averageHrsPerMonth: 12.5,
  });

  const [recentActivities] = useState([
    { id: 1, name: 'Beach Cleanup', category: 'Environmental', volunteers: 21, date: 'Apr 15, 2023', status: 'Completed' },
    { id: 2, name: 'Food Drive', category: 'Community Service', volunteers: 18, date: 'Apr 12, 2023', status: 'Completed' },
    { id: 3, name: 'Senior Center Visit', category: 'Elderly Care', volunteers: 12, date: 'Apr 10, 2023', status: 'Completed' },
    { id: 4, name: 'Park Restoration', category: 'Environmental', volunteers: 32, date: 'Apr 8, 2023', status: 'Completed' },
    { id: 5, name: 'Literacy Program', category: 'Education', volunteers: 15, date: 'Apr 5, 2023', status: 'Completed' },
  ]);

  const [topVolunteers] = useState([
    { id: 1, name: 'Sarah Johnson', hours: 128 },
    { id: 2, name: 'Michael Chen', hours: 105 },
    { id: 3, name: 'Jessica Williams', hours: 92 },
    { id: 4, name: 'David Rodriguez', hours: 88 },
    { id: 5, name: 'Emilio Patel', hours: 78 },
  ]);

  return (
    <div className="engagement-statistics-container">
      <div className="breadcrumb">Dashboard &gt; Reports &gt; Engagement Statistics</div>
      <h1>Engagement Statistics</h1>

      <div className="engagement-tabs">
        <button className="tab active">Overview</button>
        <button className="tab">Volunteers</button>
        <button className="tab">Activities</button>
        <button className="tab">Events</button>
        <button className="tab">Trends</button>
      </div>

      <div className="statistics-grid">
        <div className="stat-card">
          <div className="stat-icon">👥</div>
          <h3>Total</h3>
          <p>{stats.totalVolunteers}</p>
          <span>Registered Volunteers</span>
          <button className="view-details-button">View Details</button>
        </div>
        <div className="stat-card">
          <div className="stat-icon">✅</div>
          <h3>Total</h3>
          <p>{stats.completedActivities}</p>
          <span>Completed Activities</span>
          <button className="view-details-button">View Details</button>
        </div>
        <div className="stat-card">
          <div className="stat-icon">🎉</div>
          <h3>Total</h3>
          <p>{stats.activeEvents}</p>
          <span>Active Events</span>
          <button className="view-details-button">View Details</button>
        </div>
        <div className="stat-card">
          <div className="stat-icon">⏰</div>
          <h3>Average</h3>
          <p>{stats.averageHrsPerMonth} hrs</p>
          <span>Volunteer Time Per Month</span>
          <button className="view-details-button">View Details</button>
        </div>
      </div>

      <div className="volunteer-engagement-trends">
        <h2>Volunteer Engagement Trends</h2>
        <div className="chart-placeholder">
          Chart visualization would appear here
          {/* You would integrate a charting library like Chart.js or Recharts here */}
        </div>
      </div>

      <div className="recent-activity-completion">
        <h2>Recent Activity Completion</h2>
        <table>
          <thead>
            <tr>
              <th>Activity</th>
              <th>Category</th>
              <th>Volunteers</th>
              <th>Date</th>
              <th>Status</th>
              <th></th> {/* For actions */}
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
        <button className="view-all-activities-button">View All Activities</button>
      </div>

      <div className="top-volunteers-section">
        <h2>Top Volunteers</h2>
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
        <button className="view-all-volunteers-button">View All Volunteers</button>
      </div>
    </div>
  );
}

export default EngagementStatistics;