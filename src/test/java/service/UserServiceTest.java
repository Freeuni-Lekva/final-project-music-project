package service;

import org.freeuni.musicforum.exception.UnsuccessfulSignupException;
import org.freeuni.musicforum.model.Badge;
import org.freeuni.musicforum.model.Gender;
import org.freeuni.musicforum.model.Password;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.service.UserService;
import org.freeuni.musicforum.util.UserUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final UserService service = ServiceFactory.getUserService();

    @Test
    void testCorrectSignups() {
        User u1 = new User("Robin", "Fenty", null, Gender.WOMAN,
                "Rihanna", new Password("123"), Badge.NEWCOMER);
        User u2 = new User("Mikhail", "Gorbachev", null, Gender.MAN,
                "MikGor", new Password("1984"), Badge.NEWCOMER);

        assertDoesNotThrow(() -> service.signUp(u1));
        assertDoesNotThrow(() -> service.signUp(u2));
    }

    @Test
    void testShortParameterSignups() {
        User u1 = new User("Sandra", "Diaz-Twine", null, Gender.WOMAN,
                "iQueen", new Password("a2"), Badge.ENTHUSIAST);
        User u2 = new User("Parvati", "Shallow", null, Gender.WOMAN,
                "xo", new Password("xoxo"), Badge.CONTRIBUTOR);
        User u3 = new User("", "", null, Gender.MAN, "",
                new Password(""), Badge.NEWCOMER);
        String expectedErrorMessage = "Username/password must be longer than 2 characters";

        Exception ex1 = assertThrows(UnsuccessfulSignupException.class, (()-> service.signUp(u1)));
        assertEquals(expectedErrorMessage, ex1.getMessage());
        Exception ex2 = assertThrows(UnsuccessfulSignupException.class, (()-> service.signUp(u2)));
        assertEquals(expectedErrorMessage, ex2.getMessage());
        Exception ex3 = assertThrows(UnsuccessfulSignupException.class, (()-> service.signUp(u3)));
        assertEquals(expectedErrorMessage, ex3.getMessage());
    }

    @Test
    void testUsernameExistsSignups() {
        // A user with username "guri" and password "guri" already exists.
        User u1 = new User("Guri", "Waters", null, Gender.MAN,
                "guri", new Password("guri"), Badge.MODERATOR);
        User u2 = new User("Guri", "Waters", null, Gender.MAN,
                "guri", new Password("1234"), Badge.MODERATOR);
        String expectedErrorMessage = "User with this username already exists";

        Exception ex1 = assertThrows(UnsuccessfulSignupException.class, (()-> service.signUp(u1)));
        assertEquals(expectedErrorMessage, ex1.getMessage());
        Exception ex2 = assertThrows(UnsuccessfulSignupException.class, (()-> service.signUp(u2)));
        assertEquals(expectedErrorMessage, ex2.getMessage());
    }

    @Test
    void testCorrectLogins() {
        assertTrue(service.login("guri", "guri"));
        assertTrue(service.login("eva", "2000"));
        assertTrue(service.login("u#700", "ushi"));
        assertTrue(service.login("melanie1996", "A_B_C*"));
    }

    @Test
    void testWrongUsernameLogins() {
        assertFalse(service.login("evangelina", "2000"));
        assertFalse(service.login("smith", "2000"));
        assertFalse(service.login("2000", "2000"));
        assertFalse(service.login("ushi", "ushi"));
    }

    @Test
    void testWrongMissedPasswordLogins() {
        assertFalse(service.login("u#700", "A_B_C*"));
        assertFalse(service.login("guri", "namibia"));
        assertFalse(service.login("eva", "fahrenheit"));
        assertFalse(service.login("melanie1996", "guri"));
    }

    @Test
    void testWrongHashedPasswordLogins() {
        assertFalse(service.login("u#700", UserUtils.hashPassword("ushi")));
        assertFalse(service.login("guri", UserUtils.hashPassword("guri")));
        assertFalse(service.login("melanie1996", UserUtils.hashPassword("A_B_C*")));
        assertFalse(service.login("eva", UserUtils.hashPassword("2000")));
    }
}
