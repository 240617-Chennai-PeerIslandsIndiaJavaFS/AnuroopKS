import React, { createContext, useState } from 'react';

const ExpenseContext = createContext();

const ExpenseProvider = ({ children }) => {
  const [expenses, setExpenses] = useState([]);
  const [categories, setCategories] = useState(['Food', 'Transport', 'Utilities','Others']);

  return (
    <ExpenseContext.Provider value={{ expenses, setExpenses, categories, setCategories }}>
      {children}
    </ExpenseContext.Provider>
  );
};

export { ExpenseContext, ExpenseProvider };