import React from 'react';
import  './ActiveChannels.css'

const ActiveChannels = () => {
  const channels = [
    { title: 'Event Planning', description: 'Coordinate upcoming events and activities' },
    { title: 'Team Leaders', description: 'Discussion for volunteer team leaders' },
    { title: 'New Volunteers', description: 'Workspace for new volunteers' },
    { title: 'Tech Support', description: 'Get help with app/web technical issues' },
  ];

  return (
    <div className="active-channels-section">
      <h2>Active Channels</h2>
      <div className="channels-grid">
        {channels.map((channel, index) => (
          <div key={index} className="channel-card">
            <h3>{channel.title}</h3>
            <p>{channel.description}</p>
            <button className="join-button">Join</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ActiveChannels;