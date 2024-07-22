import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ExpenseProvider } from './ExpenseContext';
import Dashboard from './Dashboard';
import ExpenseForm from './ExpenseForm';
import ExpenseList from './ExpenseList';
import './App.css';
function App() {
  return (
    <ExpenseProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/add" element={<ExpenseForm />} />
          <Route path="/list" element={<ExpenseList />} />
        </Routes>
      </Router>
    </ExpenseProvider>
  );
}

export default App;