package leejj_server_project3_test; // 메인과 동일한 패키지

import com.busanit501.leejj_server_project3_test.todo_test.dao.DAO;
import org.junit.jupiter.api.Test;

public class TodoDAOTest {
    private DAO todoDAO = new DAO();

    @Test
    public void testGetTime() {
        String time = todoDAO.getTime();
        System.out.println("DB에서 받아온 시간: " + time);
    }

    @Test
    public void testGetTime2() throws Exception { // 👈 여기에 반드시 throws Exception 추가!
        String time = todoDAO.getTime2();
        System.out.println("getTime2 결과: " + time);
    }
}