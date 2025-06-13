import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css'; // Create this CSS file for styling

function Header() {
  return (
    <header className="header">
      <nav className="navbar">
        <div className="logo">VolunteerHub</div>
        <ul className="nav-links">
          <li><Link to="/">Dashboard</Link></li>
          <li><Link to="/events">Events</Link></li> {/* Placeholder, might be a dropdown */}
          <li><Link to="/volunteers/manage">Volunteers</Link></li>
          <li><Link to="/activities/available">Activities</Link></li>
          <li><Link to="/attendance">Attendance</Link></li> {/* Placeholder */}
          <li><Link to="/feedback">Feedback</Link></li>
          <li><Link to="/reports/engagement">Reports</Link></li>
          <li><Link to="/profile">Profile</Link></li>
          <li><Link to="/notifications">Notifications</Link></li>
          <li><Link to="/settings/roles">Settings</Link></li>
        </ul>
        <div className="header-actions">
          <Link to="/help" className="help-button">Help</Link>
          <button className="create-event-button">Create Event</button>
          <div className="user-avatar"></div> {/* User avatar/icon */}
        </div>
      </nav>
    </header>
  );
}

export default Header;