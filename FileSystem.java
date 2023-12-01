import java.util.Scanner;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.nio.file.Path;
import java.nio.file.Paths;

class User {
    private String name;
    private boolean isDirectory;
    private User parent;
    private List<User> subDirectories;
    private String content; // For file content
    private static User currentDirectory;
    private static User rootDirectory;

    // Constructor for creating a directory
    User(User parent, String name, boolean isDirectory) {
        this.name = name;
        this.parent = parent;
        this.isDirectory = isDirectory;
        this.subDirectories = new ArrayList<>();
        this.content = ""; // Initialize content for directories
        if (parent != null) {
            parent.addSubDirectory(this);
        }
    }

    // Constructor for creating a file with content
    User(User parent, String name, boolean isDirectory, String content) {
        this(parent, name, isDirectory);
        if (!isDirectory) {
            this.content = content;
        }
    }

    // Getters and setters

    String getName() {
        return name;
    }

    boolean isDirectory() {
        return isDirectory;
    }

    User getParent() {
        return parent;
    }

    List<User> getSubDirectories() {
        return subDirectories;
    }

    String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }

    // Add a subdirectory to the current directory
    void addSubDirectory(User directory) {
        subDirectories.add(directory);
    }

    // Getters and setters for the static fields

    static User getCurrentDirectory() {
        return currentDirectory;
    }

    static void setCurrentDirectory(User currentDirectory) {
        User.currentDirectory = currentDirectory;
    }

    static User getRootDirectory() {
        return rootDirectory;
    }

    static void setRootDirectory(User rootDirectory) {
        User.rootDirectory = rootDirectory;
    }

    // Other utility methods...

    // Find a User by its path
    Path getPath() {
        if (parent == null) {
            return Paths.get(name);
        } else {
            return parent.getPath().resolve(name);
        }
    }

    boolean isRoot() {
        return parent == null;
    }

    static User getByPath(Path path) {
        // Implementation of finding a User by its path
        // You may need to implement this based on your directory structure
        return null;
    }

    // Additional methods...

    // For demonstration purposes
    long getSize() {
        // Implementation of getting the size of a file or directory
        // You may need to implement this based on your requirements
        return 0;
    }

    // For demonstration purposes
    boolean isFile() {
        // Implementation of checking if a User is a file
        // You may need to implement this based on your requirements
        return !isDirectory;
    }
}

public class FileSystem {
    public static void main(String[] args) {

        User rootNode = new User(null, "root", true);
        User.setCurrentDirectory(rootNode);
        User.setRootDirectory(rootNode);

        Scanner scan = new Scanner(System.in);
        while (true) {

            try {
                System.out.print("prompt> ");
                String userInput = scan.nextLine();
                String[] splitting = userInput.split(" ");

                switch (splitting[0]) {
                    case "create":
                        String newFileName = splitting[1];
                        if (fileOrDirectoryExists(newFileName)) {
                            System.out.println("ERROR: File or directory with the same name already exists.");
                        } else {
                            createFile(newFileName);
                            System.out.println("File created: " + newFileName);
                        }
                        break;

                    case "cat":
                        String catFileName = splitting[1];
                        String fileContent = readFileContents(catFileName);
                        if (fileContent != null) {
                            System.out.println(fileContent);
                        } else {
                            System.out.println("ERROR: Unable to read file " + catFileName);
                        }
                        break;

                    case "rm":
                        String rmFileName = splitting[1];
                        boolean fileRemoved = removeFile(rmFileName);
                        if (fileRemoved) {
                            System.out.println("File removed: " + rmFileName);
                        } else {
                            System.out.println("ERROR: Unable to remove file " + rmFileName);
                        }
                        break;

                    case "mkdir":
                        User newDirectory = mkdir(User.getCurrentDirectory(), splitting[1]);
                        if (newDirectory != null) {
                            System.out.println("Directory created: " + newDirectory.getName());
                        }
                        break;

                    case "rmdir":
                        if (rmdir(splitting[1])) {
                            System.out.println("Directory removed: " + splitting[1]);
                        } else {
                            System.out.println("ERROR: Unable to remove directory " + splitting[1]);
                        }
                        break;

                    case "cd":
                        cd(splitting[1]);
                        break;

                    case "ls":
                        ls();
                        break;

                    case "du":
                        long totalSize = calculateTotalSize(User.getCurrentDirectory());
                        System.out.println("Total disk usage: " + totalSize + " bytes");
                        break;

                    case "pwd": // done
                        pwd();
                        break;

                    case "find": // done
                        if (splitting.length < 2) {
                            System.out.println("ERROR: Please provide a target name for the find command.");
                        } else {
                            find(splitting[1]);
                        }
                        break;

                    case "exit": // done
                        System.out.println("Exiting the FileSystem program.");
                        System.exit(0);
                        break;

                    default:
                        System.err
                                .println("ERROR : incorrect command or file/directory name not entered or other error");
                        System.out.print("prompt> ");
                        userInput = scan.nextLine();
                        splitting = userInput.split(" ");
                }

            } finally {
                // Close the scanner in the finally block to ensure it's always closed
                scan.close();
            }

        }

    }

