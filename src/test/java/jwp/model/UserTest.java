package jwp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;

    @BeforeEach
    void init() {
        user = new User("jungwoo1","1","jungwoo","jungwoo@gmail.com");
    }

    @Test
    void update() {
        User user1 = new User("jungwoo1", "2", "jungwoo2", "jungwoo2@gmail.com");
        user.update(user1);
        assertEquals("2",user.getPassword());
        assertEquals("jungwoo2",user.getName());
        assertEquals("jungwoo2@gmail.com",user.getEmail());
    }

    @Test
    void matchPassword() {
        assertTrue(user.matchPassword("1"));
    }

//    @Test
//    void isSameUser() {
//        assertTrue(user.isSameUser(new User("jungwoo1", "2", "jungwoo2", "jungwoo2@gmail.com")));
//    }

}