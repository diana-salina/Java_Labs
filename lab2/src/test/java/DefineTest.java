import ru.nsu.salina.factory.commands.*;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DefineTest {
    @Test
    public void test() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> map = new HashMap<>();
        String parameters = "name 2.3";
        Double expectedValue = 2.3;
        String expectedName = "name";

        DefineCommand define = new DefineCommand();
        define.executeCommand(map, stack, parameters);

        assertEquals(map.get(expectedName), expectedValue);
    }
}
