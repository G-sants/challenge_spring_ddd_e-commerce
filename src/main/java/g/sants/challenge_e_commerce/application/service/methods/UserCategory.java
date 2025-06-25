package g.sants.challenge_e_commerce.application.service.methods;

public enum UserCategory {

    ADMIN("admin"),

    USER("user");

    private String category;

    UserCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }
}
