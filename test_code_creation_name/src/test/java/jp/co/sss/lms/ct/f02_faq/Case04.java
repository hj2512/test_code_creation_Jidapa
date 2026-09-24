package jp.co.sss.lms.ct.f02_faq;

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

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		// トップページにアクセスする
		goTo("http://localhost:8080/lms/");

		// ログイン画面が表示されていることを確認する
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// ログインID入力欄が表示されていることを確認する
		assertTrue(webDriver.findElement(
				By.id("loginId")).isDisplayed());

		// パスワード入力欄が表示されていることを確認する
		assertTrue(webDriver.findElement(
				By.id("password")).isDisplayed());

		// ログインボタンが表示されていることを確認する
		assertTrue(webDriver.findElement(
				By.cssSelector("input[type='submit']")).isDisplayed());

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "04_01_ログイン画面");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインIDを入力する
		webDriver.findElement(
				By.id("loginId"))
				.sendKeys("StudentAA01");

		// パスワードを入力する
		webDriver.findElement(
				By.id("password"))
				.sendKeys("StudentAA0");

		// ログインボタンを押下する
		webDriver.findElement(
				By.cssSelector("input[type='submit']"))
				.click();

		// 画面遷移を待機する
		visibilityTimeout(By.tagName("body"), 5);

		// ログイン画面から遷移したことを確認する
		String currentUrl = webDriver.getCurrentUrl();

		assertFalse(currentUrl.endsWith("/lms/"));

		// ログイン画面が表示されていないことを確認する
		assertTrue(webDriver.findElements(
				By.id("loginId")).isEmpty());

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "04_02_ログイン後");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 「機能」プルダウンを押下する
		webDriver.findElement(
				By.cssSelector(".dropdown-toggle"))
				.click();

		// 「ヘルプ」を押下する
		webDriver.findElement(
				By.cssSelector("a[href='/lms/help']"))
				.click();

		// 画面遷移を待機する
		visibilityTimeout(By.tagName("h4"), 5);

		// ヘルプ画面に遷移したことを確認する
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		// ヘルプ画面の見出しが表示されていることを確認する
		assertTrue(webDriver.findElement(
				By.tagName("h2")).isDisplayed());

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "04_03_ヘルプ画面");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 「よくある質問」を押下する
		webDriver.findElement(
				By.cssSelector("a[href='/lms/faq']"))
				.click();

		// 別タブに切り替える
		Object[] windowHandles = webDriver.getWindowHandles().toArray();

		webDriver.switchTo().window(
				(String) windowHandles[1]);

		// 画面遷移を待機する
		visibilityTimeout(By.tagName("h2"), 5);

		// よくある質問画面に遷移したことを確認する
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// よくある質問画面の見出しが表示されていることを確認する
		assertTrue(webDriver.findElement(
				By.tagName("h2")).isDisplayed());

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "04_04_よくある質問画面");
	}
}
