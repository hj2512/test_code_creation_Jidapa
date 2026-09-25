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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
		// LMSのトップページにアクセスする
		goTo("http://localhost:8080/lms/");

		// ログイン画面が表示されていることを確認する
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.id("loginId")).isDisplayed());
		assertTrue(webDriver.findElement(By.id("password")).isDisplayed());
		assertTrue(webDriver.findElement(
				By.cssSelector("input[type='submit']")).isDisplayed());

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "05_01_ログイン画面");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインIDを入力する
		webDriver.findElement(By.id("loginId"))
				.sendKeys("StudentAA01");

		// パスワードを入力する
		webDriver.findElement(By.id("password"))
				.sendKeys("StudentAA0");

		// ログインボタンを押下する
		webDriver.findElement(
				By.cssSelector("input[type='submit']")).click();

		// 画面遷移を待機する
		visibilityTimeout(By.tagName("body"), 5);

		// コース詳細画面に遷移したことを確認する
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// ログイン画面が表示されていないことを確認する
		assertTrue(webDriver.findElements(By.id("loginId")).isEmpty());

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "05_02_コース詳細画面");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 上部メニューの「ヘルプ」を押下する
		webDriver.findElement(By.cssSelector(".dropdown-toggle")).click();

		webDriver.findElement(
				By.cssSelector("a[href='/lms/help']")).click();

		// ヘルプ画面の表示を待機する
		visibilityTimeout(By.tagName("h4"), 5);

		// ヘルプ画面が表示されたことを確認する
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.tagName("h2")).isDisplayed());

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "05_03_ヘルプ画面");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 「よくある質問」リンクを押下する
		webDriver.findElement(
				By.cssSelector("a[href='/lms/faq']")).click();

		// 新しく開いたタブに切り替える
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		// よくある質問画面の表示を待機する
		visibilityTimeout(By.tagName("h2"), 5);

		// よくある質問画面が表示されたことを確認する
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.tagName("h2")).isDisplayed());

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "05_04_よくある質問画面");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// キーワードを入力する
		webDriver.findElement(By.id("form"))
				.sendKeys("ログイン");

		// 検索ボタンを押下する
		webDriver.findElement(
				By.cssSelector("input[type='submit']")).click();

		// 検索結果の表示を待機する
		visibilityTimeout(By.cssSelector("table.sortabletable"), 5);

		// 検索結果が表示されていることを確認する
		assertTrue(webDriver.findElement(
				By.cssSelector("table.sortabletable")).isDisplayed());

		// 検索結果に「ログイン」が含まれていることを確認する
		assertTrue(webDriver.getPageSource().contains("ログイン"));

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "05_05_キーワード検索結果");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// 「クリア」ボタンを押下する
		webDriver.findElement(
				By.cssSelector("input[type='button']")).click();

		// キーワード入力欄が空になったことを確認する
		assertEquals("",
				webDriver.findElement(By.id("form"))
						.getAttribute("value"));

		// エビデンスを保存する
		getEvidence(new Object() {
		}, "05_06_クリア後");
	}

}
