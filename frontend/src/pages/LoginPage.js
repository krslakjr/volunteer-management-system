import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import AuthService from '../services/auth.service';
import './LoginPage.css';
import '../styles/GlobalStyles.css';

const LoginPage = ({ onLogin }) => { 
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await AuthService.signin(username, password); 
      const currentUser = AuthService.getCurrentUser();
      
      if (currentUser && currentUser.id) { 
        onLogin(); 
        navigate('/dashboard'); 
      } else {
        setError('Login failed. No user data received or invalid user ID.');
      }
    } catch (err) {
      let errorMessage = 'Login failed. Please check your credentials.';
      if (err.response && err.response.data && err.response.data.message) {
        errorMessage = err.response.data.message;
      } else if (err.message) {
        errorMessage = err.message;
      }
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="authPageContainer">
      <div className="authCard card-base">
        <h2>Login</h2>
        <p>Welcome back to Volunteer Hub!</p>

        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleLogin}>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Enter your username"
              required
            />
          </div>
          <div className="form-group">
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
          <div className="forgotPassword">
            <Link to="/forgot-password">Forgot Password?</Link>
          </div>
          <button type="submit" className="button-primary" disabled={loading}>
            {loading ? 'Logging In...' : 'Log In'}
          </button>
        </form>
        <p className="authLink">
          Don't have an account? <Link to="/register">Register here</Link>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;