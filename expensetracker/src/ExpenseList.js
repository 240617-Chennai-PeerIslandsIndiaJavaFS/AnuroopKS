import React, { useContext, useEffect } from 'react';
import { ExpenseContext } from './ExpenseContext';
import './ExpenseList.css'; 

const ExpenseList = () => {
  const { expenses, setExpenses } = useContext(ExpenseContext);

  useEffect(() => {
    const storedExpenses = JSON.parse(localStorage.getItem('expenses'));
    if (storedExpenses) {
      setExpenses(storedExpenses);
    }
  }, [setExpenses]);

  useEffect(() => {
    localStorage.setItem('expenses', JSON.stringify(expenses));
  }, [expenses]);

  return (
    <div className="table-container">
      <h1>Expense List</h1>
      <table>
        <thead>
          <tr>
            <th>Description</th>
            <th>Amount (Rs.)</th>
            <th>Category</th>
          </tr>
        </thead>
        <tbody>
          {expenses.map((expense, index) => (
            <tr key={index}>
              <td>{expense.description}</td>
              <td>{expense.amount}</td>
              <td>{expense.category}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ExpenseList;