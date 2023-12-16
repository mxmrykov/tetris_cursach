import database.DataBase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseTest {
    @Test
    @DisplayName("Select test")
    public void selectTest() {
        DataBase dataBase = new DataBase();
        dataBase.saveData(1);
        dataBase.saveData(2);
        dataBase.saveData(3);
        List<String[]> allQuery = dataBase.getAll();
        List<Boolean> selectedAll = new ArrayList<>(Arrays.asList(false, false, false));
        for (String[] query : allQuery) {
            if (query[1].equals("1")) {
                selectedAll.set(0, true);
            }
            if (query[1].equals("2")) {
                selectedAll.set(1, true);
            }
            if (query[1].equals("3")) {
                selectedAll.set(2, true);
            }
        }
        Assertions.assertEquals(selectedAll, Arrays.asList(true, true, true));

    }

    @Test
    @DisplayName("Insert test")
    public void insertTest() {
        DataBase dataBase = new DataBase();
        dataBase.saveData(123321);
        List<String[]> allQuery = dataBase.getAll();
        boolean isInserted = false;
        for (String[] query : allQuery) {
            if (query[1].equals("123321")) {
                isInserted = true;
                break;
            }
        }
        Assertions.assertEquals(isInserted, true);
    }
}
