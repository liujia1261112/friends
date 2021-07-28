export function performLogin(username, password) {
  return fetch('/api/login', {
    method: 'POST',
    headers: new Headers({
      'content-type': 'application/json',
    }),
    credentials: 'include',
    body: JSON.stringify({ username, password }),
  })
    .catch(() => {
      return Promise.reject({ error: 'NETWORK_ERROR' });
    })
    .then(response => {
      if (response.ok) {
        return response.json();
      }
      return response.json().then(err => Promise.reject(err))
    });
};

export function fetchCaption(id, se,sid) {
  let url = new URL('http://'+window.location.host+'/api/caption')
  if (id) {
    url.searchParams.set('id', id);
  }
  if (se) {
    url.searchParams.set('se', se);
  }
  url.searchParams.set('sid', sid);
  return fetch(url, {
    method: 'GET',
    credentials: 'include'
  })
    .catch(() => {
      return Promise.reject({ error: 'NETWORK_ERROR' });
    })
    .then(response => {
      if (response.ok) {
        return response.json();
      }
      return response.json().then(err => Promise.reject(err));
    });
};
