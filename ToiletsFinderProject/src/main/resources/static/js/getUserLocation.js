function findMyLocation(){

    if (navigator.geolocation){
        navigator.geolocation.getCurrentPosition
        (
            (position) => {
                const bdcAPI = 'https://api-bdc.net/data/reverse-geocode-client?latitude=${position.coords.latitude}}&longitude=${position.coords.longtitude}'

                getAPI(bdcAPI)
            },
            (err) => {
                alert(err.message)
            }
        )
    } else {
        alert("Geolocation not supported.")
    }
}

function getAPI(bdcAPI) {
	const http = new XMLHttpRequest()
    var data = 0;
    http.open("GET", bdcAPI)
    http.send()
    http.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {			
            data = JSON.parse(this.responseText)
            console.log(data)
            
            var xhr = new XMLHttpRequest();
			var url = "http://localhost:8080/lc"; //change the URL for the page to get POST
	
			var dat = JSON.stringify(data)
			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-Type", "application/json");
			xhr.onreadystatechange = function () {
	    			if (xhr.readyState === 4 && xhr.status === 200) {
	        			
	    			}
			}
			xhr.send(dat);
        }
    }
	
}
