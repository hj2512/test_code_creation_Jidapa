package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:8080/lms/");

		// ログイン画面が表示されていることを確認
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.id("loginId")).isDisplayed());
		assertTrue(webDriver.findElement(By.id("password")).isDisplayed());
		assertTrue(webDriver.findElement(
				By.cssSelector("input[type='submit']")).isDisplayed());

		getEvidence(new Object() {
		}, "08_01_ログイン画面");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインIDを入力
		webDriver.findElement(By.id("loginId"))
				.sendKeys("StudentAA01");

		// パスワードを入力
		webDriver.findElement(By.id("password"))
				.sendKeys("StudentAA2345");

		// ログインボタンを押下
		webDriver.findElement(
				By.cssSelector("input[type='submit']")).click();

		visibilityTimeout(By.tagName("body"), 5);

		// コース詳細画面に遷移したことを確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElements(By.id("loginId")).isEmpty());

		getEvidence(new Object() {
		}, "08_02_コース詳細画面");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 「提出済」を含む行の「詳細」ボタンを取得
		WebElement detailButton = webDriver.findElement(
				By.xpath("//tr[contains(.,'提出済')]//input[@value='詳細']"));

		// 「詳細」ボタンを押下
		detailButton.click();

		visibilityTimeout(By.tagName("body"), 5);

		// セクション詳細画面に遷移したことを確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		}, "08_03_セクション詳細画面");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「提出済み〇〇を確認する」ボタンを取得
		WebElement reportButton = webDriver.findElement(
				By.xpath("//input[contains(@value,'提出済み') and contains(@value,'確認する')]"));

		// ボタンを押下
		reportButton.click();

		visibilityTimeout(By.tagName("body"), 5);

		// レポート登録画面に遷移したことを確認
		assertTrue(webDriver.getPageSource().contains("レポート"));

		getEvidence(new Object() {
		}, "08_04_レポート登録画面");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 報告内容を取得
		WebElement reportContent = webDriver.findElement(
				By.cssSelector("textarea[id^='content_']"));

		// 既存の報告内容を削除
		reportContent.clear();

		// 報告内容を修正
		reportContent.sendKeys("修正した週報の内容です。");

		// 「提出する」ボタンを押下
		webDriver.findElement(
				By.xpath("//button[@type='submit' and text()='提出する']"))
				.click();

		visibilityTimeout(By.tagName("body"), 5);

		// セクション詳細画面に戻ったことを確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		}, "08_05_修正後セクション詳細画面");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		WebElement welcomeLink = webDriver.findElement(
				By.xpath("//a[contains(.,'ようこそ')]"));

		welcomeLink.click();

		visibilityTimeout(By.tagName("body"), 5);

		assertEquals("ユーザー詳細", webDriver.getTitle());

		getEvidence(new Object() {
		}, "08_06_ユーザー詳細画面");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// ユーザー詳細画面にある「詳細」ボタンを取得
		java.util.List<WebElement> detailButtons = webDriver.findElements(
				By.xpath("//input[@value='詳細']"));

		// 「詳細」ボタンが存在することを確認
		assertFalse(detailButtons.isEmpty());

		// 最後の「詳細」ボタンを押下
		WebElement detailButton = detailButtons.get(detailButtons.size() - 1);

		((org.openqa.selenium.JavascriptExecutor) webDriver)
				.executeScript(
						"arguments[0].scrollIntoView({block: 'center'});",
						detailButton);

		detailButton.click();

		visibilityTimeout(By.tagName("body"), 5);

		// レポート詳細画面に遷移したことを確認
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());

		// 修正した内容が表示されていることを確認
		assertTrue(webDriver.getPageSource()
				.contains("修正した週報の内容です。"));

		getEvidence(new Object() {
		}, "08_07_レポート詳細画面");
	}

}
