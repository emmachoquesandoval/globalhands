import { request, saveSession, clearSession } from './api'

export async function login(credentials) {
  const session = await request('/auth/login', {
    method: 'POST',
    body: JSON.stringify(credentials),
  })
  saveSession(session)
  return session.user
}

export async function register(details) {
  const session = await request('/auth/register', {
    method: 'POST',
    body: JSON.stringify(details),
  })
  saveSession(session)
  return session.user
}

export function logout() {
  clearSession()
}
