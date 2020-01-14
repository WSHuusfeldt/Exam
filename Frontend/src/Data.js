/* eslint-disable jsx-a11y/alt-text */
import React, { useState, useEffect } from 'react';
import Facade from './login/ApiFacade';
import { Button, PopoverHeader, PopoverBody, UncontrolledPopover } from 'reactstrap';

export default function Data({ props }) {
  const [data, setData] = useState([]);

  useEffect(() => {
    Facade.fetchSimpleMovie().then(res => setData(res));
  }, []);

  const setNewData = newData => {
    setData(newData);
  };

  return (
    <div className="container">
      <h3>Search for a movie</h3>
      <MovieTable data={data} />
      <Search setNewData={setNewData} />
    </div>
  );
}

function MovieTable({ data }) {
  return (
    <table className="table">
      <thead className="thead-dark">
        <tr>
          <th scope="col">Title</th>
          <th scope="col">Year</th>
          <th scope="col">Plot</th>
          <th scope="col">Directors</th>
          <th scope="col">Genres</th>
          <th scope="col">Cast</th>
          <th scope="col">Poster</th>
          <th scope="col">Ratings</th>
        </tr>
      </thead>
      <tbody>
        {data.map((movie, index) => (
          <tr key={index}>
            <td>{movie.title}</td>
            <td>{movie.year}</td>
            <td>{movie.plot}</td>
            <td>{movie.directors}</td>
            <td>{movie.genres}</td>
            <td>{movie.cast}</td>
            <td>
              <Button id="UncontrolledPopover-poster" type="button">
                Show poster
              </Button>
              <UncontrolledPopover placement="bottom" target="UncontrolledPopover-poster">
                <PopoverHeader>Poster</PopoverHeader>
                <PopoverBody>
                  <img src={movie.poster} width="175px" height="300px" />
                </PopoverBody>
              </UncontrolledPopover>
            </td>
            <td>
              <Button id="UncontrolledPopover" type="button">
                Show ratings
              </Button>
              <UncontrolledPopover placement="bottom" target="UncontrolledPopover">
                <PopoverHeader>Ratings</PopoverHeader>
                <PopoverBody>
                  <table>
                    <thead>
                      <tr>
                        <th scope="col">IMDB</th>
                        <th scope="col">Tomatoes</th>
                        <th scope="col">Metacritic</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>{movie.imdb.imdbRating}</td>
                        <td>{movie.tomato.critic.rating}</td>
                        <td>{movie.metacritic.metacritic}</td>
                      </tr>
                    </tbody>
                  </table>
                </PopoverBody>
              </UncontrolledPopover>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

function Search({ setNewData }) {
  const [search, setSearch] = useState({ search: '1' });

  const onSubmit = evt => {
    evt.preventDefault();
    Facade.fetchMovieAll(search.search)
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
