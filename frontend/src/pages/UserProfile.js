import React, { useState, useEffect, useCallback } from 'react';
import AuthService from '../services/auth.service';
import { useNavigate } from 'react-router-dom';
import './UserProfile.css'; 

function UserProfile() {
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const navigate = useNavigate();

  const fetchUserProfile = useCallback(async () => {
    setLoading(true);
    setError('');
    try {
      const currentUser = AuthService.getCurrentUser();
      
      if (!currentUser || !currentUser.id) {
        console.warn("UserProfile: Current user not found or invalid ID. Redirecting.", currentUser);
        setError('User not logged in. Redirecting to login...');
        navigate('/login'); 
        return; 
      }

      console.log("UserProfile: Current user found:", currentUser); 
      setProfile({
        id: currentUser.id,
        username: currentUser.username,
        email: currentUser.email,
        firstName: currentUser.firstName || '', 
        lastName: currentUser.lastName || '',  
        profilePicture: currentUser.profilePicture || 'https://via.placeholder.com/150', 
        role: currentUser.roles && currentUser.roles.length > 0 
                ? currentUser.roles[0].name.replace('ROLE_', '')
                : 'Volunteer',
      });
      

    } catch (err) {
      console.error("UserProfile: Error fetching user profile:", err);
      setError('Failed to load profile data. Please ensure you are logged in or re-login.');
      AuthService.logout(); 
      navigate('/login');
    } finally {
      setLoading(false);
    }
  }, [navigate]); 
  useEffect(() => {
    fetchUserProfile();
  }, [fetchUserProfile]);

  const hardcodedSkillsCount = 'N/A'; 
  const hardcodedCompletedActivitiesCount = 'N/A'; 
  const hardcodedTotalVolunteerHours = 'N/A';
  const [emailNotifications, setEmailNotifications] = useState(true);
  const [smsNotifications, setSmsNotifications] = useState(false);


  if (loading) {
    return <div className="user-profile-container">Loading profile...</div>;
  }

  if (error) {
    return <div className="user-profile-container error-message">{error}</div>;
  }

  if (!profile) {
    return <div className="user-profile-container">No profile data available. Please log in.</div>;
  }

  return (
    <div className="user-profile-container">
      <div className="breadcrumb">Dashboard &gt; User Profile</div>
      <div className="profile-header">
        <div className="profile-card">
          <div className="card-icon">👤</div>
          <h3>Profile Information</h3>
          <p>Manage your personal details and preferences</p>
          <button className="edit-profile-button">Edit Profile</button>
        </div>
        <div className="profile-card">
          <div className="card-icon">✨</div>
          <h3>Skills & Interests</h3>
          <p>{hardcodedSkillsCount} skills added, 5 interest areas</p> 
          <button className="manage-skills-button">Manage Skills</button>
          <button className="add-interests-button">Add Interests</button>
        </div>
        <div className="profile-card">
          <div className="card-icon">📈</div>
          <h3>Impact Summary</h3>
          <p>{hardcodedTotalVolunteerHours} volunteer hours, {hardcodedCompletedActivitiesCount} completed activities</p>
          <button className="view-certificate-button">View Certificate</button>
        </div>
      </div>

      <div className="personal-information-section">
        <h2>Personal Information</h2>
        <div className="profile-detail">
          <div 
            className="avatar-placeholder" 
            style={{ backgroundImage: `url(${profile.profilePicture})` }} 
          ></div>
          <div>
            <h3>{profile.firstName} {profile.lastName}</h3> 
            <p>{profile.role}</p>
          </div>
          <button className="edit-icon">✏️</button>
        </div>
        <p className="contact-info">📧 {profile.email}</p>
        <p className="contact-info">📞 N/A</p>
        <p className="contact-info">🏠 N/A</p> 
      </div>

      <div className="notification-preferences-section">
        <h2>Notification Preferences</h2>
        <div className="notification-toggle">
          <span>Email Notifications</span>
          <label className="switch">
            <input 
              type="checkbox" 
              checked={emailNotifications} 
              onChange={() => setEmailNotifications(!emailNotifications)} 
            />
            <span className="slider round"></span>
          </label>
        </div>
        <p className="notification-description">Receive activity updates via email</p>

        <div className="notification-toggle">
          <span>SMS Alerts</span>
          <label className="switch">
            <input 
              type="checkbox" 
              checked={smsNotifications} 
              onChange={() => setSmsNotifications(!smsNotifications)} 
            />
            <span className="slider round"></span>
          </label>
        </div>
        <p className="notification-description">Get text reminders for upcoming activities</p>
      </div>

      <div className="skills-section personal-information-section">
        <h2>Your Skills</h2>
        <p>Skills data is not currently available.</p> 
      </div>

      <div className="activity-history-section">
        <div className="tabs">
          <button className="tab-button active">Activity History</button>
          <button className="tab-button">Upcoming Activities</button>
          <button className="tab-button">Saved Activities</button>
        </div>

        <h3>Completed Activities</h3>
        <p>No completed activities data available.</p>

        <h3>Registered Activities</h3>
        <p>No registered activities data available.</p> 

        <button className="view-all-activities-button">View All Activities</button>
      </div>

    </div>
  );
}

export default UserProfile;