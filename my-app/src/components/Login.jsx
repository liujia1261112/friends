import {performLogin,fetchCaption} from "../services";
import errorMessages from '../error';

function Login({setShowLogin,setErrorMessage, setCaption,setSid}){

    const handleLogin = () =>{
        const username=document.getElementsByClassName("username-input")[0].value;
        const password=document.getElementsByClassName("password-input")[0].value;
        performLogin(username, password)
             .then((res)=>{
                fetchCaption(null,null,res.sid)
                .then((caption) => {
                    setSid(res.sid);
                    setCaption(caption);
                    setShowLogin(false);
                    setErrorMessage('');
                });
            })
            .catch(err=>{
                setErrorMessage(errorMessages[err.error]);
        });
    };

    return(
            <div className="login-panel">
                <span className="username">Username:</span><input className="username-input" placeholder="No more than 6 letters or digits" type="text"></input>
                <br></br>
                <span className="password">Password:</span><input className="password-input" placeholder="No more than 6 letters or digits" type="password"></input>
                <br></br>
                <button className="login-button" onClick = {()=>{handleLogin()}}>LOGIN</button>
            </div>
    );
}

export default Login; 