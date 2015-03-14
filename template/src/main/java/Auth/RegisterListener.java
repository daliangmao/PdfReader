package Auth;

/**
 * Created by Andy on 3/8/15 AD.
 */
public interface RegisterListener {

    void onRegister(String username,
                    String email,
                    String password,
                    String confirm_password);
}
