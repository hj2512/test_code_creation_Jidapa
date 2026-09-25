package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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

		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.id("loginId")).isDisplayed());
		assertTrue(webDriver.findElement(By.id("password")).isDisplayed());
		assertTrue(webDriver.findElement(By.cssSelector("input[type='submit']")).isDisplayed());

		getEvidence(new Object() {
		}, "06_01_ログイン画面");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA0");
		webDriver.findElement(By.cssSelector("input[type='submit']")).click();

		visibilityTimeout(By.tagName("body"), 5);

		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElements(By.id("loginId")).isEmpty());

		getEvidence(new Object() {
		}, "06_02_コース詳細画面");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		webDriver.findElement(By.cssSelector(".dropdown-toggle")).click();
		webDriver.findElement(By.cssSelector("a[href='/lms/help']")).click();

		visibilityTimeout(By.tagName("h4"), 5);

		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.tagName("h2")).isDisplayed());

		getEvidence(new Object() {
		}, "06_03_ヘルプ画面");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		webDriver.findElement(By.cssSelector("a[href='/lms/faq']")).click();

		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		visibilityTimeout(By.tagName("h2"), 5);

		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.tagName("h2")).isDisplayed());

		getEvidence(new Object() {
		}, "06_04_よくある質問画面");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索結果を表示")
	void test05() {
		// カテゴリを選択
		List<WebElement> categoryLinks = webDriver
				.findElements(By.cssSelector("fieldset a[href*='frequentlyAskedQuestionCategoryId']"));

		// カテゴリが表示されていることを確認
		assertFalse(categoryLinks.isEmpty());

		// 最初のカテゴリをクリック
		categoryLinks.get(0).click();

		visibilityTimeout(By.cssSelector("table.sortabletable"), 5);

		// 検索結果が表示されていることを確認
		assertTrue(webDriver.findElement(By.cssSelector("table.sortabletable")).isDisplayed());

		getEvidence(new Object() {
		}, "06_05_カテゴリ検索結果");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// 最初の質問を取得
		WebElement question = webDriver.findElement(By.cssSelector("dl[id^='question-h'] dt"));

		// 質問が見える位置までスクロール
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block: 'center'});", question);

		// 質問をクリック
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].click();", question);

		// 回答を取得
		WebElement answer = webDriver.findElement(By.cssSelector("dd[id^='answer-h']"));

		// 回答が表示されていることを確認
		assertTrue(answer.isDisplayed());

		getEvidence(new Object() {
		}, "06_06_回答表示");

	}
}
