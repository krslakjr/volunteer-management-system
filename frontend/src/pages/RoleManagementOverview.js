// src/components/RoleManagementOverview.js
import React from 'react';
import './RoleManagementOverview.css'; // Uvezite CSS modul

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
                <button className="action-icon">✏️</button> {/* Ikona za uređivanje */}
                <button className="action-icon">🗑️</button> {/* Ikona za brisanje */}
                <button className="action-icon">⚙️</button> {/* Ikona za postavke/detalje */}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Ovdje bi išli "Role Details" i "Permission Summary" kao na image_5dcf5d.png */}
      <div className="role-details-section">
        {/* Placeholder za odabrane detalje role */}
      </div>
    </div>
  );
};

export default RoleManagementOverview;