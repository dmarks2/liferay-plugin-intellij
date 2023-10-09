package de.dm.intellij.liferay.language.poshi.annotation;

import com.intellij.execution.process.ProcessOutput;

public class PoshiValidatorRunnerParser {

	public static void parseOutput(ProcessOutput output, PoshiExternalValidationHost validationHost) {
		parseStdOut(output, validationHost);
		parseStdErr(output, validationHost);
	}

	private static void parseStdErr(ProcessOutput output, PoshiExternalValidationHost validationHost) {
		String message = null;

		for (String line : output.getStderrLines(false)) {
			if (line.startsWith("WARNING:")) {
				message = line.substring(9, line.length() - 4);
			} else if (message != null) {
				if (line.lastIndexOf(':') > -1) {
					String filePath = line.substring(1, line.lastIndexOf(':'));

					int lineNumber = Integer.parseInt(line.substring(line.lastIndexOf(':') + 1));

					validationHost.addWarning(message, filePath, lineNumber);

					message = null;
				}
			}
		}
	}

	private static void parseStdOut(ProcessOutput output, PoshiExternalValidationHost validationHost) {
		String message = null;
		boolean startErrors = false;
		boolean startMessage = false;
		boolean atMessage = false;

		for (String line : output.getStdoutLines(false)) {
			if (startErrors) {
				if (line.isBlank()) {
					message = null;

					startMessage = true;
				} else {
					if (message != null) {
						if (line.lastIndexOf(':') > -1) {
							String filePath;

							if (atMessage) {
								filePath = line.substring(1, line.lastIndexOf(':'));
							} else {
								filePath = line.substring(0, line.lastIndexOf(':'));
							}

							int lineNumber = Integer.parseInt(line.substring(line.lastIndexOf(':') + 1));

							validationHost.addError(message, filePath, lineNumber);

							message = null;
						}
					} else if (startMessage) {
						if (line.endsWith("at:")) {
							message = line.substring(0, line.length() - 4);

							atMessage = true;
						} else {
							message = line;

							atMessage = false;
						}

						startMessage = false;
					}
				}
			}

			if (line.endsWith("errors in POSHI")) {
				startErrors = true;
			}

		}
	}
}
