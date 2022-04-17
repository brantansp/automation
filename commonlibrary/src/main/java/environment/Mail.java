package environment;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.BodyTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;


/**
 * Backend Mail Manipulation
 * 
 * @author Administrator
 *
 */
public class Mail {

	final static String port = "993";
	public static Folder folderInbox = null;
	public static Store store = null;
	public static Message message = null;

	/**
	 * Connection establishment
	 * Usage: | mailConfig| userName| password|
	 * @param host
	 * @param userName
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */

	public static Folder mailConfig(String userName, String password) {

	
		  
		  String domain = userName .substring(userName .indexOf("@") + 1);  // Validating domain gmail 0r outllok
		  
		  Properties properties = new Properties();     
		  properties.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		  properties.setProperty("mail.imaps.socketFactory.fallback", "false");
		  properties.setProperty("mail.imaps.port", String.valueOf(port));
		  properties.setProperty("mail.imaps.socketFactory.port", String.valueOf(port));
		  properties.setProperty("mail.imaps.ssl.enable", "true");     
		  properties.setProperty("mail.store.protocol", "imaps");
		  properties.put("mail.imaps.port", String.valueOf(port));

		  
		  if(domain.equals("gmail.com")) {
			  properties.put("mail.imaps.host", "smtp.gmail.com");
		  }
		  
		  if(domain.equals("outlook.com")) {
			  properties.put("mail.imaps.host", "imap-mail.outlook.com");
          }

		  Session mailSession = Session.getInstance(properties); 
		  mailSession.setDebug(true);
		 
		  
		try {
			store = mailSession.getStore("imaps");
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			  store.connect(userName , password);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			
			// opens the inbox folder
			folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}

		return folderInbox;

	}

	/**
	 * Wait for the specified seconds
	 * 
	 * @param integer Value
	 * @throws InterruptedException
	 */
	public static void wait(int i) {
		System.out.println("Waiting for time: " + i + " seconds ");
		try {
			java.util.concurrent.TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Search an Email with particular subject and body
	 * Usage: | searchEmail| subject| body|
	 * @param subject
	 * @param body
	 * @return boolean
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static boolean searchEmail(String subject, String body) throws MessagingException, IOException {

		boolean flag = false;

		// performs search through the folder
		SearchTerm bodyTerm = new BodyTerm(body);
		Message[] bodyMessage = folderInbox.search(bodyTerm);

		for (Message msg : bodyMessage) {
			if (msg.getContent().toString().replaceAll("\\<.*?\\>", "").contains(body) && msg.getSubject().toString().contains(subject)) {
				message = msg;
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * Search an Email with particular subject
	 * Usage: | searchEmail| subject|
	 * @param subject
	 * @return boolean
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static boolean searchEmail(String subject) throws MessagingException, IOException {

		// performs search through the folder
		SearchTerm term = new SubjectTerm(subject);
		Message[] message = folderInbox.search(term);

		if (message.length == 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Get subject from the given mail
	 * Usage: | getSubject|
	 * @return subject
	 * @throws MessagingException
	 */
	public static String getSubject() throws MessagingException {

		// performs search through the folder
		String result = "No such mail received";
		
		if (message != null) {
			result = "";
			result = message.getSubject();
		}
		return result;

	}

	/**
	 * Get subject from the given mail
	 * Usage: | getSubject| body |
	 * @param body
	 * @return subject
	 * @throws MessagingException
	 * @throws IOException 
	 */
	public static String getSubject(String body) throws MessagingException, IOException {

		SearchTerm bodyTerm = new BodyTerm(body);
		Message[] bodyMessage = folderInbox.search(bodyTerm);
		String result = "No such mail received";
		
		for (Message msg : bodyMessage) {
			if (msg.getContent().toString().contains(body)) {
				result = message.getSubject();
				break;
			} 
		}
		
		return result;

	}
	
	/**
	 * Get Body Content from the given mail
	 * Usage: | getBody|
	 * @return body result
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static String getBody() throws MessagingException, IOException {

		// Gets body part of the email
		String result = "No such mail received";

		if (message != null) {
			result = "";
			result = message.getContent().toString();
		}

		return result;

	}

	/**
	 * Get Body Content from the given mail
	 * Usage: | getBody| subject |
	 * @param subject
	 * @return body result
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static String getBody(String subject) throws MessagingException, IOException {

		SearchTerm subjectTerm = new SubjectTerm(subject);
		Message[] subjectMessage = folderInbox.search(subjectTerm);
		String result = "No such mail received";
		
		for (Message msg : subjectMessage) {
			if (msg.getSubject().toString().contains(subject)) {
				result = message.getContent().toString();
				break;
			} 
		}
		
		return result;
	
	}
	
	/**
	 * Delete the searched mail
	 * Usage: | deleteMessage |
	 * @throws MessagingException
	 */
	public static void deleteMessage() throws MessagingException {

		if (message != null) {
			message.setFlag(Flags.Flag.DELETED, true);
		}
	}

	/**
	 * Search and Delete Mail
	 * Usage: | deleteMessage| subject| body|
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void deleteMessage(String subject, String body) throws MessagingException, IOException {
		SearchTerm bodyTerm = new BodyTerm(body);
		Message[] bodyMessage = folderInbox.search(bodyTerm);
		
		for (Message msg : bodyMessage) {
			if (msg.getContent().toString().contains(body) && msg.getSubject().toString().contains(subject)) {
				msg.setFlag(Flags.Flag.DELETED, true);
			} 
		}
	}
	
	/**
	 * Delete All Mails
	 * Usage: | deleteAllMails| mailId| password|
	 * @param mailId
	 * @param password
	 * @throws MessagingException
	 */
	public static void deleteAllMails(String mailId, String password) throws MessagingException {
		
		mailConfig (mailId,password);
		
		Message[] messages = folderInbox.getMessages();
		
		for (Message msg : messages) {
			msg.setFlag(Flags.Flag.DELETED, true);
		}
	}
	
	/**
	 * @throws MessagingException
	 * Usage: | closeMail |
	 */
	public void closeMail() throws MessagingException {
		folderInbox.close(false);
		store.close();
	}
}