
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EnviromentTest {
    @Test
    void testEnvironmentSetup() {
        System.out.println("Die Testumgebung funktioniert!");
        assertTrue(true, "Dieser Test sollte immer erfolgreich sein.");
    }

}
