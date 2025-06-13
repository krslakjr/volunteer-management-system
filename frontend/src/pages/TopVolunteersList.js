// src/components/TopVolunteersList.js
import React from 'react';
// Pretpostavimo da imate ikonu zvjezdice ili je vučete iz biblioteke ikona
import './TopVolunteersList.css'; // Uvezite CSS modul za stilizaciju

const TopVolunteersList = ({ volunteers }) => {
  return (
    <div className="top-volunteers-list">
      {volunteers.map(volunteer => (
        <div key={volunteer.id} className="volunteer-item">
          <img src={volunteer.avatar} alt={volunteer.name} className="volunteer-avatar" />
          <div className="volunteer-info">
            <div className="volunteer-name">{volunteer.name}</div>
            <div className="volunteer-hours">{volunteer.hours}</div>
          </div>
          <span className="star-icon">⭐</span> {/* Primjer zvjezdice */}
        </div>
      ))}
    </div>
  );
};

export default TopVolunteersList;