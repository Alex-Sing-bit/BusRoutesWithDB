package ru.vsu.cs.baklanova.database_interaction.table_objects;

public class User extends ID{
    private String name;

    private String phoneNumber;

    private String password;
    private Integer homeBuildingId;

    public User(String name, String phoneNumber, String password, Integer homeBuildingId) {
        super(1);
        setName(name);
        setPhoneNumber(phoneNumber);
        setPassword(password);
        setHomeBuildingId(homeBuildingId);
    }
    public User(int id, String name, String phoneNumber, String password, Integer homeBuildingId) {
        super(id);
        setName(name);
        setPhoneNumber(phoneNumber);
        setPassword(password);
        setHomeBuildingId(homeBuildingId);
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        name = name.strip();
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        phoneNumber = phoneNumber.strip();
        phoneNumber = phoneNumber.strip();
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        password = password.strip();

        this.password = password;
    }

    public void setHomeBuildingId(Integer homeBuildingId) {
        if (homeBuildingId != null && homeBuildingId < 1) {
            throw new IllegalArgumentException("Building id cannot be less than 1");
        }
        this.homeBuildingId = homeBuildingId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public Integer getHomeBuildingId() {
        return homeBuildingId;
    }

    public String toString() {
        return name;
    }
}
