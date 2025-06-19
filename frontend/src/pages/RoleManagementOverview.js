// src/components/RoleManagementOverview.js
import React from 'react';
import './RoleManagementOverview.css'; 

const RoleManagementOverview = ({ roles }) => {
  return (
    <div className="role-table-container">
      <div className="role-tabs">
        <button className="tab-button active">System Roles</button>
        <button className="tab-button">Custom Roles</button>
        <button className="tab-button">Permission Sets</button>
      </div>
      <table className="role-table">
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
          {roles.map((role, index) => (
            <tr key={index}>
              <td>{role.name}</td>
              <td>{role.users}</td>
              <td>{role.lastModified}</td>
              <td>{role.status}</td>
              <td>
                <button className="action-icon">✏️</button> 
                <button className="action-icon">🗑️</button> 
                <button className="action-icon">⚙️</button> 
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="role-details-section">
      </div>
    </div>
  );
};

export default RoleManagementOverview;