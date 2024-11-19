package ru.vsu.cs.baklanova.database_interaction.table_objects;

public abstract class ID {
    private int id;

    protected ID(int id) {
        setId(id);
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("ID cannot be less then 1");
        }
    }

    public int getId() {
        return id;
    }
}
