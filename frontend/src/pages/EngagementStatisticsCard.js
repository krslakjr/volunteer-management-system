// src/components/EngagementStatisticsCard.js
import React from 'react';
import './EngagementStatisticsCard.css'; 
const EngagementStatisticsCard = ({ title, valueComponent, subtitle, onClick, isClickable }) => {
  return (
    <div
      className={`engagement-statistic-card ${isClickable ? 'clickable' : ''}`}
      onClick={isClickable ? onClick : undefined} 
    >
      <h3>{title}</h3>
      <div className="value">{valueComponent}</div> 
      {subtitle && <p className="subtitle">{subtitle}</p>}
    </div>
  );
};

export default EngagementStatisticsCard;