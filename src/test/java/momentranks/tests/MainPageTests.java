package momentranks.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class MainPageTests extends TestBase {
    @BeforeEach
    public void beforeEach() {
        step("Открыть 'https://momentranks.com/'", () ->
                open("https://momentranks.com/"));
    }

    @Test
    @Description("Тест элемента Поиск")
    @DisplayName("Успешный поиск коллекционера")
    void searchInputTest() {
        step("Ввести в поисковую строку 'sambist'", () -> {
            $x("//*[@id='search']").setValue("sambist");
        });

        step("Выбрать первый элемент из выпадающего списка с результатами поиска", () -> {
            $x("//*[@id=\"react-autowhatever-1-section-1-item-0\"]").click();
        });

        step("Проверить что найден коллекционер 'sambist'", () -> {
            String actualUserName = $x("//*[@class=\"text-2xl font-semibold\"]").getText();
            actualUserName.equals("latarho");
        });
    }
}
