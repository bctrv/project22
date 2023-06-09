import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
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
        String planningDate = generateDate(3, "dd.MM.YYYY");
        $("[data-test-id='city'] input").setValue("Петрозаводск");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[name='name']").setValue("Сергей Петров");
        $("[name='phone']").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void cityDateTest() {
        String city = "Петрозаводск";
        int daysToAdd = 7;
        int defaultDaysAdded = 3;
        $("[data-test-id='city'] input").setValue("Пе");
        $$("[class=menu-item__control]").findBy(text(city)).click();
        $("[data-test-id='date'] input").click();
        $$("[class=calendar__day]");
        if (!generateDate(defaultDaysAdded, "MM").equals(generateDate(daysToAdd, "MM"))) {
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(text(generateDate(daysToAdd, "d"))).click();
        $("[name='name']").setValue("Сергей Петров");
        $("[name='phone']").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + generateDate(daysToAdd, "dd.MM.yyyy")));
    }
}
