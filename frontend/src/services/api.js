const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'
const TOKEN_KEY = 'globalhands.accessToken'
const USER_KEY = 'globalhands.user'

export async function request(path, options = {}) {
  const token = localStorage.getItem(TOKEN_KEY)
  const headers = new Headers(options.headers || {})

  if (options.body !== undefined) headers.set('Content-Type', 'application/json')
  if (token) headers.set('Authorization', `Bearer ${token}`)

  let response
  try {
    response = await fetch(`${API_BASE_URL}${path}`, { ...options, headers })
  } catch {
    throw new Error('No se pudo conectar con el servidor. Comprueba que Spring está ejecutándose.')
  }

  const contentType = response.headers.get('content-type') || ''
  const data = contentType.includes('application/json') ? await response.json() : null

  if (!response.ok) {
    const validationMessages = data?.fields ? Object.values(data.fields).join(' ') : ''
    throw new Error(validationMessages || data?.message || data?.error || 'Ha ocurrido un error.')
  }

  return data
}

export function saveSession(authResponse) {
  localStorage.setItem(TOKEN_KEY, authResponse.accessToken)
  localStorage.setItem(USER_KEY, JSON.stringify(authResponse.user))
  window.dispatchEvent(new Event('globalhands:session-changed'))
}

export function clearSession() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(USER_KEY)
  window.dispatchEvent(new Event('globalhands:session-changed'))
}

export function getSessionUser() {
  try {
    return JSON.parse(localStorage.getItem(USER_KEY) || 'null')
  } catch {
    return null
  }
}

export function hasSession() {
  return Boolean(localStorage.getItem(TOKEN_KEY))
}
