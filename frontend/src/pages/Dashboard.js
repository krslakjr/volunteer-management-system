// src/pages/Dashboard.js
import React from 'react';
import EngagementStatisticsCard from '../pages/EngagementStatisticsCard';
import RecentActivityCompletion from '../pages/RecentActivityCompletion';
// Pretpostavljamo da imate neke globalne stilove ili CSS module
import './Dashboard.css'; // Uvezite CSS modul
const Dashboard = () => {
  // Primjer podataka za statistiku (ovi podaci bi obično dolazili sa API-ja)
  const stats = [
    { title: 'Total Registered Volunteers', value: '1,248', detailsLink: '/volunteers' },
    { title: 'Completed Activities', value: '356', detailsLink: '/activities/completed' },
    { title: 'Active Events', value: '42', detailsLink: '/events/active' },
    { title: 'Average', value: '12.5 hrs', subtitle: 'Volunteer Time per Month', detailsLink: '/reports/time' },
  ];

  // Primjer podataka za nedavne aktivnosti
  const recentActivities = [
    { activity: 'Beach Cleanup', category: 'Environmental', volunteers: 21, date: 'Apr 13, 2023', status: 'Completed' },
    { activity: 'Food Drive', category: 'Community Service', volunteers: 18, date: 'Apr 10, 2023', status: 'Completed' },
    { activity: 'Border Center Visit', category: 'Elderly Care', volunteers: 12, date: 'Apr 06, 2023', status: 'Completed' },
    // ... više podataka
  ];

  return (
    <div className="dashboard-container">
      <h1>Engagement Statistics</h1>

      <div className="stats-grid">
        {stats.map((stat, index) => (
          <EngagementStatisticsCard
            key={index}
            title={stat.title}
            value={stat.value}
            subtitle={stat.subtitle}
            detailsLink={stat.detailsLink}
          />
        ))}
      </div>

      <h2>Volunteer Engagement Trends</h2>
      <div className="chart-placeholder" style={{ minHeight: '200px', border: '1px dashed #ccc', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        Chart visualization would appear here
      </div>

      <h2>Recent Activity Completion</h2>
      <RecentActivityCompletion activities={recentActivities} />
    </div>
  );
};

export default Dashboard;