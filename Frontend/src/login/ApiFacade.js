const URL = 'https://williamhuusfeldt.dk/Exam';
//const URL = 'http://localhost:8080/securitystarter';

function handleHttpErrors(res) {
  if (!res.ok) {
    return Promise.reject({ status: res.status, fullError: res.json() });
  }
  return res.json();
}

function ApiFacade() {
  //Insert utility-methods from a latter step (d) here
  const setToken = token => {
    localStorage.setItem('jwtToken', token);
  };
  const getToken = () => {
    return localStorage.getItem('jwtToken');
  };
  const loggedIn = () => {
    const loggedIn = getToken() != null;
    return loggedIn;
  };
  const logout = () => {
    localStorage.removeItem('jwtToken');
  };

  const makeOptions = (method, addToken, body) => {
    var opts = {
      method: method,
      headers: {
        'Content-type': 'application/json',
        Accept: 'application/json'
      }
    };
    if (addToken && loggedIn()) {
      opts.headers['x-access-token'] = getToken();
    }
    if (body) {
      opts.body = JSON.stringify(body);
    }
    return opts;
  };

  const login = (user, pass) => {
    const options = makeOptions('POST', true, { username: user, password: pass });
    return fetch(URL + '/api/login', options)
      .then(handleHttpErrors)
      .then(res => {
        setToken(res.token);
      });
  };

  const fetchUser = () => {
    const options = makeOptions('GET', true); //True add's the token
    return fetch(URL + '/api/info/user', options).then(handleHttpErrors);
  };

  const fetchData = () => {
    return fetch(URL + '/api/swapi/demo', makeOptions('GET')).then(handleHttpErrors);
  };

  const fetchSimpleMovie = title => {
    return fetch(URL + '/api/movieInfo/findByTitle/' + title, makeOptions('GET')).then(handleHttpErrors);
  };

  const fetchMovieAll = title => {
    return fetch(URL + '/api/movieInfo/findByTitleAll/' + title, makeOptions('GET', true)).then(handleHttpErrors);
  };

  const fetchRequestCount = title => {
      return fetch(URL + '/api/movieCount/' + title, makeOptions('GET', true)).then(handleHttpErrors);
  }

  return {
    login,
    logout,
    fetchUser,
    fetchData,
    fetchSimpleMovie,
    fetchRequestCount,
    fetchMovieAll
  };
}

export default ApiFacade();
