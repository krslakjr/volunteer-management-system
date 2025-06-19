// src/services/notification.service.js
import axios from 'axios';
import AuthService from './auth.service'; 

const API_GATEWAY_NOTIFICATION_SERVICE_URL = 'http://localhost:8080/notification-service/notifications';

const getAuthHeader = () => {
    const user = AuthService.getCurrentUser();
    if (user && user.token) {
        return { Authorization: 'Bearer ' + user.token };
    } else {
        console.warn("NotificationService: No JWT token found in localStorage. Request will be sent without authorization.");
        return {};
    }
};

const getAllNotifications = (page = 0, size = 10) => {
    return axios.get(`${API_GATEWAY_NOTIFICATION_SERVICE_URL}?page=${page}&size=${size}`, {
        headers: getAuthHeader(),
    });
};

const getNotificationById = (id) => {
    return axios.get(`${API_GATEWAY_NOTIFICATION_SERVICE_URL}/${id}`, {
        headers: getAuthHeader(),
    });
};

const createNotification = (notificationData) => {
    return axios.post(API_GATEWAY_NOTIFICATION_SERVICE_URL, notificationData, {
        headers: getAuthHeader(),
    });
};

const markNotificationAsRead = (id) => {
    return axios.put(`${API_GATEWAY_NOTIFICATION_SERVICE_URL}/${id}`, { read: true }, {
        headers: getAuthHeader(),
    });
};
const markAllNotificationsAsRead = async () => {
    console.warn("markAllNotificationsAsRead: This function currently assumes frontend logic. Consider adding a dedicated backend endpoint.");

};


const deleteNotification = (id) => {
    return axios.delete(`${API_GATEWAY_NOTIFICATION_SERVICE_URL}/${id}`, {
        headers: getAuthHeader(),
    });
};

const NotificationService = {
    getAllNotifications,
    getNotificationById,
    createNotification,
    markNotificationAsRead,
    markAllNotificationsAsRead, 
    deleteNotification,
};

export default NotificationService;