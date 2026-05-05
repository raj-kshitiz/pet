/**
 * SpendingChart
 * Pure CSS/HTML bar chart grouped by category — no external chart library.
 */
export default function SpendingChart({ expenses }) {
  if (!expenses || expenses.length === 0) return null;

  // Aggregate by category
  const totals = expenses.reduce((acc, e) => {
    const cat = e.category || 'Other';
    acc[cat] = (acc[cat] || 0) + (e.amount || 0);
    return acc;
  }, {});

  const entries = Object.entries(totals).sort((a, b) => b[1] - a[1]).slice(0, 8);
  const max = entries[0]?.[1] || 1;

  const fmt = (n) =>
    `₹${Number(n).toLocaleString('en-IN', { maximumFractionDigits: 0 })}`;

  return (
    <div className="chart-wrap">
      <div className="chart-title">📊 Spending by Category</div>
      {entries.map(([cat, total]) => (
        <div key={cat} className="chart-bar-row">
          <div className="chart-bar-label" title={cat}>{cat}</div>
          <div className="chart-bar-track">
            <div
              className="chart-bar-fill"
              style={{ width: `${(total / max) * 100}%` }}
            />
          </div>
          <div className="chart-bar-value">{fmt(total)}</div>
        </div>
      ))}
    </div>
  );
}
