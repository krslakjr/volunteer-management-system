import axios from 'axios';
import AuthService from './auth.service';

const API_GATEWAY_EVENTS_SERVICE_URL = 'http://localhost:8080/feedback-service/activities'; // Pretpostavljam da je 8080 port za gateway

const getAuthHeader = () => {
    const user = AuthService.getCurrentUser();
    if (user && user.token) {
        return { Authorization: 'Bearer ' + user.token };
    } else {
        console.warn("EventsService: No JWT token found in localStorage. Request will be sent without authorization.");
        return {};
    }
};

const getAllEvents = () => {
    return axios.get(API_GATEWAY_EVENTS_SERVICE_URL, {
        headers: getAuthHeader(),
    });
};

const getEventById = (id) => {
    return axios.get(`${API_GATEWAY_EVENTS_SERVICE_URL}/${id}`, {
        headers: getAuthHeader(),
    });
};


const createEvent = (eventData) => {
    return axios.post(API_GATEWAY_EVENTS_SERVICE_URL, eventData, {
        headers: getAuthHeader(),
    });
};

const updateEvent = (eventData) => { 
    return axios.post(API_GATEWAY_EVENTS_SERVICE_URL, eventData, { 
        headers: getAuthHeader(),
    });
};

const deleteEvent = (id) => {
    return axios.delete(`${API_GATEWAY_EVENTS_SERVICE_URL}/${id}`, {
        headers: getAuthHeader(),
    });
};

const EventsService = {
    getAllEvents,
    getEventById,
    createEvent,
    updateEvent, 
    deleteEvent,
};

export default EventsService;