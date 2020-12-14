public class Balloon {

    private String color;
    private double psi;

    public Balloon(String color) {
        this.color = color;
    }

    // getters
    public String getColor() {
        return color;
    }

    public double getPsi() {
        if (this.psi > 16.0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return psi;
        }
    }

    // setters
    public void setColor(String color) {
        this.color = color;
    }

    public void setPsi(double psi) {
        this.psi = psi;
    }

    public void inflate() { // inflates balloon by random amount between 0.0 and 5.0
        this.psi = this.psi + (Math.random() * 5.0);
    }

    public boolean isExploded() {
        if (this.psi > 16.0) {
            return true;
        } else {
            return false;
        }
    }

}
