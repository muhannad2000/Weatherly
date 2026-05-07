# Weatherly

Weatherly is a JavaFX weather application that allows users to register, log in, and search for real-time weather information by city name.

The application displays the current temperature and wind speed using a free weather API. It also includes dynamic background images based on temperature and an animated wind fan that changes speed according to wind speed.

## Features

- User registration and login/logout
- Saves user accounts locally using a text file
- Fetches live weather data from a free API
- Displays temperature and wind speed
- Dynamic background images based on temperature
- Animated wind fan based on wind speed
- Optional current location detection using IP-based location
- Error handling for invalid city names
- Clean glass-style JavaFX user interface

## Technologies Used

- Java
- JavaFX
- Maven
- Open-Meteo Weather API
- IP-based location service
- GitHub

## How to Run

1. Open the project in IntelliJ IDEA.
2. Make sure Maven is loaded.
3. Run the project using Maven:

```bash
mvn javafx:run
```

## Project Structure

```text
Weatherly/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/weatherfx/
│       │       ├── Main.java
│       │       └── WeatherService.java
│       └── resources/
│           └── images/
│               ├── verycold.png
│               ├── cold.png
│               ├── chilly.png
│               └── hot.png
└── users.txt
```

## Main Screens

- Login Screen
- Register Screen
- Main Weather Screen

## Notes

The current location feature is based on the user's internet connection/IP address. Therefore, the detected city is approximate and not GPS-based.

## Developer

Student Name: MHD Muhannad Alshatti  
