const weatherForm = document.querySelector('.weather-result');
const inputGroup = document.querySelector('.input-group');
const locationInput = inputGroup.querySelector('#locationInput');
const BaseURL = 'http://localhost:8080/user/CheckWeather';

inputGroup.addEventListener('submit', (e) => {
    e.preventDefault();
    const location = locationInput.value.trim();
    const infoDiv = document.querySelector('#weatherInfo');
    
    infoDiv.innerHTML = '';
    infoDiv.classList.remove('show');
    
    if (!location) {
        infoDiv.innerHTML = '<p class="error">Please enter a location.</p>';
        infoDiv.classList.add('show');
        return;
    }
    
    // Show loading state
    infoDiv.innerHTML = '<p class="loading">Fetching weather for ' + location + '... ⏳</p>';
    infoDiv.classList.add('show');
    
    const weatherData = async (BaseURL) => {
        try {
            const response = await fetch(BaseURL + '?location=' + encodeURIComponent(location));
            
            if (!response.ok) {
                throw new Error('Failed to fetch weather data');
            }
            
            const data = await response.json();
            console.log('Weather data:', data);
            displayWeather(data, location, infoDiv);
        } catch (error) {
            console.error('Error fetching weather:', error);
            infoDiv.innerHTML = '<p class="error">❌ Error fetching weather data. Please try again.</p>';
            infoDiv.classList.add('show');
        }
    };
    
    weatherData(BaseURL);
});

function displayWeather(data, location, infoDiv) {
    if (!Array.isArray(data) || data.length === 0) {
        infoDiv.innerHTML = '<p class="error">No weather data found for this location.</p>';
        infoDiv.classList.add('show');
        return;
    }
    
    console.log(data);
    
    // Get the current/first day weather for background
    const currentWeather = data[0].weather;
    changeBackgroundByWeather(currentWeather);
    
    // Create header
    const firstDayEmoji = getWeatherEmoji(data[0].weather);
    let weatherHTML = `
        <div class="weather-header">
            <h2>${firstDayEmoji} ${location}</h2>
            <p class="forecast-days">${data.length} Day Forecast</p>
        </div>
        <div class="weather-grid">
    `;
    
    // Create grid cards for each day
    data.forEach((item, index) => {
        const { date, temperatureMax, temperatureMin, weather, sunrise, sunset } = item;
        const emoji = getWeatherEmoji(weather);
        
        weatherHTML += `
            <div class="weather-card">
                <div class="card-header">
                    <span class="card-emoji">${emoji}</span>
                    <span class="card-date">${date}</span>
                </div>
                <div class="card-weather">${weather}</div>
                <div class="card-temps">
                    <div class="temp-max">
                        <span class="temp-label">High</span>
                        <span class="temp-value">${temperatureMax}°C</span>
                    </div>
                    <div class="temp-divider"></div>
                    <div class="temp-min">
                        <span class="temp-label">Low</span>
                        <span class="temp-value">${temperatureMin}°C</span>
                    </div>
                </div>
                <div class="card-sun-times">
                    <div class="sun-time">
                        <span class="sun-icon">🌅</span>
                        <span class="sun-label">${formatTime(sunrise)}</span>
                    </div>
                    <div class="sun-time">
                        <span class="sun-icon">🌇</span>
                        <span class="sun-label">${formatTime(sunset)}</span>
                    </div>
                </div>
            </div>
        `;
    });
    
    weatherHTML += `</div>`;
    
    infoDiv.innerHTML = weatherHTML;
    infoDiv.classList.add('show');
}

// function formatDate(dateString) {
//     const date = new Date(dateString);
//     const options = { weekday: 'short', month: 'short', day: 'numeric' };
//     return date.toLocaleDateString('en-US', options);
// }

function formatTime(timeString) {
    // Assuming time format is like "2026-02-06T07:52"
    if (!timeString) return 'N/A';
    
    try {
        const time = new Date(timeString);
        return time.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: true });
    } catch (e) {
        // If it's already in a simple format like "07:52"
        return timeString.substring(timeString.length - 5);
    }
}

function getWeatherEmoji(description) {
    if (!description) return '🌦️';
    
    description = description.toLowerCase();
    
    if (description.includes('sun') || description.includes('clear')) return '☀️';
    if (description.includes('cloud')) return '☁️';
    if (description.includes('rain')) return '🌧️';
    if (description.includes('storm') || description.includes('thunder')) return '⛈️';
    if (description.includes('snow')) return '❄️';
    if (description.includes('fog') || description.includes('mist')) return '🌫️';
    if (description.includes('wind')) return '💨';
    if (description.includes('drizzle')) return '🌦️';
    
    return '🌦️';
}

function changeBackgroundByWeather(weatherDescription) {
    if (!weatherDescription) return;
    
    const body = document.body;
    weatherDescription = weatherDescription.toLowerCase();
    
    // Remove all existing weather classes
    body.className = '';
    
    // Add appropriate weather class
    if (weatherDescription.includes('sun') || weatherDescription.includes('clear')) {
        body.classList.add('weather-sunny');
    } else if (weatherDescription.includes('cloud')) {
        body.classList.add('weather-cloudy');
    } else if (weatherDescription.includes('rain')) {
        body.classList.add('weather-rainy');
    } else if (weatherDescription.includes('storm') || weatherDescription.includes('thunder')) {
        body.classList.add('weather-stormy');
    } else if (weatherDescription.includes('snow')) {
        body.classList.add('weather-snowy');
    } else if (weatherDescription.includes('fog') || weatherDescription.includes('mist')) {
        body.classList.add('weather-foggy');
    } else if (weatherDescription.includes('drizzle')) {
        body.classList.add('weather-drizzle');
    } else {
        body.classList.add('weather-default');
    }
}