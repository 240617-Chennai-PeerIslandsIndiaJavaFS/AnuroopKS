import React, { useContext, useRef } from "react";
import { ExpenseContext } from "./ExpenseContext";
import { useNavigate } from "react-router-dom";
import "./ExpenseForm.css";

const ExpenseForm = () => {
  const { setExpenses, categories } = useContext(ExpenseContext);
  const descriptionRef = useRef();
  const amountRef = useRef();
  const categoryRef = useRef();
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const newExpense = {
      description: descriptionRef.current.value,
      amount: parseFloat(amountRef.current.value),
      category: categoryRef.current.value,
    };
    setExpenses((prevExpenses) => [...prevExpenses, newExpense]);
    navigate("/");
  };

  return (
    <div className="expense-form-container">
      <h1>Add New Expense</h1>
      <form onSubmit={handleSubmit} className="expense-form">
        <div className="form-group">
          <label>Description</label>
          <input type="text" ref={descriptionRef} required />
        </div>
        <div className="form-group">
          <label>Amount</label>
          <input type="number" ref={amountRef} required />
        </div>
        <div className="form-group">
          <label>Category</label>
          <select ref={categoryRef}>
            {categories.map((category) => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </select>
        </div>
        <button type="submit" className="submit-button">
          Add Expense
        </button>
      </form>
    </div>
  );
};
export default ExpenseForm;
