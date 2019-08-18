package com.company;

import java.util.HashMap;

class SymbolTable {

    private HashMap<String, Integer> map;

    SymbolTable() {
        map = new HashMap<>();
    }

    void addEntry(String symbol, int address) {
        map.put(symbol, address);
    }

    boolean contains(String symbol) {
        return map.containsKey(symbol);
    }

    int getAddress(String symbol) {
        return map.get(symbol);
    }
}
