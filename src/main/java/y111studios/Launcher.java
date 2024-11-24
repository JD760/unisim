package y111studios;

/**
 * Launcher class used to inject the required argument for the LibGDX to work on MacOS, while
 * retaining the ability to run the application on other operating systems.
 */
public abstract class Launcher {

    /**
     * Main method that launches the application. This method will attempt to launch the application
     * with the required argument for MacOS. If an exception occurs, the application will be
     * launched normally.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Get the path to the java home directory
            String javaHome = System.getProperty("java.home");
            // Get the path to the java binary
            String javaBin = javaHome + "/bin/java";
            // Get the classpath
            String classpath = System.getProperty("java.class.path");
            // Get the main class entry point
            String className = "y111studios.App";

            ProcessBuilder builder;
            // Handle windows uniquely to prevent issues with the required argument
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                // Pass in the argument
                builder = new ProcessBuilder(javaBin, "-XstartOnFirstThread", "-cp", classpath,
                        className);
            } else {
                builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
            }
            builder.inheritIO(); // Pass IO to new process
            // Begin process
            Process process = builder.start();
            // Wait for process to finish
            process.waitFor();
            // Exit with the same status code
            System.exit(process.exitValue());
        } catch (Exception e) {
            // If an exception occurs, try to run the application normally
            App.main(args);
        }
    }

}
