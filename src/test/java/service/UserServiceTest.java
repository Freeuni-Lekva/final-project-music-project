package service;

import org.freeuni.musicforum.exception.UnsuccessfulSignupException;
import org.freeuni.musicforum.model.Badge;
import org.freeuni.musicforum.model.Gender;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.service.UserService;
import org.freeuni.musicforum.util.UserUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void testService1() {
        UserService service = ServiceFactory.getUserService();

        /***************************** Test Correct Signups ****************************/
        User u1 = new User("Robin", "Fenty", null, Gender.WOMAN,
                "Rihanna", "123", Badge.NEWCOMER);
        User u2 = new User("Mikhail", "Gorbachev", null, Gender.MAN,
                "MikGor", "1984", Badge.NEWCOMER);
        service.signUp(u1);
        service.signUp(u2);

        /***************************** Test Correct Logins *****************************/
        assertTrue(service.login("Rihanna", UserUtils.hashPassword("123")));
        assertTrue(service.login("MikGor", UserUtils.hashPassword("1984")));
    }

    @Test
    public void testService2() {
        UserService service = ServiceFactory.getUserService();

        /*************************** Test Incorrect Signups ****************************/
        User u1 = new User("Empress", "Lion", null, Gender.WOMAN,
                "Empress", "lion", Badge.NEWCOMER);
        User u2 = new User("Avian", "Tiger", null, Gender.OTHER,
                "Empress", "tiger", Badge.NEWCOMER);
        User u3 = new User("Chanel", "Oberlin", null, Gender.WOMAN,
                "Queen", "cotton", Badge.NEWCOMER);
        service.signUp(u1);
        String expectedErrorMessage = "User with this username already exists";
        Exception ex1 = assertThrows(UnsuccessfulSignupException.class,
                ()->{ service.signUp(u2);
                });
        assertEquals(expectedErrorMessage, ex1.getMessage());
        service.signUp(u3);
        Exception ex2 = assertThrows(UnsuccessfulSignupException.class,
                () ->{ service.signUp(u1);
                });
        assertEquals(expectedErrorMessage, ex2.getMessage());

        /**************************** Test Incorrect Logins ****************************/
        // User that shouldn't have been added.
        assertFalse(service.login("Empress", UserUtils.hashPassword("tiger")));
        // First name instead of username.
        assertFalse(service.login("Chanel", UserUtils.hashPassword("cotton")));
        // Not hashed password.
        assertFalse(service.login("Queen", "cotton"));

        /***************************** Test Correct Logins *****************************/
        assertTrue(service.login("Empress", UserUtils.hashPassword("lion")));
        assertTrue(service.login("Queen", UserUtils.hashPassword("cotton")));

    }

    @Test
    public void testService3() {
        UserService service = ServiceFactory.getUserService();

        /******************** Test Short Username/Password Signups *********************/
        User u1 = new User("Sandra", "Diaz-Twine", null, Gender.WOMAN,
                "iQueen", "a2", Badge.ENTHUSIAST);
        User u2 = new User("Parvati", "Shallow", null, Gender.WOMAN,
                "xo", "xoxo", Badge.CONTRIBUTOR);
        User u3 = new User("", "", null, Gender.MAN, "",
                "", Badge.NEWCOMER);
        String expectedErrorMessage = "Username/Password must be longer than 2 characters";
        Exception ex1 = assertThrows(UnsuccessfulSignupException.class,
                ()->{ service.signUp(u1);
                });
        assertEquals(expectedErrorMessage, ex1.getMessage());
        Exception ex2 = assertThrows(UnsuccessfulSignupException.class,
                ()->{ service.signUp(u2);
                });
        assertEquals(expectedErrorMessage, ex2.getMessage());
        Exception ex3 = assertThrows(UnsuccessfulSignupException.class,
                ()->{ service.signUp(u3);
                });
        assertEquals(expectedErrorMessage, ex3.getMessage());
    }

}
