package com.company;

import java.util.*;
import java.io.*;

class Parser {

    private Scanner scanner;
    private String currentCommand;

    Parser(String fileName) throws IOException {
        scanner = new Scanner(new File(fileName));
        File tempFile = File.createTempFile("tempFile", "tmp");
        tempFile.deleteOnExit();
        FileWriter fileWriter = new FileWriter(tempFile);
        String currentLine;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine().replace(" ", "").split("//")[0] + "\n";
            if (!currentLine.equals("\n")) {
                fileWriter.write(currentLine);
            }
        }
        fileWriter.close();
        scanner = new Scanner(tempFile);
    }

    boolean hasMoreCommands() {
        return scanner.hasNextLine();
    }

    void advance() {
        currentCommand = scanner.nextLine();
    }

    CommandType commandType() {
        if (currentCommand.startsWith("@")) {
            return CommandType.A_COMMAND;
        }
        else if (currentCommand.contains("=") || currentCommand.contains(";")) {
            return CommandType.C_COMMAND;
        }
        else {
            return CommandType.L_COMMAND;
        }
    }

    String symbol() {
        return currentCommand.substring(1);
    }

    String dest() {
        if (currentCommand.contains("=")) {
            return currentCommand.split("=")[0];
        }
        else {
            return "";
        }
    }

    String comp() {
        if (currentCommand.contains("=")) {
            return currentCommand.split("=")[1];
        }
        else {
            return currentCommand.split(";")[0];
        }
    }

    String jump() {
        if (currentCommand.contains(";")) {
            return currentCommand.split(";")[1];
        }
        else {
            return "";
        }
    }
}
