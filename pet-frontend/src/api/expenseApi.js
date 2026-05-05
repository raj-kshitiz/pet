/**
 * expenseApi.js
 * All HTTP calls to the Spring Boot backend.
 * Uses native fetch() only — no axios, no external HTTP library.
 */

const BASE = '/api';

async function request(url, options = {}) {
  const res = await fetch(url, {
    headers: { 'Content-Type': 'application/json', ...options.headers },
    ...options,
  });

  if (res.status === 204 || res.status === 404) return [];
  if (!res.ok) {
    const text = await res.text().catch(() => 'Unknown error');
    throw new Error(text || `HTTP ${res.status}`);
  }

  const text = await res.text();
  return text ? JSON.parse(text) : null;
}

// ── CRUD ──────────────────────────────────────────────────────────
export const getAllExpenses = () => request(`${BASE}/expense`);

export const addExpense = (expense) =>
  request(`${BASE}/expense`, { method: 'POST', body: JSON.stringify(expense) });

export const updateExpense = (expense) =>
  request(`${BASE}/expense`, { method: 'PUT', body: JSON.stringify(expense) });

export const deleteExpense = (id) =>
  request(`${BASE}/expense/${id}`, { method: 'DELETE' });

export const deleteAllExpenses = () =>
  request(`${BASE}/expense/all`, { method: 'DELETE' });

// ── FILTER ────────────────────────────────────────────────────────
export const filterExpenses = ({ category, paidTo, startDate, endDate, minAmount } = {}) => {
  const params = new URLSearchParams();
  if (category)  params.set('category',  category);
  if (paidTo)    params.set('paidTo',    paidTo);
  if (startDate) params.set('startDate', startDate);
  if (endDate)   params.set('endDate',   endDate);
  if (minAmount) params.set('minAmount', minAmount);
  return request(`${BASE}/filter?${params.toString()}`);
};

// ── PERIOD SUMMARY ────────────────────────────────────────────────
// period: 'today' | 'week' | 'month' | 'year'
export const getSummaryByPeriod = (period) =>
  request(`${BASE}/expenses/summary?period=${period}`);

export const getLastXDays = (days) =>
  request(`${BASE}/expenses/summary/last/${days}`);
