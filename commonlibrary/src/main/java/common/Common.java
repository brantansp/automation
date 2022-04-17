package common;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import tools.Extend;

/**
 * Update 1
 * Exposes methods which are very common & generic like generating random
 * number, getting current date & time.
 * 
 * @author Administrator
 * 
 */
public class Common {

	/**
	 * Usage: | checkIfStringExistsIn| valueToCheck| validateIn |
	 * @param valueToCheck
	 *            - string to check
	 * @param validateInString
	 *            - string to check into
	 * @return returns true if string exists in provided string
	 */
	public boolean checkIfStringExistsIn(String valueToCheck,
			String validateInString) {
		if (validateInString.toLowerCase().contains(valueToCheck.toLowerCase())) {
			Extend.success("For checkIfStringExistsIn : ["+valueToCheck + "] exists in ["+validateInString+"]");
			return true;
			
		} else {
			Extend.error("For checkIfStringExistsIn : ["+valueToCheck + "] does not exists in ["+validateInString+"]");
			return false;
		}
	}
	
	/**
	 * Usage: | checkIfStringNotExistsIn| valueToCheck| validateIn |
	 * @param valueToCheck
	 *            - string to check
	 * @param validateInString
	 *            - string to check into
	 * @return returns true if string exists in provided string
	 */
	public boolean checkIfStringNotExistsIn(String valueToCheck,
			String validateInString) {
		if(isNullOrEmpty(validateInString)) {
			Extend.error("for checkIfStringNotExistsIn : validateInString is empty");
			return false;
		} else {
			if (validateInString.toLowerCase().contains(valueToCheck.toLowerCase())) {
				Extend.error("For checkIfStringNotExistsIn : ["+valueToCheck + "] does exists in ["+validateInString+"]");
				return false;
				
			} else {
				Extend.success("For checkIfStringNotExistsIn : ["+valueToCheck + "] does not exists in ["+validateInString+"]");
				return true;
			}
		}

	}
	
    public static boolean isNullOrEmpty(String str) {
    	if(str != null && !str.isEmpty()) {
    		return false;
    	} else {
    		return true;
    	}
    }

	/**
	 * 
	 * Usage: | getIndexOf| valueToCheck| getIndexInString |           

	 * @param valueToCheck
	 *            - string to check
	 * @param getIndexInString
	 *            - string to check into
	 * @return Returns the index of first occurrence of string
	 */
	public int getIndexOf(String valueToCheck, String getIndexInString) {
		return getIndexInString.indexOf(valueToCheck);
	}

	/**
	 * Usage: | checkIfStringAreEqual | firstString | secondString |
	 * @param firstString
	 * @param secondString
	 * @return true if strings are equal, considers case
	 */
	public boolean checkIfStringAreEqual(String firstString, String secondString) {
		if (firstString.equals(secondString)) {
			Extend.success("For checkIfStringAreEqual : [" +firstString + "] is equals to ["+secondString+"]");
			return true;
		} else {
			Extend.error("For checkIfStringAreEqual : [" +firstString + "] is not equals to ["+secondString+"]");
			return false;
		}
	}
	
	/**
	 * Usage: | checkIfStringAreNotEqual | firstString | secondString |
	 * @param firstString
	 * @param secondString
	 * @return true if strings are equal, considers case
	 */
	public boolean checkIfStringAreNotEqual(String firstString, String secondString) {
		if (firstString.equals(secondString)) {
			Extend.error("For checkIfStringAreNotEqual : [" +firstString + "] is equals to ["+secondString+"]");
			return false;			
		} else {
			Extend.success("For checkIfStringAreNotEqual : [" +firstString + "] is not equals ["+secondString+"]");
			return true;
		}
	}

	/**
	 * Usage: | checkIfStringAreEqualIgnoreCase| firstString | secondString |
	 * @param firstString
	 * @param secondString
	 * @return returns true if strings are equal, ignores case
	 */
	public boolean checkIfStringAreEqualIgnoreCase(String firstString,
			String secondString) {
		if (firstString.equalsIgnoreCase(secondString)) {
			Extend.success("For checkIfStringAreEqualIgnoreCase : ["+firstString + "] is equals to ["+secondString+"]");
			return true;
		} else {
			Extend.error("For checkIfStringAreEqualIgnoreCase : ["+firstString + "] is not equals to ["+secondString+"]");
			return false;
		}
	}
	
