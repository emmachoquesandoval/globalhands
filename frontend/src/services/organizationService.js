import { request } from './api'

export function getOrganizations() {
  return request('/organizations')
}

export function getOrganization(id) {
  return request(`/organizations/${id}`)
}
