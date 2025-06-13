import React, { useState } from 'react';
import './TeamCreation.css'; // Create this CSS file

function TeamCreation() {
  const [teamDetails, setTeamDetails] = useState({
    teamName: '',
    teamDescription: '',
    teamCategory: '',
  });

  const [availableVolunteers, setAvailableVolunteers] = useState([
    { id: 1, name: 'Sarah Johnson', role: 'Event Coordination', activities: 15, status: 'Available' },
    { id: 2, name: 'Michael Chen', role: 'First Aid', activities: 8, status: 'Available' },
    { id: 3, name: 'Aisha Patel', role: 'Community Outreach', activities: 12, status: 'Available' },
    { id: 4, name: 'David Rodriguez', role: 'Translation', activities: 6, status: 'Available' },
    // Add more volunteers
  ]);

  const [selectedTeamMembers, setSelectedTeamMembers] = useState([]);

  const [teamSettings, setTeamSettings] = useState({
    allowMembersToInvite: false,
    teamVisibility: 'public', // 'public' or 'private'
    teamNotifications: false,
  });

  const handleTeamDetailsChange = (e) => {
    const { name, value } = e.target;
    setTeamDetails({ ...teamDetails, [name]: value });
  };

  const handleToggleMemberSelection = (volunteerId) => {
    setSelectedTeamMembers((prevSelected) =>
      prevSelected.includes(volunteerId)
        ? prevSelected.filter((id) => id !== volunteerId)
        : [...prevSelected, volunteerId]
    );
  };

  const handleTeamSettingsChange = (e) => {
    const { name, type, checked, value } = e.target;
    setTeamSettings({
      ...teamSettings,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleCreateTeam = (e) => {
    e.preventDefault();
    console.log('Creating Team:', {
      teamDetails,
      selectedTeamMembers,
      teamSettings,
    });
    alert('Team created (check console)');
    // In a real app, send this data to a backend API
    // Reset form
    setTeamDetails({ teamName: '', teamDescription: '', teamCategory: '' });
    setSelectedTeamMembers([]);
    setTeamSettings({ allowMembersToInvite: false, teamVisibility: 'public', teamNotifications: false });
  };

  return (
    <div className="team-creation-container">
      <div className="breadcrumb">Volunteers &gt; Teams &gt; Create Team</div>
      <h1>Team Creation</h1>

      <div className="team-info-cards">
        <div className="info-card">
          <div className="card-icon">👥</div>
          <h3>Team Details</h3>
          <p>Create a new volunteer team by filling out the information below.</p>
        </div>
        <div className="info-card">
          <div className="card-icon">🌟</div>
          <h3>Team Benefits</h3>
          <p>Teams allow volunteers to collaborate on activities, build relationships, and track collective impact.</p>
        </div>
      </div>

      <form onSubmit={handleCreateTeam} className="create-team-form">
        <div className="form-section">
          <h2>Team Details</h2>
          <div className="form-group">
            <label htmlFor="teamName">Team Name</label>
            <input type="text" id="teamName" name="teamName" value={teamDetails.teamName} onChange={handleTeamDetailsChange} placeholder="Enter team name" required />
          </div>
          <div className="form-group">
            <label htmlFor="teamDescription">Team Description</label>
            <textarea id="teamDescription" name="teamDescription" value={teamDetails.teamDescription} onChange={handleTeamDetailsChange} placeholder="Describe the team's purpose and goals" rows="3"></textarea>
          </div>
          <div className="form-group">
            <label htmlFor="teamCategory">Team Category</label>
            <select id="teamCategory" name="teamCategory" value={teamDetails.teamCategory} onChange={handleTeamDetailsChange}>
              <option value="">Select a category</option>
              <option value="Environmental">Environmental</option>
              <option value="Community Service">Community Service</option>
              <option value="Healthcare">Healthcare</option>
              {/* Add more categories */}
            </select>
          </div>
        </div>

        <div className="form-section">
          <h2>Team Members</h2>
          <div className="search-members">
            <input type="text" placeholder="Search by name or skills" />
            <button className="search-icon">🔍</button>
          </div>
          <div className="available-members-list">
            <table>
              <thead>
                <tr>
                  <th></th> {/* Checkbox column */}
                  <th>Name</th>
                  <th>Role</th>
                  <th>Activities</th>
                  <th>Status</th>
                  <th></th> {/* For actions/info */}
                </tr>
              </thead>
              <tbody>
                {availableVolunteers.map((volunteer) => (
                  <tr key={volunteer.id}>
                    <td>
                      <input
                        type="checkbox"
                        checked={selectedTeamMembers.includes(volunteer.id)}
                        onChange={() => handleToggleMemberSelection(volunteer.id)}
                      />
                    </td>
                    <td>
                      <div className="volunteer-info">
                        <div className="volunteer-avatar-small"></div>
                        {volunteer.name}
                      </div>
                    </td>
                    <td>{volunteer.role}</td>
                    <td>{volunteer.activities} Activities</td>
                    <td>{volunteer.status}</td>
                    <td><button className="info-icon">ℹ️</button></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <p className="selected-members-count">Selected Team Members ({selectedTeamMembers.length})</p>
          <p className="hint-text">No team members selected yet. Add volunteers by clicking the checkboxes above.</p>
        </div>

        <div className="form-section">
          <h2>Team Settings</h2>
          <div className="setting-item">
            <div className="setting-toggle">
              <span>Allow members to invite</span>
              <label className="switch">
                <input type="checkbox" name="allowMembersToInvite" checked={teamSettings.allowMembersToInvite} onChange={handleTeamSettingsChange} />
                <span className="slider round"></span>
              </label>
            </div>
            <p className="setting-description">Team members can invite other volunteers to join</p>
          </div>

          <div className="setting-item">
            <div className="setting-toggle">
              <span>Team visibility</span>
              <select name="teamVisibility" value={teamSettings.teamVisibility} onChange={handleTeamSettingsChange}>
                <option value="public">Public</option>
                <option value="private">Private</option>
              </select>
            </div>
            <p className="setting-description">Control who can see this team?</p>
          </div>

          <div className="setting-item">
            <div className="setting-toggle">
              <span>Team notifications</span>
              <label className="switch">
                <input type="checkbox" name="teamNotifications" checked={teamSettings.teamNotifications} onChange={handleTeamSettingsChange} />
                <span className="slider round"></span>
              </label>
            </div>
            <p className="setting-description">Send updates to all team members</p>
          </div>
        </div>

        <div className="form-actions">
          <button type="button" className="cancel-button">Cancel</button>
          <button type="submit" className="create-team-submit-button">Create Team</button>
        </div>
      </form>
    </div>
  );
}

export default TeamCreation;