	/**
	 * Usage: | checkIfStringAreEqualInArray | array|
	 * @param String as array 
	 * @return false if array contains unequal elements else true
	 */
	
	public boolean checkIfStringAreEqualInArray(ArrayList<String> args) {
		Set<String> set = new HashSet<String>(args);

		if (1 == set.size()) {
			Extend.success("All elements are same in the array : "+ set);
			return true;
		} else {
			Extend.error("All elements are not same in the array : "+ set);
			return false;
		}
	}
	
	/**
	 * String Concatenation 
	 * Usage: | stringConcatenation | value1| value2|
	 * @param value1
	 * @param value2
	 * @return combination of value1 and value2
	 */
	
	public String stringConcatenation (String value1, String value2) {
	
		String finalString = value1.concat(value2);
		return finalString;
		
		
	}

	/**
	 * Usage: | getSubString | inputString | startIndex| endIndex|
	 * @param inputString
	 * @param startIndex
	 * @param endIndex
	 * @return returns the sub string from a string
	 */
	public String getSubString(String inputString, int startIndex, int endIndex) {
		if (startIndex < 0)
			return inputString;
		else
			return inputString.substring(startIndex, endIndex);
	}
	
	/**
	 * Get Substring by another String with only start string
	 * Usage: | getSubStringByStringWithStartString | input | start|
	 * @param inputString
	 * @param start
	 * @return result
	 */
	
	public String getSubStringByStringWithStartString(String inputString, String start) {
		
		return StringUtils.substringAfterLast(inputString, start);
	}
	
	/**
	 * Split String using given value
	 * Usage: | splitString | inputstring| separator| value|
	 * @param inputstring
	 * @param separator
	 * @param value
	 * @return
	 */
	
	
	public String splitString(String inputstring,String separator, int value)
	{
		
		String[] searchname = inputstring.split(separator);
		return searchname[value];
		
	}
	
	
	
	/**
	 * Replace find and replace the string 
	 * Usage: | replaceString | inputstring| find| replace|
	 * @param inputstring
	 * @param find
	 * @param replace
	 * @return result
	 */
	
	
	public String replaceString(String inputstring,String find,String replace) {
		String result = inputstring.replaceAll(find,replace);
		return result;
	}
	
	/**
	 * Get Substring by another String
	 * Usage: | getSubStringByString | input | start| end|
	 * @param inputString
	 * @param start
	 * @param end
	 * @return result
	 */
	
	public String getSubStringByString(String inputString, String start, String end) {
		
		String result = StringUtils.substringBetween(inputString, start, end);
		return result;
		
	}

	/**
	 * Usage: | getStringLength | inputSting|
	 * @param inputSting
	 * @return get length of string
	 */
	public int getStringLength(String inputSting) {
		return inputSting.length();
	}
	
	/**
	 * Usage: | getCurrentDateTimeStamp| formats|
	 * Format be like yMMddm,dd-MM-yyyy HH-mm-ss,yMMddHHmmss 
	 * @return get current date time stamp in format formats
	 * @throws Exception 
	 */
	public String getCurrentDateTimeStamp(String formats) throws Exception {
		
		Date date = new Date();
		String showFormat;
		
		switch (formats) {
		case "dd-MM-yyyy HH-mm-ss":
			showFormat = "dd-MM-yyyy HH-mm-ss";
			break;
		case "yMMddm":
			showFormat = "yMMddm";
			break;
		case "yMMddHHmmss":
			showFormat = "yMMddHHmmss";
			break;
		case "yMdHm":
			showFormat = "yMdHm";
			break;	
		case "dd-MM-yyyy-HH-mm-ss":
			showFormat = "dd-MM-yyyy-HH-mm-ss";
			break;
		case "yyyy/MM/dd HH:mm:ss":
			showFormat = "yyyy/MM/dd HH:mm:ss";
			break;
		case "yyyy/MM/dd":
			showFormat = "yyyy/MM/dd";
			break;
		case "yyyy-MM-dd":
			showFormat = "yyyy-MM-dd";
			break;
		case "iso":
			showFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
			break;
		default:
			showFormat = "y-M-d";	
		}
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(showFormat);
		
		String currentDateTimeStamp = dateFormat.format(date);
		System.out.println(currentDateTimeStamp);
		if (formats.equals("yMMddHHmmss")) {
			currentDateTimeStamp = currentDateTimeStamp.substring(0, 12) + getRandomString("2", "NUMERIC");
		}
		
		return currentDateTimeStamp;
	
	}

