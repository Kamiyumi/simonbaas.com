 let audio = document.getElementById("harambe");
 function getVolume() {
                alert("Current Volume is: \n" + audio.volume * 100 + "%");
            }
            function setVolumeHalf() {
                audio.volume = 0.1;
            }

            function setVolumeFull()
            {
                audio.volume = 1;
            }

            function setVolumeMute()
            {
                audio.volume = 0;
            }