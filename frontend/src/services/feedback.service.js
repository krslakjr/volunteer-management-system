// src/services/feedback.service.js
import axios from 'axios';
import AuthService from './auth.service';

const API_GATEWAY_FEEDBACK_SERVICE_URL = 'http://localhost:8080/feedback-service/feedbacks';

const getAuthHeader = () => {
    const user = AuthService.getCurrentUser();
    if (user && user.token) {
        return { Authorization: 'Bearer ' + user.token };
    } else {
        console.warn("FeedbackService: No JWT token found in localStorage. Request will be sent without authorization.");
        return {};
    }
};

const getAllFeedbacks = () => {
    return axios.get(API_GATEWAY_FEEDBACK_SERVICE_URL, {
        headers: getAuthHeader(),
    });
};

const getFeedbackById = (id) => {
    return axios.get(`${API_GATEWAY_FEEDBACK_SERVICE_URL}/${id}`, {
        headers: getAuthHeader(),
    });
};

const createFeedback = (feedbackData) => {
    console.log("Sending feedbackData to backend:", feedbackData); 
    return axios.post(API_GATEWAY_FEEDBACK_SERVICE_URL, feedbackData, { 
        headers: getAuthHeader(),
    });
};

const updateFeedback = (id, feedbackData) => {
    return axios.put(`${API_GATEWAY_FEEDBACK_SERVICE_URL}/${id}`, feedbackData, {
        headers: getAuthHeader(),
    });
};

const deleteFeedback = (id) => {
    return axios.delete(`${API_GATEWAY_FEEDBACK_SERVICE_URL}/${id}`, {
        headers: getAuthHeader(),
    });
};

const FeedbackService = {
    getAllFeedbacks,
    getFeedbackById,
    createFeedback,
    updateFeedback,
    deleteFeedback,
};

export default FeedbackService;