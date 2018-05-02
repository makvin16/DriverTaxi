package zm.com.taxidriver.login.signup.event;

public class AdditionalEvent {
    private String photo;
    private boolean isAnimals, isFood, isDrunk, isSmoking;

    public AdditionalEvent(String photo, boolean isAnimals, boolean isFood, boolean isDrunk, boolean isSmoking) {
        this.photo = photo;
        this.isAnimals = isAnimals;
        this.isFood = isFood;
        this.isDrunk = isDrunk;
        this.isSmoking = isSmoking;
    }

    public String getPhoto() {
        return photo;
    }

    public boolean isAnimals() {
        return isAnimals;
    }

    public boolean isFood() {
        return isFood;
    }

    public boolean isDrunk() {
        return isDrunk;
    }

    public boolean isSmoking() {
        return isSmoking;
    }
}
