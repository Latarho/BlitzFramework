package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPage {

    private SelenideElement
            mainPageText = $x("//*[@class=\"Text_h1__kFVci mb-4 text-3xl md:mt-12 xl:text-5xl\"]"),
            searchInput = $x("//*[@id='search']"),
            firstResultFromList = $x("//*[@id=\"react-autowhatever-1-section-1-item-0\"]"),
            firstResultFromSearch = $x("//*[@class=\"text-2xl font-semibold\"]"),
            wrongResultFromSearch = $x("//*[@class=\"MuiTypography-root Error_help__g_M6c MuiTypography-h5\"]");

    @Step("Открыть главную страницу портала MomentRanks")
    public MainPage openPage() {
        open(baseUrl);

        return this;
    }

    @Step("Проверить что открыта главная страница портала MomentRanks")
    public MainPage checkPage() {
        mainPageText.shouldHave(Condition.text("Discover, track, and analyze NFTs across the ecosystem"));

        return this;
    }

    @Step("Ввести имя коллекционера в поисковую строку")
    public MainPage setUserName(String userName) {
        searchInput.setValue(userName);

        return this;
    }

    @Step("Ввести имя коллекционера в поисковую строку и нажать Enter")
    public MainPage setUserNameAndPressEnter(String userName) {
        searchInput.setValue(userName).pressEnter();

        return this;
    }

    @Step("Выбрать первый элемент из выпадающего списка с результатами поиска")
    public MainPage selectFirstResultFromList() {
        firstResultFromList.click();

        return this;
    }

    @Step("Проверить что в первом поисковом результате содержится имя коллекционера")
    public MainPage checkFirstResultFromSearch(String userName) {
        String actualUserName = firstResultFromSearch.getText();
        assertEquals(userName, actualUserName, "Искомое имя коллекционера не соответсвует результату поиска");

        return this;
    }

    @Step("Проверить что в поисковом результате содержится сообщение об ошибке (коллекционер не найден)")
    public MainPage checkWrongResultFromSearch(String wrongUserName) {
        String actualWrongMessage = wrongResultFromSearch.getText();
        assertEquals("Oops! \"" + wrongUserName + "\" was not found. If you believe this is an error, " +
                "please refresh the page. New accounts may take a few hours to index.", actualWrongMessage,
                "Сообщение об ошибке не соответсвует ожидаемому");

        return this;
    }

    @Step("Проверить что заголовок главной страницы портала MomentRanks содержит корректную информацию")
    public MainPage checkTitle() {
        String actualTitle = title();
        assertEquals("MomentRanks | The Premier NFT Resource", actualTitle,
                "Заголовок главной страницы не соответсвует ожидаемому");

        return this;
    }
}