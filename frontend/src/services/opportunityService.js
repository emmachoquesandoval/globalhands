import { request } from './api'

export function getOpportunities(filters = {}) {
  const params = new URLSearchParams()
  for (const [key, value] of Object.entries(filters)) {
    if (value !== undefined && value !== null && value !== '') params.set(key, value)
  }
  const query = params.toString()
  return request(`/opportunities${query ? `?${query}` : ''}`)
}

export function getOpportunity(id) {
  return request(`/opportunities/${id}`)
}

export function getCategories() {
  return request('/categories')
}
