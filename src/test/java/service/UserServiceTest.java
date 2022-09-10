package service;

import org.freeuni.musicforum.dao.InMemoryUserDAO;
import org.freeuni.musicforum.exception.NoSuchUserExistsException;
import org.freeuni.musicforum.exception.UnsuccessfulSignupException;
import org.freeuni.musicforum.model.*;
import org.freeuni.musicforum.service.ReviewService;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.service.UserService;
import org.freeuni.musicforum.util.Utils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private static final UserService service = new UserService(new InMemoryUserDAO());

    @Test
    void testCorrectSignups() {
        User u1 = new User("Robin", "Fenty", null, Gender.WOMAN,
                "Rihanna", new Password("123"), new Badge(Badge.BadgeEnum.NEWCOMER));
        User u2 = new User("Mikhail", "Gorbachev", null, Gender.MAN,
                "MikGor", new Password("1984"), new Badge(Badge.BadgeEnum.NEWCOMER));

        assertDoesNotThrow(() -> service.signUp(u1));
        assertDoesNotThrow(() -> service.signUp(u2));
    }

    @Test
    void testShortParameterSignups() {
        User u1 = new User("Sandra", "Diaz-Twine", null, Gender.WOMAN,
                "iQueen", new Password("a2"), new Badge(Badge.BadgeEnum.ENTHUSIAST));
        User u2 = new User("Parvati", "Shallow", null, Gender.WOMAN,
                "xo", new Password("xoxo"), new Badge(Badge.BadgeEnum.CONTRIBUTOR));
        User u3 = new User("", "", null, Gender.MAN, "",
                new Password(""), new Badge(Badge.BadgeEnum.NEWCOMER));
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
                "guri", new Password("guri"), new Badge(Badge.BadgeEnum.ADMINISTRATOR));
        User u2 = new User("Guri", "Waters", null, Gender.MAN,
                "guri", new Password("1234"), new Badge(Badge.BadgeEnum.ADMINISTRATOR));
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
        assertFalse(service.login("u#700", Utils.hashText("ushi")));
        assertFalse(service.login("guri", Utils.hashText("guri")));
        assertFalse(service.login("melanie1996", Utils.hashText("A_B_C*")));
        assertFalse(service.login("eva", Utils.hashText("2000")));
    }

    @Test
    void testUserStatus(){
        assertTrue(service.isActive("guri"));
        assertTrue(service.isActive("eva"));
        assertTrue(service.isActive("melanie1996"));
        assertFalse(service.isActive("u#700"));
    }

    @Test
    void testBanUser(){
        UserService userService = new UserService(new InMemoryUserDAO());
        userService.banUser("eva");
        assertFalse(userService.isActive("eva"));
        assertTrue(userService.userExists("eva"));
        assertTrue(userService.isActive("guri"));
        assertTrue(userService.isActive("melanie1996"));
    }

    @Test
    void testGetProfileData(){
        UserService userService = new UserService(new InMemoryUserDAO());

        User newUser = new User("Yvie", "Oddly", new Date(), Gender.OTHER, "Yvie",
                new Password("password"),new Badge(Badge.BadgeEnum.NEWCOMER));
        userService.signUp(newUser);
        PublicUserData pd = userService.getProfileData("Yvie");


        assertEquals(pd.username(), "Yvie");
        assertEquals(pd.firstName(), "Yvie");
        assertEquals(pd.lastName(), "Oddly");
        assertEquals(pd.badge().toString(), Badge.BadgeEnum.NEWCOMER.toString());
        assertEquals(pd.profileImageBase64(), null);
        assertEquals(pd.status(), Status.ACTIVE);
        assertEquals(pd.prestige(), 0);

        String expectedErrorMessage = "User with provided username yvie does not exist";

        Exception ex1 = assertThrows(NoSuchUserExistsException.class, (()-> userService.getProfileData("yvie")));
        assertEquals(expectedErrorMessage, ex1.getMessage());

    }

    @Test
    void testUpdateBadge(){
        UserService userService = new UserService(new InMemoryUserDAO());
        userService.updateBadge("eva");
        userService.updateBadge("guri");


        assertEquals(userService.getProfileData("eva").badge().toString(), Badge.BadgeEnum.NEWCOMER.toString());
        assertEquals(userService.getProfileData("guri").badge().toString(), Badge.BadgeEnum.ADMINISTRATOR.toString());


        ReviewService rs = ServiceFactory.getReviewService();
        rs.postReview(new Review("someId", "eva", "reviewText", 2));
        for(int i = 0; i<100; i++){
            rs.upvoteReview(Utils.hashText("someId" +"eva"+"reviewText"));
        }
        userService.updateBadge("eva");
        assertEquals(userService.getProfileData("eva").badge().toString(), Badge.BadgeEnum.ENTHUSIAST.toString());
    }

    @Test
    void testFriendShipStatus(){
        UserService userService = new UserService(new InMemoryUserDAO());

        String expectedErrorMessage = "User with provided username user1 does not exist";
        Exception ex1 = assertThrows(NoSuchUserExistsException.class, (()->userService.getFriendshipStatus("user1", "eva")));
        assertEquals(expectedErrorMessage, ex1.getMessage());
        Exception ex2 = assertThrows(NoSuchUserExistsException.class, (()->userService.getFriendshipStatus("guri", "user1")));
        assertEquals(expectedErrorMessage, ex2.getMessage());

        FriendshipStatus fs = userService.getFriendshipStatus("guri", "eva");
        assertEquals(fs, FriendshipStatus.FRIENDS);

        FriendshipStatus fs1 = userService.getFriendshipStatus("guri", "melanie1996");
        assertEquals(fs1, null);

        userService.sendFriendRequest("guri", "melanie1996");
        assertEquals(userService.getFriendshipStatus("guri", "melanie1996"), FriendshipStatus.REQUEST_SENT);
        assertEquals(userService.getFriendshipStatus("melanie1996", "guri"), FriendshipStatus.ACCEPT_REQUEST);

        userService.acceptFriendRequest("melanie1996", "guri");
        assertEquals(userService.getFriendshipStatus("guri", "melanie1996"), FriendshipStatus.FRIENDS);
        assertEquals(userService.getFriendshipStatus("melanie1996", "guri"), FriendshipStatus.FRIENDS);

        userService.removeFriendshipStatus("guri", "melanie1996");
        assertEquals(userService.getFriendshipStatus("guri", "melanie1996"), null);

        userService.sendFriendRequest("guri", "melanie1996");
        assertEquals(userService.getFriendshipStatus("guri", "melanie1996"), FriendshipStatus.REQUEST_SENT);
        assertEquals(userService.getFriendshipStatus("melanie1996", "guri"), FriendshipStatus.ACCEPT_REQUEST);

        userService.removeFriendRequest("melanie1996", "guri");
        assertEquals(userService.getFriendshipStatus("guri", "melanie1996"), null);

    }

}
