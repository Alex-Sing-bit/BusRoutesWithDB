package ru.vsu.cs.baklanova.database_interaction.table_objects;

public class Street extends ID {
    private String name;
    private StreetTypeEnum type;

    public Street(int id, String name, StreetTypeEnum type) {
        super(id);
        setName(name);
        setType(type);
    }
    public void setName(String name) {
            if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Street name cannot be null or empty");
        }
        name = name.strip();
        this.name = name;
    }

    public void setType(StreetTypeEnum type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public StreetTypeEnum getType() {
        return type;
    }

    public String getTypeRu() {
        return type.getRuTranslation();
    }


    public String toString() {
        return type.getRuTranslation() + " " + name;
    }
}

