import { request } from './api'

export function getMyApplications() {
  return request('/applications/me')
}

export function applyToOpportunity(opportunityId, message) {
  return request(`/opportunities/${opportunityId}/applications`, {
    method: 'POST',
    body: JSON.stringify({ message }),
  })
}