	/** 
	 * Usage: | getRandomNumber| min| max|
	 * Returns a random number between 1 and 10000 */
	public int getRandomNumber(int min, int max) {

		Random random = new Random();
		int randomNumber = random.nextInt((max - min) + 1) + min;
		return randomNumber;
	}
	
	
	
	
	/** 
	 * Usage: | getRandomIP |
	 * Returns a random IP */
	public String getRandomIP() {
		
         Random r = new Random();
         String randomIp =  r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
         return randomIp;
         
    }
	
	
	/**
	 * Usage: | getRandomValueFromArray| array |
	 * @param String as array 
	 * @return random value from an array
	 */
	
	public String  getRandomValueFromArray(String[] args) {
		
		int rnd = new Random().nextInt(args.length);
	    return args[rnd];

	}

	

	/**
	 * 
	 * Usage: | getRandomString | sizeOf | type|
	 * @param sizeOfString
	 *            - size of string to generate.
	 * @param type
	 *            - NUMERIC,ALPHAUPPER,ALPHANUMERIC
	 * @return random string of provided type of provided length.
	 * @throws Exception
	 */
	public String getRandomString(String sizeOfString, String type)
			throws Exception {
		StringBuffer buffer = new StringBuffer();
		String characters = "";
		int length = Integer.parseInt(sizeOfString);

		switch (type.toUpperCase()) {
		case "ALPHA":
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		case "ALPHAUPPER":
			characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		case "ALPHALOWER":
			characters = "abcdefghijklmnopqrstuvwxyz";
			break;
		case "ALPHANUMERIC":
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;
		case "NUMERIC":
			characters = "1234567890";
			break;
		default:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		}

		int charactersLength = characters.length();
		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}

	/**
	 * 
	 * Usage: | getResultOfCalculation| number1| operator| number2|
	 * @param number1
	 * @param operator like +,-,*,/
	 * @param number2
	 * @return Returns calculation of two integers
	 */
	public int getResultOfCalculation(int number1, char operator, int number2) {
		switch (operator) {
		case '+':
			return (number1 + number2);

		case '-':
			return (number1 - number2);

		case '*':
			return (number1 * number2);

		case '/':
			return (number1 / number2);

		}
		Extend.error("Invalid operator provided");
		return 0;
	}
	
	
	/**
	 * Usage: | hashString | message| algorithm|
	 * @param message
	 * @param algorithm // algorithm can be "MD5", "SHA-1", "SHA-256"
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	
	
	public String hashString(String message, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	       
	        MessageDigest digest = MessageDigest.getInstance(algorithm);
	        byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
	        
	        StringBuffer stringBuffer = new StringBuffer();
	        for (int i = 0; i < hashedBytes.length; i++) {
	            stringBuffer.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16)
	                    .substring(1));
	        }
	        
	        return stringBuffer.toString();
	        
	        
	}
	
	/**
	 * Change string to upper case
	 * Usage: | upperCase| message|
	 * @param message
	 * @return
	 */
	
	public String upperCase(String message) {
		
		message = message.toUpperCase();
		return message;
		
	}
	
	/**
	 * Change string to lower case
	 * Usage: | lowerCase| message|
	 * @param message
	 * @return
	 */
	
	public String lowerCase(String message) {
		
		message = message.toLowerCase();
		return message;
		
	}
	
	/**
	 * Get File Path
	 * Usage: | getPath| fileName| pathType|
	 * pathytype can be fullpath
	 * @param fileName
	 * @return
	 */
	
	public String getPath(String fileName,String pathType) {
		
		File f = new File(fileName);
		String path = null;
		
		switch (pathType) {
		
		    case "fullpath":  
			path = f.getAbsolutePath();
			break;
			
		}
		
		return path;
		
	}
	
	/**
	 * 
	 * Usage: | comparisonResult| number1(double)| operator| number2(double)|
	 * @param number1
	 * @param operator
	 * @param number2
	 * @return Returns comparison result of two operator
	 * 
	 */	
	
