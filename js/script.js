// General function to play a genre
function playGenre(genre) {
    const musicLists = {
        jazz: ["resources/music/jazz1.mp3", "resources/music/jazz2.mp3", "resources/music/jazz3.mp3"],
        country: ["resources/music/country1.mp3", "resources/music/country2.mp3", "resources/music/country3.mp3"],
        pop: ["resources/music/pop1.mp3", "resources/music/pop2.mp3", "resources/music/pop3.mp3"]
    };
    var audioPlayer = document.getElementById('audio-element');
    var state = loadMusicState();

    audioPlayer.src = musicLists[genre][0];
    audioPlayer.volume = state.volume || 0.3;
    audioPlayer.currentTime = state.currentTime || 0; // Set the current time
    audioPlayer.play();

    saveMusicState({ genre: genre, volume: audioPlayer.volume, track: audioPlayer.src, currentTime: audioPlayer.currentTime, playing: true });
}

// Specific genre functions
function playJazz() { playGenre('jazz'); }
function playCountry() { playGenre('country'); }
function playPop() { playGenre('pop'); }

function muteMusic() {
    var audioPlayer = document.getElementById('audio-element');
    audioPlayer.volume = 0;
    audioPlayer.currentTime = 0; // Reset the song to the beginning
    audioPlayer.play(); // Optional: Start playing the song again

    const state = loadMusicState();
    saveMusicState({ ...state, volume: 0, currentTime: 0 });
}


function downloadCv() {
    var link = document.createElement('a');
    link.href = 'resources/gigaImages/c.pdf'; // Correct path to the PDF
    link.download = 'SimonBaasCV.pdf';
    document.body.appendChild(link); // Append to body
    link.click(); // Trigger the click
    document.body.removeChild(link); // Remove the link after clicking
}

function contactInformation()
{
    alert("Contact Information: \n Email: Simon.baas94@gmail.com \n #: 0704208562");
}

document.addEventListener('DOMContentLoaded', function() {
    fetch('resources/navbar.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('navbar-placeholder').innerHTML = data;
        })
        .catch(error => {
            console.error('Error loading the navbar:', error);
        });
});


document.addEventListener('DOMContentLoaded', function() {
    fetch('resources/audio-bar.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('audio-bar').innerHTML = data;
        })
        .catch(error => {
            console.error('Error loading the navbar:', error);
        });
});

 document.addEventListener("DOMContentLoaded", function() {
        const fadeInSections = document.querySelectorAll('.fade-section');

        const appearOnScroll = new IntersectionObserver(function(entries, appearOnScroll) {
            entries.forEach(entry => {
                if (!entry.isIntersecting) {
                    return;
                } else {
                    entry.target.classList.add('fade-in-section');
                    appearOnScroll.unobserve(entry.target);
                }
            });
        }, { threshold: 0.5 }); // Adjust threshold as needed

        fadeInSections.forEach(section => {
            appearOnScroll.observe(section);
        });
    });

function saveMusicState(state) {
    var audioPlayer = document.getElementById('audio-element');
    if (audioPlayer) {
        state.currentTime = audioPlayer.currentTime;
    }
    localStorage.setItem('musicState', JSON.stringify(state));
}

function loadMusicState() {
    const state = localStorage.getItem('musicState');
    return state ? JSON.parse(state) : {};
}

// Adjusted window onload event to load and play music
window.onload = function() {
    var state = loadMusicState();
    if (state && state.playing) {
        playGenre(state.genre);
    }
    // Rest of your onload logic...
};

// Adjusted window onbeforeunload event to save music state
window.onbeforeunload = function() {
    var audioPlayer = document.getElementById('audio-element');
    if (audioPlayer) {
        var state = loadMusicState();
        state.currentTime = audioPlayer.currentTime;
        saveMusicState(state);
    }
};

function playNextSong() {
    const musicLists = {
        jazz: ["resources/music/jazz1.mp3", "resources/music/jazz2.mp3", "resources/music/jazz3.mp3"],
        country: ["resources/music/country1.mp3", "resources/music/country2.mp3", "resources/music/country3.mp3"],
        pop: ["resources/music/pop1.mp3", "resources/music/pop2.mp3", "resources/music/pop3.mp3"]
    };
    
    var state = loadMusicState();
    if (state.genre && musicLists[state.genre]) {
        var currentIndex = musicLists[state.genre].indexOf(state.track);
        var nextIndex = (currentIndex + 1) % musicLists[state.genre].length; // Move to the next song, or loop back to the first song
        playGenre(state.genre, nextIndex);
    }
}
