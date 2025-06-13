// src/pages/LoginPage.js
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styles from './LoginPage.css'; // Uvezite CSS modul
import '../styles/GlobalStyles.css'; // Uvezite globalne stilove za opće klase

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    setError('');

    // Simulacija poziva API-ja za prijavu
    console.log('Attempting to log in with:', { email, password });

    // Simulacija uspješne prijave
    setTimeout(() => {
      if (email === 'test@example.com' && password === 'password123') {
        localStorage.setItem('authToken', 'fake-jwt-token'); // Simulacija tokena
        navigate('/dashboard'); // Preusmjeri na dashboard
      } else {
        setError('Invalid email or password. Please try again.');
      }
    }, 1000);
  };

  return (
    <div className={styles.authPageContainer}> {/* Koristi opći kontejner za auth stranice */}
      <div className={`${styles.authCard} card-base`}> {/* Dodaj globalnu klasu card-base */}
        <h2>Login</h2>
        <p>Welcome back to Volunteer Hub!</p>

        {error && <div className="error-message">{error}</div>} {/* Koristi globalnu klasu */}

        <form onSubmit={handleLogin}>
          <div className="form-group"> {/* Koristi globalnu klasu */}
            <label htmlFor="email">Email Address</label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              required
            />
          </div>
          <div className="form-group"> {/* Koristi globalnu klasu */}
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter your password"
              required
            />
          </div>
          <div className={styles.forgotPassword}>
            <Link to="/forgot-password">Forgot Password?</Link> {/* Ruta za zaboravljenu lozinku */}
          </div>
          <button type="submit" className="button-primary">Log In</button> {/* Koristi globalnu klasu */}
        </form>
        <p className={styles.authLink}>
          Don't have an account? <Link to="/register">Register here</Link>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;