import React, { useState } from 'react';
import './FeedbackRatings.css'; // Create this CSS file

function FeedbackRatings() {
  const [feedbackList] = useState([
    { id: 1, activity: 'Beach Cleanup', volunteer: 'Sarah Johnson', rating: 4, daysAgo: 2, status: 'Pending Response' },
    { id: 2, activity: 'Food Drive', volunteer: 'Michael Chen', rating: 3, daysAgo: 1, status: 'Responded' },
    { id: 3, activity: 'Senior Center Visit', volunteer: 'Jessica Lee', rating: 5, daysAgo: 1, status: 'Responded' },
    { id: 4, activity: 'Park Restoration', volunteer: 'David Kim', rating: 4, daysAgo: 3, status: 'Responded' },
    { id: 5, activity: 'Homeless Shelter', volunteer: 'Jessica Taylor', rating: 2, daysAgo: 2, status: 'Escalated' },
  ]);

  const [selectedFeedback, setSelectedFeedback] = useState({
    id: 1,
    activity: 'Beach Cleanup',
    volunteer: 'Sarah Johnson',
    date: 'June 19, 2023',
    feedbackText: "The event was well-organized and impactful. I appreciate the organizers who provided us with adequate, but we could have used more trash bags. The team leader was very supportive and encouraging. I would definitely volunteer for this type of event again!",
    overallRating: 4,
    organizationRating: 4,
    equipmentRating: 3,
    leadershipRating: 5,
    wouldVolunteerAgain: 'Yes',
  });

  const [response, setResponse] = useState('');

  const handleResponseSubmit = () => {
    console.log('Responding to feedback:', selectedFeedback.id, 'with:', response);
    alert('Response sent (check console)');
    setResponse(''); // Clear response field
    // In a real app, update feedback status via API
  };

  const handleArchive = () => {
    console.log('Archiving feedback:', selectedFeedback.id);
    alert('Feedback archived (check console)');
    // In a real app, update feedback status via API
  };

  return (
    <div className="feedback-ratings-container">
      <div className="breadcrumb">Dashboard &gt; Feedback &gt; Activity Ratings</div>
      <h1>Activity Ratings & Feedback</h1>

      <div className="feedback-tabs">
        <button className="tab active">All Feedback</button>
        <button className="tab">Pending Review</button>
        <button className="tab">Responded</button>
        <button className="tab">Archived</button>
      </div>

      <div className="feedback-list-section">
        <input type="text" placeholder="Search by event, volunteer..." />
        <button className="export-data-button">Export Data</button>

        <h2>Recent Feedback</h2>
        <table>
          <thead>
            <tr>
              <th>Activity</th>
              <th>Volunteer</th>
              <th>Rating</th>
              <th>Days Ago</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {feedbackList.map((feedback) => (
              <tr key={feedback.id} onClick={() => setSelectedFeedback(feedback)}>
                <td>
                  <div className="volunteer-info">
                    <div className="volunteer-avatar-small"></div>
                    {feedback.activity}
                  </div>
                </td>
                <td>{feedback.volunteer}</td>
                <td>{feedback.rating}/5</td>
                <td>{feedback.daysAgo} days ago</td>
                <td><span className={`status-badge ${feedback.status.toLowerCase().replace(' ', '-')}`}>{feedback.status}</span></td>
                <td>
                  <button className="action-icon">...</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {selectedFeedback && (
        <div className="feedback-details-section">
          <h2>Feedback Details</h2>
          <div className="feedback-detail-card">
            <h3>Feedback from {selectedFeedback.volunteer}</h3>
            <p className="feedback-date">{selectedFeedback.activity} - {selectedFeedback.date}</p>
            <p className="feedback-text">{selectedFeedback.feedbackText}</p>
            <div className="feedback-actions-buttons">
              <button className="respond-button">Respond</button>
              <button className="archive-button" onClick={handleArchive}>Archive</button>
            </div>
          </div>

          <div className="rating-summary-grid">
            <div className="rating-card">
              <div className="rating-icon">⭐</div>
              <h3>Overall Rating</h3>
              <p>{selectedFeedback.overallRating}/5</p>
            </div>
            <div className="rating-card">
              <div className="rating-icon">🏢</div>
              <h3>Organization</h3>
              <p>{selectedFeedback.organizationRating}/5</p>
            </div>
            <div className="rating-card">
              <div className="rating-icon">🛠️</div>
              <h3>Equipment</h3>
              <p>{selectedFeedback.equipmentRating}/5</p>
            </div>
            <div className="rating-card">
              <div className="rating-icon">🦸</div>
              <h3>Leadership</h3>
              <p>{selectedFeedback.leadershipRating}/5</p>
            </div>
            <div className="rating-card">
              <div className="rating-icon">👍</div>
              <h3>Would Volunteer Again</h3>
              <p>{selectedFeedback.wouldVolunteerAgain}</p>
            </div>
          </div>

          <div className="response-section">
            <h2>Response</h2>
            <p>Your response to {selectedFeedback.volunteer}</p>
            <textarea
              placeholder="Thank the volunteer and address their feedback..."
              value={response}
              onChange={(e) => setResponse(e.target.value)}
            ></textarea>
            <div className="response-actions">
              <button className="send-response-button" onClick={handleResponseSubmit}>Send Response</button>
              <button className="save-draft-button">Save Draft</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default FeedbackRatings;