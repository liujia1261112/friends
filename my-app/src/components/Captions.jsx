import errorMessages from '../error';
import { useState, useContext, useEffect } from 'react';
import { fetchCaption } from '../services';
import CaptionContext from './CaptionContext';

function Captions({ sid,setErrorMessage, setCaption, setShowLogin}) {

    const [showChnCaption, setShowChnCaption] = useState(true);
    const [showEngCaption, setShowEngCaption] = useState(false);

    const caption = useContext(CaptionContext);

    const se = [24, 24, 25, 24, 24, 25, 24, 24, 24, 18];

    useEffect(() => {
        initSeason();
        setSeasonEpisode();
    }, []);

    const setSeasonEpisode = () =>{
        let nums = caption.se.replace("s", "").split("e");
        let season = parseInt(nums[0]);
        document.getElementById("season").selectedIndex = season - 1;
        initEpisode();
        let episode = parseInt(nums[1]);
        document.getElementById("episode").selectedIndex = episode - 1;
    }

    const handleCaption = (i) => {
        let id = null;
        let se = null;
        if (i) id = caption.id + i;
        else se = "s" + (document.getElementById("season").selectedIndex + 1) + "e" + (document.getElementById("episode").selectedIndex + 1);

        fetchCaption(id, se,sid)
            .then((caption) => {
                setSeasonEpisode();
                setCaption(caption);
                setErrorMessage('');
                setShowEngCaption(false);
            })
            .catch(err => {
                if(err.error==="LOGIN_REQUIRED"){
                    setShowLogin(true);
                }
                else{setErrorMessage(errorMessages[err.error]);
                }
            });
    }

    const handleCaptionDisplay = (changeChn) => {
        if (changeChn) {
            setShowChnCaption(!showChnCaption);
        } else {
            setShowEngCaption(!showEngCaption);
        }
    }

    const initEpisode = () => {
        let episodes = "";
        let season = document.getElementById("season").selectedIndex;
        for (let i = 0; i < se[season]; i++) {
            episodes += "<option>Episode" + (i + 1) + "</option>";
        }
        document.getElementById("episode").innerHTML = episodes;
    }

    const initSeason = () =>{
        let seasons = "";
        for (let i = 0; i < se.length; i++) {
            seasons += "<option>Season" + (i + 1) + "</option>";
        }
        document.getElementById("season").innerHTML = seasons;
    }

    return (
        <div className="caption-panel">
            <div className="position">
                <select id="season" onChange={() => { initEpisode() }} >
                </select>
                <select id="episode">
                </select>
                <button className="episode-selection-button" onClick={() => { handleCaption(null) }}>Go</button>
            </div>
            <div className="caption-display">
            <div className="chn-caption-panel">
                <span className="chn">中文:</span>
                <br></br>
                <div className="chn-caption">{showChnCaption ? caption.chn : " "}</div>
            </div>
            <button className='chn-caption-button' onClick={() => handleCaptionDisplay(true)}>{showChnCaption ? "Hide" : "Show"}</button>
            <div className='eng-caption-panel'>
                <span className='eng'>English:</span>
                <br></br>
                <div className='eng-caption'>{showEngCaption ? caption.eng : " "}</div>
            </div>
            <button className='eng-caption-button' onClick={() => { handleCaptionDisplay() }}>{showEngCaption ? "Hide" : "Show"}</button>
            </div>    
            <br></br>
            <button className='prev-button' onClick={() => { handleCaption(-1) }}>Prev</button>
            <button className='next-button' onClick={() => { handleCaption(1) }}>Next</button>
        </div>
    );
}

export default Captions; 