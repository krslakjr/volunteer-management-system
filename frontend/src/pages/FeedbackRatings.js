import React, { useState, useEffect } from 'react';
import FeedbackService from '../services/feedback.service'; 
import './FeedbackRatings.css'; 

function FeedbackRatings() {
    const [feedbackList, setFeedbackList] = useState([]);
    const [selectedFeedback, setSelectedFeedback] = useState(null); 
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [response, setResponse] = useState('');

    const fetchFeedbacks = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await FeedbackService.getAllFeedbacks();
            console.log("Fetched Feedbacks:", response.data);
            setFeedbackList(response.data); 
            if (response.data.length > 0) {
                setSelectedFeedback(response.data[0]); 
            }
        } catch (err) {
            const resError =
                (err.response &&
                    err.response.data &&
                    err.response.data.message) ||
                err.message ||
                err.toString();
            setError(`Error fetching feedbacks: ${resError}. Please ensure backend services are running and you are authorized.`);
            console.error('Error fetching feedbacks:', err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchFeedbacks();
    }, []);
    const formatDate = (timestamp) => {
        if (!timestamp) return 'N/A';
        const date = new Date(timestamp);
        return date.toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
        });
    };

    const calculateDaysAgo = (timestamp) => {
        if (!timestamp) return 'N/A';
        const date = new Date(timestamp);
        const now = new Date();
        const diffTime = Math.abs(now.getTime() - date.getTime());
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
        return `${diffDays} day${diffDays === 1 ? '' : 's'} ago`;
    };

    const handleResponseSubmit = () => {
        if (!selectedFeedback) return;
        console.log('Responding to feedback:', selectedFeedback.feedbackId, 'with:', response);
        alert('Response sent (check console)');
        setResponse('');
    };

    const handleArchive = () => {
        if (!selectedFeedback) return;
        console.log('Archiving feedback:', selectedFeedback.feedbackId);
        alert('Feedback archived (check console)');
    };

    if (loading) {
        return (
            <div className="feedback-ratings-container loading-message-container">
                <p className="loading-message">Loading feedback data...</p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="feedback-ratings-container error-message-container">
                <p className="error-message">{error}</p>
            </div>
        );
    }

    return (
        <div className="feedback-ratings-container">
            <h1>Activity Ratings & Feedback</h1>

            <div className="feedback-list-section">
                <h2>Recent Feedback</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Rating</th>
                            <th>Comment</th>
                            <th>Timestamp</th> 
                            <th>Days Ago</th>
                        </tr>
                    </thead>
                    <tbody>
                        {feedbackList.length === 0 ? (
                            <tr>
                                <td colSpan="5" className="no-feedback-message">No feedback available.</td>
                            </tr>
                        ) : (
                            feedbackList.map((feedback) => (
                                <tr key={feedback.feedbackId} onClick={() => setSelectedFeedback(feedback)}
                                    className={selectedFeedback && selectedFeedback.feedbackId === feedback.feedbackId ? 'selected-row' : ''} 
                                >
                                    <td>{feedback.rating}/5</td>
                                    <td className="feedback-comment-preview">{feedback.comment.substring(0, 50)}{feedback.comment.length > 50 ? '...' : ''}</td>
                                    <td>{formatDate(feedback.timestamp)}</td>
                                    <td>{calculateDaysAgo(feedback.timestamp)}</td>
                                    
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            {selectedFeedback && (
                <div className="feedback-details-section">
                    <h2>Feedback Details</h2>
                    <div className="feedback-detail-card">
                        <h3>Feedback (ID: {selectedFeedback.feedbackId})</h3>
                        <p className="feedback-date">Date: {formatDate(selectedFeedback.timestamp)}</p>
                        <p className="feedback-text">{selectedFeedback.comment}</p>
                        
                        <div className="feedback-actions-buttons">
                            <button className="respond-button" onClick={handleResponseSubmit}>Respond</button>
                            <button className="archive-button" onClick={handleArchive}>Archive</button>
                        </div>
                    </div>

                    <div className="rating-summary-grid">
                        <div className="rating-card">
                            <div className="rating-icon">⭐</div>
                            <h3>Overall Rating</h3>
                            <p>{selectedFeedback.rating}/5</p> 
                        </div>
                        <div className="rating-card">
                            <div className="rating-icon">🏢</div>
                            <h3>Organization</h3>
                            <p>4/5</p> 
                        </div>
                        <div className="rating-card">
                            <div className="rating-icon">🛠️</div>
                            <h3>Equipment</h3>
                            <p>5/5</p> 
                        </div>
                        <div className="rating-card">
                            <div className="rating-icon">🦸</div>
                            <h3>Leadership</h3>
                            <p>5/5</p> 
                        </div>
                        <div className="rating-card">
                            <div className="rating-icon">👍</div>
                            <h3>Would Volunteer Again</h3>
                            <p>4/5</p> 
                        </div>
                    </div>

                   
                </div>
            )}
        </div>
    );
}

export default FeedbackRatings;