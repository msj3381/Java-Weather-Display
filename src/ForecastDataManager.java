import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ForecastDataManager {
	private static String TimeForecastTemperature;
	private static String TimeForecastTime;
	private static String TimeForecastIconLocation;
	private static Date TimeForecastDate = new Date();;
	private static SimpleDateFormat TimeForecastSimpleDataFormat = new SimpleDateFormat("aa h:mm", Locale.KOREA);
	
	ForecastDataManager(String time, String icon, String temperature) {
		TimeForecastDate.setTime((long) Long.parseLong(time) * 1000);
		TimeForecastTime = TimeForecastSimpleDataFormat.format(TimeForecastDate);
		
		TimeForecastTemperature = temperature;
		if (icon.equals("01d")) {
			TimeForecastIconLocation = "dayClearSky.png";
		} else if (icon.equals("02d")) {
			TimeForecastIconLocation = "dayFewClouds.png";
		} else if (icon.equals("03d")) {
			TimeForecastIconLocation = "allScatteredClouds.png";
		} else if (icon.equals("04d")) {
			TimeForecastIconLocation = "allBrokenClouds.png";
		} else if (icon.equals("09d")) {
			TimeForecastIconLocation = "allShowerRain.png";
		} else if (icon.equals("10d")) {
			TimeForecastIconLocation = "dayRain.png";
		} else if (icon.equals("11d")) {
			TimeForecastIconLocation = "allThunderStorm.png";
		} else if (icon.equals("13d")) {
			TimeForecastIconLocation = "allSnow.png";
		} else if (icon.equals("50d")) {
			TimeForecastIconLocation = "allMist.png";
		} else if (icon.equals("01n")) {
			TimeForecastIconLocation = "nightClearSky.png";
		} else if (icon.equals("02n")) {
			TimeForecastIconLocation = "nightFewClouds.png";
		} else if (icon.equals("03n")) {
			TimeForecastIconLocation = "allScatteredClouds.png";
		} else if (icon.equals("04n")) {
			TimeForecastIconLocation = "allBrokenClouds.png";
		} else if (icon.equals("09n")) {
			TimeForecastIconLocation = "allShowerRain.png";
		} else if (icon.equals("10n")) {
			TimeForecastIconLocation =  "nightRain.png";
		} else if (icon.equals("11n")) {
			TimeForecastIconLocation = "allThunderStorm.png";
		} else if (icon.equals("13n")) {
			TimeForecastIconLocation = "allSnow.png";
		} else if (icon.equals("50n")) {
			TimeForecastIconLocation = "allMist.png";
		}
	}
	
	public static String getTime() {
		return TimeForecastTime;
	}
	
	public static String getIconLocation() {
		return TimeForecastIconLocation;
	}
	
	public static String getTemperature() {
		return TimeForecastTemperature;
	}
}
