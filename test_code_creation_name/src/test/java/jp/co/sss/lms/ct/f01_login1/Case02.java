package jp.co.sss.lms.ct.f01_login1;

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
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {

		goTo("http://localhost:8080/lms/");

		webDriver.findElement(By.id("loginId"))
				.sendKeys("StudentAB99");

		webDriver.findElement(By.id("password"))
				.sendKeys("Test1234");

		webDriver.findElement(By.cssSelector("input[type='submit']"))
				.click();

		//エラーメッセージが表示されるまで待つ
		visibilityTimeout(
				By.cssSelector("span.help-inline.error"), 5);

		//画面に表示された文字を取得する
		String errorMessage = webDriver.findElement(
				By.cssSelector("span.help-inline.error"))
				.getText();

		System.out.println(errorMessage);

		//期待結果と実際の結果の確認
		assertEquals("* ログインに失敗しました。", errorMessage);

		//テスト結果の画面保存
		getEvidence(new Object() {
		}, "02_認証失敗");
	}

}
