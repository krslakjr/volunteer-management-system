import React, { useState } from 'react';
import { useParams } from 'react-router-dom'; // To get event ID from URL
import './AttendanceTracking.css'; // Create this CSS file
import './AttendanceTracking.css'; // Uvezite CSS modul

function AttendanceTracking() {
  const { eventId } = useParams(); // Get eventId from URL if using dynamic route
  const [eventName, setEventName] = useState('Beach Cleanup');
  const [eventDate, setEventDate] = useState('Saturday, June 15, 2023 • 9:00 AM - 12:00 PM • Ocean Park');

  const [volunteers, setVolunteers] = useState([
    { id: 1, name: 'Michael Brown', email: 'michael.brown@email.com', status: 'Present', time: '8:55 AM' },
    { id: 2, name: 'Jessica Lee', email: 'jessica.lee@email.com', status: 'Present', time: '9:00 AM' },
    { id: 3, name: 'David Wilson', email: 'david.wilson@email.com', status: 'Present', time: '8:50 AM' },
    { id: 4, name: 'Emily Taylor', email: 'emily.taylor@email.com', status: 'Absent', time: '-' },
    { id: 5, name: 'Robert Johnson', email: 'robert.johnson@email.com', status: 'Present', time: '8:52 AM' },
    { id: 6, name: 'Sophia Martinez', email: 'sophia.martinez@email.com', status: 'Absent', time: '-' },
    // Add more volunteers as needed
  ]);

  const totalVolunteers = volunteers.length;
  const presentVolunteers = volunteers.filter(v => v.status === 'Present').length;
  const absentVolunteers = totalVolunteers - presentVolunteers;

  const markAllPresent = () => {
    setVolunteers(volunteers.map(v => ({ ...v, status: 'Present', time: v.time === '-' ? new Date().toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: true }) : v.time })));
    alert('All volunteers marked as Present!');
  };

  const toggleAttendance = (id) => {
    setVolunteers(volunteers.map(v =>
      v.id === id
        ? { ...v, status: v.status === 'Present' ? 'Absent' : 'Present', time: v.status === 'Present' ? '-' : new Date().toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: true }) }
        : v
    ));
  };

  return (
    <div className="attendance-tracking-container">
      <div className="breadcrumb">Events &gt; {eventName} &gt; Attendance</div>
      <h1>{eventName} - Attendance Tracking</h1>
      <p className="event-date-details">{eventDate}</p>

      <div className="attendance-header">
        <input type="text" placeholder="Enter name or email" />
        <button className="mark-all-present-button" onClick={markAllPresent}>Mark All Present</button>
      </div>

      <div className="attendance-summary-tabs">
        <button className="tab active">All Volunteers ({totalVolunteers})</button>
        <button className="tab">Present ({presentVolunteers})</button>
        <button className="tab">Absent ({absentVolunteers})</button>
      </div>

      <div className="attendance-list">
        <h2>Attendance List</h2>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Status</th>
              <th>Time</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {volunteers.map((volunteer) => (
              <tr key={volunteer.id}>
                <td>
                  <div className="volunteer-info">
                    <div className="volunteer-avatar-small"></div>
                    {volunteer.name}
                  </div>
                </td>
                <td>{volunteer.email}</td>
                <td>
                  <span className={`status-badge ${volunteer.status.toLowerCase()}`}>
                    {volunteer.status}
                  </span>
                </td>
                <td>{volunteer.time}</td>
                <td>
                  <button className="action-toggle-button" onClick={() => toggleAttendance(volunteer.id)}>
                    {volunteer.status === 'Present' ? 'Mark Absent' : 'Mark Present'}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button className="load-more-button">Load More</button>
      </div>

      <div className="attendance-statistics">
        <h2>Attendance Summary</h2>
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-icon">👥</div>
            <h3>Total Volunteers</h3>
            <p>{totalVolunteers}</p>
          </div>
          <div className="stat-card present-card">
            <div className="stat-icon">✅</div>
            <h3>Present</h3>
            <p>{presentVolunteers} volunteers ({((presentVolunteers / totalVolunteers) * 100).toFixed(0)}%)</p>
          </div>
          <div className="stat-card absent-card">
            <div className="stat-icon">❌</div>
            <h3>Absent</h3>
            <p>{absentVolunteers} volunteers ({((absentVolunteers / totalVolunteers) * 100).toFixed(0)}%)</p>
          </div>
        </div>
      </div>

      <div className="attendance-actions-footer">
        <button className="export-attendance-button">Export Attendance</button>
        <button className="send-reminders-button">Send Reminders</button>
      </div>
    </div>
  );
}

export default AttendanceTracking;