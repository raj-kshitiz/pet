import { useState, useCallback } from 'react';
import { filterExpenses, getSummaryByPeriod, getLastXDays } from '../api/expenseApi';
import ExpenseTable from '../components/ExpenseTable';
import SpendingChart from '../components/SpendingChart';
import { useToast } from '../components/Toast';

const PERIOD_CHIPS = [
  { label: '📅 Today',      value: 'today' },
  { label: '📆 This Week',  value: 'week'  },
  { label: '🗓️ This Month', value: 'month' },
  { label: '📊 This Year',  value: 'year'  },
  { label: '⏱️ Last 7 days', value: 'last7' },
  { label: '⏱️ Last 30 days',value: 'last30'},
];

const fmt = (n) => `₹${Number(n).toLocaleString('en-IN', { maximumFractionDigits: 2 })}`;
const sum  = (arr) => (arr || []).reduce((t, e) => t + (e.amount || 0), 0);

const EMPTY_FILTERS = { category: '', paidTo: '', startDate: '', endDate: '', minAmount: '' };

export default function FilterPage() {
  const toast = useToast();
  const [filters, setFilters]   = useState(EMPTY_FILTERS);
  const [results, setResults]   = useState(null);
  const [activePeriod, setActivePeriod] = useState(null);
  const [loading, setLoading]   = useState(false);

  const setF = (field) => (e) => setFilters((f) => ({ ...f, [field]: e.target.value }));

  const runFilter = useCallback(async () => {
    setLoading(true);
    setActivePeriod(null);
    try {
      const clean = Object.fromEntries(
        Object.entries(filters).filter(([, v]) => v !== '' && v !== null)
      );
      const data = await filterExpenses(clean);
      setResults(Array.isArray(data) ? data : []);
    } catch (err) {
      toast(err.message || 'Filter failed', 'error');
    } finally {
      setLoading(false);
    }
  }, [filters, toast]);

  async function runPeriod(chip) {
    setLoading(true);
    setActivePeriod(chip.value);
    setFilters(EMPTY_FILTERS);
    try {
      let data;
      if (chip.value === 'last7')  data = await getLastXDays(7);
      else if (chip.value === 'last30') data = await getLastXDays(30);
      else data = await getSummaryByPeriod(chip.value);
      setResults(Array.isArray(data) ? data : []);
    } catch (err) {
      toast(err.message || 'Failed to load period', 'error');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <div className="page-header">
        <h1>Filter &amp; Insights 🔍</h1>
        <p>Understand your spending patterns — knowledge is power!</p>
      </div>

      {/* Period chips */}
      <div className="section-title"><span>Quick Period View</span></div>
      <div className="period-chips mb-lg">
        {PERIOD_CHIPS.map((c) => (
          <button
            key={c.value}
            className={`chip${activePeriod === c.value ? ' active' : ''}`}
            onClick={() => runPeriod(c)}
          >
            {c.label}
          </button>
        ))}
      </div>

      {/* Advanced filter */}
      <div className="card card--padded mb-lg">
        <div className="section-title"><span>Advanced Filter</span></div>
        <div className="filter-grid mb-md">
          <div className="form-group">
            <label className="form-label" htmlFor="f-category">Category</label>
            <input id="f-category" className="form-input" placeholder="e.g. Food" value={filters.category} onChange={setF('category')} />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="f-paidTo">Paid To</label>
            <input id="f-paidTo" className="form-input" placeholder="e.g. Zomato" value={filters.paidTo} onChange={setF('paidTo')} />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="f-startDate">Start Date</label>
            <input id="f-startDate" type="date" className="form-input" value={filters.startDate} onChange={setF('startDate')} />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="f-endDate">End Date</label>
            <input id="f-endDate" type="date" className="form-input" value={filters.endDate} onChange={setF('endDate')} />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="f-minAmount">Min Amount (₹)</label>
            <input id="f-minAmount" type="number" min="0" className="form-input" placeholder="0" value={filters.minAmount} onChange={setF('minAmount')} />
          </div>
        </div>
        <div className="flex gap-sm">
          <button className="btn btn-primary" onClick={runFilter} disabled={loading}>
            {loading ? '⏳ Loading…' : '🔍 Apply Filter'}
          </button>
          <button className="btn btn-ghost" onClick={() => { setFilters(EMPTY_FILTERS); setResults(null); setActivePeriod(null); }}>
            Clear
          </button>
        </div>
      </div>

      {/* Results */}
      {loading && <div className="spinner-wrap"><div className="spinner" /></div>}

      {!loading && results !== null && (
        <>
          {/* Stats */}
          {results.length > 0 && (
            <div className="card card--padded mb-lg flex items-center justify-between gap-md" style={{ flexWrap: 'wrap' }}>
              <div>
                <div className="text-xs text-muted font-bold" style={{ textTransform: 'uppercase', letterSpacing: '.07em' }}>Results</div>
                <div className="font-bold">{results.length} expense{results.length !== 1 ? 's' : ''}</div>
              </div>
              <div>
                <div className="text-xs text-muted font-bold" style={{ textTransform: 'uppercase', letterSpacing: '.07em' }}>Total</div>
                <div className="font-bold" style={{ fontSize: 'var(--fs-lg)', color: 'var(--clr-primary)' }}>{fmt(sum(results))}</div>
              </div>
            </div>
          )}

          {/* Chart */}
          {results.length > 1 && (
            <div className="mb-lg">
              <SpendingChart expenses={results} />
            </div>
          )}

          {/* Table */}
          <div className="section-title"><span>Results</span><span className="count-badge">{results.length}</span></div>
          <ExpenseTable expenses={results} onRefresh={() => results && runFilter()} />
        </>
      )}
    </div>
  );
}
