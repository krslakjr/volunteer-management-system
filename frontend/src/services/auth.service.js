// src/services/auth.service.js
import axios from 'axios';

const API_GATEWAY_USER_SERVICE_URL = 'http://localhost:8080/user-service/api/auth';

const register = (username, email, password, firstName, lastName, profilePicture, roles) => {
    return axios.post(API_GATEWAY_USER_SERVICE_URL + '/signup', {
        username,
        email,
        password,
        firstName,
        lastName,
        profilePicture,
        roles, // <--- DODAJ OVU LINIJU!
    });
};

const signin = (username, password) => {
    console.log("AuthService: Pokušavam signin za korisnika:", username);

    return axios.post(API_GATEWAY_USER_SERVICE_URL + '/signin', {
        username,
        password,
    })
    .then(response => {
        console.log("AuthService: Odgovor primljen:", response);
        console.log("AuthService: response.data.token je:", response.data.token);

        if (response.data.token) { 
            console.log("AuthService: Token pronađen. Spremam u localStorage...");
            localStorage.setItem('user', JSON.stringify(response.data));
            console.log("AuthService: Podaci spremljeni u localStorage.");
        } else {
            console.log("AuthService: Token NIJE pronađen u response.data.");
        }
        return response.data;
    })
    .catch(error => {
        console.error("AuthService: Greška prilikom signin-a:", error.response || error);
        throw error;
    });
};

const logout = () => {
    console.log("AuthService: Brisanje 'user' iz localStorage.");
    localStorage.removeItem('user');
};

const getCurrentUser = () => {
    const user = localStorage.getItem('user');
    console.log("AuthService: Pokušavam dohvatiti 'user' iz localStorage:", user);
    return user ? JSON.parse(user) : null;
};

const AuthService = {
    register,
    signin,
    logout,
    getCurrentUser,
};

export default AuthService;