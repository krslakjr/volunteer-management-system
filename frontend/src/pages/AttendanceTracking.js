import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import './AttendanceTracking.css'; 

function AttendanceTracking() {
  const { eventId } = useParams();
  const [volunteers, setVolunteers] = useState([
    { id: 1, name: 'Michael Brown', email: 'michael.brown@email.com', status: 'Present', time: '8:55 AM' },
    { id: 2, name: 'Jessica Lee', email: 'jessica.lee@email.com', status: 'Present', time: '9:00 AM' },
    { id: 3, name: 'David Wilson', email: 'david.wilson@email.com', status: 'Present', time: '8:50 AM' },
    { id: 4, name: 'Emily Taylor', email: 'emily.taylor@email.com', status: 'Absent', time: '-' },
    { id: 5, name: 'Robert Johnson', email: 'robert.johnson@email.com', status: 'Present', time: '8:52 AM' },
    { id: 6, name: 'Sophia Martinez', email: 'sophia.martinez@email.com', status: 'Absent', time: '-' },
  ]);

  const [activeFilter, setActiveFilter] = useState('All'); 

  const totalVolunteers = volunteers.length;
  const presentVolunteersCount = volunteers.filter(v => v.status === 'Present').length;
  const absentVolunteersCount = totalVolunteers - presentVolunteersCount;

  const filteredVolunteers = volunteers.filter(volunteer => {
    if (activeFilter === 'All') {
      return true; 
    }
    return volunteer.status === activeFilter; 
  });

  const markAllPresent = () => {
    setVolunteers(volunteers.map(v => ({ 
      ...v, 
      status: 'Present', 
      time: v.time === '-' ? new Date().toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: true }) : v.time 
    })));
    alert('All volunteers marked as Present!');
    setActiveFilter('All'); 
  };

  const toggleAttendance = (id) => {
    setVolunteers(volunteers.map(v =>
      v.id === id
        ? { 
            ...v, 
            status: v.status === 'Present' ? 'Absent' : 'Present', 
            time: v.status === 'Present' ? '-' : new Date().toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: true }) 
          }
        : v
    ));
  };

  const handleFilterChange = (filter) => {
    setActiveFilter(filter);
  };

  return (
    <div className="attendance-tracking-container">
      <h1>Attendance Tracking</h1>

      <div className="attendance-summary-tabs">
        <button 
          className={`tab ${activeFilter === 'All' ? 'active' : ''}`}
          onClick={() => handleFilterChange('All')}
        >
          All Volunteers ({totalVolunteers})
        </button>
        <button 
          className={`tab ${activeFilter === 'Present' ? 'active' : ''}`}
          onClick={() => handleFilterChange('Present')}
        >
          Present ({presentVolunteersCount})
        </button>
        <button 
          className={`tab ${activeFilter === 'Absent' ? 'active' : ''}`}
          onClick={() => handleFilterChange('Absent')}
        >
          Absent ({absentVolunteersCount})
        </button>
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
            {filteredVolunteers.length === 0 ? (
                <tr>
                    <td colSpan="5" className="no-volunteers-message">No volunteers match the current filter.</td>
                </tr>
            ) : (
                filteredVolunteers.map((volunteer) => (
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
                ))
            )}
          </tbody>
        </table>
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
            <p>{presentVolunteersCount} volunteers ({((presentVolunteersCount / totalVolunteers) * 100).toFixed(0)}%)</p>
          </div>
          <div className="stat-card absent-card">
            <div className="stat-icon">❌</div>
            <h3>Absent</h3>
            <p>{absentVolunteersCount} volunteers ({((absentVolunteersCount / totalVolunteers) * 100).toFixed(0)}%)</p>
          </div>
        </div>
      </div>

      <div className="attendance-actions-footer">
        <button className="send-reminders-button" onClick={markAllPresent}>Mark All Present</button>
      </div>
    </div>
  );
}

export default AttendanceTracking;