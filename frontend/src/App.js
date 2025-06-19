import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from 'react-router-dom';

import Header from './components/Header';
import Sidebar from './components/Sidebar';

import LoginPage from '../src/pages/LoginPage';
import RegisterPage from '../src/pages/RegisterPage';
import Dashboard from '../src/pages/Dashboard';
import VolunteerManagement from '../src/pages/VolunteerManagement';
import AvailableActivities from '../src/pages/AvailableActivities';
import Notifications from '../src/pages/Notifications';
import AttendanceTracking from '../src/pages/AttendanceTracking';
import FeedbackRatings from '../src/pages/FeedbackRatings'; 
import FindActivities from '../src/pages/FindActivities';
import EngagementStatistics from '../src/pages/EngagementStatistics';
import RoleManagement from '../src/pages/RoleManagement';
import TeamCreation from '../src/pages/TeamCreation';
import ActiveChannels from '../src/pages/ActiveChannels';
import EditEvent from '../src/pages/EditEvent';

import EventsPage from '../src/pages/EventPages';
import CreateEventPage from '../src/pages/CreateEventPages';

import AuthService from '../src/services/auth.service';

import './App.css';

function App() {
  const [initialUserData, setInitialUserData] = useState(() => {
    const user = AuthService.getCurrentUser();
    return {
      isLoggedIn: !!user,
      roles: user && user.roles ? user.roles : [], 
    };
  });

  const [isLoggedIn, setIsLoggedIn] = useState(initialUserData.isLoggedIn);
  const [currentUserRoles, setCurrentUserRoles] = useState(
    initialUserData.roles.map(role => role.name || role)
  );
    
  console.log("App: INIT - isLoggedIn:", isLoggedIn, "currentUserRoles:", currentUserRoles);


  const handleLogin = () => {
    const currentUser = AuthService.getCurrentUser();
    const newStatus = !!currentUser;
    setIsLoggedIn(newStatus);
    const rolesFromUser = currentUser && currentUser.roles 
                          ? currentUser.roles.map(role => typeof role === 'object' && role.name ? role.name : role) 
                          : [];
    setCurrentUserRoles(rolesFromUser);
    console.log("App: handleLogin pozvan. Novi isLoggedIn status:", newStatus, "Korisnik (iz AuthService):", currentUser, "Nove uloge:", rolesFromUser);
  };

  const handleLogout = () => {
    AuthService.logout();
    setIsLoggedIn(false);
    setCurrentUserRoles([]); 
    console.log("App: handleLogout pozvan, isLoggedIn postavljen na FALSE. Uloge očišćene.");
  };

  useEffect(() => {
    const handleStorageChange = () => {
      const updatedUser = AuthService.getCurrentUser();
      const updatedStatus = !!updatedUser;
      const updatedRoles = updatedUser && updatedUser.roles 
                           ? updatedUser.roles.map(role => typeof role === 'object' && role.name ? role.name : role) 
                           : [];

      if (isLoggedIn !== updatedStatus || JSON.stringify(currentUserRoles) !== JSON.stringify(updatedRoles)) {
          setIsLoggedIn(updatedStatus);
          setCurrentUserRoles(updatedRoles); 
          console.log("App: 'storage' event detektovan. isLoggedIn ažuriran na:", updatedStatus, "Roles:", updatedRoles);
      }
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, [isLoggedIn, currentUserRoles]); 

  return (
    <Router>
      <AppLayout isLoggedIn={isLoggedIn} onLogout={handleLogout} onLogin={handleLogin} currentUserRoles={currentUserRoles} />
    </Router>
  );
}

function AppLayout({ isLoggedIn, onLogout, onLogin, currentUserRoles }) { 
  const location = useLocation();
  const hideNavAndSidebar = location.pathname === '/login' || location.pathname === '/register';

  console.log("AppLayout: Trenutna ruta:", location.pathname, "Globalno isLoggedIn stanje:", isLoggedIn, "Globalne uloge (AppLayout):", currentUserRoles); // DODATNI LOG

  const ProtectedRoute = ({ children }) => {
    console.log("ProtectedRoute: Provjera za rutu:", location.pathname, "isLoggedIn (iz AppLayouta):", isLoggedIn);
    if (!isLoggedIn) {
      console.log("ProtectedRoute: Korisnik NIJE prijavljen. Preusmjeravam na /login.");
      return <Navigate to="/login" replace />;
    }
    return children;
  };

  return (
    <div className="app-main-container">

      {!hideNavAndSidebar && (
        <Header isLoggedIn={isLoggedIn} onLogout={onLogout} />
      )}

      <div className={`main-content-layout ${hideNavAndSidebar ? 'full-width' : ''}`}>
        {!hideNavAndSidebar && (
          // Proslijedi currentUserRoles Sidebar-u
          <Sidebar currentUserRoles={currentUserRoles} />
        )}

        <div className="content-area-wrapper">
          <Routes>
            <Route path="/login" element={<LoginPage onLogin={onLogin} />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route
                path="/"
                element={isLoggedIn ? <Navigate to="/dashboard" replace /> : <Navigate to="/login" replace />}
            />
            <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
            <Route path="/volunteers/manage" element={<ProtectedRoute><VolunteerManagement /></ProtectedRoute>} />
            <Route path="/activities/available" element={<ProtectedRoute><AvailableActivities /></ProtectedRoute>} />
            <Route path="/notifications" element={<ProtectedRoute><Notifications /></ProtectedRoute>} />
            <Route path="/events" element={<ProtectedRoute><EventsPage /></ProtectedRoute>} />
            <Route path="/events/create" element={<ProtectedRoute><CreateEventPage /></ProtectedRoute>} />
            <Route path="/events/attendance/:eventId?" element={<ProtectedRoute><AttendanceTracking /></ProtectedRoute>} />
            <Route path="/feedback" element={<ProtectedRoute><FeedbackRatings /></ProtectedRoute>} />
            <Route path="/activities/find" element={<ProtectedRoute><FindActivities /></ProtectedRoute>} />
            <Route path="/reports/engagement" element={<ProtectedRoute><EngagementStatistics /></ProtectedRoute>} />
            <Route path="/settings/roles" element={<ProtectedRoute><RoleManagement /></ProtectedRoute>} />
            <Route path="/teams/create" element={<ProtectedRoute><TeamCreation /></ProtectedRoute>} />
            <Route path="/channels/active" element={<ProtectedRoute><ActiveChannels /></ProtectedRoute>} />
            <Route path="/events/edit/:id" element={<ProtectedRoute><EditEvent /></ProtectedRoute>} />
            <Route path='/volunteers' element={<ProtectedRoute><VolunteerManagement /></ProtectedRoute>} />
            <Route path="*" element={isLoggedIn ? <Navigate to="/dashboard" replace /> : <Navigate to="/login" replace />} />
          </Routes>
        </div>
      </div>
    </div>
  );
}

export default App;