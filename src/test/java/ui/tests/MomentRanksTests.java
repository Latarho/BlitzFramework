package ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MomentRanksTests extends TestBase {

    public final String userName = "sambist";
    public final String wrongUserName = "dshgshgi";
    public final String playerName = "Serge Ibaka";

    @Test
    @Tag("ui")
    @Description("Тест заголовок главной страницы")
    @DisplayName("Проверка заголовка главной страницы")
    void checkMainPageTitle() {
        mainPage.openPage()
                .checkTitle();
    }

    @Test
    @Tag("ui")
    @Description("Тест элемент Поиск (валидное имя коллекционера)")
    @DisplayName("Поиск коллекционера по имени, валидное имя: " + userName)
    void searchUserNameAtMomentRanks() {
        mainPage.openPage()
                .checkPage()
                .setUserName(userName)
                .selectFirstResultFromList()
                .checkFirstResultFromSearch(userName);
    }

    @Test
    @Tag("ui")
    @Description("Тест элемент Поиск (невалидное имя коллекционера)")
    @DisplayName("Поиск коллекционера по имени, невалидное имя: " + wrongUserName)
    void searchWrongUserNameAtMomentRanks() {
        mainPage.openPage()
                .checkPage()
                .setUserNameAndPressEnter(wrongUserName)
                .checkWrongResultFromSearch(wrongUserName);
    }

    @Test
    @Tag("ui")
    @Description("Тест заголовок страницы торговой площадки TopShot")
    @DisplayName("Проверка заголовка страницы торговой площадки TopShot")
    void checkTopShotMarketplacePageTitle() {
        topShotMarketplacePage.openPage()
                .checkTitle();
    }

    @Test
    @Tag("ui")
    @Description("Тест элемент Поиск (валидное имя игрока)")
    @DisplayName("Поиск игрока на торговой площадке по имени, валидное имя: " + playerName)
    void searchPlayerNameAtMarketPlace() {
        topShotMarketplacePage.openPage()
                .checkPage()
                .setPlayerName(playerName)
                .selectFirstResultFromList()
                .checkFirstResultFromSearch(playerName);
    }
}