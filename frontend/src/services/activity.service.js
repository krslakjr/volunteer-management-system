// src/services/activity.service.js

import axios from 'axios';
import AuthService from './auth.service';

const API_GATEWAY_ACTIVITY_SERVICE_URL = 'http://localhost:8080/activity-service/activities';

const getAuthHeader = () => {
    const user = AuthService.getCurrentUser();
    console.log("ActivityService: Pokušavam dohvatiti usera za auth header:", user);

    if (user && user.token) {
        console.log("ActivityService: Token pronađen za auth header. Vraćam Authorization zaglavlje.");
        return { Authorization: 'Bearer ' + user.token };
    } else {
        console.warn("ActivityService: Nema JWT tokena u localStorage. Zahtjev će biti poslan bez autorizacije.");
        return {};
    }
};

const getAllActivities = () => {
    return axios.get(API_GATEWAY_ACTIVITY_SERVICE_URL, {
        headers: getAuthHeader(),
    });
};

const getActivityById = (id) => {
    return axios.get(`${API_GATEWAY_ACTIVITY_SERVICE_URL}/${id}`, {
        headers: getAuthHeader(),
    });
};

const createActivity = (activityData) => {
    return axios.post(API_GATEWAY_ACTIVITY_SERVICE_URL, activityData, {
        headers: getAuthHeader(),
    });
};

const ActivityService = {
    getAllActivities,
    getActivityById,
    createActivity,
};

export default ActivityService;