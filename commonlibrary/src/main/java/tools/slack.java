package tools;

import java.io.IOException;

import in.ashwanthkumar.slack.webhook.Slack;
import in.ashwanthkumar.slack.webhook.SlackMessage;

/**
 * API interface to let slack to interact with fitnesse
 * @author Administrator
 *
 */


public class slack {
	
	
	/**
	 * 
	 * @param URL
	 *            - Webhook URL for slack
	 * 
	*/
	
	
	public static String rs = null;
	public static String webhookUrl = "https://hooks.slack.com/services/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";
	
	//Run Status
	/**
	 * Usage: | notifyStatus| nstatus|
	 * @param nstatus
	 */
	public static void notifyStatus(String nstatus) {
		rs = nstatus;
	}
	
	/**
	 * Usage: | slackNotification| tcase| img| status|
	 * @param tcase => testcase
	 * @param img => screenshoot
	 * @param status => testcase status either pass or fail
	 * @return
	 * @throws IOException
	 */
	public static String slackNotification(String tcase,String img,String status) throws IOException {
		
		
		if(rs.equalsIgnoreCase("notify")) {
			
			if(status.equalsIgnoreCase("false")) {
				
				new Slack(webhookUrl)
			    .push(new SlackMessage()
			    .bold(tcase)		
			    .text(" => <http://localhost:4000/FrontPage.Pando?responder=search&searchScope=root&searchString="+tcase+"&searchType=Search+Titles|Automation Link>"
			    		+ "=> <"+img+"|Screenshoot Link>"
			    		+ "=> <https://pando.freshrelease.com/PANDO/tcm/test-cases/"+tcase+"|TestCase Link>"));

			    return tcase;	
				
			}
			 
		
		
	  } else {
		 return rs; 
	  }
		
		return status;
		
	}   
	
	
}	
	