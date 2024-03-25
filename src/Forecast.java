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

public class Forecast extends Thread {

	private static HttpURLConnection timeForecastConnection;
	private static BufferedReader timeForecastBufferedReader;
	private static String timeForecastLine;
	private static StringBuffer timeForecastResponseContent;
	private static URL timeForecastURL;
	private static int timeForecastConnectionStatus;

	private static JSONParser timeForecastJSONParser;
	private static JSONObject timeForecastJSONObject;

	private static JSONObject timeForecastMainJSONObject;
	private static JSONArray timeForecastWeatherJSONArray;
	private static JSONObject timeForecastWeatherJSONObject;
	private static JSONObject timeForecastSysJSONObject;
	private static JSONObject timeForecastWindJSONObject;

	private static SimpleDateFormat timeForecastUpdatedSimpleDateFormat;
	private static Date timeForecastUpdatedDate;

	Forecast() {
		this.start();
	}

	public void run() {
		try {
			while (true) {
				timeForecastURL = new URL(
						"https://api.openweathermap.org/data/2.5/onecall?lat=34.7442&lon=127.7378&exclude=alerts&appid=44d9c5468dc017fac0d3dfcb1acd4f30&units=metric&lang=kr");
				timeForecastConnection = (HttpURLConnection) timeForecastURL.openConnection();
				timeForecastResponseContent = new StringBuffer();
				timeForecastConnectionStatus = timeForecastConnection.getResponseCode();

				timeForecastConnection = (HttpURLConnection) timeForecastURL.openConnection();
				timeForecastConnection.setRequestMethod("GET");
				timeForecastConnection.setConnectTimeout(5000);
				timeForecastConnection.setReadTimeout(5000);

				if (timeForecastConnectionStatus > 299) {
					timeForecastBufferedReader = new BufferedReader(
							new InputStreamReader(timeForecastConnection.getErrorStream()));

					while ((timeForecastLine = timeForecastBufferedReader.readLine()) != null) {
						timeForecastResponseContent.append(timeForecastLine);
					}

					timeForecastBufferedReader.close();
				} else {
					timeForecastBufferedReader = new BufferedReader(
							new InputStreamReader(timeForecastConnection.getInputStream()));

					while ((timeForecastLine = timeForecastBufferedReader.readLine()) != null) {
						timeForecastResponseContent.append(timeForecastLine);
					}
					timeForecastBufferedReader.close();
					// translateJSON(timeForecastResponseContent.toString());
					System.out.println(timeForecastResponseContent.toString());
				}

				timeForecastURL = null;
				timeForecastConnection = null;
				timeForecastBufferedReader = null;
				timeForecastLine = null;
				timeForecastResponseContent = null;
				timeForecastConnectionStatus = 0;

				Thread.sleep(1000);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			// } catch (ParseException e) {
			// e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void translateJSON(String beforeTranslated) throws ParseException {
		timeForecastJSONParser = new JSONParser();

		timeForecastJSONObject = (JSONObject) timeForecastJSONParser.parse(beforeTranslated);

		timeForecastMainJSONObject = (JSONObject) timeForecastJSONObject.get("main");
		timeForecastSysJSONObject = (JSONObject) timeForecastJSONObject.get("sys");
		timeForecastWindJSONObject = (JSONObject) timeForecastJSONObject.get("wind");

		System.out.println(timeForecastMainJSONObject.get("temp"));
		System.out.println(timeForecastMainJSONObject.get("feels_like"));
		System.out.println(timeForecastMainJSONObject.get("temp_min"));
		System.out.println(timeForecastMainJSONObject.get("temp_max"));
		System.out.println(timeForecastMainJSONObject.get("pressure"));
		System.out.println(timeForecastMainJSONObject.get("humidity"));
		System.out.println(timeForecastMainJSONObject.get("sea_level"));
		System.out.println(timeForecastMainJSONObject.get("grnd_level"));

		System.out.println(timeForecastSysJSONObject.get("sunrise"));
		System.out.println(timeForecastSysJSONObject.get("sunset"));

		System.out.println(timeForecastJSONObject.get("visibility"));

		timeForecastUpdatedDate = new Date();
		timeForecastUpdatedDate.setTime((long) timeForecastJSONObject.get("dt") * 1000);
		timeForecastUpdatedSimpleDateFormat = new SimpleDateFormat("aa hh:mm:ss", Locale.KOREA);

		System.out.println(timeForecastUpdatedSimpleDateFormat.format(timeForecastUpdatedDate));

		timeForecastJSONParser = null;
		timeForecastJSONObject = null;
		timeForecastMainJSONObject = null;
		timeForecastUpdatedDate = null;
		timeForecastUpdatedSimpleDateFormat = null;
		timeForecastSysJSONObject = null;
		timeForecastWindJSONObject = null;

	}

}
