// src/components/TeamCreationForm.js
import React, { useState } from 'react';
import ActiveChannels from './ActiveChannels'; // Pretpostavka da ActiveChannels dolazi iz iste komponente kao TeamCreationForm
import './TeamCreationForm.css'; // Uvezite CSS modul za stilizaciju

const TeamCreationForm = () => {
  // Stanja za Team Details
  const [teamName, setTeamName] = useState('');
  const [teamDescription, setTeamDescription] = useState('');
  const [teamCategory, setTeamCategory] = useState('');

  // Stanja za Team Members
  const [searchMembers, setSearchMembers] = useState('');
  // U stvarnoj aplikaciji, ovo bi bio niz objekata volontera
  const [selectedMembers, setSelectedMembers] = useState([
    { id: 'v1', name: 'John Doe' },
    { id: 'v2', name: 'Jane Smith' }
  ]); // Primjer inicijalnih članova

  // Stanja za Team Settings
  const [allowMemberInvite, setAllowMemberInvite] = useState(false);
  const [allowOrganizerInvite, setAllowOrganizerInvite] = useState(false);
  const [teamVisibility, setTeamVisibility] = useState('Public'); // npr. Public, Private, Hidden
  const [teamNotifications, setTeamNotifications] = useState([]); // niz odabranih opcija (e.g., ['email', 'sms'])

  const handleCreateTeam = (e) => {
    e.preventDefault(); // Spriječi defaultno ponašanje forme
    // Ovdje biste implementirali logiku za slanje podataka na backend
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
    // Nakon uspješnog kreiranja, možda preusmjeriti korisnika ili resetovati formu
  };

  const handleRemoveMember = (memberId) => {
    setSelectedMembers(selectedMembers.filter(member => member.id !== memberId));
  };

  const handleSearchMembers = (e) => {
    setSearchMembers(e.target.value);
    // U stvarnosti, ovdje biste pokrenuli API poziv za pretragu volontera
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
        {/* Team Details Section */}
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
              {/* Dodajte ostale kategorije po potrebi */}
            </select>
          </div>
        </div>

        {/* Team Benefits Card - Ovo je više informativni panel, bez forme elemenata */}
        <div className="team-benefits-card">
          <h3>Team Benefits</h3>
          <p>Teams allow volunteers to collaborate on activities, build relationships, and track collective impact.</p>
        </div>

        {/* Team Members Section */}
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
            {/* Ovdje bi se dodavala logika za prikaz rezultata pretrage
                i dugme za dodavanje volontera u selectedMembers listu */}
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

        {/* Team Settings Section */}
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
              multiple // Omogućava višestruki odabir
              value={teamNotifications}
              onChange={handleNotificationChange}
            >
              <option value="email">Email</option>
              <option value="sms">SMS</option>
              <option value="app">In-App Notification</option>
            </select>
          </div>
        </div>

        {/* Team Actions */}
        <div className="team-actions">
          <button type="submit" className="button-primary">Create Team</button>
          <button type="button" className="button-secondary" onClick={() => console.log('Cancel creation')}>Cancel</button>
        </div>
      </form>

      {/* Active Channels Section - Ovo može biti i zasebna komponenta u TeamCreationForm.js
          ako je direktno povezana sa procesom kreiranja tima,
          ili zasebna komponenta ako je dio nekog drugog layouta.
          Na osnovu slike, čini se da je na istoj stranici. */}
      <ActiveChannels /> {/* Pretpostavka da ActiveChannels komponenta postoji */}

    </div>
  );
};

export default TeamCreationForm;