package environment;

import java.io.IOException;
import java.net.InetAddress;
public class Network {

	/**
	 * ping the provided IP address & if not reachable wait for given time out
	 * Usage: | ping| ipAddress| timeOut|
	 * @param ipAddress
	 *            - IP address to ping
	 * @param timeOut
	 *            - time out to wait for ping to response
	 * @throws IOException
	 */
	public void ping(String ipAddress, int timeOut) throws IOException {

		System.out.println("Pinging IP Address: " + ipAddress + " with Timeout set to: "
				+ timeOut);
		InetAddress address = InetAddress.getByName(ipAddress);
		// Try to reach the specified address within the timeout
		// periode. If during this periode the address cannot be
		// reach then the method returns false.
		int defaultTimeOut = 5000;
		boolean reachable = false;
		while (defaultTimeOut <= timeOut) {
			reachable = address.isReachable(defaultTimeOut);
			if (reachable) {
				System.out.println("Host is reachable");
				break;
			}
			System.out.println("waiting for 5 seconds");
			defaultTimeOut = defaultTimeOut + 5000;
		}
		if (!reachable) {
			System.out.println("Host is NOT reachable");
		}
	}
	
	/**
	 * Usage: | getLocalIPAddress|
	 * @return
	 * @throws Exception
	 */

	public static String getLocalIPAddress() throws Exception {
		InetAddress ip = InetAddress.getLocalHost();
		return ip.getHostAddress();
	}
}
