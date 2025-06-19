import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Header.css';

function Header({ isLoggedIn, onLogout }) { 
  const navigate = useNavigate();

  const handleLogoutClick = () => {
    if (onLogout) { 
      onLogout(); 
    }
    navigate('/login'); 
  };

  return (
    <header className="header">
      <nav className="navbar">
        <Link to="/dashboard" className="logo">VolunteerHub</Link>
        
        <div className="header-actions">
          {isLoggedIn ? (
            <button onClick={handleLogoutClick} className="nav-action-button logout-button">
              Logout
            </button>
          ) : (
            <>
              <Link to="/login" className="nav-action-button login-button">Login</Link>
              <Link to="/register" className="nav-action-button register-button">Register</Link>
            </>
          )}
          
          <div className="user-avatar"></div>
        </div>
      </nav>
    </header>
  );
}

export default Header;