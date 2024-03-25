import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 프레임(Frame) 관리 클래스
 */

public class Frame {

	private static String weatherIconLocation = "allMist.png";
	private JLabel currentTimeLabel = new JLabel();
	private Image bufferedImage;
	private Graphics bufferedGraphics;
	private JPanel defaultPanel = new JPanel();
	private JPanel weatherPanel = new JPanel();
	private JPanel firstPanel = new JPanel();
	private JPanel selectingPanel = new JPanel();
	private JButton yesButton = new JButton("예");
	private JButton noButton = new JButton("아니오");
	private JLabel weatherTemperatureLabel = new JLabel("0.0");
	private JLabel weatherFeelsLikeLabel = new JLabel("체감 기온 | 0.0 °C");
	private JLabel weatherDegreeCelsiusLabel = new JLabel("°C");
	private JLabel weatherHumidityLabel = new JLabel("습도 | 0 %");
	private JLabel weatherTemperatureMaxLabel = new JLabel("최고 기온 | 0.0 °C");
	private JLabel weatherTemperatureMinLabel = new JLabel("최저 기온 | 0.0 °C");
	private JLabel weatherPressureLabel = new JLabel("기압 | 0 hPa");
	private JLabel weatherVisibilityLabel = new JLabel("가시거리 | 0 km");
	private JLabel weatherConditionLabel = new JLabel("현재 상태");
	private JLabel weatherSunriseLabel = new JLabel("일출 | 오전 12:00");
	private JLabel weatherSunsetLabel = new JLabel("일몰 | 오전 12:00");
	private JLabel weatherWindLabel = new JLabel("바람 | 북 풍속 0 m/s, 돌풍속 0 m/s");
	private JLabel weatherUpdatedLabel = new JLabel("오전 12:00:00 를 기준으로 함.");
	private Image weatherIconImage;
	private Image modifiedWeatherIconImage;
	private Image weatherBackgroundImage;

	private Date weatherSunriseDate;
	private Date weatherSunsetDate;
	private Date weatherUpdatedDate;
	private SimpleDateFormat weatherSunriseSimpleDateFormat;
	private SimpleDateFormat weatherSunsetSimpleDateFormat;
	private SimpleDateFormat weatherUpdatedSimpleDateFormat;
	private String weatherIconLocationSaver = "";
	private boolean isModified = false;

	private JLabel weatherBackground = new JLabel();
	
	private static String weatherTemperature;
	private static String weatherFeelsLike;
	private static String weatherTemperatureMin;
	private static String weatherTemperatureMax;
	private static String weatherHumidity;
	private static String weatherPressure;
	private static String weatherVisibility;
	private static String weatherCondition;
	private static String weatherSunrise = "0";
	private static String weatherSunset = "0";
	private static String weatherWindSpeed = "0";
	private static String weatherWindDirection = "북";
	private static String weatherWindGust = "0";
	private static String weatherUpdated = "0";
	private static String weatherBackgroundLocation;

	private JFrame frame = new JFrame();

