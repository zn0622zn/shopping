import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 张男
 * @date: 2024/1/24---18:05
 */
@SpringBootTest
public class A {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String string = passwordEncoder.encode("liuguoliang");
        System.err.println(string);
        boolean result = passwordEncoder.matches("liuguoliang","$2a$2a$2a$2a$2a$10$PCT91AZFeVJUOPqrWarEmu6zTQhpBLll0IvnK..qYAhfQ/T7kq9wu");
        System.err.println(result);
    }
}
