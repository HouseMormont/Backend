package ro.ubbcluj.cs.mormont;

import org.mockito.Mockito;
import org.springframework.jdbc.core.RowMapper;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.TestNG;
import org.testng.annotations.BeforeTest;
import ro.ubbcluj.cs.mormont.Domain.User;
import ro.ubbcluj.cs.mormont.database.DBHelper;

import java.util.ArrayList;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by flaviu on 1/18/2017.
 */

public class BackendApplicationTestsTest {

    @Mock
    private JdbcTemplate mockJdbc = mock(JdbcTemplate.class);
    private DBHelper dbHelper;

    @BeforeTest
    private void initialize_tests() throws Exception {
        PowerMockito.whenNew(JdbcTemplate.class).withArguments(any(),any()).thenReturn(mockJdbc);
        dbHelper = DBHelper.getInstance(mockJdbc);
    }

    @Test
    public void testFindUser() {

        PowerMockito.when(mockJdbc.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(new ArrayList<>());
        User user = dbHelper.findUser("alin");
        assertEquals(null, user);

    }
    @Test
    public void testCreateUser() {
        ArrayList<Object> users = new ArrayList<>();
        User expected = new User(1, "alin", "pass");
        users.add(expected);
        PowerMockito.when(mockJdbc.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(users);
        User actual = dbHelper.findUser("alin");
        assertEquals(expected, actual);

    }
    @Test
    public void testSaveNewDoc() throws Exception {
        PowerMockito.when(mockJdbc.update(anyString(), any(Object[].class))).thenReturn(1);
        dbHelper.saveNewDocument(1, (float) 2.0, "admin", 2, "2010.2.2","mydoc");
        Mockito.verify(mockJdbc, times(1)).update(anyString(), eq(1),eq((float)2.0),eq("admin"),eq(2),eq("2010.2.2"),eq("mydoc"));
    }
}