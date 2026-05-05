import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import { ToastProvider } from './components/Toast';
import Dashboard   from './pages/Dashboard';
import AllExpenses from './pages/AllExpenses';
import AddExpense  from './pages/AddExpense';
import FilterPage  from './pages/FilterPage';

export default function App() {
  return (
    <BrowserRouter>
      <ToastProvider>
        <div className="app-shell">
          <Navbar />
          <main className="main-content">
            <Routes>
              <Route path="/"         element={<Dashboard />}   />
              <Route path="/expenses" element={<AllExpenses />}  />
              <Route path="/add"      element={<AddExpense />}   />
              <Route path="/filter"   element={<FilterPage />}   />
            </Routes>
          </main>
        </div>
      </ToastProvider>
    </BrowserRouter>
  );
}
