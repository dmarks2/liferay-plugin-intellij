package de.dm.intellij.liferay.language.poshi.annotation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class PoshiValidatorRunnerParser {

	public static void parseOutput(String output, PoshiExternalValidationHost validationHost) {
		String message = null;
		boolean startErrors = false;
		boolean startMessage = false;

		try (BufferedReader bufferedReader = new BufferedReader(new StringReader(output))) {
			String line = bufferedReader.readLine();;

			while (line != null){
				if (startErrors) {
					if (line.isBlank()) {
						message = null;

						startMessage = true;
					} else {
						if (message != null) {
							if (line.lastIndexOf(':') > -1) {
								String filePath = line.substring(1, line.lastIndexOf(':'));

								int lineNumber = Integer.parseInt(line.substring(line.lastIndexOf(':') + 1));

								validationHost.addError(message, filePath, lineNumber);

								message = null;
							}
						} else if (startMessage) {
							if (line.endsWith("at:")) {
								message = line.substring(0, line.length() - 4);

								startMessage = false;
							}
						}
					}
				}

				if (line.endsWith("errors in POSHI")) {
					startErrors = true;
				}

				line = bufferedReader.readLine();
			}
		} catch (IOException ioException) {
			//unable to parse?
		}
	}
}
