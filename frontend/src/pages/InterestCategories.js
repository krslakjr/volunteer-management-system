// src/components/InterestCategories.js
import React from 'react';
import './InterestCategories.css'; // Uvezite CSS modul
const InterestCategories = () => {
  const categories = ['Environmental', 'Education', 'Animal Welfare', 'Community', 'Health'];

  return (
    <div className="interest-categories">
      {categories.map((category, index) => (
        <button key={index} className="category-button">{category}</button>
      ))}
    </div>
  );
};

export default InterestCategories;