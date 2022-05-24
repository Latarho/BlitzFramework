package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TopShotMarketplacePage {

    private SelenideElement
            topShotMarketplacePageText = $x("//*[@class=\"Text_h1__kFVci flex items-start text-2xl md:text-3xl\"]"),
            searchInput = $x("//*[@id='search' and contains (@placeholder, 'Search by player, team, set, series, tier, play...')]"),
            firstResultFromList = $x("//*[@id=\"react-autowhatever-1-section-0-item-0\"]"),
            firstResultFromSearch = $x("//*[@class=\"group-hover:text-gradient-mr mt-2.5 mb-0.5 text-sm font-semibold\"]");


    @Step("Открыть страницу торговой площадки TopShot")
    public TopShotMarketplacePage openPage() {
        open(baseUrl + "topshot/marketplace");

        return this;
    }

    @Step("Ввести имя игрока в поисковую строку")
    public TopShotMarketplacePage setPlayerName(String playerName) {
        searchInput.setValue(playerName);

        return this;
    }

    @Step("Выбрать первый элемент из выпадающего списка с результатами поиска")
    public TopShotMarketplacePage selectFirstResultFromList() {
        firstResultFromList.click();

        return this;
    }

    @Step("Проверить что в первом поисковом результате содержится имя игрока")
    public TopShotMarketplacePage checkFirstResultFromSearch(String playerName) {
        firstResultFromSearch.shouldHave(Condition.text(playerName));

        return this;
    }

    @Step("Проверить что открыта страница торговой площадки TopShot")
    public TopShotMarketplacePage checkPage() {
        topShotMarketplacePageText.shouldHave(Condition.text("NBA Top Shot Marketplace "));

        return this;
    }

    @Step("Проверить что заголовок страницы торговой площадки TopShot содержит корректную информацию")
    public TopShotMarketplacePage checkTitle() {
        String actualTitle = title();
        assertEquals("Marketplace Explorer for NBA Top Shot", actualTitle,
                "Заголовок страницы торговой площадки TopShot не соответсвует ожидаемому");

        return this;
    }
}