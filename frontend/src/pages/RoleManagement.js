import React, { useState } from 'react';
import './RoleManagement.css'; 

function RoleManagement() {
  const [roles, setRoles] = useState([
    { id: 1, name: 'Administrator', users: 3, lastModified: 'Yesterday', status: 'Active' },
    { id: 2, name: 'Organizer', users: 12, lastModified: '5 days ago', status: 'Active' },
    { id: 3, name: 'Volunteer', users: 150, lastModified: '1 year ago', status: 'Active' },
  ]);

  const [selectedRole, setSelectedRole] = useState(roles[0]); 

  const permissions = [
    { feature: 'Dashboard', Administrator: 'Full Access', Organizer: 'View Only', Volunteer: 'View Only' },
    { feature: 'Events', Administrator: 'Full Access', Organizer: 'Create/Edit', Volunteer: 'View Self' },
    { feature: 'Volunteers', Administrator: 'Full Access', Organizer: 'View/Manage', Volunteer: 'View Self' },
    { feature: 'Attendance', Administrator: 'Full Access', Organizer: 'Record/Edit', Volunteer: 'View Self' },
    { feature: 'Feedback', Administrator: 'Full Access', Organizer: 'View/Respond', Volunteer: 'Submit Only' },
    { feature: 'Reports', Administrator: 'Full Access', Organizer: 'View Only', Volunteer: 'No Access' },
    { feature: 'Role Management', Administrator: 'Full Access', Organizer: 'No Access', Volunteer: 'No Access' },
  ];

  return (
    <div className="role-management-container">
      <div className="breadcrumb">Dashboard &gt; Settings &gt; Roles & Permissions</div>
      <h1>Role Management</h1>
      <p className="page-description">Manage user roles and their associated permissions across the platform.</p>

      <div className="role-actions">
        <button className="add-new-role-button">Add New Role</button>
        <button className="build-add-permissions-button">Build & Add Permissions</button>
      </div>

      <div className="role-tabs">
        <button className="tab active">System Roles</button>
        <button className="tab">Custom Roles</button>
        <button className="tab">Permission Sets</button>
      </div>

      <div className="role-list-section">
        <table>
          <thead>
            <tr>
              <th>Role Name</th>
              <th>Users</th>
              <th>Last Modified</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {roles.map((role) => (
              <tr key={role.id} className={selectedRole.id === role.id ? 'selected-row' : ''} onClick={() => setSelectedRole(role)}>
                <td>{role.name}</td>
                <td>{role.users}</td>
                <td>{role.lastModified}</td>
                <td><span className={`status-badge ${role.status.toLowerCase()}`}>{role.status}</span></td>
                <td>
                  <button className="action-icon">✏️</button>
                  <button className="action-icon">🗑️</button>
                  <button className="action-icon">⚙️</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {selectedRole && (
        <div className="role-details-section">
          <h2>Role Details</h2>
          <div className="role-detail-cards">
            <div className="role-detail-card">
              <div className="card-icon">💡</div>
              <h3>Selected Role</h3>
              <p>{selectedRole.name}</p>
              <span>Full access to all system features and settings</span>
              <div className="card-actions">
                <button className="edit-role-button">Edit Role</button>
                <button className="view-users-button">View Users</button>
              </div>
            </div>
            <div className="role-detail-card">
              <div className="card-icon">🔑</div>
              <h3>Permission Summary</h3>
              <p>Full Access</p>
              <span>This role has unrestricted access to all 28 system features</span>
              <button className="modify-permissions-button">Modify Permissions</button>
            </div>
          </div>
        </div>
      )}

      <div className="permission-matrix-section">
        <h2>Permission Matrix</h2>
        <table>
          <thead>
            <tr>
              <th>Feature</th>
              <th>Administrator</th>
              <th>Organizer</th>
              <th>Volunteer</th>
            </tr>
          </thead>
          <tbody>
            {permissions.map((p, index) => (
              <tr key={index}>
                <td>{p.feature}</td>
                <td>{p.Administrator}</td>
                <td>{p.Organizer}</td>
                <td>{p.Volunteer}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default RoleManagement;