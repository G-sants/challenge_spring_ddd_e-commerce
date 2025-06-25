package g.sants.challenge_e_commerce.adapter.security.methods;

public abstract class SecurityCategory {

    public static final String AUTH = "/auth";
    public static final String USER = "/users";
    public static final String ITEM_P1 = "/item";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String USER_ROLE = "USER";


    protected SecurityCategory(){
        throw  new IllegalStateException("Utility Class");
    }
}