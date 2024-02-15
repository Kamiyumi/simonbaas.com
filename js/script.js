function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
}

function playJazz() {
    const jazzList = ["resources/music/jazz1.mp3", "resources/music/jazz2.mp3", "resources/music/jazz3.mp3"];
    shuffleArray(jazzList);
    var audioPlayer = document.getElementById('audio-element');
    audioPlayer.volume = 0.3;
    audioPlayer.src = jazzList[0];
    audioPlayer.play();
}

function playCountry() {
    const countryList = ["resources/music/country1.mp3", "resources/music/country2.mp3", "resources/music/country3.mp3"];
    shuffleArray(countryList);
    var audioPlayer = document.getElementById('audio-element');
    audioPlayer.volume = 0.3;
    audioPlayer.src = countryList[0];
    audioPlayer.play();
}

function playPop() {
    const popList = ["resources/music/pop1.mp3", "resources/music/pop2.mp3", "resources/music/pop3.mp3"];
    shuffleArray(popList);
    var audioPlayer = document.getElementById('audio-element');
    audioPlayer.volume = 0.3;
    audioPlayer.src = popList[0];d
    audioPlayer.play();
}

function muteMusic() {
    var audioPlayer = document.getElementById('audio-element');
    audioPlayer.volume = 0;
    alert("Music muted!");
}

function downloadCv() {
    var link = document.createElement('a');
    link.href = 'resources/gigaImages/c.pdf'; // Correct path to the PDF
    link.download = 'SimonBaasCV.pdf';
    document.body.appendChild(link); // Append to body
    link.click(); // Trigger the click
    document.body.removeChild(link); // Remove the link after clicking
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



