import { useEffect, useState, useCallback } from 'react';
import { getAllExpenses, deleteAllExpenses } from '../api/expenseApi';
import ExpenseTable from '../components/ExpenseTable';
import { useToast } from '../components/Toast';

const fmt = (n) => `₹${Number(n).toLocaleString('en-IN', { maximumFractionDigits: 2 })}`;

export default function AllExpenses() {
  const toast = useToast();
  const [expenses, setExpenses] = useState([]);
  const [loading, setLoading]   = useState(true);
  const [search, setSearch]     = useState('');

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const data = await getAllExpenses();
      setExpenses(Array.isArray(data) ? data.sort((a, b) => new Date(b.date) - new Date(a.date)) : []);
    } catch (err) {
      toast(err.message || 'Could not load expenses', 'error');
    } finally {
      setLoading(false);
    }
  }, [toast]);

  useEffect(() => { load(); }, [load]);

  async function handleDeleteAll() {
    if (!window.confirm('Delete ALL expenses? This cannot be undone! 🚨')) return;
    try {
      await deleteAllExpenses();
      toast('All expenses deleted.', 'info');
      load();
    } catch (err) {
      toast(err.message || 'Delete all failed', 'error');
    }
  }

  const q = search.toLowerCase();
  const filtered = expenses.filter((e) =>
    !q ||
    e.category?.toLowerCase().includes(q) ||
    e.paidTo?.toLowerCase().includes(q) ||
    e.itemBought?.toLowerCase().includes(q) ||
    e.description?.toLowerCase().includes(q)
  );

  const total = filtered.reduce((t, e) => t + (e.amount || 0), 0);

  return (
    <div>
      <div className="page-header">
        <h1>All Expenses 📋</h1>
        <p>Every rupee tracked is a step towards financial clarity.</p>
      </div>

      {/* Toolbar */}
      <div className="flex items-center gap-md mb-lg" style={{ flexWrap: 'wrap' }}>
        <input
          id="search-expenses"
          className="form-input"
          style={{ maxWidth: 320 }}
          placeholder="🔎 Search by category, payee, item…"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
        <div style={{ flex: 1 }} />
        {expenses.length > 0 && (
          <button className="btn btn-danger btn-sm" onClick={handleDeleteAll}>
            🗑️ Delete All
          </button>
        )}
      </div>

      {/* Stats bar */}
      {!loading && filtered.length > 0 && (
        <div className="card card--padded mb-lg flex items-center justify-between gap-md" style={{ flexWrap: 'wrap' }}>
          <div>
            <div className="text-xs text-muted font-bold" style={{ textTransform: 'uppercase', letterSpacing: '.07em' }}>Showing</div>
            <div className="font-bold">{filtered.length} expense{filtered.length !== 1 ? 's' : ''}</div>
          </div>
          <div>
            <div className="text-xs text-muted font-bold" style={{ textTransform: 'uppercase', letterSpacing: '.07em' }}>Total</div>
            <div className="font-bold" style={{ fontSize: 'var(--fs-lg)', color: 'var(--clr-primary)' }}>{fmt(total)}</div>
          </div>
        </div>
      )}

      {loading ? (
        <div className="spinner-wrap"><div className="spinner" /></div>
      ) : (
        <ExpenseTable expenses={filtered} onRefresh={load} />
      )}
    </div>
  );
}
