// src/components/SocialPlatformCard.js
import React from 'react';
import { Link } from 'react-router-dom';
import './SocialPlatformCard.css'; // Import CSS module for styling

const SocialPlatformCard = ({ platform, description, actionText, actionLink, isAddPlatform }) => {
  return (
    <div className={`social-platform-card ${isAddPlatform ? 'add-platform' : ''}`}>
      <h3>{platform}</h3>
      <p>{description}</p>
      <Link to={actionLink} className="action-button">{actionText}</Link>
    </div>
  );
};

export default SocialPlatformCard;