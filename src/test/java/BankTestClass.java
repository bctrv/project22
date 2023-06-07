import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class BankTestClass {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    String generateDate(int daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void positiveTest() {
        $("[placeholder='Город']").setValue("Петрозаводск");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.YYYY"));
        $("[name='name']").setValue("Сергей Петров");
        $("[name='phone']").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(11000));
    }

    @Test
    void cityDateTest() {
        $("[placeholder='Город']").setValue("Пе");
        $(byText("Петрозаводск")).click();
        $("[data-test-id='date']").click();
        $(byText("30")).click();
        $("[name='name']").setValue("Сергей Петров");
        $("[name='phone']").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(11000));
    }


}
