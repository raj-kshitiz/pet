import { NavLink } from 'react-router-dom';

const links = [
  { to: '/',         icon: '🏠', label: 'Dashboard'   },
  { to: '/expenses', icon: '📋', label: 'All Expenses' },
  { to: '/add',      icon: '➕', label: 'Add Expense'  },
  { to: '/filter',   icon: '🔍', label: 'Filter & Insights' },
];

export default function Navbar() {
  return (
    <aside className="sidebar">
      <div className="sidebar__logo">
        <div className="sidebar__logo-icon">🌿</div>
        <span>PET</span>
      </div>

      <nav className="sidebar__nav">
        {links.map(({ to, icon, label }) => (
          <NavLink
            key={to}
            to={to}
            end={to === '/'}
            className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}
          >
            <span className="nav-link__icon">{icon}</span>
            {label}
          </NavLink>
        ))}
      </nav>

      <div className="sidebar__footer">Personal Expense Tracker</div>
    </aside>
  );
}
