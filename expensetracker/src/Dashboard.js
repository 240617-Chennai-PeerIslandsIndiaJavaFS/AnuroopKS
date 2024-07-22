import React, { useContext, useMemo } from 'react';
import { ExpenseContext } from './ExpenseContext';
import { Link } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
  const { expenses } = useContext(ExpenseContext);

  const totalExpenses = useMemo(() => {
    return expenses.reduce((total, expense) => total + expense.amount, 0);
  }, [expenses]);

  const categoryBreakdown = useMemo(() => {
    return expenses.reduce((acc, expense) => {
      acc[expense.category] = (acc[expense.category] || 0) + expense.amount;
      return acc;
    }, {});
  }, [expenses]);

  return (
    <div className="dashboard-container">
      <h1>Expense Tracker</h1>
      <p>Total Expenses: Rs.{totalExpenses}</p>
      <h2>Category wise Expense:</h2>
      <ul>
        {Object.entries(categoryBreakdown).map(([category, amount]) => (
          <li key={category}>{category}: Rs.{amount}</li>
        ))}
      </ul>
      <Link to="/add">Add Expense</Link>
      <Link to="/list">View Expenses</Link>
    </div>
  );
};

export default Dashboard;