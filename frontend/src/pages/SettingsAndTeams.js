// src/pages/SettingsAndTeams.js (ili podstranica za /settings i /teams)
import React, { useState } from 'react';
import RoleManagementDetails from '../components/RoleManagementDetails';
import TeamCreationForm from '../components/TeamCreationForm';
import InterestCategories from '../components/InterestCategories';
import './SettingsAndTeams.css'; // Uvezite CSS modul za stilizaciju

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
      { module: 'Role Management', access: 'No Access' }, // Možda je na slici "Full Access" za Admina, ovo je samo primjer
    ],
  });

  return (
    <div className="settings-teams-container">
      {/* Sekcija za Role Management Detalje */}
      <RoleManagementDetails role={selectedRole} />

      {/* Sekcija za kategorije interesa */}
      <h2>Interest Categories</h2>
      <InterestCategories />

      {/* Sekcija za nedavno pregledano */}
      <div className="recently-viewed-section">
        <h2>Recently Viewed</h2>
        {/* Placeholder za nedavno pregledane stavke */}
      </div>

      {/* Sekcija za kreiranje tima */}
      <TeamCreationForm />
    </div>
  );
};

export default SettingsAndTeams;