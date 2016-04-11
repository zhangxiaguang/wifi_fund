package cn.agilean.wifi;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.ValidatableResponse;

public class AbstractRestAssuredTest {
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
