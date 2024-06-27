package com.mscs.emr.automation.testData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.mscs.emr.automation.regex.Generex;
import com.mscs.emr.automation.utils.Utils;

public final class MockDataUtils {
	private static int MAX_AVG_AGE = 90;
	private static final Random randomizer = new Random();
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException  {
		System.out.println(String.valueOf(Integer.parseInt(generateNumberString(0,1))+Integer.parseInt(generateNumberString(0,1))));
		}
	
	
	public static List<String> generateRandomEmailAddresses(int count) {
		List<String> emails = new ArrayList<String>();
		String email = "[a-z]{5,10}\\@[a-z]{5,20}\\.[a-z]{2,3}";
		for (int i = 0; i < count; i++) {
			emails.add(i, new Generex(email).random());
		}
		return emails;
	}

	public static String generateRandomEmailAddress() {
		String email = "[a-z]{5,10}\\@[a-z]{5,20}\\.[a-z]{2,3}";
		return new Generex(email).random();
	}

	public static String generateRandomZipCode() {
		// String zip = "[0-9]{5}";
		String zip = "[1-9]{1}[0-9]{4}";
		return new Generex(zip).random();
	}

	public static String generateRandomFutureDate() {
		int mm = randomizer.nextInt(12) + 1;
		int dd = randomizer.nextInt(mm == 2 ? 28 : 30) + 1;
		int yyyy = Calendar.getInstance().get(Calendar.YEAR) + randomizer.nextInt(5) + 1;
		return String.format("%02d/%02d/%4d", mm, dd, yyyy);
	}

	public static String generateRandomPastDate(int upToYear) {
		int mm = randomizer.nextInt(12) + 1;
		int dd = randomizer.nextInt(mm == 2 ? 28 : 30) + 1;
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		int yyyy = curYear - MAX_AVG_AGE
				+ (upToYear < curYear - MAX_AVG_AGE
						? 0
						: randomizer.nextInt(MAX_AVG_AGE - (upToYear >= curYear ? 0 : curYear - upToYear)));
		return String.format("%02d/%02d/%4d", mm, dd, yyyy);
	}

	public static String generateRandomPhoneNumberWithDashes() {
		String phone = "[1-9]{3}-[0-9]{3}-[0-9]{4}";
		return new Generex(phone).random();
	}

	public static String generateRandomPhoneNumberWithoutDashes() {
		String phone = "[1-9]{3}[0-9]{3}[0-9]{4}";
		return new Generex(phone).random();
	}

	public static String generateRandomSsnNumber() {
		String ssn = "[0-9]{3}-[0-9]{2}-[0-9]{4}";
		return new Generex(ssn).random();
	}

	public static String generateNumberString(int minLen, int maxLen) {
		String numberString = wordRegexInt(minLen, maxLen);
		return new Generex(numberString).random();
	}

	public static String generateParagraph(int i, int j) {
		String paragraph = generateSentence() + " " + generateSentence() + " " + generateSentence() + " "
				+ generateSentence() + " " + generateSentence();
		return paragraph;
	}

	public static String generateSentence() {
		String sentence = generateWord(8, 10) + " " + generateWord(5, 7) + " " + generateWord(5, 7) + " "
				+ generateWord(5, 7) + ".";
		return sentence;
	}

	public static String generateSentence(int length) {
		String sentence = "";
		while (sentence.length() < length) {
			sentence = sentence.concat(generateWord(8)).concat(" ");
		}
		return sentence;
	}

	public static String generateFullName(int minLen, int maxLen) {
		String first = generateWord(minLen, maxLen);
		String middle = generateWord(minLen, maxLen);
		String last = generateWord(minLen, maxLen);
		return last + " ," + first + " " + middle;
	}

	public static String generateRandomStreetAddress() {
		String streetAddress = wordRegexInt(3, 6) + " " + wordRegex(3, 6) + " " + wordRegex(3, 6);
		return new Generex(streetAddress).random();
	}
	
