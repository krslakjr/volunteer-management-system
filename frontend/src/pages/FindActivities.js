import React, { useState } from 'react';
import './FindActivities.css'; 

function FindActivities() {
  const [searchResults] = useState([
    { id: 1, name: 'Beach Cleanup', category: 'Environmental', location: 'San Diego', date: 'Jun 15, 2023' },
    { id: 2, name: 'Food Drive', category: 'Community Service', location: 'Los Angeles', date: 'Jun 16, 2023' },
    { id: 3, name: 'Senior Center Visit', category: 'Elderly Care', location: 'San Francisco', date: 'Jun 20, 2023' },
    { id: 4, name: 'Park Restoration', category: 'Environmental', location: 'Portland', date: 'Jun 22, 2023' },
    { id: 5, name: 'Homeless Shelter', category: 'Community Service', location: 'Seattle', date: 'Jun 25, 2023' },
  ]);

  const [searchCriteria, setSearchCriteria] = useState({
    keywords: '',
    category: '',
    location: '',
    dateRange: '',
  });

  const handleSearchChange = (e) => {
    const { name, value } = e.target;
    setSearchCriteria({ ...searchCriteria, [name]: value });
  };

  const handleSearchActivities = () => {
    console.log('Searching with criteria:', searchCriteria);
  };

  const handleClearFilters = () => {
    setSearchCriteria({
      keywords: '',
      category: '',
      location: '',
      dateRange: '',
    });
    console.log('Filters cleared');
  };

  return (
    <div className="find-activities-container">
      <div className="breadcrumb">Events &gt; Activities &gt; Search</div>
      <h1>Find Activities</h1>

      <div className="search-filters-section">
        <div className="search-filter-card">
          <div className="card-icon">🔍</div>
          <h3>Search Filters</h3>
          <p>Narrow down activities by applying filters</p>
        </div>
        <div className="saved-searches-card">
          <div className="card-icon">💾</div>
          <h3>Saved Searches</h3>
          <p>Quick access to your frequent searches</p>
          <button className="view-all-button">View All</button>
        </div>
      </div>

      <div className="search-criteria-section">
        <h2>Search Criteria</h2>
        <div className="form-grid">
          <div className="form-group">
            <label htmlFor="keywords">Keywords</label>
            <input type="text" id="keywords" name="keywords" value={searchCriteria.keywords} onChange={handleSearchChange} placeholder="Search by activity name or" />
          </div>
          <div className="form-group">
            <label htmlFor="category">Category</label>
            <select id="category" name="category" value={searchCriteria.category} onChange={handleSearchChange}>
              <option value="">Select category</option>
              <option value="Environmental">Environmental</option>
              <option value="Community Service">Community Service</option>
              <option value="Elderly Care">Elderly Care</option>
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="location">Location</label>
            <input type="text" id="location" name="location" value={searchCriteria.location} onChange={handleSearchChange} placeholder="Enter city, state or zip code" />
          </div>
          <div className="form-group">
            <label htmlFor="dateRange">Date Range</label>
            <input type="date" id="dateRange" name="dateRange" value={searchCriteria.dateRange} onChange={handleSearchChange} />
          </div>
        </div>
        <div className="search-criteria-actions">
          <button className="search-activities-button" onClick={handleSearchActivities}>Search Activities</button>
          <button className="clear-filters-button" onClick={handleClearFilters}>Clear filters</button>
        </div>
      </div>

      <div className="search-results-section">
        <h2>Search Results (124)</h2>
        <div className="activity-tabs">
          <button className="tab active">All Activities</button>
          <button className="tab">Upcoming</button>
          <button className="tab">Past</button>
          <button className="tab">My Favorites</button>
        </div>
        <table>
          <thead>
            <tr>
              <th>Activity</th>
              <th>Category</th>
              <th>Location</th>
              <th>Date</th>
              <th></th> 
            </tr>
          </thead>
          <tbody>
            {searchResults.map((activity) => (
              <tr key={activity.id}>
                <td>{activity.name}</td>
                <td>{activity.category}</td>
                <td>{activity.location}</td>
                <td>{activity.date}</td>
                <td><button className="action-icon">⭐</button></td>
              </tr>
            ))}
          </tbody>
        </table>
        <button className="load-more-button">Load More</button>
      </div>
    </div>
  );
}

export default FindActivities;