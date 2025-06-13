// src/components/RecentActivityCompletion.js
import React from 'react';
import './RecentActivityCompletion.css'; // Uvezite CSS modul

const RecentActivityCompletion = ({ activities }) => {
  return (
    <div className="recent-activities-table-container">
      <table className="recent-activities-table">
        <thead>
          <tr>
            <th>Activity</th>
            <th>Category</th>
            <th>Volunteers</th>
            <th>Date</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {activities.map((activity, index) => (
            <tr key={index}>
              <td>{activity.activity}</td>
              <td>{activity.category}</td>
              <td>{activity.volunteers}</td>
              <td>{activity.date}</td>
              <td>{activity.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default RecentActivityCompletion;