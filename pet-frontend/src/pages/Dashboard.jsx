import { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { getAllExpenses, getSummaryByPeriod } from '../api/expenseApi';
import SummaryTile from '../components/SummaryTile';
import ExpenseTable from '../components/ExpenseTable';

function getGreeting() {
  const h = new Date().getHours();
  if (h < 12) return { text: 'Good morning', emoji: '☀️' };
  if (h < 17) return { text: 'Good afternoon', emoji: '🌤️' };
  return { text: 'Good evening', emoji: '🌙' };
}

function todayDate() {
  return new Date().toLocaleDateString('en-IN', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' });
}

const TILES = [
  { key: 'today', label: 'Today',      icon: '📅', color: '#4a7c59' },
  { key: 'week',  label: 'This Week',  icon: '📆', color: '#6a6cbf' },
  { key: 'month', label: 'This Month', icon: '🗓️', color: '#c97c3a' },
  { key: 'year',  label: 'This Year',  icon: '📊', color: '#3a9e8a' },
];

function sum(arr) { return (arr || []).reduce((t, e) => t + (e.amount || 0), 0); }

export default function Dashboard() {
  const navigate = useNavigate();
  const { text: greet, emoji } = getGreeting();

  const [all, setAll]         = useState([]);
  const [periods, setPeriods] = useState({});
  const [loading, setLoading] = useState(true);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const [allData, ...periodData] = await Promise.all([
        getAllExpenses(),
        ...TILES.map((t) => getSummaryByPeriod(t.key)),
      ]);
      setAll(Array.isArray(allData) ? allData : []);
      const p = {};
      TILES.forEach((t, i) => { p[t.key] = Array.isArray(periodData[i]) ? periodData[i] : []; });
      setPeriods(p);
    } catch {
      setAll([]);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { load(); }, [load]);

  // Motivational streak (count distinct dates in last 7 days)
  const recentDates = new Set(all.filter((e) => {
    const d = new Date(e.date);
    const diff = (Date.now() - d.getTime()) / 86400000;
    return diff <= 7;
  }).map((e) => e.date));
  const streak = recentDates.size;

  const recent = [...all].sort((a, b) => new Date(b.date) - new Date(a.date)).slice(0, 6);

  return (
    <div>
      {/* Greeting */}
      <div className="greeting-card">
        <div className="greeting-card__time">{todayDate()}</div>
        <div className="greeting-card__title">{greet}! {emoji}</div>
        <div className="greeting-card__sub">
          {all.length === 0
            ? "Welcome! Let's start tracking your expenses. It's a great habit 🌱"
            : `You've tracked ${all.length} expense${all.length !== 1 ? 's' : ''} in total. Keep it up!`}
        </div>
        {streak > 0 && (
          <div className="greeting-card__streak">
            🔥 {streak} day{streak !== 1 ? 's' : ''} tracked this week
          </div>
        )}
      </div>

      {/* Summary Tiles */}
      {loading ? (
        <div className="spinner-wrap"><div className="spinner" /></div>
      ) : (
        <div className="tiles-grid">
          {TILES.map((t) => (
            <SummaryTile
              key={t.key}
              icon={t.icon}
              label={t.label}
              amount={sum(periods[t.key])}
              count={periods[t.key]?.length}
              accentColor={t.color}
              onClick={() => navigate('/filter')}
            />
          ))}
        </div>
      )}

      {/* Recent Expenses */}
      <div className="section-title">
        <span>Recent Expenses</span>
        <span className="count-badge">{recent.length}</span>
      </div>
      <ExpenseTable expenses={recent} onRefresh={load} />

      {/* FAB → Add */}
      <button
        className="fab"
        title="Add new expense"
        onClick={() => navigate('/add')}
        aria-label="Add expense"
      >+</button>
    </div>
  );
}
