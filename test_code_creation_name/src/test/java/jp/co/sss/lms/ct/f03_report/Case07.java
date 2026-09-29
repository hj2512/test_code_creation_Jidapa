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
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		assertTrue(webDriver.findElement(
				By.cssSelector("input[type='submit']")).isDisplayed());

		getEvidence(new Object() {
		}, "07_01_ログイン画面");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		webDriver.findElement(By.id("loginId"))
				.sendKeys("StudentAA01");

		webDriver.findElement(By.id("password"))
				.sendKeys("StudentAA2345");

		webDriver.findElement(
				By.cssSelector("input[type='submit']")).click();

		visibilityTimeout(By.tagName("body"), 5);

		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		assertTrue(webDriver.findElements(By.id("loginId")).isEmpty());

		getEvidence(new Object() {
		}, "07_02_コース詳細画面");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 「未提出」の行にある「詳細」ボタンを取得
		WebElement detailButton = webDriver.findElement(
				By.xpath("//span[text()='未提出']/ancestor::tr//input[@value='詳細']"));

		// 「詳細」ボタンを押下
		detailButton.click();

		visibilityTimeout(By.tagName("body"), 5);

		// セクション詳細画面に遷移したことを確認
		assertTrue(webDriver.getPageSource().contains("セクション詳細"));

		getEvidence(new Object() {
		}, "07_03_セクション詳細画面");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「日報を提出する」ボタンを取得
		WebElement reportButton = webDriver.findElement(
				By.xpath("//input[contains(@value,'を提出する')]"));

		// 「日報を提出する」ボタンを押下
		reportButton.click();

		visibilityTimeout(By.tagName("body"), 5);

		// レポート登録画面が表示されたことを確認
		assertTrue(webDriver.getPageSource().contains("レポート"));

		getEvidence(new Object() {
		}, "07_04_レポート登録画面");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// 報告内容を入力
		WebElement reportContent = webDriver.findElement(
				By.cssSelector("textarea[id^='content_']"));
		reportContent.sendKeys("本日の研修内容を確認しました。");

		// 「提出する」ボタンをクリック
		webDriver.findElement(
				By.xpath("//button[@type='submit' and text()='提出する']"))
				.click();

		visibilityTimeout(By.tagName("body"), 5);

		// セクション詳細画面に戻ったことを確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// 「提出済み〇〇を確認する」ボタンが表示されていることを確認
		WebElement submittedButton = webDriver.findElement(
				By.xpath("//input[contains(@value,'提出済み') and contains(@value,'を確認する')]"));

		assertTrue(submittedButton.isDisplayed());

		// ボタン名に「提出済み」が含まれていることを確認
		assertTrue(submittedButton.getAttribute("value").contains("提出済み"));

		getEvidence(new Object() {
		}, "07_05_提出後");
	}

}
