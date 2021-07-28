import '../App.css';
import Login from './Login';
import Logout from './Logout';
import Captions from './Captions';
import { useState, useEffect } from 'react';
import CaptionContext from "./CaptionContext";

function App() {
  const [errorMessage, setErrorMessage] = useState();
  const [showLogin, setShowLogin] = useState(true);
  const [caption, setCaption] = useState(null);
  const [sid, setSid] = useState(null);

  useEffect(() => {
    if (caption) {
      setShowLogin(false);
    }
    else {
      setShowLogin(true);
    }
  }, []);

  return (
    <CaptionContext.Provider value={caption}> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
        <div className="my-app">
          <h1 className="app-header">Learn English through Friends</h1>
          {showLogin && <Login setSid={setSid} setErrorMessage={setErrorMessage} setShowLogin={setShowLogin} setCaption={setCaption}/>}
          {!showLogin && <Captions sid={sid} setErrorMessage={setErrorMessage} setCaption={setCaption} setShowLogin={setShowLogin}/>}
          {!showLogin && <Logout setShowLogin={setShowLogin} setErrorMessage={setErrorMessage}/>}
        </div>
        <div className="error-message">{errorMessage}</div>
    </CaptionContext.Provider>
  );
}

export default App;
