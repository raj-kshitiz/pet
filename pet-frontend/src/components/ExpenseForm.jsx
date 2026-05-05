import { useState, useEffect } from 'react';

const EMPTY = {
  paidTo: '',
  itemBought: '',
  date: new Date().toISOString().split('T')[0],
  amount: '',
  description: '',
  category: '',
};

const formatDateInput = (value) => {
  if (!value) return '';
  return typeof value === 'string' && value.includes('T') ? value.split('T')[0] : value;
};

export default function ExpenseForm({ initial, onSubmit, onCancel, submitLabel = 'Save Expense' }) {
  const [form, setForm] = useState(initial ? { ...initial, date: formatDateInput(initial.date) } : EMPTY);
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    if (initial) setForm({ ...initial, date: formatDateInput(initial.date) });
  }, [initial]);

  const set = (field) => (e) => setForm((f) => ({ ...f, [field]: e.target.value }));

  function validate() {
    const errs = {};
    if (!form.date)     errs.date     = 'Date is required';
    if (!form.amount || Number(form.amount) <= 0) errs.amount = 'Amount must be greater than 0';
    if (!form.category) errs.category = 'Category is required';
    return errs;
  }

  async function handleSubmit(e) {
    e.preventDefault();
    const errs = validate();
    if (Object.keys(errs).length) { setErrors(errs); return; }
    setErrors({});
    setSaving(true);
    try {
      await onSubmit({ ...form, date: formatDateInput(form.date), amount: Number(form.amount) });
    } finally {
      setSaving(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} noValidate>
      <div className="form-grid" style={{ marginBottom: 'var(--sp-md)' }}>
        {/* Paid To */}
        <div className="form-group">
          <label className="form-label" htmlFor="ef-paidTo">Paid To</label>
          <input
            id="ef-paidTo"
            className="form-input"
            placeholder="e.g. Amazon, Zomato…"
            value={form.paidTo}
            onChange={set('paidTo')}
          />
        </div>

        {/* Item Bought */}
        <div className="form-group">
          <label className="form-label" htmlFor="ef-itemBought">Item Bought</label>
          <input
            id="ef-itemBought"
            className="form-input"
            placeholder="e.g. Groceries, Coffee…"
            value={form.itemBought}
            onChange={set('itemBought')}
          />
        </div>

        {/* Date */}
        <div className="form-group">
          <label className="form-label" htmlFor="ef-date">Date *</label>
          <input
            id="ef-date"
            type="date"
            className="form-input"
            value={form.date}
            onChange={set('date')}
            required
          />
          {errors.date && <span className="form-error">{errors.date}</span>}
        </div>

        {/* Amount */}
        <div className="form-group">
          <label className="form-label" htmlFor="ef-amount">Amount (₹) *</label>
          <input
            id="ef-amount"
            type="number"
            min="0.01"
            step="0.01"
            className="form-input"
            placeholder="0.00"
            value={form.amount}
            onChange={set('amount')}
            required
          />
          {errors.amount && <span className="form-error">{errors.amount}</span>}
        </div>

        {/* Category */}
        <div className="form-group">
          <label className="form-label" htmlFor="ef-category">Category *</label>
          <input
            id="ef-category"
            className="form-input"
            placeholder="e.g. Food, Transport, Entertainment…"
            value={form.category}
            onChange={set('category')}
            required
          />
          {errors.category && <span className="form-error">{errors.category}</span>}
          <span className="form-hint">Type any category that makes sense to you 🙂</span>
        </div>
      </div>

      {/* Description — full width */}
      <div className="form-group" style={{ marginBottom: 'var(--sp-lg)' }}>
        <label className="form-label" htmlFor="ef-description">Description</label>
        <textarea
          id="ef-description"
          className="form-textarea"
          placeholder="Any extra notes about this expense…"
          value={form.description}
          onChange={set('description')}
        />
      </div>

      <div className="flex gap-sm" style={{ justifyContent: 'flex-end' }}>
        {onCancel && (
          <button type="button" className="btn btn-ghost" onClick={onCancel}>
            Cancel
          </button>
        )}
        <button type="submit" className="btn btn-primary" disabled={saving}>
          {saving ? '⏳ Saving…' : `✅ ${submitLabel}`}
        </button>
      </div>
    </form>
  );
}
