// src/components/RecentActivityCompletion.js
import React from 'react';
import './RecentActivityCompletion.css';

const RecentActivityCompletion = ({ activities }) => {
  return (
    <div className="recent-activity-table-container">
      {activities.length > 0 ? (
        <table className="recent-activity-table">
          <thead>
            <tr>
              <th>Activity</th>
              <th>Volunteers</th>
              <th>Date</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {activities.map((activity, index) => (
              <tr key={index}>
                <td>{activity.activity}</td>
                <td>{activity.volunteers}</td>
                <td>{activity.date}</td>
                <td>
                  <span className={`status-badge status-${activity.status.toLowerCase().replace(/\s/g, '-')}`}>
                    {activity.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p className="no-activities-message">No recent activities to display.</p>
      )}
    </div>
  );
};

export default RecentActivityCompletion;