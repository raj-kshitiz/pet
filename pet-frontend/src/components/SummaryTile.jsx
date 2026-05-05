export default function SummaryTile({ icon, label, amount, count, accentColor, active, onClick }) {
  return (
    <div
      className={`tile${active ? ' active' : ''}`}
      style={{ '--tile-accent': accentColor }}
      onClick={onClick}
      role="button"
      tabIndex={0}
      onKeyDown={(e) => e.key === 'Enter' && onClick?.()}
    >
      <div className="tile__icon">{icon}</div>
      <div className="tile__label">{label}</div>
      <div className="tile__amount">
        {amount != null ? `₹${Number(amount).toLocaleString('en-IN', { maximumFractionDigits: 2 })}` : '—'}
      </div>
      {count != null && (
        <div className="tile__count">{count} expense{count !== 1 ? 's' : ''}</div>
      )}
    </div>
  );
}
