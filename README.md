# Проект по автоматизации тестирования для ["book-club"](https://book-club.qa.guru).

## :pushpin: Содержание:

- <a href="#tools">Технологии и инструменты</a>
- <a href="#jenkins">Сборка в Jenkins</a>
- <a href="#allure">Пример Allure-отчета</a>
- <a href="#telegram">Уведомление в Telegram при помощи бота</a>

<a id="tools"></a>
## :computer: Использованный стек технологий

<p align="center">
<img width="6%" title="IntelliJ IDEA" src="media/logo/Intelij_IDEA.svg">
<img width="6%" title="Java" src="media/logo/Java.svg">
<img width="6%" title="Selenide" src="media/logo/Selenide.svg">
<img width="6%" title="Selenoid" src="media/logo/Selenoid.svg">
<img width="6%" title="Allure Report" src="media/logo/Allure_Report.svg">
<img width="5%" title="Allure TestOps" src="media/logo/AllureTestOps.svg">
<img width="6%" title="Gradle" src="media/logo/Gradle.svg">
<img width="6%" title="JUnit5" src="media/logo/JUnit5.svg">
<img width="6%" title="GitHub" src="media/logo/GitHub.svg">
<img width="6%" title="Jenkins" src="media/logo/Jenkins.svg">
<img width="6%" title="Telegram" src="media/logo/Telegram.svg">
<img width="5%" title="Jira" src="media/logo/Jira.svg">
</p>

- В данном проекте автотесты написаны на языке <code>Java</code> с использованием фреймворка для тестирования Selenide.
- В качестве сборщика был использован - <code>Gradle</code>.
- Использованы фреймворки <code>JUnit 5</code> и [Selenide](https://selenide.org/).
- При прогоне тестов браузер запускается в [Selenoid](https://aerokube.com/selenoid/).
- Для удаленного запуска реализована джоба в <code>Jenkins</code> с формированием Allure-отчета и отправкой результатов в <code>Telegram</code> при помощи бота.

<a id="jenkins"></a>
## <img src="media/logo/Jenkins.svg" title="Jenkins" width="4%"/> Сборка в [Jenkins](https://www.jenkins.io).
<p align="center">
<img title="Jenkins Build" src="media/screens/Jenkins.PNG">
</p>

<a id="allure"></a>
## <img src="media/logo/Allure_Report.svg" title="Allure Report" width="4%"/> Пример [Allure-отчета](https://allurereport.org)
### Overview

<p align="center">
<img title="Allure Overview" src="media/screens/Allure.PNG">
</p>

### Результат выполнения теста

<p align="center">
<img title="Test Results in Alure" src="media/screens/Result.PNG">
</p>

## <img src="media/logo/AllureTestOps.svg" title="Allure TestOps" width="4%"/> Интеграция с [Allure TestOps](https://allure.autotests.cloud)

Выполнена интеграция сборки <code>Jenkins</code> с <code>Allure TestOps</code>.
Результат выполнения автотестов отображается в <code>Allure TestOps</code>
На Dashboard в <code>Allure TestOps</code> отображена статистика пройденных тестов.

<p align="center">
<img title="Allure TestOps DashBoard" src="media/screens/TestOps.PNG">
</p>

## <img src="media/logo/Jira.svg" title="Jira" width="4%"/> Интеграция с [Jira](https://jira.autotests.cloud)

Реализована интеграция <code>Allure TestOps</code> с <code>Jira</code>, в тикете отображается информация, какие тест-кейсы были написаны в рамках задачи и результат их прогона.

<p align="center">
<img title="Jira Task" src="media/screens/Jira.PNG">
</p>

<a id="telegram"></a>
## <img width="4%" style="vertical-align:middle" title="Telegram" src="media/logo/Telegram.svg"> Уведомления в Telegram с использованием бота

После завершения сборки, бот созданный в <code>Telegram</code>, автоматически обрабатывает и отправляет сообщение с результатом.

<p align="center">
<img width="70%" title="Telegram Notifications" src="media/screens/Telegram.PNG">
</p>