    private static boolean fileOrDirectoryExists(String name) {
        User currentDirectory = User.getCurrentDirectory();
        List<User> filesAndDirectories = currentDirectory.getSubDirectories();

        for (User fileOrDirectory : filesAndDirectories) {
            if (fileOrDirectory.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private static void createFile(String fileName) {
        User currentDirectory = User.getCurrentDirectory();
        User newFile = new User(currentDirectory, fileName, false);

        // Read characters from the keyboard input until a tilde (~) is encountered
        StringBuilder contentBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the content of the file. Type '~' to finish.");

            while (true) {
                String line = scanner.nextLine();
                if (line.contains("~")) {
                    line = line.substring(0, line.indexOf("~"));
                    if (line.isEmpty()) {
                        break; // Empty line after tilde indicates the end of content
                    }
                    contentBuilder.append(line);
                    break;
                }
                contentBuilder.append(line).append("\n");
            }
        } // Scanner will be closed automatically here

        // Ensure contentBuilder is not empty before creating a file
        if (contentBuilder.length() == 0) {
            System.out.println("ERROR: File not created. No content provided.");
            return;
        }

        newFile.setContent(contentBuilder.toString());
        currentDirectory.getSubDirectories().add(newFile);
    }

    private static String readFileContents(String fileName) {
        User currentDirectory = User.getCurrentDirectory();
        List<User> files = currentDirectory.getSubDirectories();

        for (User file : files) {
            if (file.getName().equals(fileName) && file.isFile()) {
                // Simulating reading the content of the file
                // Replace this with your actual file reading logic
                return "File content of " + fileName;
            }
        }

        return null;
    }

    private static boolean removeFile(String fileName) {
        User currentDirectory = User.getCurrentDirectory();
        List<User> files = currentDirectory.getSubDirectories();

        Iterator<User> iterator = files.iterator();
        while (iterator.hasNext()) {
            User file = iterator.next();
            if (file.getName().equals(fileName) && file.isFile()) {
                iterator.remove();
                return true;
            }
        }

        System.out.println("ERROR: Unable to remove file " + fileName + ". File not found or is not a file.");
        return false;
    }

    private static User mkdir(User parent, String dirName) {

        if (directoryExists(parent, dirName)) {
            System.out.println("ERROR: Directory or file with the same name already exists.");
            return null;
        }

        User newDirectory = new User(parent, dirName, true);
        parent.getSubDirectories().add(newDirectory);
        return newDirectory;
    }

    private static boolean directoryExists(User parent, String dirName) {

        List<User> subDirectories = parent.getSubDirectories();
        for (User entry : subDirectories) {
            if (entry.getName().equals(dirName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean rmdir(String dirName) {
        User currentDirectory = User.getCurrentDirectory();
        List<User> subDirectories = currentDirectory.getSubDirectories();

        // Handle the case of an empty directory
        if (subDirectories == null || subDirectories.isEmpty()) {
            System.out.println("ERROR: Nothing inside the folder.");
            return false; // Return false if no directories are present
        }

        // Use a case-insensitive check for directory name
        Iterator<User> iterator = subDirectories.iterator();
        while (iterator.hasNext()) {
            User file = iterator.next();
            if (file.getName().equalsIgnoreCase(dirName) && file.isDirectory()) {
                iterator.remove();
                System.out.println("Deleted the directory");
                return true; // Return true if directory is successfully removed
            }
        }

        // Provide a more specific error message
        System.out.println("ERROR: Directory '" + dirName + "' not found or not a directory");
        return false; // Return false if directory is not found or not a directory
    }

    private static void cd(String dirName) {
        User currentDirectory = User.getCurrentDirectory();

        // Special case: If "dirName" is "/", set the current directory to the root
        // directory
        if (dirName.equals("/")) {
            User.setCurrentDirectory(User.getRootDirectory());
            System.out.println("Current directory set to root: " + User.getCurrentDirectory().getName());
            return;
        }

        // Special case: If "dirName" is "..", set the current directory to its parent
        if (dirName.equals("..")) {
            User parentDirectory = currentDirectory.getParent();
            if (parentDirectory != null) {
                User.setCurrentDirectory(parentDirectory);
                System.out.println("Current directory set to parent: " + User.getCurrentDirectory().getName());
            } else {
                System.out.println("ERROR: Already at the root directory.");
            }
            return;
        }

        // Use java.nio.file.Path for better path manipulation
        Path newPath = Paths.get(dirName);
        Path resolvedPath = currentDirectory.getPath().resolve(newPath).normalize();

        // Validate the resolved path
        if (!resolvedPath.startsWith(User.getRootDirectory().getPath())) {
            System.out.println("ERROR: Invalid path specified.");
            return;
        }

        // Find the specified directory in the resolved path
        User foundDirectory = User.getByPath(resolvedPath);

        // If the specified directory is found, set the current directory to it
        if (foundDirectory != null && foundDirectory.isDirectory()) {
            User.setCurrentDirectory(foundDirectory);
            System.out.println("Current directory set: " + User.getCurrentDirectory().getName());
        } else {
            // If the specified directory is not found or not a directory, print an error
            System.out.println("ERROR: Directory not found or not a directory");
        }
    }

    private static void ls() {
        User currentDirectory = User.getCurrentDirectory();
        List<User> fileList = currentDirectory.getSubDirectories();

        if (fileList == null || fileList.isEmpty()) {
            System.out.println("Empty directory");
            return;
        }

        // Separate files and directories
        List<User> directories = new ArrayList<>();
        List<User> files = new ArrayList<>();

        for (User file : fileList) {
            if (file.isDirectory()) {
                directories.add(file);
            } else {
                files.add(file);
            }
        }

        // Sort the lists alphabetically
        directories.sort(Comparator.comparing(User::getName));
        files.sort(Comparator.comparing(User::getName));

        // Print directories with "(*)" after each
        for (User directory : directories) {
            System.out.println(directory.getName() + " (*)");
        }

        // Print files
        for (User file : files) {
            System.out.println(file.getName());
        }
    }

    private static void du() {
        User currentDirectory = User.getCurrentDirectory();
        long totalSize = calculateTotalSize(currentDirectory);
        System.out.println("Total disk usage: " + totalSize + " bytes");
    }

    private static long calculateTotalSize(User directory) {
        long totalSize = 0;

        // Iterate through files and subdirectories
        for (User file : directory.getSubDirectories()) {
            if (file.isDirectory()) {
                // If it's a directory, recursively calculate its size
                totalSize += calculateTotalSize(file);
            } else if (file.isFile()) {
                // If it's a file, add its size to the total
                totalSize += file.getSize();
            }
        }

        return totalSize;
    }

    private static void pwd() {
        User currentDirectory = User.getCurrentDirectory();
        String fullPath = getFullPath(currentDirectory);
        System.out.println("Current directory path: " + fullPath);
    }

    private static String getFullPath(User directory) {
        // Base case: If the directory is the root, return "/"
        if (directory.isRoot()) {
            return "/";
        }

        // Recursive case: Concatenate the directory name with the parent's path
        String parentPath = getFullPath(directory.getParent());
        return parentPath.equals("/") ? parentPath + directory.getName() : parentPath + "/" + directory.getName();
    }

    private static void find(String targetName) {
        User currentDirectory = User.getCurrentDirectory();
        searchAndPrint(currentDirectory, targetName);
    }

    private static void searchAndPrint(User directory, String targetName) {
        // Check if the current directory contains a file or directory with the target
        // name
        for (User file : directory.getSubDirectories()) {
            if (file.getName().equals(targetName)) {
                // Print the full path if a match is found
                System.out.println(getFullPath(file));
            }

            // Recursively search in subdirectories
            if (file.isDirectory()) {
                searchAndPrint(file, targetName);
            }
        }
    }

}
//
/*

       public void addAtStart(String s){
        if (headPtr == null){
            StringNode temp = new StringNode(s);
            headPtr = temp;
        } else{
            StringNode temp = new StringNode(s);
            temp.nextObj = headPtr;
            headPtr = temp;
         }
       }

       public void addAtEnd(String s){
        StringNode newObj  = new StringNode(s);
        if (headPtr == null){
            headPtr = newObj;
        } else{
            StringNode temp = headPtr;
            while (temp.nextObj!= null){
                temp = temp.nextObj;

            }
            temp.nextObj = headPtr;
        }
       }

       public void addAfter(String newVal, String afterVal){
        StringNode newObj = new StringNode(newVal);
        if( headPtr == null){
            headPtr = newObj;
        }
        else{
            StringNode temp = headPtr;
            while (temp.nextObj!= null && !temp.value().equals(afterVal)){
                temp = temp.nextObj;
            }
            newObj.nextObj = temp.nextObj;
            temp.nextObj = newObj;
        }
       }

    


 */
//