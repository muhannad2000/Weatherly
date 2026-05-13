# Weatherly

Weatherly is a JavaFX weather application that allows users to register, log in, and search for real-time weather information by city name.

The application displays the current temperature and wind speed using a free weather API. It also includes dynamic background images based on temperature and an animated wind fan that changes speed according to wind speed.

## Features

- User registration and login/logout
- Stores user information locally in an encrypted binary file
- Uses `RandomAccessFile` to manage fixed-size user records
- Stores the following local user data:
  - Username
  - Password
  - IP address
  - Approximate city location
- Prevents duplicate usernames during registration
- Fetches live weather data from a free API
- Displays temperature and wind speed
- Dynamic background images based on temperature
- Animated wind fan based on wind speed
- Optional current location detection using IP-based location
- Error handling for invalid city names
- Clean glass-style JavaFX user interface
- Developer-only method for decrypting and printing stored user records in the console for verification

## Technologies Used

- Java
- JavaFX
- Maven
- RandomAccessFile
- Binary file storage
- Simple XOR-based data encryption
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
├── README.md
├── users.dat
├── src/
│   └── main/
│       ├── java/
│       │   └── com/weatherfx/
│       │       ├── Main.java
│       │       ├── WeatherService.java
│       │       ├── User.java
│       │       └── UserFileManager.java
│       └── resources/
│           └── images/
│               ├── verycold.png
│               ├── cold.png
│               ├── chilly.png
│               └── hot.png
```

## Main Screens

- Login Screen
- Register Screen
- Main Weather Screen

## Local User Data Storage

User data is stored locally in a binary file named:
users.dat

The application does not store user information in a normal readable text file.
Instead, it uses RandomAccessFile with fixed-size encrypted records.

Each user record stores:

* Username
* Password
* IP address
* Approximate city location

The data is encrypted before being written to the file and decrypted internally when needed, such as during login validation.

## Developer Verification

A developer-only method is included inside UserFileManager.java:
printAllUsersForDeveloper()

This method can decrypt and print stored user records in the IntelliJ console for verification purposes.
It is not part of the visible Weatherly user interface and is not enabled in the normal application flow.

## Notes

The current location feature is based on the user’s internet connection/IP address. Therefore, the detected city is approximate and not GPS-based.

The encryption used in this academic project is a simple XOR-based method implemented with standard Java features to satisfy the project requirements for protected non-plain-text local storage.

## Developer

Student Name: MHD Muhannad Alshatti  
