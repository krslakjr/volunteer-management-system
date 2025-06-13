// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

// Import komponenti za zaglavlje (Header)
import Header from './components/Header';

// Import svih stranica koje smo kreirali ili koje su bile prisutne u vašem originalnom App.js
// Podesite putanje prema vašoj strukturi projekta (npr. src/pages)
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import Dashboard from './pages/Dashboard'; // Sadrži statistiku angažmana, nedavne aktivnosti, top volontere, socijalne platforme
import VolunteerManagement from './pages/VolunteerManagement';
import AvailableActivities from './pages/AvailableActivities';
import UserProfile from './pages/UserProfile';
import Notifications from './pages/Notifications';
import AttendanceTracking from './pages/AttendanceTracking';
import FeedbackRatings from './pages/FeedbackRatings';
import FindActivities from './pages/FindActivities';
import EngagementStatistics from './pages/EngagementStatistics'; // Detaljna stranica za izvještaje
import RoleManagement from './pages/RoleManagement'; // Sadrži pregled uloga i detalje dozvola
import TeamCreation from './pages/TeamCreation'; // Sadrži formu za kreiranje tima i aktivne kanale

function App() {
  // Simulacija provjere autentifikacije. U pravoj aplikaciji, ovo bi bilo složenije
  // (npr. provjera tokena iz localStorage, Context API za stanje korisnika, itd.)
  const isAuthenticated = () => {
    // Ovo je samo placeholder. Zamijenite pravom logikom provjere prijave.
    return localStorage.getItem('authToken') ? true : false;
  };

  // Komponenta za zaštitu ruta
  // Renderuje dijete komponente ako je korisnik autentifikovan, inače preusmjerava na login
  const ProtectedRoute = ({ children }) => {
    if (!isAuthenticated()) {
      return <Navigate to="/login" replace />;
    }
    return children;
  };

  return (
    <Router>
      <div className="app-main-container"> {/* Glavni kontejner za cijelu aplikaciju */}
        {/* Header se renderuje samo ako korisnik nije na stranicama za prijavu/registraciju
            Ovo je gruba provjera, bolja bi bila na osnovu stanja autentifikacije. */}
        {window.location.pathname !== '/login' && window.location.pathname !== '/register' && <Header />}

        {/* Padding za sadržaj (ili stilizujte direktno u komponentama stranica) */}
        <div className="content-area">
          <Routes>
            {/* Javne rute (dostupne svima) */}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />

            {/* Defaultna ruta: preusmjerava na login ili dashboard, ovisno o statusu prijave */}
            <Route
              path="/"
              element={isAuthenticated() ? <Navigate to="/dashboard" replace /> : <Navigate to="/login" replace />}
            />

            {/* Privatne rute (zaštićene, zahtijevaju prijavu) */}
            <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
            <Route path="/volunteers/manage" element={<ProtectedRoute><VolunteerManagement /></ProtectedRoute>} />
            <Route path="/activities/available" element={<ProtectedRoute><AvailableActivities /></ProtectedRoute>} />
            <Route path="/profile" element={<ProtectedRoute><UserProfile /></ProtectedRoute>} />
            <Route path="/notifications" element={<ProtectedRoute><Notifications /></ProtectedRoute>} />
            {/* Dinamička ruta za praćenje prisutnosti */}
            <Route path="/events/attendance/:eventId" element={<ProtectedRoute><AttendanceTracking /></ProtectedRoute>} />
            <Route path="/feedback" element={<ProtectedRoute><FeedbackRatings /></ProtectedRoute>} />
            <Route path="/activities/find" element={<ProtectedRoute><FindActivities /></ProtectedRoute>} />
            <Route path="/reports/engagement" element={<ProtectedRoute><EngagementStatistics /></ProtectedRoute>} />
            <Route path="/settings/roles" element={<ProtectedRoute><RoleManagement /></ProtectedRoute>} />
            <Route path="/teams/create" element={<ProtectedRoute><TeamCreation /></ProtectedRoute>} />

            {/* Ruta za nepostojeće stranice (opciono) */}
            {/* <Route path="*" element={<NotFoundPage />} /> */}
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;