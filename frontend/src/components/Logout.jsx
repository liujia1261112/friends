function Logout({setShowLogin,setErrorMessage}) {

const handleLogout = () =>{
    setShowLogin(true);
    setErrorMessage('');
}

return (
    <div className = "logout-panel">
        <button className="logout-button" onClick={()=>handleLogout()}>LOG OUT</button>
    </div>
    );
}

export default Logout; 