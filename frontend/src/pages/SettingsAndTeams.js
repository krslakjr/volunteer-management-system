// src/pages/SettingsAndTeams.js 
import React, { useState } from 'react';
import RoleManagementDetails from '../components/RoleManagementDetails';
import TeamCreationForm from '../components/TeamCreationForm';
import InterestCategories from '../components/InterestCategories';
import './SettingsAndTeams.css';

const SettingsAndTeams = () => {
  const [selectedRole, setSelectedRole] = useState({
    name: 'Administrator',
    description: 'Full access to all system features and settings.',
    permissions: [
      { module: 'Dashboard', access: 'Full Access' },
      { module: 'Events', access: 'View/Manage' },
      { module: 'Volunteers', access: 'Full Access' },
      { module: 'Attendance', access: 'View/Edit' },
      { module: 'Feedback', access: 'View/Respond' },
      { module: 'Reports', access: 'Submit Only' },
      { module: 'Role Management', access: 'No Access' }, 
    ],
  });

  return (
    <div className="settings-teams-container">
      <RoleManagementDetails role={selectedRole} />

      <h2>Interest Categories</h2>
      <InterestCategories />

      <div className="recently-viewed-section">
        <h2>Recently Viewed</h2>
      </div>

      <TeamCreationForm />
    </div>
  );
};

export default SettingsAndTeams;