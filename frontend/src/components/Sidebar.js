import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Sidebar.css';
const Sidebar = ({ currentUserRoles }) => {
  const location = useLocation();

  const hasRole = (requiredRole) => {
    const includesRole = currentUserRoles && currentUserRoles.includes(requiredRole);
    console.log(`Checking if user has role: ${requiredRole}. User roles: ${currentUserRoles}. Result: ${includesRole}`); 
    return includesRole;
  };

  const navItems = [
    { name: 'Dashboard', path: '/dashboard', roles: ['ROLE_VOLUNTEER', 'ROLE_ORGANIZER', 'ROLE_ADMIN'] },
    { name: 'Events', path: '/events', roles: ['ROLE_VOLUNTEER', 'ROLE_ORGANIZER', 'ROLE_ADMIN'] },
    { name: 'Activities', path: '/activities/available', roles: ['ROLE_VOLUNTEER', 'ROLE_ORGANIZER', 'ROLE_ADMIN'] },
    { name: 'Feedback', path: '/feedback', roles: ['ROLE_VOLUNTEER', 'ROLE_ORGANIZER', 'ROLE_ADMIN'] },
    { name: 'Notifications', path: '/notifications', roles: ['ROLE_VOLUNTEER', 'ROLE_ORGANIZER', 'ROLE_ADMIN'] },

    { name: 'Volunteers', path: '/volunteers/manage', roles: ['ROLE_ORGANIZER', 'ROLE_ADMIN'] },
    { name: 'Attendance', path: '/events/attendance', roles: ['ROLE_ORGANIZER', 'ROLE_ADMIN'] },
    { name: 'Reports', path: '/reports/engagement', roles: ['ROLE_ORGANIZER', 'ROLE_ADMIN'] },
    { name: 'Settings', path: '/settings/roles', roles: ['ROLE_ADMIN'] },
  ];

  const filteredNavItems = navItems.filter(item => {
    
    if (!currentUserRoles || currentUserRoles.length === 0) {
      return false;
    }
    
    const hasRequiredRole = item.roles.some(role => hasRole(role));
    return hasRequiredRole;
  });

  return (
    <aside className="sidebar">
      <ul className="sidebar-nav">
        {filteredNavItems.map((item) => (
          <li key={item.name} className={location.pathname === item.path ? 'active' : ''}>
            <Link to={item.path}>
              {item.name}
            </Link>
          </li>
        ))}
      </ul>
    </aside>
  );
};

export default Sidebar;