	public static boolean comparisonResult(double number1, String operator, double number2) {

		boolean result = false;

		switch (operator) {

		case "<":

			if (number1 < number2) {
				result = true;
			} else {
				result = false;
			}

			break;

		case ">":

			if (number1 > number2) {
				result = true;
			} else {
				result = false;
			}

			break;

		case "<=":

			if (number1 <= number2) {
				result = true;
			} else {
				result = false;
			}

			break;

		case ">=":

			if (number1 >= number2) {
				result = true;
			} else {
				result = false;
			}

			break;

		case "equal":

			if (number1 == number2) {
				result = true;
			} else {
				result = false;
			}

			break;

		case "!=":

			if (number1 != number2) {
				result = true;
			} else {
				result = false;
			}
			break;
		default:
			Extend.error("Invalid operator provided");
		}

		return result;

	}
	 
	    /** Trim the given String
	     * Usage: | getTrim| value|
		 * @param String 
		 * @return returns string data
		 * 
		 */
	 
	public static String getTrim(String value) {
		value = value.trim();
		return value;
	}
	
	/**
	 * Check File Downloaded in a given directory path
	 * Usage: | checkIfFileDownloaded| fileStartsWith| directoryPath|
	 * @param fileStartsWith 
	 * @return Returns boolean of File search result
	 * 
	 */	
	
	public boolean checkIfFileDownloaded(final String fileStartsWith, String directoryPath) throws IOException {
		File dir = new File(directoryPath);

		File[] listFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				String name = file.getName().toLowerCase();
				return name.startsWith(fileStartsWith);
			}
		});
		
		if (listFiles.length == 0) {
			Extend.error("File starting with [" + fileStartsWith + "] was not found");
			return false;
		} else {
			Extend.success("File starting with [" + fileStartsWith + "] was  found");
            return true;
		}
	}
	
	/**
	 * Clean Files in the given directory path
	 * Usage: | cleanFilesInDirectory | directoryPath|
	 * cleanFileDownloadDirectory
	 * 
	 */	
	
	public static void cleanFilesInDirectory (String directoryPath) throws IOException {
		File dir = new File(directoryPath);
		FileUtils.cleanDirectory(dir);
	}
	
	/**
	 * 
	 * Usage: | isFloatMax| amount|
	 * @param amount
	 * @return boolean of is Float Max validation
	 */
	
	public static boolean isFloatMax(String amount) {
		
		Float f = Float.parseFloat(amount);
		if (f == Float.MAX_VALUE) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Append value to existing arraylist and return the array
	 * Usage: | appendValueToArray | existingArray|newValue|
	 * @return
	 */	
	public static ArrayList<Object> appendValueToArray(ArrayList <Object> existingArray, Object newValue){
		ArrayList<Object> oldArray =existingArray; 
		oldArray.add("\""+newValue+"\"");
		return oldArray;
	}
	
	/**
	 * To find the difference between the two given strings
	 * Usage: | stringCompareForDifference | sourceStr| compareStr|
	 * @return missing words
	 */	
	public static List<String> stringCompareForDifference(String sourceStr, String compareStr) {
		StringTokenizer at = new StringTokenizer(sourceStr, " ");
		StringTokenizer bt = new StringTokenizer(compareStr, " ");
		String srctoken = null;
		String desttoken = null;
		List<String> missingWords = new ArrayList<String>();

		if (at.countTokens() != bt.countTokens()) {
			missingWords.add("Words count in string mismatches - " + at.countTokens() + " != " + bt.countTokens()
					+ " || Source - [" + sourceStr + " ] compare - [ " + compareStr + "]");
			int i = 1;
			while (at.hasMoreTokens()) {
				srctoken = at.nextToken();
				Extend.info(i + " : " + srctoken);
				i++;
			}

			i = 1;
			while (bt.hasMoreTokens()) {
				desttoken = bt.nextToken();
				Extend.info(i + " : " + desttoken);
				i++;
			}
		} else {
			while (at.hasMoreTokens()) {
				
				srctoken = at.nextToken();
				if (srctoken.matches("^\\d+\\.\\d+")) {
					srctoken = Double.toString(getPrecisionValue(srctoken, 1));
					}
				
				desttoken = bt.nextToken();
				if (desttoken.matches("^\\d+\\.\\d+")) {
					desttoken = Double.toString(getPrecisionValue(desttoken, 1));

				}
				if (!srctoken.equals(desttoken)) {
					missingWords.add(srctoken + " != " + desttoken);
				}
			}
		}
		return missingWords;
	}

	public static double getPrecisionValue(String value, int precision) {
		int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(Double.valueOf(value) * scale) / scale;
	}
	
	public static void main(String[] args) {
		System.out.println(getPrecisionValue("10.196", 2));
	}
}