package ru.vsu.cs.baklanova.database_interaction.table_objects;

public class Building extends ID {
    private int number;
    private BuildingTypeEnum type;
    private int streetId;

    public Building(int id, int number, BuildingTypeEnum type, int streetId) {
        super(id);
        setNumber(number);
        setType(type);
        setStreetId(streetId);
    }


    public void setNumber(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Building number cannot be less than 1");
        }
        this.number = number;
    }

    public void setStreetId(int streetId) {
        if (streetId < 1) {
            throw new IllegalArgumentException("Street id cannot be less than 1");
        }
        this.streetId = streetId;
    }

    public void setType(BuildingTypeEnum type) {
        this.type = type;
    }
    public int getNumber() {
        return number;
    }

    public BuildingTypeEnum getType() {
        return type;
    }

    public String getTypeRu() {
        return type.getRuTranslation();
    }

    public int getStreetId() {
        return streetId;
    }

    public String toString() {
        return "д." + number;
    }
}
