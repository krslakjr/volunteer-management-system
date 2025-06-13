// src/components/EngagementStatisticsCard.js
import React from 'react';
import { Link } from 'react-router-dom'; // Pretpostavljamo react-router-dom
import './EngagementStatisticsCard.css'; // Uvezite CSS modul

const EngagementStatisticsCard = ({ title, value, subtitle, detailsLink }) => {
  return (
    <div className="stats-card">
      <h3>{title}</h3>
      <div className="stats-value">{value}</div>
      {subtitle && <div className="stats-subtitle">{subtitle}</div>}
      <Link to={detailsLink} className="view-details-button">View Details</Link>
    </div>
  );
};

export default EngagementStatisticsCard;