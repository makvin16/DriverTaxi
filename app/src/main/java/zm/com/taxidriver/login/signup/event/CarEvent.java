package zm.com.taxidriver.login.signup.event;

public class CarEvent {
    private String brand, model, number;
    private int year, passenger, id_type;

    public CarEvent(String brand, String model, String number, int year, int passenger, int id_type) {
        this.brand = brand;
        this.model = model;
        this.number = number;
        this.year = year;
        this.passenger = passenger;
        this.id_type = id_type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getNumber() {
        return number;
    }

    public int getYear() {
        return year;
    }

    public int getPassenger() {
        return passenger;
    }

    public int getId_type() {
        return id_type;
    }
}
