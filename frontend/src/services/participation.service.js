// src/services/participation.service.js
import axios from 'axios';
import AuthService from './auth.service'; 
const API_GATEWAY_PARTICIPATION_SERVICE_URL = 'http://localhost:8080/participation-service/participations';

const getAuthHeader = () => {
    const user = AuthService.getCurrentUser();
    if (user && user.token) {
        return { Authorization: 'Bearer ' + user.token };
    } else {
        console.error("ParticipationService: User not authenticated. No JWT token found.");
        throw new Error("User not authenticated. Please log in.");
    }
};

const applyForActivity = (activityId, volunteerId) => {
    const data = {
        activity: {
            activity_id: activityId 
        },
        volunteer: {
            volunteer_id: volunteerId
        },
        registrationDate: new Date().toISOString().split('T')[0], 
        attendanceStatus: "registered"
    };

    let headers;
    try {
        headers = getAuthHeader();
    } catch (error) {
        return Promise.reject(error);
    }

    console.log("ParticipationService: Sending request to:", API_GATEWAY_PARTICIPATION_SERVICE_URL);
    console.log("ParticipationService: Request payload (data):", data);
    console.log("ParticipationService: Request headers:", headers);

    return axios.post(API_GATEWAY_PARTICIPATION_SERVICE_URL, data, { headers })
        .then(response => {
            console.log("ParticipationService: Successfully applied for activity:", response.data);
            return response;
        })
        .catch(error => {
            console.error("ParticipationService: Error applying for activity:", error.response ? error.response.data : error.message);
            return Promise.reject(error.response ? error.response.data : error.message);
        });
};

const getParticipationsByVolunteerId = (volunteerId) => {
    let headers;
    try {
        headers = getAuthHeader();
    } catch (error) {
        return Promise.reject(error);
    }
    
    console.log("ParticipationService: Fetching participations for volunteer ID:", volunteerId);
    return axios.get(`${API_GATEWAY_PARTICIPATION_SERVICE_URL}/volunteer/${volunteerId}`, { headers })
        .then(response => {
            console.log("ParticipationService: Participations fetched:", response.data);
            return response;
        })
        .catch(error => {
            console.error("ParticipationService: Error fetching participations for volunteer:", error.response ? error.response.data : error.message);
            return Promise.reject(error.response ? error.response.data : error.message);
        });
};


const ParticipationService = {
    applyForActivity,
    getParticipationsByVolunteerId,
};

export default ParticipationService;