// src/components/RoleManagementDetails.js
import React from 'react';
import { Link } from 'react-router-dom';
import './RoleManagementDetails.css'; 

const RoleManagementDetails = ({ role }) => {
  return (
    <div className="role-management-details-section">
      <div className="role-detail-card">
        <h3>Selected Role</h3>
        <h4>{role.name}</h4>
        <p>{role.description}</p>
        <Link to={`/roles/${role.name}/edit`} className="button-secondary">Edit Role</Link>
        <Link to={`/roles/${role.name}/view-features`} className="button-secondary">View System Features</Link>
      </div>

      <div className="permission-summary-card">
        <h3>Permission Summary</h3>
        <p>Detailed report with approved matches and match profiles.</p>
        <Link to={`/roles/${role.name}/view-profiles`} className="button-secondary">View Profiles</Link>
      </div>

      <table className="permissions-table">
        <thead>
          <tr>
            <th></th> 
            <th>View Only</th>
            <th>Create/Edit</th>
            <th>View Self</th>
            <th>Submit Only</th>
            <th>No Access</th>
          </tr>
        </thead>
        <tbody>
          {role.permissions.map((perm, index) => (
            <tr key={index}>
              <td>{perm.module}</td>
              <td><input type="radio" name={`${perm.module}-access`} checked={perm.access === 'View Only'} readOnly /></td>
              <td><input type="radio" name={`${perm.module}-access`} checked={perm.access === 'Create/Edit'} readOnly /></td>
              <td><input type="radio" name={`${perm.module}-access`} checked={perm.access === 'View Self'} readOnly /></td>
              <td><input type="radio" name={`${perm.module}-access`} checked={perm.access === 'Submit Only'} readOnly /></td>
              <td><input type="radio" name={`${perm.module}-access`} checked={perm.access === 'No Access'} readOnly /></td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="permission-actions">
        <button className="button-primary">View Details</button>
        <button className="button-secondary">Save</button>
      </div>
    </div>
  );
};

export default RoleManagementDetails;