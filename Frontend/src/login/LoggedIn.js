/* eslint-disable eqeqeq */
import React, { useState, useEffect } from 'react';
import facade from './ApiFacade';
import { Link } from 'react-router-dom';
import settings from '../settings';
import { Card, CardTitle, CardText, Row, Col } from 'reactstrap';

export default function LoggedIn() {
  const [username, setUsername] = useState();
  const [role, setRole] = useState();

  useEffect(() => {
    facade
      .fetchUser()
      .then(res => {
        setUsername(res.userName);
        setRole(res.roleList);
      })
      .catch(e => console.log(e));
  }, []);

  return (
    <div>
      <h2>Data Received from server</h2>
      <h3>Username: {username}</h3>
      <h3>Role: {role}</h3>
      <br />
      {role == 'admin' ? <AdminOption /> : ''}
    </div>
  );
}


function AdminOption() {
  
  return (
    <div className="container">
      <Row>
        <Col sm="6">
          <Card body>
            <CardTitle>Movie data</CardTitle>
            <CardText>View more data as admin. This includes ratings from Rotten Tomato, Imdb and Metacritic.</CardText>
            <Link to={settings.getURL("Data")} activeClassName="active">Show data</Link>
          </Card>
        </Col>
        <Col sm="6">
          <Card body>
            <CardTitle>Request</CardTitle>
            <CardText>View the number of requests each film has had.</CardText>
            <br />
            <Link to={settings.getURL("Request")} activeClassName="active">Show requests</Link>
          </Card>
        </Col>
      </Row>
    </div>
  );
}
