package ru.vsu.cs.baklanova.database_interaction.table_objects;


public class Route extends ID{
    private int number;
    private RouteTypeEnum type;


    public Route(int id, int number, RouteTypeEnum type) {
        super(id);
        setNumber(number);
        setType(type);
    }

    public void setNumber(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Route number cannot be less than 1");
        }
        this.number = number;
    }

    public void setType(RouteTypeEnum type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public RouteTypeEnum getType() {
        return type;
    }

    public String getTypeRu() {
        return type.getRuTranslation();
    }

    public String toString() {
        return "Маршрут №" + number + " (" + type.getRuTranslation() + ")";
    }
}
