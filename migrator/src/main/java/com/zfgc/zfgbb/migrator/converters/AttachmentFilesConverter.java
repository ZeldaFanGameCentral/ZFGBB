package com.zfgc.zfgbb.migrator.converters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFAttachmentsDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFAttachmentsDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFAttachmentsDbMapper;

@Component
public class AttachmentFilesConverter {

	private static final Logger logger = LoggerFactory.getLogger(AttachmentFilesConverter.class);

	@Autowired
	private SMFAttachmentsDbMapper smfAttachmentsMapper;

	@Value("${zfgbb.migrator.attachments.source-path:}")
	private String sourcePath;

	@Value("${zfgbb.migrator.attachments.target-path:}")
	private String targetPath;

	public void convertToZfgbb() throws IOException {
		if (sourcePath == null || sourcePath.isBlank() || targetPath == null || targetPath.isBlank()) {
			throw new IllegalStateException(
					"zfgbb.migrator.attachments.source-path and target-path must both be set");
		}

		Path source = Paths.get(sourcePath);
		Path target = Paths.get(targetPath);
		Files.createDirectories(target);

		try (Stream<Path> files = Files.walk(source)) {
			files.filter(Files::isRegularFile)
					.filter(p -> {
						String name = p.getFileName().toString();
						return !name.contains("avatar")
								&& !name.equals("attachments")
								&& !name.equals(".gitignore");
					})
					.forEach(filePath -> {
						Cancellable.check();
						String fileName = filePath.getFileName().toString();
						int underscore = fileName.indexOf('_');
						if (underscore < 0) {
							logger.warn("skipping {} - no '_' in filename, can't extract hash", fileName);
							return;
						}
						String fileHash = fileName.substring(underscore + 1);

						SMFAttachmentsDbExample ex = new SMFAttachmentsDbExample();
						ex.createCriteria().andFileHashEqualTo(fileHash);
						SMFAttachmentsDb attachment = smfAttachmentsMapper.selectByExample(ex)
								.stream().findFirst().orElse(null);
						if (attachment == null) {
							logger.warn("no SMF attachment record for filehash {} (file {})", fileHash, fileName);
							return;
						}

						Path destination = target.resolve(attachment.getFilename());
						try {
							Files.copy(filePath, destination, StandardCopyOption.REPLACE_EXISTING);
							logger.info("wrote {} -> {}", fileName, destination);
						} catch (IOException e) {
							throw new RuntimeException("failed to write " + destination, e);
						}
					});
		}
	}
}
