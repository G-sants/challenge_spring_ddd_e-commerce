package g.sants.challenge_e_commerce.application.communication;

public class CheckOutOrder{

    private double totalPrice;
    private String status;

    public CheckOutOrder(String status, double totalPrice){
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
