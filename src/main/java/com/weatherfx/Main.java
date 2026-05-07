package com.weatherfx;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showLoginScreen();
    }

    private void showLoginScreen() {
        Label title = new Label("Weatherly");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Create Account");

        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter username and password.");
            } else {
                boolean found = false;

                try {
                    java.io.BufferedReader reader = new java.io.BufferedReader(
                            new java.io.FileReader("users.txt")
                    );

                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");

                        if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                            found = true;
                            break;
                        }
                    }

                    reader.close();

                } catch (Exception ex) {
                    messageLabel.setText("Error reading file.");
                }

                if (found) {
                    showMainScreen(username);
                } else {
                    messageLabel.setText("Invalid username or password.");
                }
            }
        });

        registerButton.setOnAction(e -> showRegisterScreen());

        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setStyle("-fx-background-color: linear-gradient(to bottom, #ECEFF1, #FFFFFF);");

        box.getChildren().addAll(
                title,
                usernameField,
                passwordField,
                loginButton,
                registerButton,
                messageLabel
        );

        Scene scene = new Scene(box, 420, 520);
        stage.setTitle("Weatherly - Login");
        stage.setScene(scene);
        stage.show();
    }

    private void showRegisterScreen() {
        Label title = new Label("Create Account");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("New username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("New password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm password");

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");

        Label messageLabel = new Label();

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("Please fill all fields.");
            } else if (!password.equals(confirmPassword)) {
                messageLabel.setText("Passwords do not match.");
            } else {
                try {
                    java.io.FileWriter writer = new java.io.FileWriter("users.txt", true);
                    writer.write(username + "," + password + "\n");
                    writer.close();

                    messageLabel.setText("Account created successfully!");
                    showLoginScreen();

                } catch (Exception ex) {
                    messageLabel.setText("Error saving user.");
                }
            }
        });

        backButton.setOnAction(e -> showLoginScreen());

        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setStyle("-fx-background-color: linear-gradient(to bottom, #ECEFF1, #FFFFFF);");

        box.getChildren().addAll(
                title,
                usernameField,
                passwordField,
                confirmPasswordField,
                registerButton,
                backButton,
                messageLabel
        );

        Scene scene = new Scene(box, 420, 520);
        stage.setTitle("Weatherly - Register");
        stage.setScene(scene);
        stage.show();
    }

    private void showMainScreen(String username) {
        Label logoLabel = new Label("Weatherly");
        logoLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #102A43;" +
                        "-fx-background-color: rgba(255,255,255,0.55);" +
                        "-fx-background-radius: 24;" +
                        "-fx-border-radius: 24;" +
                        "-fx-border-color: rgba(255,255,255,0.75);" +
                        "-fx-padding: 10px 28px;"
        );

        Label welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #263238;");

        Label subtitleLabel = new Label("Search current weather by city");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #455A64;");

        TextField cityField = new TextField();
        cityField.setPromptText("Enter city name, e.g. Istanbul");
        cityField.setMaxWidth(420);
        cityField.setStyle("-fx-font-size: 14px; -fx-padding: 9px; -fx-background-radius: 10;");

        Button searchButton = new Button("Search Weather");
        searchButton.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-color: #2E7D32;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 8px 18px;"
        );

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-padding: 7px 18px;" +
                        "-fx-background-radius: 10;" +
                        "-fx-background-color: rgba(255,255,255,0.60);" +
                        "-fx-text-fill: #102A43;"
        );

        Label weatherTitle = new Label("WEATHER");
        weatherTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #102A43;");

        Label weatherLabel = new Label("Search to show weather");
        weatherLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #243B53; -fx-font-weight: bold;");

        VBox weatherCard = new VBox(10, weatherTitle, weatherLabel);
        weatherCard.setAlignment(Pos.CENTER);
        weatherCard.setPadding(new Insets(18));
        weatherCard.setPrefWidth(210);
        weatherCard.setMinHeight(130);
        weatherCard.setStyle(
                "-fx-background-color: rgba(255,255,255,0.50);" +
                        "-fx-background-radius: 18;" +
                        "-fx-border-radius: 18;" +
                        "-fx-border-color: rgba(255,255,255,0.80);"
        );

        Label windTitle = new Label("WIND");
        windTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #102A43;");

        Label windLabel = new Label("Wind speed will appear");
        windLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #243B53; -fx-font-weight: bold;");

        Circle centerCircle = new Circle(4);
        centerCircle.setStyle("-fx-fill: #102A43;");

        Line blade1 = new Line(0, 0, 0, -22);
        blade1.setStrokeWidth(5);
        blade1.setStyle("-fx-stroke: #102A43;");

        Line blade2 = new Line(0, 0, 19, 11);
        blade2.setStrokeWidth(5);
        blade2.setStyle("-fx-stroke: #102A43;");

        Line blade3 = new Line(0, 0, -19, 11);
        blade3.setStrokeWidth(5);
        blade3.setStyle("-fx-stroke: #102A43;");

        Circle invisibleCircle = new Circle(24);
        invisibleCircle.setStyle("-fx-fill: transparent; -fx-stroke: transparent;");

        Group fanIcon = new Group(invisibleCircle, blade1, blade2, blade3, centerCircle);

        RotateTransition fanAnimation = new RotateTransition(Duration.seconds(1.2), fanIcon);
        fanAnimation.setByAngle(360);
        fanAnimation.setCycleCount(Animation.INDEFINITE);
        fanAnimation.play();

        VBox windCard = new VBox(10, windTitle, windLabel, fanIcon);
        windCard.setAlignment(Pos.CENTER);
        windCard.setPadding(new Insets(18));
        windCard.setPrefWidth(210);
        windCard.setMinHeight(130);
        windCard.setStyle(
                "-fx-background-color: rgba(255,255,255,0.50);" +
                        "-fx-background-radius: 18;" +
                        "-fx-border-radius: 18;" +
                        "-fx-border-color: rgba(255,255,255,0.80);"
        );

        HBox infoCards = new HBox(16, weatherCard, windCard);
        infoCards.setAlignment(Pos.CENTER);
        infoCards.setOpacity(0);

        VBox glassCard = new VBox(16);
        glassCard.setAlignment(Pos.CENTER);
        glassCard.setPadding(new Insets(35));
        glassCard.setMaxWidth(560);
        glassCard.setStyle(
                "-fx-background-color: rgba(255,255,255,0.35);" +
                        "-fx-background-radius: 28;" +
                        "-fx-border-radius: 28;" +
                        "-fx-border-color: rgba(255,255,255,0.75);"
        );

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(32));
        box.setStyle("-fx-background-color: linear-gradient(to bottom, #ECEFF1, #FFFFFF);");

        searchButton.setOnMouseEntered(e ->
                searchButton.setStyle(
                        "-fx-font-size: 14px;" +
                                "-fx-background-color: #1B5E20;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 8px 18px;"
                )
        );

        searchButton.setOnMouseExited(e ->
                searchButton.setStyle(
                        "-fx-font-size: 14px;" +
                                "-fx-background-color: #2E7D32;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 8px 18px;"
                )
        );

        searchButton.setOnAction(e -> {
            String city = cityField.getText().trim();

            if (city.isEmpty()) {
                weatherLabel.setText("Please enter a city name.");
                windLabel.setText("No wind data");
                infoCards.setOpacity(1);
                box.setStyle("-fx-background-color: linear-gradient(to bottom, #ECEFF1, #FFFFFF);");
            } else {
                String weather = WeatherService.getWeather(city);

                if (weather.equals("City not found.") || weather.equals("Error fetching weather.")) {
                    weatherLabel.setText("City not found.\nPlease enter a valid city.");
                    windLabel.setText("No wind data");

                    fanAnimation.stop();
                    fanIcon.setRotate(0);

                    infoCards.setOpacity(1);
                    box.setStyle("-fx-background-color: linear-gradient(to bottom, #ECEFF1, #FFFFFF);");
                    return;
                }

                double temperature = extractTemperature(weather);
                double windSpeed = extractWindSpeed(weather);

                weatherLabel.setText("City: " + city + "\nTemperature: " + temperature + "°C");
                windLabel.setText("Speed: " + windSpeed + " km/h");

                infoCards.setOpacity(1);

                fanAnimation.stop();

                if (windSpeed < 10) {
                    fanAnimation.setDuration(Duration.seconds(1.6));
                } else if (windSpeed < 20) {
                    fanAnimation.setDuration(Duration.seconds(0.9));
                } else if (windSpeed < 35) {
                    fanAnimation.setDuration(Duration.seconds(0.45));
                } else {
                    fanAnimation.setDuration(Duration.seconds(0.20));
                }

                fanAnimation.playFromStart();

                if (temperature < 0) {
                    changeBackgroundWithFade(box, "/images/verycold.png");
                } else if (temperature >= 0 && temperature < 10) {
                    changeBackgroundWithFade(box, "/images/cold.png");
                } else if (temperature >= 10 && temperature <= 15) {
                    changeBackgroundWithFade(box, "/images/chilly.png");
                } else {
                    changeBackgroundWithFade(box, "/images/hot.png");
                }
            }
        });

        logoutButton.setOnAction(e -> showLoginScreen());

        glassCard.getChildren().addAll(
                welcomeLabel,
                subtitleLabel,
                cityField,
                searchButton,
                infoCards
        );

        box.setSpacing(14);
        box.getChildren().addAll(logoLabel, glassCard, logoutButton);

        Scene scene = new Scene(box, 600, 500);
        stage.setTitle("Weatherly - Main");
        stage.setScene(scene);
        stage.show();

        askForCurrentLocation(cityField, searchButton);
    }

    private void askForCurrentLocation(TextField cityField, Button searchButton) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Current Location");
        alert.setHeaderText("Use your current location?");
        alert.setContentText("Weatherly can detect your approximate city using your internet connection.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                String currentCity = detectCurrentCity();

                if (currentCity.equals("Unknown")) {
                    Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                    errorAlert.setTitle("Location Not Found");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Could not detect your current city. Please enter it manually.");
                    errorAlert.showAndWait();
                } else {
                    cityField.setText(currentCity);
                    searchButton.fire();
                }
            }
        });
    }

    private String detectCurrentCity() {
        try {
            String apiUrl = "http://ip-api.com/json/";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String result = response.toString();

            if (!result.contains("\"status\":\"success\"")) {
                return "Unknown";
            }

            String key = "\"city\":\"";
            int start = result.indexOf(key);

            if (start == -1) {
                return "Unknown";
            }

            start = start + key.length();
            int end = result.indexOf("\"", start);

            if (end == -1) {
                return "Unknown";
            }

            String city = result.substring(start, end);

            if (city.isEmpty()) {
                return "Unknown";
            }

            return city;

        } catch (Exception e) {
            return "Unknown";
        }
    }

    private void setBackgroundImage(VBox box, String imagePath) {
        String imageUrl = getClass().getResource(imagePath).toExternalForm();

        box.setStyle(
                "-fx-background-image: url('" + imageUrl + "');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center center;"
        );
    }

    private void changeBackgroundWithFade(VBox box, String imagePath) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(350), box);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.55);

        fadeOut.setOnFinished(e -> {
            setBackgroundImage(box, imagePath);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(550), box);
            fadeIn.setFromValue(0.55);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }

    private double extractTemperature(String weather) {
        try {
            String[] lines = weather.split("\n");

            for (String line : lines) {
                if (line.startsWith("Temperature:")) {
                    String number = line
                            .replace("Temperature:", "")
                            .replace("°C", "")
                            .trim();

                    return Double.parseDouble(number);
                }
            }

        } catch (Exception e) {
            return 20;
        }

        return 20;
    }

    private double extractWindSpeed(String weather) {
        try {
            String[] lines = weather.split("\n");

            for (String line : lines) {
                if (line.startsWith("Wind Speed:")) {
                    String number = line
                            .replace("Wind Speed:", "")
                            .replace("km/h", "")
                            .trim();

                    return Double.parseDouble(number);
                }
            }

        } catch (Exception e) {
            return 0;
        }

        return 0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}