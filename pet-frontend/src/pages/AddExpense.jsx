import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { addExpense } from '../api/expenseApi';
import ExpenseForm from '../components/ExpenseForm';
import { useToast } from '../components/Toast';

export default function AddExpense() {
  const navigate = useNavigate();
  const toast    = useToast();
  const [done, setDone] = useState(false);

  async function handleSubmit(expense) {
    await addExpense(expense);
    setDone(true);
    toast('Expense added! Great job tracking 🌟', 'success', 4000);
    setTimeout(() => navigate('/'), 1800);
  }

  return (
    <div>
      <div className="page-header">
        <h1>Add Expense ➕</h1>
        <p>Tracking your spending is one of the best habits you can build. 🌱</p>
      </div>

      {done ? (
        <div className="empty-state">
          <div className="empty-state__icon">🎉</div>
          <div className="empty-state__title">Nicely done!</div>
          <div className="empty-state__sub">Your expense has been saved. Redirecting you to the dashboard…</div>
        </div>
      ) : (
        <div className="card card--padded" style={{ maxWidth: 740 }}>
          <div className="section-title" style={{ marginBottom: 'var(--sp-lg)' }}>
            <span>New Expense</span>
          </div>
          <ExpenseForm
            onSubmit={handleSubmit}
            onCancel={() => navigate('/')}
            submitLabel="Add Expense"
          />
        </div>
      )}
    </div>
  );
}
