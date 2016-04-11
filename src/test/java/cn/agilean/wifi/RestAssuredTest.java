package cn.agilean.wifi;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.FormAuthConfig;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.ValidatableResponse;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
/**
 * 
 * @author zxg
 *	PS:TestNG、登录认证form方式
 *		
 *	
 */
public class RestAssuredTest {

	@BeforeClass
	public void beforeClass() {
		RestAssured.baseURI = "http://mock-api.com/";
		RestAssured.port = 80;
		RestAssured.basePath = "";
		RestAssured.rootPath = "";
	}

	@Test
	public void should_return_json_str_and_id_equal_1_by_get_url() {
		JsonPath jsonStr = getAsJson("/J-B51SQ3MKjU1h07.scene/web");
		Assert.assertEquals(jsonStr.getString("id"), "1");
		Assert.assertEquals(jsonStr.getString("name"), "zxgjava-1");
	}
	
	@Test
	public void should_return_json_str_and_id_equal_1() {
		given().header("foo", "bar").when().get("/J-B51SQ3MKjU1h07.scene/web")
		.then().assertThat().body("id", equalTo("1"));
	}

	@Test
	public void should_return_json_str_and_id_equal_2_by_post_url() {
		JSONObject json = new JSONObject();
		json.put("id", "2");
		JsonPath jsonStr = postAndReturnJson("/KQGSGPbIGxvW1fcp.mock/web", json);
		Assert.assertEquals(jsonStr.getString("id"), "2");
		Assert.assertEquals(jsonStr.getString("name"), "zxgjava-2");
	}

	@Test
	public void should_return_json_str_and_id_equal_3_by_put_url() {
		JSONObject json = new JSONObject();
		json.put("id", "3");
		JsonPath jsonStr = putAndReturnJson("/KQGSGPbIGxvW1fcp.mock/web", json);
		Assert.assertEquals(jsonStr.getString("id"), "3");
		Assert.assertEquals(jsonStr.getString("name"), "zxgjava-3");
	}

	@Test
	public void should_return_result_equal_1_by_delete_url() {
		JsonPath jsonStr = deleteAndReturnJson("/KQGSGPbIGxvW1fcp.mock/web");
		Assert.assertEquals(jsonStr.getString("result"), "1");
	}

	@Test
	public void should_return_status_200() {
		FormAuthConfig formConfig = new FormAuthConfig("/login", "username",
				"password");
		given().contentType("application/json;charset=utf-8")
				.auth()
				.form("admin", "admin",
						formConfig.withLoggingEnabled()).when()
				.get("/mockers/33").then().assertThat().statusCode(200);
	}

	@Test
	public void should_return_status_401() {
		given().contentType("application/json;charset=utf-8")
				.cookie("JSESSIONID", "417347C7BFF68BFF34B241387CC2B4A4")
				.when()
				.get("/mockers/33").then().assertThat().statusCode(401);
	}

	@Test
	public void should_enable_login() {
		FormAuthConfig formConfig = new FormAuthConfig("/login", "username",
				"password");
		given().contentType("application/json;charset=utf-8")
				.auth()
				.form("admin", "admin",
						formConfig.withLoggingEnabled()).when()
				.get("http://mock-api.com/#!/mocker-dashboard").then()
				.assertThat().statusCode(200);
	}

	protected ValidatableResponse post(String url, Object body) {
		return given().header("foo", "bar").and()
				.contentType("application/json;charset=utf-8").body(body)
				.when().post(url).then();
	}

	protected ValidatableResponse put(String url, Object body) {
		return given().header("foo", "bar")
				.contentType("application/json;charset=utf-8").body(body)
				.when().put(url).then();
	}

	protected ValidatableResponse delete(String url) {
		return given().header("foo", "bar")
				.contentType("application/json;charset=utf-8").when()
				.delete(url).then();
	}

	protected JsonPath postAndReturnJson(String url, Object body) {
		ValidatableResponse response = post(url, body);
		return response.extract().jsonPath();
	}

	protected ValidatableResponse postTextAndReturnJson(String url, String body) {
		return given().header("foo", "bar").and()
				.contentType("text/plain;charset=utf-8").body(body).when()
				.post(url).then();
	}

	protected JsonPath putAndReturnJson(String url, Object body) {
		ValidatableResponse response = put(url, body);
		return response.extract().jsonPath();
	}

	protected JsonPath deleteAndReturnJson(String url) {
		ValidatableResponse response = delete(url);
		return response.extract().jsonPath();
	}

	protected ValidatableResponse getFrom(String url) {
		return given().header("foo", "bar").when().get(url).then();
	}

	protected JsonPath getAsJson(String url) {
		return getFrom(url).extract().body().jsonPath();
	}

	protected String getAsString(String url) {
		return getFrom(url).extract().body().asString();
	}
}
