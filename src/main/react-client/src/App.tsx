import React from 'react';
import {
  Switch,
  Route,
  Redirect
} from "react-router-dom";
import logo from './logo.svg';
import './css/App.css';

function App(): JSX.Element {
  
  return (
    <div>
      <Switch>
        <Route exact path="index.html">
          <Redirect to="/" />
        </Route>
        <Route exact path="/" component={Home} />
        <Route exact path="/login" component={Login} />
      </Switch>
    </div>
  );
  
}

function Login(): JSX.Element {
  
  return (
    <div>
      <p>hello...</p>
      <form name="login-form" action="login" method="POST">
        <input type="text" name="username" />
        <input type="password" name="password" />
        <input name="submit" type="submit" value="submit" />
      </form>
    </div>
  );
  
}

function Home(): JSX.Element {
  
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
  
}

export default App;
