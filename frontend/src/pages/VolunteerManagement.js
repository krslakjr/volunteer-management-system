import React, { useState } from 'react';
import './VolunteerManagement.css'; // Create this CSS file

function VolunteerManagement() {
  const [volunteers, setVolunteers] = useState([
    { id: 1, name: 'Sarah Johnson', role: 'Environmental', events: 15, rating: '4/5', status: 'Active' },
    { id: 2, name: 'Michael Chen', role: 'Education', events: 8, rating: '4/5', status: 'Active' },
    { id: 3, name: 'Jessica Williams', role: 'Healthcare', events: 12, rating: '4/5', status: 'Active' },
    { id: 4, name: 'David Rodriguez', role: 'Community', events: 6, rating: '4/5', status: 'Active' },
  ]);

  const [newVolunteer, setNewVolunteer] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    skills: '',
    availability: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewVolunteer({ ...newVolunteer, [name]: value });
  };

  const handleAddVolunteer = (e) => {
    e.preventDefault();
    console.log('New Volunteer:', newVolunteer);
    // In a real app, you would send this data to a backend API
    // For now, let's just clear the form
    setNewVolunteer({
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      skills: '',
      availability: '',
    });
    alert('Volunteer added (check console)');
  };

  return (
    <div className="volunteer-management-container">
      <div className="breadcrumb">Dashboard &gt; Volunteers &gt; Manage</div>
      <h1>Volunteer Management</h1>

      <div className="search-section">
        <input type="text" placeholder="Search volunteers" />
        <button className="add-volunteer-button">Add Volunteer</button>
      </div>

      <div className="volunteer-list">
        <h2>All Volunteers</h2>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Role</th>
              <th>Events</th>
              <th>Rating</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {volunteers.map((volunteer) => (
              <tr key={volunteer.id}>
                <td>{volunteer.name}</td>
                <td>{volunteer.role}</td>
                <td>{volunteer.events}</td>
                <td>{volunteer.rating}</td>
                <td>{volunteer.status}</td>
                <td>
                  <button className="action-icon">✏️</button>
                  <button className="action-icon">🗑️</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="add-new-volunteer-form">
        <h2>Add New Volunteer</h2>
        <form onSubmit={handleAddVolunteer}>
          <div className="form-row">
            <div className="form-group">
              <label htmlFor="firstName">First Name</label>
              <input type="text" id="firstName" name="firstName" value={newVolunteer.firstName} onChange={handleInputChange} placeholder="Enter first name" required />
            </div>
            <div className="form-group">
              <label htmlFor="lastName">Last Name</label>
              <input type="text" id="lastName" name="lastName" value={newVolunteer.lastName} onChange={handleInputChange} placeholder="Enter last name" required />
            </div>
          </div>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input type="email" id="email" name="email" value={newVolunteer.email} onChange={handleInputChange} placeholder="Enter email address" required />
          </div>
          <div className="form-group">
            <label htmlFor="phone">Phone</label>
            <input type="tel" id="phone" name="phone" value={newVolunteer.phone} onChange={handleInputChange} placeholder="Enter phone number" />
          </div>
          <div className="form-group">
            <label htmlFor="skills">Skills & Interests</label>
            <input type="text" id="skills" name="skills" value={newVolunteer.skills} onChange={handleInputChange} placeholder="Enter skills, separated by commas" />
          </div>
          <div className="form-group">
            <label htmlFor="availability">Availability</label>
            <input type="text" id="availability" name="availability" value={newVolunteer.availability} onChange={handleInputChange} placeholder="Weekdays, weekends, evenings, etc." />
          </div>
          <div className="form-actions">
            <button type="button" className="cancel-button">Cancel</button>
            <button type="submit" className="add-volunteer-submit-button">Add Volunteer</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default VolunteerManagement;