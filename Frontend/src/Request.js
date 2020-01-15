/* eslint-disable jsx-a11y/alt-text */
import React, { useState, useEffect } from 'react';
import Facade from './login/ApiFacade';

export default function Request({ props }) {
  const [data, setData] = useState([]);

  useEffect(() => {
    Facade.fetchRequestCount().then(res => setData(res));
  }, []);

  const setNewData = newData => {
    setData(newData);
  };

  return (
    <div className="container">
      <h3>Search for a movie</h3>
      <RequestTable data={data} />
      <Search setNewData={setNewData} />
    </div>
  );
}

function RequestTable({ data }) {
  return (
    <table className="table">
      <thead className="thead-dark">
        <tr>
          <th scope="col">Title of movie</th>
          <th scope="col">Amount of requests</th>
        </tr>
      </thead>
      <tbody>
      {data.map((movie, index) => (
          <tr key={index}>
            <td>{movie.title}</td>
            <td>{movie.requestAmount}</td>
          </tr>
        ))}
      </tbody>
      <br/>
    </table>
  );
}

function Search({ setNewData }) {
  const [search, setSearch] = useState({ search: '1' });

  const onSubmit = evt => {
    evt.preventDefault();
    Facade.fetchRequestCount(search.search)
      .then(res => (Array.isArray(res) ? setNewData(res) : setNewData([res])))
      .catch(e => setNewData([]));
  };

  const onChange = evt => {
    setSearch({ ...search, [evt.target.id]: evt.target.value });
  };

  return (
    <form className="container" onSubmit={onSubmit} onChange={onChange}>
      <div className="row">
        <div className="input-group col-sm-6">
          <input type="text" id="search" className="form-control" placeholder="Search..."></input>
        </div>
        <div className="input-group col-sm-3">
          <input type="submit" className="btn btn-dark" value="Search" />
        </div>
      </div>
    </form>
  );
}
