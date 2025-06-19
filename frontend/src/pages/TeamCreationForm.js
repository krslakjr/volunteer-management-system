// src/components/TeamCreationForm.js
import React, { useState } from 'react';
import ActiveChannels from './ActiveChannels'; 
import './TeamCreationForm.css';

const TeamCreationForm = () => {
  // Stanja za Team Details
  const [teamName, setTeamName] = useState('');
  const [teamDescription, setTeamDescription] = useState('');
  const [teamCategory, setTeamCategory] = useState('');

  const [searchMembers, setSearchMembers] = useState('');
  const [selectedMembers, setSelectedMembers] = useState([
    { id: 'v1', name: 'John Doe' },
    { id: 'v2', name: 'Jane Smith' }
  ]);

  const [allowMemberInvite, setAllowMemberInvite] = useState(false);
  const [allowOrganizerInvite, setAllowOrganizerInvite] = useState(false);
  const [teamVisibility, setTeamVisibility] = useState('Public'); 
  const [teamNotifications, setTeamNotifications] = useState([]); 

  const handleCreateTeam = (e) => {
    e.preventDefault(); 
    console.log('Kreiranje tima:', {
      teamName,
      teamDescription,
      teamCategory,
      selectedMembers,
      allowMemberInvite,
      allowOrganizerInvite,
      teamVisibility,
      teamNotifications,
    });
    alert('Tim je kreiran (provjerite konzolu za podatke)!');
  };

  const handleRemoveMember = (memberId) => {
    setSelectedMembers(selectedMembers.filter(member => member.id !== memberId));
  };

  const handleSearchMembers = (e) => {
    setSearchMembers(e.target.value);
    console.log("Pretraga volontera:", e.target.value);
  };

  const handleNotificationChange = (e) => {
    const options = Array.from(e.target.selectedOptions, option => option.value);
    setTeamNotifications(options);
  };

  return (
    <div className="team-creation-section">
      <h1>Team Creation</h1>

      <form onSubmit={handleCreateTeam}>
        <div className="team-details-card">
          <h3>Team Details</h3>
          <p>Create a new volunteer team by filling out the information below.</p>
          <div className="form-group">
            <label htmlFor="teamName">Team Name</label>
            <input
              type="text"
              id="teamName"
              value={teamName}
              onChange={(e) => setTeamName(e.target.value)}
              placeholder="Enter team name"
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="teamDescription">Team Description</label>
            <textarea
              id="teamDescription"
              value={teamDescription}
              onChange={(e) => setTeamDescription(e.target.value)}
              placeholder="Describe the team's purpose and goals"
              rows="4"
            ></textarea>
          </div>
          <div className="form-group">
            <label htmlFor="teamCategory">Team Category</label>
            <select
              id="teamCategory"
              value={teamCategory}
              onChange={(e) => setTeamCategory(e.target.value)}
              required
            >
              <option value="">Select a category</option>
              <option value="Environmental">Environmental</option>
              <option value="Education">Education</option>
              <option value="Community">Community</option>
              <option value="Healthcare">Healthcare</option>
              <option value="Animal Welfare">Animal Welfare</option>
              <option value="Senior Care">Senior Care</option>
            </select>
          </div>
        </div>

        <div className="team-benefits-card">
          <h3>Team Benefits</h3>
          <p>Teams allow volunteers to collaborate on activities, build relationships, and track collective impact.</p>
        </div>

        <div className="team-members-section">
          <h2>Team Members</h2>
          <div className="form-group">
            <label htmlFor="searchMembers">Search Volunteers</label>
            <input
              type="text"
              id="searchMembers"
              value={searchMembers}
              onChange={handleSearchMembers}
              placeholder="Search by name or skills"
            />
          </div>
          <div className="selected-team-members">
            <h3>Selected Team Members ({selectedMembers.length})</h3>
            {selectedMembers.length === 0 ? (
              <p>No team members selected yet. Add volunteers from the list above.</p>
            ) : (
              <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
                {selectedMembers.map(member => (
                  <span key={member.id} className="member-tag">
                    {member.name}
                    <button type="button" onClick={() => handleRemoveMember(member.id)} aria-label={`Remove ${member.name}`}>
                      &times;
                    </button>
                  </span>
                ))}
              </div>
            )}
          </div>
        </div>

        <div className="team-settings-section">
          <h2>Team Settings</h2>
          <div className="setting-item">
            <label htmlFor="allowMemberInvite">Allow members to invite others</label>
            <p>Team members can invite other volunteers to join.</p>
            <input
              type="checkbox"
              id="allowMemberInvite"
              checked={allowMemberInvite}
              onChange={(e) => setAllowMemberInvite(e.target.checked)}
              className="toggle-switch"
            />
          </div>
          <div className="setting-item">
            <label htmlFor="allowOrganizerInvite">Allow organizers to invite other volunteers to join</label>
            <p>Team organizers can invite other volunteers to join.</p>
            <input
              type="checkbox"
              id="allowOrganizerInvite"
              checked={allowOrganizerInvite}
              onChange={(e) => setAllowOrganizerInvite(e.target.checked)}
              className="toggle-switch"
            />
          </div>
          <div className="form-group setting-item">
            <label htmlFor="teamVisibility">Team visibility</label>
            <p>Only who can see this team?</p>
            <select
              id="teamVisibility"
              value={teamVisibility}
              onChange={(e) => setTeamVisibility(e.target.value)}
            >
              <option value="Public">Public (Anyone can see and join)</option>
              <option value="Private">Private (Only members can see, by invitation)</option>
              <option value="Hidden">Hidden (Visible only to organizers)</option>
            </select>
          </div>
          <div className="form-group setting-item">
            <label htmlFor="teamNotifications">Team notifications</label>
            <p>Send updates to all team members.</p>
            <select
              id="teamNotifications"
              multiple 
              value={teamNotifications}
              onChange={handleNotificationChange}
            >
              <option value="email">Email</option>
              <option value="sms">SMS</option>
              <option value="app">In-App Notification</option>
            </select>
          </div>
        </div>

        <div className="team-actions">
          <button type="submit" className="button-primary">Create Team</button>
          <button type="button" className="button-secondary" onClick={() => console.log('Cancel creation')}>Cancel</button>
        </div>
      </form>

    
      <ActiveChannels /> 

    </div>
  );
};

export default TeamCreationForm;