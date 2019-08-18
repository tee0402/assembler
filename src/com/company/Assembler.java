package com.company;

import java.io.*;

public class Assembler {

    public static void main(String[] args) throws IOException {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.addEntry("SP", 0);
        symbolTable.addEntry("LCL", 1);
        symbolTable.addEntry("ARG", 2);
        symbolTable.addEntry("THIS", 3);
        symbolTable.addEntry("THAT", 4);
        symbolTable.addEntry("R0", 0);
        symbolTable.addEntry("R1", 1);
        symbolTable.addEntry("R2", 2);
        symbolTable.addEntry("R3", 3);
        symbolTable.addEntry("R4", 4);
        symbolTable.addEntry("R5", 5);
        symbolTable.addEntry("R6", 6);
        symbolTable.addEntry("R7", 7);
        symbolTable.addEntry("R8", 8);
        symbolTable.addEntry("R9", 9);
        symbolTable.addEntry("R10", 10);
        symbolTable.addEntry("R11", 11);
        symbolTable.addEntry("R12", 12);
        symbolTable.addEntry("R13", 13);
        symbolTable.addEntry("R14", 14);
        symbolTable.addEntry("R15", 15);
        symbolTable.addEntry("SCREEN", 16384);
        symbolTable.addEntry("KBD", 24576);

        Parser parser = new Parser(args[0]);
        int romAddress = 0;
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.commandType() == CommandType.L_COMMAND) {
                symbolTable.addEntry(parser.symbol(), romAddress);
            }
            else {
                romAddress++;
            }
        }

        parser = new Parser(args[0]);
        FileWriter fileWriter = new FileWriter(args[0].split("\\.")[0] + ".hack");
        int ramAddress = 16;
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.commandType() == CommandType.A_COMMAND) {
                if (parser.symbol().matches("\\d+")) {
                    fileWriter.write(String.format("%16s", Integer.toBinaryString(Integer.parseInt(parser.symbol()))).replace(' ', '0') + "\n");
                }
                else {
                    if (symbolTable.contains(parser.symbol())) {
                        fileWriter.write(String.format("%16s", Integer.toBinaryString(symbolTable.getAddress(parser.symbol()))).replace(' ', '0') + "\n");
                    }
                    else {
                        fileWriter.write(String.format("%16s", Integer.toBinaryString(ramAddress)).replace(' ', '0') + "\n");
                        symbolTable.addEntry(parser.symbol(), ramAddress);
                        ramAddress++;
                    }
                }
            }
            else if (parser.commandType() == CommandType.C_COMMAND) {
                fileWriter.write("111" + Code.comp(parser.comp()) + Code.dest(parser.dest()) + Code.jump(parser.jump()) + "\n");
            }
        }
        fileWriter.close();
    }
}
