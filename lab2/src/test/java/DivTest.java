import ru.nsu.salina.exceptions.DivisionByZeroException;
import ru.nsu.salina.factory.commands.*;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DivTest {
    @Test
    public void test1() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> map = new HashMap<>();
        String parameters = "a 2.3";
        String stackParameters = "a";
        Double expected = 4.6 / 2.3;

        DefineCommand define = new DefineCommand();
        define.executeCommand(map, stack, parameters);
        PushCommand push = new PushCommand();
        push.executeCommand(map, stack,stackParameters);
        parameters = "b 4.6";
        stackParameters = "b";
        define.executeCommand(map, stack, parameters);
        push.executeCommand(map, stack,stackParameters);

        DivCommand div = new DivCommand();
        div.executeCommand(map, stack, "");

        assertEquals(stack.lastElement(), expected);
    }

    @Test
    public void test2() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> map = new HashMap<>();
        String parameters = "a 2.3";
        String stackParameters = "a";
        Double expected = 4.6 / 2.3;

        DefineCommand define = new DefineCommand();
        define.executeCommand(map, stack, parameters);
        PushCommand push = new PushCommand();
        push.executeCommand(map, stack,stackParameters);
        parameters = "b 4.6";
        stackParameters = "b";
        define.executeCommand(map, stack, parameters);
        push.executeCommand(map, stack,stackParameters);

        DivCommand div = new DivCommand();
        div.executeCommand(map, stack, "");

        try {
            div.executeCommand(map, stack, "");
        } catch (DivisionByZeroException ex) {
            assertEquals("Division by zero", ex.getMessage());
        }
    }
}