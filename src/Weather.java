import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Weather extends Thread {

	private static HttpURLConnection weatherConnection;
	private static BufferedReader weatherBufferedReader;
	private static String weatherLine;
	private static StringBuffer weatherResponseContent;
	private static URL weatherURL;
	private static int weatherConnectionStatus;

	private static JSONParser weatherJSONParser;
	private static JSONObject weatherJSONObject;

	private static JSONObject weatherMainJSONObject;
	private static JSONArray weatherWeatherJSONArray;
	private static JSONObject weatherWeatherJSONObject;
	private static JSONObject weatherSysJSONObject;
	private static JSONObject weatherWindJSONObject;

	private static String weatherBackgroundLocation;
	private static String weatherIconLocation;

	private static SimpleDateFormat weatherUpdatedSimpleDateFormat;
	private static Date weatherUpdatedDate;

	Weather() {
		this.start();
	}

	public void run() {
		try {
			while (true) {
				weatherURL = new URL(
						"http://api.openweathermap.org/data/2.5/weather?id=1832157&appid=44d9c5468dc017fac0d3dfcb1acd4f30&units=metric&lang=kr");
				weatherConnection = (HttpURLConnection) weatherURL.openConnection();
				weatherResponseContent = new StringBuffer();
				weatherConnectionStatus = weatherConnection.getResponseCode();

				weatherConnection = (HttpURLConnection) weatherURL.openConnection();
				weatherConnection.setRequestMethod("GET");
				weatherConnection.setConnectTimeout(5000);
				weatherConnection.setReadTimeout(5000);

				if (weatherConnectionStatus > 299) {
					weatherBufferedReader = new BufferedReader(
							new InputStreamReader(weatherConnection.getErrorStream()));

					while ((weatherLine = weatherBufferedReader.readLine()) != null) {
						weatherResponseContent.append(weatherLine);
					}

					weatherBufferedReader.close();
				} else {
					weatherBufferedReader = new BufferedReader(
							new InputStreamReader(weatherConnection.getInputStream()));

					while ((weatherLine = weatherBufferedReader.readLine()) != null) {
						weatherResponseContent.append(weatherLine);
					}
					weatherBufferedReader.close();
					translateJSON(weatherResponseContent.toString());
					System.out.println(weatherResponseContent.toString());
				}

				weatherURL = null;
				weatherConnection = null;
				weatherBufferedReader = null;
				weatherLine = null;
				weatherResponseContent = null;
				weatherConnectionStatus = 0;

				Thread.sleep(1000);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void translateJSON(String beforeTranslated) throws ParseException {
		weatherJSONParser = new JSONParser();

		weatherJSONObject = (JSONObject) weatherJSONParser.parse(beforeTranslated);

		weatherMainJSONObject = (JSONObject) weatherJSONObject.get("main");
		weatherWeatherJSONArray = (JSONArray) weatherJSONObject.get("weather");
		weatherWeatherJSONObject = (JSONObject) weatherWeatherJSONArray.get(0);
		weatherSysJSONObject = (JSONObject) weatherJSONObject.get("sys");
		weatherWindJSONObject = (JSONObject) weatherJSONObject.get("wind");

		System.out.println(weatherMainJSONObject.get("temp"));
		System.out.println(weatherMainJSONObject.get("feels_like"));
		System.out.println(weatherMainJSONObject.get("temp_min"));
		System.out.println(weatherMainJSONObject.get("temp_max"));
		System.out.println(weatherMainJSONObject.get("pressure"));
		System.out.println(weatherMainJSONObject.get("humidity"));
		System.out.println(weatherMainJSONObject.get("sea_level"));
		System.out.println(weatherMainJSONObject.get("grnd_level"));

		System.out.println(weatherSysJSONObject.get("sunrise"));
		System.out.println(weatherSysJSONObject.get("sunset"));

		System.out.println(weatherJSONObject.get("visibility"));

		System.out.println(weatherWeatherJSONObject.get("icon"));
		System.out.println(weatherWeatherJSONObject.get("description"));

		if (weatherWeatherJSONObject.get("icon").toString().equals("01d")) {
			weatherBackgroundLocation = "daySunBackground.png";
			weatherIconLocation = "dayClearSky.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("02d")) {
			weatherBackgroundLocation = "dayCloudBackground.png";
			weatherIconLocation = "dayFewClouds.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("03d")) {
			weatherBackgroundLocation = "dayCloudBackground.png";
			weatherIconLocation = "allScatteredClouds.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("04d")) {
			weatherBackgroundLocation = "dayBrokenCloudBackground.png";
			weatherIconLocation = "allBrokenClouds.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("09d")) {
			weatherBackgroundLocation = "dayShowerRainBackground.png";
			weatherIconLocation = "allShowerRain.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("10d")) {
			weatherBackgroundLocation = "dayRainBackground.png";
			weatherIconLocation = "dayRain.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("11d")) {
			weatherBackgroundLocation = "dayThunderStormBackground.png";
			weatherIconLocation = "allThunderStorm.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("13d")) {
			weatherBackgroundLocation = "daySnowBackground.png";
			weatherIconLocation = "allSnow.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("50d")) {
			weatherBackgroundLocation = "dayMistBackground.png";
			weatherIconLocation = "allMist.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("01n")) {
			weatherBackgroundLocation = "nightSunBackground.png";
			weatherIconLocation = "nightClearSky.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("02n")) {
			weatherBackgroundLocation = "nightCloudBackground.png";
			weatherIconLocation = "nightFewClouds.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("03n")) {
			weatherBackgroundLocation = "nightCloudBackground.png";
			weatherIconLocation = "allScatteredClouds.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("04n")) {
			weatherBackgroundLocation = "nightBrokenCloudBackground.png";
			weatherIconLocation = "allBrokenClouds.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("09n")) {
			weatherBackgroundLocation = "nightShowerRainBackground.png";
			weatherIconLocation = "allShowerRain.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("10n")) {
			weatherBackgroundLocation = "nightRainBackground.png";
			weatherIconLocation = "nightRain.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("11n")) {
			weatherBackgroundLocation = "nightThunderStormBackground.png";
			weatherIconLocation = "allThunderStorm.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("13n")) {
			weatherBackgroundLocation = "nightSnowBackground.png";
			weatherIconLocation = "allSnow.png";
		} else if (weatherWeatherJSONObject.get("icon").toString().equals("50n")) {
			weatherBackgroundLocation = "nightMistBackground.png";
			weatherIconLocation = "allMist.png";
		}

		Frame.changeWeatherImages(weatherBackgroundLocation, weatherIconLocation);

		weatherUpdatedDate = new Date();
		weatherUpdatedDate.setTime((long) weatherJSONObject.get("dt") * 1000);
		weatherUpdatedSimpleDateFormat = new SimpleDateFormat("aa hh:mm:ss", Locale.KOREA);

		System.out.println(weatherUpdatedSimpleDateFormat.format(weatherUpdatedDate));

		Frame.setWeatherInformation(weatherMainJSONObject.get("temp").toString(),
				weatherMainJSONObject.get("feels_like").toString(), weatherMainJSONObject.get("temp_min").toString(),
				weatherMainJSONObject.get("temp_max").toString(), weatherMainJSONObject.get("humidity").toString(),
				weatherMainJSONObject.get("pressure").toString(), weatherJSONObject.get("visibility").toString(),
				weatherWeatherJSONObject.get("description").toString(), weatherSysJSONObject.get("sunrise").toString(),
				weatherSysJSONObject.get("sunset").toString(), weatherWindJSONObject.get("speed").toString(),
				weatherWindJSONObject.get("deg").toString(), weatherWindJSONObject.get("gust").toString(),
				weatherJSONObject.get("dt").toString());

		weatherJSONParser = null;
		weatherJSONObject = null;
		weatherMainJSONObject = null;
		weatherWeatherJSONArray = null;
		weatherWeatherJSONObject = null;
		weatherUpdatedDate = null;
		weatherUpdatedSimpleDateFormat = null;
		weatherSysJSONObject = null;
		weatherWindJSONObject = null;

	}
}
