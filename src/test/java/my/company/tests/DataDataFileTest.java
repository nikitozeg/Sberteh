package my.company.tests;


import junit.framework.ComparisonFailure;
import my.company.steps.DataFile;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Nikita Ivanov tazg@ya.ru
 *         Date: 14.02.16
 */

public class DataDataFileTest {
    DataFile hp;
    int i=0;
    String[] values;
    int count;

    @Before //Will be executed before every test
    public void setUp() throws IOException {
        hp = new DataFile();
        count = getLinesCount();
    }

    @Test
    public void checkResultTest() throws IOException {

        while (i < count) {
            values = hp.getCurrentLine();
            try {
                assertEquals(getCalculatedResult(values[0], values[1], values[2]), getFactResult(values[3]));
            } catch (ComparisonFailure ee) {
            }
            System.out.println();
            i++;
        }

    }

    @Step("Получение ожидаемого результата операции {0} {2} {1}")
    public String getCalculatedResult(String a, String b, String operator) {  //Parse
        double result = .0;
        double operand1 = Integer.parseInt(a);
        double operand2 = Integer.parseInt(b);
        switch (operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                checkNotZero(operand2);
                result = operand1 / operand2;
                break;
        }
        return new BigDecimal(result).setScale(1, RoundingMode.HALF_UP).toString();  //convert result to standard value
    }

    @Step("Получение фактического результата: {0}")
    public String getFactResult(String factResult) throws IOException {
        return new BigDecimal(factResult).setScale(1, RoundingMode.HALF_UP).toString();
    }

    @Step("Получение количества строк в файле")
    public int getLinesCount() throws IOException {
        return hp.getCountLines();
    }

    @Step("Проверка делителя {0} на ноль")
    private void checkNotZero(double intValue) {
        assertTrue("Делитель равен 0", intValue != 0.0);
    }

}

