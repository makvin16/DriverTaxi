package zm.com.taxidriver.db;

import io.realm.RealmObject;

public class Driver extends RealmObject {
    private String name, surname, email, phone, password, photo;
    private boolean animals, drunk, food, smoking;
    private int id_car;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isAnimals() {
        return animals;
    }

    public void setAnimals(boolean animals) {
        this.animals = animals;
    }

    public boolean isDrunk() {
        return drunk;
    }

    public void setDrunk(boolean drunk) {
        this.drunk = drunk;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public int getId_car() {
        return id_car;
    }

    public void setId_car(int id_car) {
        this.id_car = id_car;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", animals=" + animals +
                ", drunk=" + drunk +
                ", food=" + food +
                ", smoking=" + smoking +
                ", id_car=" + id_car +
                '}';
    }
}
