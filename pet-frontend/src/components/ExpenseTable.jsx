import { useState } from 'react';
import ExpenseForm from './ExpenseForm';
import { updateExpense, deleteExpense } from '../api/expenseApi';
import { useToast } from './Toast';

const fmt = (n) => `₹${Number(n).toLocaleString('en-IN', { maximumFractionDigits: 2 })}`;

export default function ExpenseTable({ expenses, onRefresh }) {
  const toast = useToast();
  const [editId, setEditId] = useState(null);
  const [deleting, setDeleting] = useState(null);

  async function handleUpdate(expense) {
    try {
      await updateExpense(expense);
      toast('Expense updated! 🎉', 'success');
      setEditId(null);
      onRefresh();
    } catch (err) {
      toast(err.message || 'Update failed', 'error');
    }
  }

  async function handleDelete(id) {
    if (!window.confirm('Delete this expense? This cannot be undone.')) return;
    setDeleting(id);
    try {
      await deleteExpense(id);
      toast('Expense deleted.', 'info');
      onRefresh();
    } catch (err) {
      toast(err.message || 'Delete failed', 'error');
    } finally {
      setDeleting(null);
    }
  }

  if (!expenses || expenses.length === 0) {
    return (
      <div className="empty-state">
        <div className="empty-state__icon">🧾</div>
        <div className="empty-state__title">No expenses yet</div>
        <div className="empty-state__sub">Start tracking by adding your first expense. Every great habit starts with a single step! 🌱</div>
      </div>
    );
  }

  return (
    <div className="expense-table-wrap">
      <table>
        <thead>
          <tr>
            <th>Date</th>
            <th>Paid To</th>
            <th>Item</th>
            <th>Category</th>
            <th>Amount</th>
            <th>Description</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {expenses.map((exp) =>
            editId === exp.id ? (
              <tr key={exp.id} className="editing">
                <td colSpan={7} style={{ padding: 'var(--sp-md)' }}>
                  <ExpenseForm
                    initial={exp}
                    onSubmit={handleUpdate}
                    onCancel={() => setEditId(null)}
                    submitLabel="Update"
                  />
                </td>
              </tr>
            ) : (
              <tr key={exp.id}>
                <td style={{ whiteSpace: 'nowrap' }}>{exp.date}</td>
                <td>{exp.paidTo || <span className="text-muted">—</span>}</td>
                <td>{exp.itemBought || <span className="text-muted">—</span>}</td>
                <td><span className="pill">{exp.category}</span></td>
                <td className="amount-cell">{fmt(exp.amount)}</td>
                <td className="text-muted text-sm">{exp.description || '—'}</td>
                <td>
                  <div className="flex gap-sm">
                    <button
                      className="btn-icon"
                      title="Edit"
                      onClick={() => setEditId(exp.id)}
                    >✏️</button>
                    <button
                      className="btn-icon"
                      title="Delete"
                      disabled={deleting === exp.id}
                      onClick={() => handleDelete(exp.id)}
                    >{deleting === exp.id ? '⏳' : '🗑️'}</button>
                  </div>
                </td>
              </tr>
            )
          )}
        </tbody>
      </table>
    </div>
  );
}
