package ru.vsu.cs.baklanova.database_interaction.table_objects;

public class Stop extends ID {
    private String name;
    private int streetId;

    public Stop(int id, String name, int streetId) {
        super(id);
        setName(name);
        setStreetId(streetId);
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        name = name.strip();
        this.name = name;
    }

    public void setStreetId(int streetId) {
        if (streetId < 1) {
            throw new IllegalArgumentException("Street id cannot be less than 1");
        }
        this.streetId = streetId;
    }

    public String getName() {
        return name;
    }

    public int getStreetId() {
        return streetId;
    }

    public String toString() {
        return "Остановка " + name;
    }
}
