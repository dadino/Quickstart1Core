package com.dadino.quickstart.core;

class Sample {
    private final int id;
    private final String name;

    Sample(int id) {
        this.id = id;
        this.name = "Sample " + id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
