package environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class FileUtil {
	private static String ZIP_SOURCE_FOLDER = "";
	private static List<String> listOfFileToZip;

	/**
	 * Find files with extension
	 * Usage: | findFilesWithExtension| dirName| final extension|
	 * @param dirName
	 *            - directory name
	 * @param extension
	 *            - file extension
	 * @return - list of files.
	 */
	public static File[] findFilesWithExtension(String dirName,
			final String extension) {
		File dir = new File(dirName);
		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith("." + extension);
			}
		});
	}

	/**
	 * Download file from FTP
	 * Usage: | downloadFileFromFTP| server| portNumber| userName| password| remoteFile| saveAs|
	 * @param server
	 *            - FTP server
	 * @param portNumber
	 *            - port
	 * @param userName
	 *            - FTP user name
	 * @param password
	 *            - FTP password
	 * @param remoteFile
	 *            - full path of file to download
	 * @param saveAs
	 *            - full path to store file locally.
	 */
	public static void downloadFileFromFTP(String server, String portNumber,
			String userName, String password, String remoteFile, String saveAs) {
		int port = Integer.parseInt(portNumber);
		FTPClient ftpClient = new FTPClient();
		try {
			System.out.println("Logging to FTP: " + server);
			System.out.println("FTP port: " + port);
			System.out.println("FTP User name: " + userName);
			System.out.println("FTP password: " + password);
			System.out.println("Downloading file: " + remoteFile);
			System.out.println("Saving locally as: " + saveAs);
			ftpClient.connect(server, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// APPROACH #1: using retrieveFile(String, OutputStream)
			File downloadFile = new File(saveAs);
			OutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(downloadFile));
			boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
			outputStream.close();

			if (success) {
				System.out.println("File downloaded successfully.");
			} else {
				System.out.println("Failed to download file.");
			}
			// APPROACH #2: using InputStream retrieveFileStream(String)
			// String remoteFile2 = "/test/song.mp3";
			// File downloadFile2 = new File("D:/Downloads/song.mp3");
			// OutputStream outputStream2 = new BufferedOutputStream(
			// new FileOutputStream(downloadFile2));
			// InputStream inputStream =
			// ftpClient.retrieveFileStream(remoteFile2);
			// byte[] bytesArray = new byte[4096];
			// int bytesRead = -1;
			// while ((bytesRead = inputStream.read(bytesArray)) != -1) {
			// outputStream2.write(bytesArray, 0, bytesRead);
			// }
			// success = ftpClient.completePendingCommand();
			// if (success) {
			// System.out.println("File #2 has been downloaded successfully.");
			// }
			// outputStream2.close();
			// inputStream.close();

		} catch (IOException exception) {
			System.err.println("Error: " + exception.getMessage());
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException exception) {
				System.err.println("Error: " + exception.getMessage());
			}
		}
	}

	/**
	 * Upload file to FTP
	 * Usage: | uploadFileToFTP| server| portNumber| userName| password| fileToUpload| uploadAs|
	 * @param server
	 *            - server name to upload file
	 * @param portNumber
	 *            - ftp port number
	 * @param userName
	 *            - user name to connect to ftp
	 * @param password
	 *            - password
	 * @param fileToUpload
	 *            - file to upload
	 * @param uploadAs
	 *            - filename to upload as
	 */
	public static void uploadFileToFTP(String server, String portNumber,
			String userName, String password, String fileToUpload,
			String uploadAs) {
		int port = Integer.parseInt(portNumber);
		FTPClient ftpClient = new FTPClient();
		try {
			System.out.println("Logging to FTP: " + server);
			System.out.println("FTP port: " + port);
			System.out.println("FTP User name: " + userName);
			System.out.println("FTP password: " + password);
			System.out.println("Uploading file: " + fileToUpload);
			
			ftpClient.connect(server, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			// APPROACH #1: using retrieveFile(String, OutputStream)
			File uploadFile = new File(fileToUpload);
			InputStream inputStream = new BufferedInputStream(
					new FileInputStream(uploadFile));
			boolean success = ftpClient.storeFile(uploadAs, inputStream);
			inputStream.close();
			if (success) {
				System.out.println("File uploaded successfully.");
			} else {
				System.out.println("Failed to upload file.");
			}
		} catch (IOException exception) {
			System.err.println("Error: " + exception.getMessage());
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException exception) {
				System.err.println("Error: " + exception.getMessage());
			}
		}
	}

	/**
	 * Delete files
	 * Usage: | deleteFiles|File[] files|
	 * @param files
	 *            - list of files
	 * @throws IOException
	 */
	public static void deleteFiles(File[] files) throws IOException {
		for (File file : files) {
			System.out.println("Deleting file: " + file.getAbsolutePath());
			FileUtils.forceDelete(file);
		}
	}

	/**
	 * Replace file content in give file with new content
	 * Usage: | replaceFileContent| filePath| source | target |
	 * @param filePath
	 *            - file to replace content in.
	 * @param sourceString
	 *            - content to replace
	 * @param targetString
	 *            - new content at source string
	 * @return
	 * @throws IOException
	 */
	public static void replaceFileContent(String filePath, String sourceString,
			String targetString) throws IOException {

		System.out.println("Replacing file content " + sourceString + " with "
				+ targetString + " in file " + filePath);
		File file = new File(filePath);
		// We are using the canWrite() method to check whether we can
		// modified file content.
		if (file.canWrite()) {
			System.out.println("File is writable!");
		} else {
			System.out.println("File is in read only mode!");
		}
		// Now make our file writable
		file.setWritable(true);
		if (file.canWrite()) {
			System.out.println("Changed file to writable!");
		}
		String fileContents = FileUtils.readFileToString(file);
		String newFileContencts = fileContents.replace(sourceString,
				targetString);
		FileUtils.writeStringToFile(file, newFileContencts);
	}

	/**
	 * Append given content to file
	 * Usage: | appendFileContent| filePath| data|
	 * @param filePath
	 *            - file to append content to.
	 * @param data
	 *            - content to append
	 * @throws IOException
	 */
	public static void appendFileContent(String filePath, String data)
			throws IOException {
		System.out.println("Appending file content " + data + " to file " + filePath);
		File file = new File(filePath);
		// We are using the canWrite() method to check whether we can
		// modified file content.
		if (file.canWrite()) {
			System.out.println("File is writable!");
		} else {
			System.out.println("File is in read only mode!");
		}
		// Now make our file writable
		file.setWritable(true);
		if (file.canWrite()) {
			System.out.println("Changed file to writable!");
		}
		FileUtils.writeStringToFile(file, data);
	}

	/**
	 * 
	 * Usage: | appendFileContentOnNLIfNotExists| filePath| data|
	 * @param filePath
	 *            : Path of the File whose data is to be appended
	 * @param data
	 *            : Data to be appended on the new line of the file
	 * @throws IOException
	 *             : throws I/O exceptions
	 */

	public static void appendFileContentOnNLIfNotExists(String filePath,
			String data) throws IOException {
		System.out.println("Appending file content " + data + " to file " + filePath);
		File file = new File(filePath);
		// We are using the canWrite() method to check whether we can
		// modified file content.
		if (file.canWrite()) {
			System.out.println("File is writable!");
		} else {
			System.out.println("File is in read only mode!");
		}
		// Now make our file writable
		file.setWritable(true);
		if (file.canWrite()) {
			System.out.println("Changed file to writable!");
		}
		String fileContents = FileUtils.readFileToString(file);
		Boolean contentExists = fileContents.contains(data);
		if (!contentExists) {
			FileUtils.writeStringToFile(file, "\n");
			FileUtils.writeStringToFile(file, data);

			System.out.println("File is updated!");
		}

	}

	/**
	 * get file content in give file with new content
	 * Usage: | getFileContent| filePath|
	 * @param filePath
	 *            - file to replace content in.
	 * @return
	 * @throws IOException
	 */
	public static String getFileContent(String filePath) throws IOException {
		String fileContents = "";
		System.out.println("getting file content from file " + filePath);
		File file = new File(filePath);
		// We are using the canWrite() method to check whether we can
		// modified file content.
		if (file.canRead()) {
			System.out.println("File is Readable!");
		} else {
			System.out.println("File is NOT in read only mode!");
			file.setReadable(true);
			System.out.println("Changed file to Readable!");
		}
		fileContents = FileUtils.readFileToString(file);
		return fileContents;
	}
	
	/**
	 * Usage: | createFileFromContent| fileName| data| format|
	 * @param fileName
	 *              - Mention with Path or WithoutPath
	 * @param data
	 *              - Data which will be added to file
	 * @param format
	 *               - like UTF-8 , ..
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	
	
	public static void createFileFromContent(String fileName,String data,String format) 
			throws FileNotFoundException, UnsupportedEncodingException {
		
		PrintWriter writer = new PrintWriter(fileName, format);
		writer.println(data);
		writer.close();
		
	}
	
	/**
	 * Usage: | readPdfFile| filePath|
	 * @param filePath
	 *             - Mention the path of PDF file
	 * @return
	 * @throws IOException
	 */
	
	
	public static String readPdfFile(String filePath) throws IOException {
		
		  File file = new File(filePath);
	      PDDocument document = PDDocument.load(file);
	      PDFTextStripper pdfStripper = new PDFTextStripper();
	      String text = pdfStripper.getText(document);
	      document.close();
	      
	      //return text.replaceAll("\r\n|\\r|\\n", " ").trim();
	      return StringUtils.normalizeSpace(text);
		
	}
			
	/**
	 * get last modified file.
	 * Usage: | getLastModifiedFilePath| dirPath|
	 * @param dirPath
	 *            - directory path.
	 * @return last modified file from the directory
	 */
	public static String getLastModifiedFilePath(String dirPath) {
		File fileName = new File(dirPath);
		File[] files = fileName.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastModified = Long.MIN_VALUE;
		File lastModifiedFile = null;
		for (File file : files) {
			if (file.lastModified() > lastModified) {
				lastModifiedFile = file;
				lastModified = file.lastModified();
			}
		}
		return lastModifiedFile.getAbsolutePath();
	}

	/**
	 * get last modified file.
	 * Usage: | getLastModifiedFile| dirPath|
	 * @param dirPath
	 *            - directory path.
	 * @return last modified file from the directory
	 */
	public static File getLastModifiedFile(String dirPath) {
		File fileName = new File(dirPath);
		File[] files = fileName.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastModified = Long.MIN_VALUE;
		File lastModifiedFile = null;
		for (File file : files) {
			if (file.lastModified() > lastModified) {
				lastModifiedFile = file;
				lastModified = file.lastModified();
			}
		}
		return lastModifiedFile;
	}
	
	/**
	 * Rename Source file with Destination name 
	 * Usage: | fileRename| sourcePath| descPath|
	 * @param sourcePath
	 * @param descPath
	 */
	
	public static void fileRename(String sourcePath,String descPath) {
		
		 File source = new File(sourcePath); 
		 File dest = new File(descPath); 
		
		 source.renameTo(dest);
		
	}

	/**
	 * Unzip files
	 * Usage: | unzipFile| zipFile| extractTo|
	 * @param zipFile
	 * @param extractTo
	 * @throws IOException
	 */
	public static void unzipFile(String zipFile, String extractTo)
			throws IOException {

		byte[] buffer = new byte[1024 * 5];

		File folder = new File(extractTo);
		if (!folder.exists()) {
			folder.mkdir();
		}

		ZipInputStream ziStream = new ZipInputStream(new FileInputStream(
				zipFile));
		ZipEntry zEntity = ziStream.getNextEntry();

		while (zEntity != null) {
			if (zEntity.isDirectory()) {
				zEntity = ziStream.getNextEntry();
				continue;
			}

			String fileName = zEntity.getName();
			File newFile = new File(extractTo + File.separator + fileName);
			new File(newFile.getParent()).mkdirs();
			FileOutputStream foStream = new FileOutputStream(newFile);
			int len;
			while ((len = ziStream.read(buffer)) > 0) {
				foStream.write(buffer, 0, len);
			}

			foStream.close();
			zEntity = ziStream.getNextEntry();
		}
		ziStream.closeEntry();
		ziStream.close();
	}

	/**
	 * Gets files from the sourceFolder and makes a zip in the destination
	 * folder.
	 * Usage: | zipFile| sourceFolder| outputZipFolder| outputZipFileName|
	 * @param sourceFolder
	 * @param outputZipFolder
	 * 
	 */
	public static void zipFile(String sourceFolder, String outputZipFolder,
			String outputZipFileName) {
		boolean isFoldersValidated = false;
		File zipDestinationFolder = null;
		if (new File(sourceFolder).isDirectory()) {
			zipDestinationFolder = new File(outputZipFolder);
			if (!zipDestinationFolder.isDirectory()) {
				if (zipDestinationFolder.mkdirs()) {
					System.out.println("Destination path created successfully.");
				}
			}
			isFoldersValidated = true;
		} else {
			isFoldersValidated = false;
		}

		if (isFoldersValidated) {
			System.out.println("Provided paths have been located.");
			setZipSourceFolder(sourceFolder);
			listOfFileToZip = new ArrayList<String>();
			generateFileStructure(new File(sourceFolder));
			zipFiles(zipDestinationFolder.getAbsolutePath() + File.separator
					+ outputZipFileName);
			System.out.println("Creating zipfile.");
		} else {
			System.out.println("Provided paths could not be found.");
		}
	}

	/**
	 * Get the source folder path for current file in use.
	 */
	private static String getZipSourceFolder() {
		return ZIP_SOURCE_FOLDER;
	}

	/**
	 * Set the source folder path for current file in use.
	 */
	private static void setZipSourceFolder(String sourceFolder) {
		ZIP_SOURCE_FOLDER = sourceFolder;
	}

	/**
	 * Traverse a directory and get all files, and add the file into fileList
	 * 
	 * @param node
	 *            file or directory
	 */
	private static void generateFileStructure(File node) {

		// add file only
		if (node.isFile()) {
			listOfFileToZip.add(getRelativeFilepath(node.getAbsoluteFile()
					.toString()));
		}
		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileStructure(new File(node, filename));
			}
		}
	}

	/**
	 * Format the file path for zip
	 * 
	 * @param file
	 *            file path
	 * @return Formatted file path
	 */
	private static String getRelativeFilepath(String file) {
		return file.substring(getZipSourceFolder().length() + 1, file.length());
	}

	/**
	 * Zip the list of files from the <code>sourceFolder</code>
	 * 
	 * @param zipFileFullPath
	 *            output ZIP file location
	 */
	private static void zipFiles(String zipFileFullPath) {

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(zipFileFullPath);
			ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("Output to Zip : " + zipFileFullPath);

			for (String file : FileUtil.listOfFileToZip) {

				System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(ZIP_SOURCE_FOLDER
						+ File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			zos.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * To download files from URL
	 * Usage: | downloadFileFromURL| webUrl|fileName|
	 * @param webUrl
	 * @param fileName
	 * @throws MalformedURLException
	 */
	public static void downloadFileFromURL(String webUrl, String fileName) throws MalformedURLException {

		URL url = new URL(webUrl);

		try (InputStream in = url.openStream()) {
			Files.copy(in, Paths.get(fileName),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			
		}
	}
}
