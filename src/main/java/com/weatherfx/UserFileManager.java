package com.weatherfx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class UserFileManager {

    private static final String FILE_NAME = "users.dat";

    private static final int USERNAME_SIZE = 64;
    private static final int PASSWORD_SIZE = 64;
    private static final int IP_SIZE = 64;
    private static final int CITY_SIZE = 64;

    private static final int RECORD_SIZE =
            USERNAME_SIZE + PASSWORD_SIZE + IP_SIZE + CITY_SIZE;

    private static final byte ENCRYPTION_KEY = 7;

    public static boolean saveUser(User user) {
        if (usernameExists(user.getUsername())) {
            return false;
        }

        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            file.seek(file.length());

            writeEncryptedField(file, user.getUsername(), USERNAME_SIZE);
            writeEncryptedField(file, user.getPassword(), PASSWORD_SIZE);
            writeEncryptedField(file, user.getIpAddress(), IP_SIZE);
            writeEncryptedField(file, user.getCityLocation(), CITY_SIZE);

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public static boolean validateLogin(String username, String password) {
        File dataFile = new File(FILE_NAME);

        if (!dataFile.exists()) {
            return false;
        }

        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            long numberOfRecords = file.length() / RECORD_SIZE;

            for (int i = 0; i < numberOfRecords; i++) {
                file.seek((long) i * RECORD_SIZE);

                String savedUsername = readEncryptedField(file, USERNAME_SIZE);
                String savedPassword = readEncryptedField(file, PASSWORD_SIZE);

                file.skipBytes(IP_SIZE);
                file.skipBytes(CITY_SIZE);

                if (savedUsername.equals(username) && savedPassword.equals(password)) {
                    return true;
                }
            }

        } catch (IOException e) {
            return false;
        }

        return false;
    }

    private static boolean usernameExists(String username) {
        File dataFile = new File(FILE_NAME);

        if (!dataFile.exists()) {
            return false;
        }

        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            long numberOfRecords = file.length() / RECORD_SIZE;

            for (int i = 0; i < numberOfRecords; i++) {
                file.seek((long) i * RECORD_SIZE);

                String savedUsername = readEncryptedField(file, USERNAME_SIZE);

                file.skipBytes(PASSWORD_SIZE);
                file.skipBytes(IP_SIZE);
                file.skipBytes(CITY_SIZE);

                if (savedUsername.equals(username)) {
                    return true;
                }
            }

        } catch (IOException e) {
            return false;
        }

        return false;
    }

    private static void writeEncryptedField(RandomAccessFile file, String value, int size) throws IOException {
        byte[] rawBytes = value.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = new byte[size];

        for (int i = 0; i < size; i++) {
            byte originalByte = 0;

            if (i < rawBytes.length) {
                originalByte = rawBytes[i];
            }

            encryptedBytes[i] = (byte) (originalByte ^ ENCRYPTION_KEY);
        }

        file.write(encryptedBytes);
    }

    private static String readEncryptedField(RandomAccessFile file, int size) throws IOException {
        byte[] encryptedBytes = new byte[size];
        file.readFully(encryptedBytes);

        byte[] decryptedBytes = new byte[size];

        for (int i = 0; i < size; i++) {
            decryptedBytes[i] = (byte) (encryptedBytes[i] ^ ENCRYPTION_KEY);
        }

        int actualLength = 0;

        while (actualLength < decryptedBytes.length && decryptedBytes[actualLength] != 0) {
            actualLength++;
        }

        return new String(decryptedBytes, 0, actualLength, StandardCharsets.UTF_8);
    }

    public static void printAllUsersForDeveloper() {
        File dataFile = new File(FILE_NAME);

        if (!dataFile.exists()) {
            System.out.println("No users.dat file found.");
            return;
        }

        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            long numberOfRecords = file.length() / RECORD_SIZE;

            System.out.println("========== Stored Users ==========");

            if (numberOfRecords == 0) {
                System.out.println("No stored users found.");
            }

            for (int i = 0; i < numberOfRecords; i++) {
                file.seek((long) i * RECORD_SIZE);

                String username = readEncryptedField(file, USERNAME_SIZE);
                String password = readEncryptedField(file, PASSWORD_SIZE);
                String ipAddress = readEncryptedField(file, IP_SIZE);
                String cityLocation = readEncryptedField(file, CITY_SIZE);

                System.out.println("User " + (i + 1));
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                System.out.println("IP Address: " + ipAddress);
                System.out.println("City Location: " + cityLocation);
                System.out.println("----------------------------------");
            }

        } catch (IOException e) {
            System.out.println("Error reading encrypted user data.");
        }
    }
}