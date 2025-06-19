import axios from 'axios';
import AuthService from './auth.service'; 

const API_GATEWAY_VOLUNTEER_SERVICE_URL = 'http://localhost:8080/activity-service/volunteers'; 

const getAuthHeader = () => {
    const user = AuthService.getCurrentUser();
    if (user && user.token) {
        return { Authorization: 'Bearer ' + user.token };
    } else {
        console.warn("VolunteerService: No JWT token found in localStorage. Request will be sent without authorization.");
        return {};
    }
};

const getAllVolunteers = () => {
    return axios.get(API_GATEWAY_VOLUNTEER_SERVICE_URL, {
        headers: getAuthHeader(),
    });
};

const getVolunteerById = (id) => {
    return axios.get(`${API_GATEWAY_VOLUNTEER_SERVICE_URL}/${id}`, {
        headers: getAuthHeader(),
    });
};

const createVolunteer = (volunteerData) => {
    return axios.post(API_GATEWAY_VOLUNTEER_SERVICE_URL, volunteerData, {
        headers: getAuthHeader(),
    });
};

const updateVolunteer = (id, volunteerData) => {
    return axios.put(`${API_GATEWAY_VOLUNTEER_SERVICE_URL}/${id}`, volunteerData, {
        headers: getAuthHeader(),
    });
};

const deleteVolunteer = (id) => {
    return axios.delete(`${API_GATEWAY_VOLUNTEER_SERVICE_URL}/${id}`, {
        headers: getAuthHeader(),
    });
};

const VolunteerService = {
    getAllVolunteers,
    getVolunteerById,
    createVolunteer,
    updateVolunteer,
    deleteVolunteer,
};

export default VolunteerService;