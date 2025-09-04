package de.dm.intellij.liferay.language.osgi.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.execution.DefaultExecutionResult;
import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class GogoShellRunProfileState implements RunProfileState {
    
    private final ExecutionEnvironment environment;
    private final GogoShellRunConfiguration configuration;
    
    public GogoShellRunProfileState(ExecutionEnvironment environment, GogoShellRunConfiguration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Nullable
    @Override
    public ExecutionResult execute(Executor executor, @NotNull ProgramRunner runner) throws ExecutionException {
        ConsoleView consoleView = createConsole();
        
        ProcessHandler processHandler = new GogoTelnetProcessHandler();
        consoleView.attachToProcess(processHandler);
        
        CompletableFuture.runAsync(() -> executeGogoCommand(consoleView, processHandler));
        
        return new DefaultExecutionResult(consoleView, processHandler);
    }

    private void executeGogoCommand(ConsoleView consoleView, ProcessHandler processHandler) {
		try (GogoShellClient client = new GogoShellClient(configuration.getHost(), configuration.getPort())) {
            ApplicationManager.getApplication().invokeLater(() -> {
                consoleView.print("Connected to " + configuration.getHost() + ":" + configuration.getPort() + "\n", 
                    ConsoleViewContentType.SYSTEM_OUTPUT);
                consoleView.print("Executing: " + configuration.getFullCommand() + "\n", 
                    ConsoleViewContentType.USER_INPUT);
            });

			String result = client.send(configuration.getFullCommand());

			ApplicationManager.getApplication().invokeLater(() -> {
				consoleView.print(result + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
			});

            ApplicationManager.getApplication().invokeLater(() -> {
                consoleView.print("Command executed successfully\n", ConsoleViewContentType.SYSTEM_OUTPUT);
                processHandler.destroyProcess();
            });
            
        } catch (IOException e) {
            ApplicationManager.getApplication().invokeLater(() -> {
                consoleView.print("Error: " + e.getMessage() + "\n", ConsoleViewContentType.ERROR_OUTPUT);
                processHandler.destroyProcess();
            });
        }
    }

	private ConsoleView createConsole() {
		return TextConsoleBuilderFactory.getInstance()
				.createBuilder(environment.getProject())
				.getConsole();
	}

    private static class GogoTelnetProcessHandler extends ProcessHandler {
        @Override
        protected void destroyProcessImpl() {
            notifyProcessTerminated(0);
        }

        @Override
        protected void detachProcessImpl() {
            destroyProcessImpl();
        }

        @Override
        public boolean detachIsDefault() {
            return false;
        }

        @Nullable
        @Override
        public OutputStream getProcessInput() {
            return null;
        }
    }
}
