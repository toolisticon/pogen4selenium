package io.toolisticon.pogen4selenium.runtime;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DownloadManager {

	private final File downloadFolder;
	
	private DownloadManager(File downloadFolder) {
		
		if (downloadFolder == null || !downloadFolder.isDirectory()) {
			throw new IllegalArgumentException("Passed file must not be null and represent a drirectory");
		}
		
		this.downloadFolder = downloadFolder;
	}
	
	public File findDownload (String nameRegex) {
		
		List<File> matchingFiles = Arrays.stream(this.downloadFolder.listFiles())
			.filter(e -> !e.isDirectory())
			.filter(e -> e.getName().matches(nameRegex))
			.collect(Collectors.toList());
		
		return matchingFiles.isEmpty() ? null : matchingFiles.get(0);	
		
	}
	
	public static DownloadManager get(File downloadFolder) {
		return new DownloadManager(downloadFolder);
	}
	
}
