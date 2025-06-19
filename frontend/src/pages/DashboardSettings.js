// src/pages/DashboardSettings.js
import React from 'react';
import TopVolunteersList from '../components/TopVolunteersList';
import SocialPlatformCard from '../components/SocialPlatformCard';
import RoleManagementOverview from '../components/RoleManagementOverview';
import './DashboardSettings.css';

const DashboardSettings = () => {
  const topVolunteers = [
    { id: 1, name: 'Sarah Johnson', hours: '20 hours 756 records', avatar: '/path/to/avatar1.jpg' },
    { id: 2, name: 'Michael Chan', hours: '20 hours 756 records', avatar: '/path/to/avatar2.jpg' },
    { id: 3, name: 'Jessica Williams', hours: '20 hours 756 records', avatar: '/path/to/avatar3.jpg' },
    { id: 4, name: 'David Rodriguez', hours: '20 hours 756 records', avatar: '/path/to/avatar4.jpg' },
    { id: 5, name: 'Celia Patel', hours: '20 hours 756 records', avatar: '/path/to/avatar5.jpg' },
  ];

  const roles = [
    { name: 'Administrator', users: 3, lastModified: 'Yesterday', status: 'Active' },
    { name: 'Organizer', users: 12, lastModified: '3 days ago', status: 'Active' },
    { name: 'Volunteer', users: 150, lastModified: '1 year ago', status: 'Active' },
  ];

  return (
    <div className="dashboard-settings-container">
      <h2>Top Volunteers</h2>
      <TopVolunteersList volunteers={topVolunteers} />
      <button className="view-all-volunteers-button">View All Volunteers</button>

      <h2>Social Platform Integrations</h2>
      <div className="social-platforms-grid">
        <SocialPlatformCard
          platform="Facebook"
          description="Connect with profile data and share organization's page."
          actionText="Connect"
          actionLink="/settings/integrations/facebook"
        />
        <SocialPlatformCard
          platform="LinkedIn"
          description="Connect with profile data and share organization's page."
          actionText="Connect"
          actionLink="/settings/integrations/linkedin"
        />
        <SocialPlatformCard
          platform="YouTube"
          description="Share ideas, volunteer tips and information."
          actionText="Connect"
          actionLink="/settings/integrations/youtube"
        />
        <SocialPlatformCard
          platform="Add Platform"
          description="Connect additional social media platforms across the platform."
          actionText="Learn More"
          actionLink="/settings/integrations/add"
          isAddPlatform={true} 
        />
      </div>

      <div className="role-management-section">
        <h2>Role Management</h2>
        <p>Manage user roles and their associated permissions across the platform.</p>
        <div className="role-management-actions">
          <button className="button-primary">Add New Role</button>
          <button className="button-secondary">Build Add Permissions</button>
        </div>
        <RoleManagementOverview roles={roles} />
      </div>
    </div>
  );
};

export default DashboardSettings;