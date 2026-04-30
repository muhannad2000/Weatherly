# Weatherly

Weatherly is a JavaFX weather application that allows users to register, log in, and search for real-time weather information by city name.

## Features

- User registration and login/logout
- Saves user accounts locally using a text file
- Fetches live weather data from a free API
- Displays temperature and wind speed
- Dynamic background images based on temperature
- Animated wind fan based on wind speed
- Error handling for invalid city names
- Clean glass-style JavaFX user interface

## Technologies Used

- Java
- JavaFX
- Maven
- Open-Meteo Weather API

## How to Run

1. Open the project in IntelliJ IDEA.
2. Make sure Maven is loaded.
3. Run the project using:
mvn javafx:run

```bash
mvn javafx:run


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

Developer
Student Name: MHD Muhannad Alshatti