	public static String generateRandomAlphaNumericNumber(int minLen, int maxLen) {
		String alphaNumericNumber = wordRegex(minLen, maxLen) + wordRegexInt(minLen, maxLen);
		return new Generex(alphaNumericNumber).random();
	}

	private static String wordRegex(int minLen, int maxLen) {
		return String.format("[A-Z][a-z]{%s,%s}", minLen, maxLen);
	}

	private static String wordRegex(int Len) {
		return String.format("[A-Z][a-z]{%s}", Len - 1);
	}

	private static String wordRegexInt(int minLen, int maxLen) {
		return String.format("[1-9][1-9]{%s,%s}", minLen, maxLen);
	}

	public static String generateWord(int min, int max) {
		String word = wordRegex(min, max);
		return new Generex(word).random();
	}

	public static String generateWord(int size) {
		String word = wordRegex(size);
		return new Generex(word).random();
	}

	public static String createUniqueNumber(Integer length) {
		return new Generex("[1-9]{" + length + "}").random();
	}

	public static String replaceSpecialCharacters(String actualText) {
		return actualText.replaceAll("[^a-zA-Z0-9]+", "");
	}

	public static String generateUniqueMrnNumber() {
		return generateWord(2, 3).toUpperCase() + Utils.getHostName().toUpperCase()
				+ MockDataUtils.createUniqueNumber(5);
	}
	/** This method return the guid randomly
	 * @return
	 */
	public static String getguid() {
	       UUID uuid = UUID.randomUUID();
	       return uuid.toString();        
	}
	public static String generateFirstName() {
		return generateWord(5, 9);
	}

	public static String generateMiddleName() {
		return generateWord(5, 9);
	}

	public static String generateLastName() {
		return generateWord(5, 9);
	}

	public static String generateUserProfileName() {
		return generateWord(5, 12);
	}

	public static String createRandomDeaNumber() {
		String finalDeaNumber = null;

		String sixDigit = MockDataUtils.createUniqueNumber(6);
		String d1st = sixDigit.substring(0, 1);
		String d2st = sixDigit.substring(1, 2);
		String d3st = sixDigit.substring(2, 3);
		String d4st = sixDigit.substring(3, 4);
		String d5st = sixDigit.substring(4, 5);
		String d6st = sixDigit.substring(5, 6);
		int sumOf1st3rd5th = Integer.parseInt(d1st) + Integer.parseInt(d3st) + Integer.parseInt(d5st);
		int sumOf2st4rd6th = Integer.parseInt(d2st) + Integer.parseInt(d4st) + Integer.parseInt(d6st);
		int doubleOfSecondSeries = sumOf2st4rd6th * 2;
		int total = sumOf1st3rd5th + doubleOfSecondSeries;
		String lastDigit = String.valueOf(total).substring(1, 2);
		finalDeaNumber = MockDataUtils.generateWord(2).toUpperCase() + String.valueOf(d1st) + String.valueOf(d2st)
				+ String.valueOf(d3st) + String.valueOf(d4st) + String.valueOf(d5st) + String.valueOf(d6st) + lastDigit;

		return finalDeaNumber;
	}

	public static String generateUserName() {
		return MockDataUtils.generateWord(3, 3) + MockDataUtils.createUniqueNumber(10);
	}

	public static int generateRandomInt(int min, int max) {
		return randomizer.nextInt((max - min) + 1) + min;
	}

	public static String getRandomGender() {
		return randomizer.nextInt() % 2 == 0 ? "MALE" : "FEMALE";
	}

	public static String[] generateArrayWords(int sizeArray, int sizeWord) {
		String[] array = new String[sizeArray];
		for (int i = 0; i < sizeArray; i++) {
			String word = wordRegex(sizeWord);
			array[i] = new Generex(word).random();
		}

		return array;
	}

	public static String generateSpecialCharacters(int size) {
		String word = specialCharRegex(size);
		return new Generex(word).random();
	}

	private static String specialCharRegex(int Len) {
		return String.format("[^a-zA-Z0-9]{%s}", Len - 1);
	}
}