	// 시계
	private Thread clock = new Thread() {
		public void run() {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("aa hh:mm:ss", Locale.KOREA);

			long currentTime;

			while (true) {
				currentTime = System.currentTimeMillis();
				currentTimeLabel.setText(timeFormatter.format(currentTime));
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	// 날씨 관련 사진 정보 자동 동기화
	private Thread weatherImages = new Thread() {
		public void run() {
			while (true) {

				if (!(weatherIconLocationSaver.equals(weatherIconLocation))) {
					weatherIconImage = new ImageIcon(
							Main.class.getResource("imgsrc/weatherIcon/" + weatherIconLocation)).getImage();
					modifiedWeatherIconImage = weatherIconImage.getScaledInstance(100, 100,
							java.awt.Image.SCALE_SMOOTH);

					weatherIconLocationSaver = weatherIconLocation;
				}

				weatherPanel = new JPanel() {

					public void paintComponent(Graphics g) {
						g.drawImage(modifiedWeatherIconImage, 340, 90, null);
						this.repaint();
					}

				};
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	// 날씨 정보 자동 동기화
	private Thread weatherInformation = new Thread() {
		public void run() {
			while (true) {
				weatherTemperatureLabel.setText(weatherTemperature);
				weatherFeelsLikeLabel.setText("체감 기온 | " + weatherFeelsLike + " °C");
				weatherHumidityLabel.setText("습        도 | " + weatherHumidity + " %");
				weatherTemperatureMaxLabel.setText("최고 기온 | " + weatherTemperatureMax + " °C");
				weatherTemperatureMinLabel.setText("최저 기온 | " + weatherTemperatureMin + " °C");
				weatherPressureLabel.setText("기        압 | " + weatherPressure + " hPa");
				weatherVisibilityLabel.setText("가시 거리 | " + weatherVisibility + " km");
				weatherConditionLabel.setText(weatherCondition);
				
				weatherBackground.setIcon(new ImageIcon(Main.class.getResource("imgsrc/weatherBackground/" + weatherIconLocation)));
				

				weatherSunriseDate = new Date();
				weatherSunriseDate.setTime((long) Long.parseLong(weatherSunrise) * 1000);
				weatherSunriseSimpleDateFormat = new SimpleDateFormat("aa h:mm", Locale.KOREA);

				weatherSunsetDate = new Date();
				weatherSunsetDate.setTime((long) Long.parseLong(weatherSunset) * 1000);
				weatherSunsetSimpleDateFormat = new SimpleDateFormat("aa h:mm", Locale.KOREA);

				weatherSunriseLabel
						.setText("일        출 | " + weatherSunriseSimpleDateFormat.format(weatherSunriseDate));
				weatherSunsetLabel.setText("일        몰 | " + weatherSunsetSimpleDateFormat.format(weatherSunsetDate));

				weatherWindLabel.setText("바        람 | " + weatherWindDirection + " 풍속 " + weatherWindSpeed
						+ " m/s, 돌풍속 " + weatherWindGust + " m/s");

				weatherUpdatedDate = new Date();
				weatherUpdatedDate.setTime((long) Long.parseLong(weatherUpdated) * 1000);
				weatherUpdatedSimpleDateFormat = new SimpleDateFormat("aa h시 mm분 ss초", Locale.KOREA);

				weatherUpdatedLabel.setText(weatherUpdatedSimpleDateFormat.format(weatherUpdatedDate) + "를 기준으로 함.");

				weatherSunriseDate = null;
				weatherSunsetDate = null;
				weatherUpdatedDate = null;
				weatherSunriseSimpleDateFormat = null;
				weatherSunsetSimpleDateFormat = null;
				weatherUpdatedSimpleDateFormat = null;

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	Frame() {

		clock.start(); // Start Clock.
		weatherImages.start(); // Start Updating Weather-Specialized Pictures.
		weatherInformation.start(); // Start Updating the Information of Weather.

		// Set Current Time Label Properties.
		currentTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		currentTimeLabel.setOpaque(true);
		currentTimeLabel.setBackground(Color.DARK_GRAY);
		currentTimeLabel.setForeground(Color.WHITE);
		currentTimeLabel.setFont(new Font("NanumSquare", Font.PLAIN, 25));

		firstPanel.setLayout(new GridLayout(2, 1));
		firstPanel.add(new JLabel(
				"<html><body><center>환영합니다. 프로그램을 구동할 컴퓨터의 모니터가 터치모드를 지원하거나, <br> 사용자가 그와 비슷한 방식으로 기기에 접근할 수 있는 마우스가 설치되어 있습니까?</center></body>",
				JLabel.CENTER));
		firstPanel.add(selectingPanel);

		yesButton.setFont(new Font("NanumSqaure", Font.PLAIN, 25));
		noButton.setFont(new Font("NanumSqaure", Font.PLAIN, 25));

		selectingPanel.setLayout(new GridLayout(2, 1));
		selectingPanel.add(yesButton);
		selectingPanel.add(noButton);

		defaultPanel.setLayout(new GridLayout(1, 1));
		defaultPanel.add(weatherPanel);

		// Set Weather Panel.
		weatherPanel.setBackground(Color.BLACK);
		weatherPanel.setOpaque(true);
		weatherPanel.setLayout(null);
		
		weatherPanel.add(weatherTemperatureLabel);
		weatherPanel.add(weatherDegreeCelsiusLabel);
		weatherPanel.add(weatherFeelsLikeLabel);
		weatherPanel.add(weatherHumidityLabel);
		weatherPanel.add(weatherTemperatureMaxLabel);
		weatherPanel.add(weatherTemperatureMinLabel);
		weatherPanel.add(weatherPressureLabel);
		weatherPanel.add(weatherVisibilityLabel);
		weatherPanel.add(weatherConditionLabel);
		weatherPanel.add(weatherSunriseLabel);
		weatherPanel.add(weatherSunsetLabel);
		weatherPanel.add(weatherWindLabel);
		weatherPanel.add(weatherUpdatedLabel);
		weatherPanel.add(weatherBackground);
		
		weatherBackground.setIcon(new ImageIcon(Main.class.getResource("imgsrc/weatherBackground/" + weatherIconLocation)));
		weatherBackground.setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);

		// Set the Properties of Weather Panel.
		weatherTemperatureLabel.setFont(new Font("NanumSquare", Font.BOLD, 120));
		weatherTemperatureLabel.setForeground(Color.WHITE);
		weatherTemperatureLabel.setHorizontalAlignment(JLabel.RIGHT);
		weatherTemperatureLabel.setBounds(10, 10, 260, 300);

		weatherDegreeCelsiusLabel.setFont(new Font("NanumSquare", Font.BOLD, 60));
		weatherDegreeCelsiusLabel.setForeground(Color.WHITE);
		weatherDegreeCelsiusLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherDegreeCelsiusLabel.setBounds(265, 0, 300, 275);

		weatherFeelsLikeLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherFeelsLikeLabel.setForeground(Color.WHITE);
		weatherFeelsLikeLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherFeelsLikeLabel.setBounds(40, 180, 300, 100);

		weatherHumidityLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherHumidityLabel.setForeground(Color.WHITE);
		weatherHumidityLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherHumidityLabel.setBounds(220, 180, 300, 100);

		weatherTemperatureMaxLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherTemperatureMaxLabel.setForeground(Color.WHITE);
		weatherTemperatureMaxLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherTemperatureMaxLabel.setBounds(40, 200, 300, 100);

		weatherTemperatureMinLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherTemperatureMinLabel.setForeground(Color.WHITE);
		weatherTemperatureMinLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherTemperatureMinLabel.setBounds(220, 200, 300, 100);

		weatherPressureLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherPressureLabel.setForeground(Color.WHITE);
		weatherPressureLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherPressureLabel.setBounds(40, 220, 300, 100);

		weatherVisibilityLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherVisibilityLabel.setForeground(Color.WHITE);
		weatherVisibilityLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherVisibilityLabel.setBounds(220, 220, 300, 100);

		weatherSunriseLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherSunriseLabel.setForeground(Color.WHITE);
		weatherSunriseLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherSunriseLabel.setBounds(40, 240, 300, 100);

		weatherSunsetLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherSunsetLabel.setForeground(Color.WHITE);
		weatherSunsetLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherSunsetLabel.setBounds(220, 240, 300, 100);

		weatherWindLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherWindLabel.setForeground(Color.WHITE);
		weatherWindLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherWindLabel.setBounds(40, 260, 400, 100);

		weatherConditionLabel.setFont(new Font("NanumSquare", Font.PLAIN, 15));
		weatherConditionLabel.setForeground(Color.WHITE);
		weatherConditionLabel.setHorizontalAlignment(JLabel.CENTER);
		weatherConditionLabel.setBounds(240, 130, 300, 100);

		weatherUpdatedLabel.setFont(new Font("NanumSquare", Font.PLAIN, 10));
		weatherUpdatedLabel.setForeground(Color.WHITE);
		weatherUpdatedLabel.setHorizontalAlignment(JLabel.LEFT);
		weatherUpdatedLabel.setBounds(5, 5, 300, 10);

		// Set Main Frame.
		frame.add(defaultPanel);
		frame.setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Magic Mirror Software 1.0");
	}

	public static void changeWeatherImages(String weatherBackgroundLocation, String weatherIconLocation) {
		Frame.weatherIconLocation = weatherIconLocation;
		Frame.weatherBackgroundLocation = weatherBackgroundLocation;
	}

	public static void setWeatherInformation(String weatherTemperature, String weatherFeelsLike,
			String weatherTemperatureMin, String weatherTemperatureMax, String weatherHumidity, String weatherPressure,
			String weatherVisibility, String weatherCondition, String weatherSunrise, String weatherSunset,
			String weatherWindSpeed, String weatherWindDirection, String weatherWindGust, String weatherUpdated) {
		Frame.weatherTemperature = Double.toString((Math.round(Double.parseDouble(weatherTemperature) * 10) / 10.0)); // 기온
																														// 반올림
		Frame.weatherFeelsLike = Double.toString((Math.round(Double.parseDouble(weatherFeelsLike) * 10) / 10.0)); // 체감
																													// 온도
																													// 반올림
		Frame.weatherTemperatureMin = Double
				.toString((Math.round(Double.parseDouble(weatherTemperatureMin) * 10) / 10.0)); // 최저 기온 반올림
		Frame.weatherTemperatureMax = Double
				.toString((Math.round(Double.parseDouble(weatherTemperatureMax) * 10) / 10.0)); // 최고 기온 반올림
		Frame.weatherHumidity = weatherHumidity; // 습도
		Frame.weatherPressure = weatherPressure; // 기압
		Frame.weatherVisibility = Integer.toString(Integer.parseInt(weatherVisibility) / 1000); // 가시거리
		Frame.weatherCondition = weatherCondition; // 날씨 상태
		Frame.weatherSunrise = weatherSunrise; // 일출
		Frame.weatherSunset = weatherSunset; // 일몰
		Frame.weatherWindSpeed = weatherWindSpeed; // 풍속

		if (Integer.parseInt(weatherWindDirection) == 360 || Integer.parseInt(weatherWindDirection) == 0) {
			Frame.weatherWindDirection = "북";
		} else if (Integer.parseInt(weatherWindDirection) > 0 && Integer.parseInt(weatherWindDirection) < 45) {
			Frame.weatherWindDirection = "북북동";
		} else if (Integer.parseInt(weatherWindDirection) == 45) {
			Frame.weatherWindDirection = "북동";
		} else if (Integer.parseInt(weatherWindDirection) > 45 && Integer.parseInt(weatherWindDirection) < 50) {
			Frame.weatherWindDirection = "동북동";
		} else if (Integer.parseInt(weatherWindDirection) == 50) {
			Frame.weatherWindDirection = "동";
		} else if (Integer.parseInt(weatherWindDirection) > 50 && Integer.parseInt(weatherWindDirection) < 135) {
			Frame.weatherWindDirection = "동남동";
		} else if (Integer.parseInt(weatherWindDirection) == 135) {
			Frame.weatherWindDirection = "남동";
		} else if (Integer.parseInt(weatherWindDirection) > 135 && Integer.parseInt(weatherWindDirection) < 180) {
			Frame.weatherWindDirection = "남남동";
		} else if (Integer.parseInt(weatherWindDirection) == 180) {
			Frame.weatherWindDirection = "남";
		} else if (Integer.parseInt(weatherWindDirection) > 180 && Integer.parseInt(weatherWindDirection) < 225) {
			Frame.weatherWindDirection = "남남서";
		} else if (Integer.parseInt(weatherWindDirection) == 225) {
			Frame.weatherWindDirection = "남서";
		} else if (Integer.parseInt(weatherWindDirection) > 225 && Integer.parseInt(weatherWindDirection) < 270) {
			Frame.weatherWindDirection = "서남서";
		} else if (Integer.parseInt(weatherWindDirection) == 270) {
			Frame.weatherWindDirection = "서";
		} else if (Integer.parseInt(weatherWindDirection) > 270 && Integer.parseInt(weatherWindDirection) < 315) {
			Frame.weatherWindDirection = "서북서";
		} else if (Integer.parseInt(weatherWindDirection) == 315) {
			Frame.weatherWindDirection = "북서";
		} else if (Integer.parseInt(weatherWindDirection) > 315 && Integer.parseInt(weatherWindDirection) < 360) {
			Frame.weatherWindDirection = "북북서";
		}

		Frame.weatherWindGust = weatherWindGust; // 돌풍속
		Frame.weatherUpdated = weatherUpdated; // 날씨 기준 시간
	}
}
