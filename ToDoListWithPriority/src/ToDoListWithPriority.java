import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.TreeMap;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Comparator;


class Task {
    String name;
    int priority;

    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{name='" + name + "', priority=" + priority + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if =(this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return priority == task.priority && Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priority);
    }
}

public class ToDoListWithPriority {

    private TreeMap<Integer, PriorityQueue<Task>> taskList;
    private HashSet<Task> completedTasks;

    public ToDoListWithPriority() {
        taskList = new TreeMap<>();
        completedTasks = new HashSet<>();
    }

    public void addTask(String taskName, int priority) {
        Task task = new Task(taskName, priority);
        taskList.putIfAbsent(priority, new PriorityQueue<>(Comparator.comparingInt((Task t) -> t.priority).reversed()));
        taskList.get(priority).add(task);
        System.out.println("Task added: " + task);
    }

    public void displayTasks() {
        System.out.println("Current To-Do List (sorted by priority):");
        if (taskList.isEmpty()) {
            System.out.println("No tasks in the to-do list.");
        } else {
            for (Map.Entry<Integer, PriorityQueue<Task>> entry : taskList.entrySet()) {
                for (Task task : entry.getValue()) {
                    if (!completedTasks.contains(task)) {
                        System.out.println(task);
                    }
                }
            }
        }
    }

    public void displayCompletedTasks() {
        System.out.println("Completed Tasks:");
        if (completedTasks.isEmpty()) {
            System.out.println("No completed tasks.");
        } else {
            for (Task task : completedTasks) {
                System.out.println(task);
            }
        }
    }

    public void markTaskCompleted(String taskName) {
        for (Map.Entry<Integer, PriorityQueue<Task>> entry : taskList.entrySet()) {
            Iterator<Task> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.name.equals(taskName)) {
                    completedTasks.add(task);
                    iterator.remove();
                    System.out.println("Task completed: " + task);
                    return;
                }
            }
        }
        System.out.println("Task not found: " + taskName);
    }

    public void removeTask(String taskName) {
        for (Map.Entry<Integer, PriorityQueue<Task>> entry : taskList.entrySet()) {
            Iterator<Task> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.name.equals(taskName)) {
                    iterator.remove();
                    completedTasks.remove(task);
                    System.out.println("Task removed: " + task);
                    return;
                }
            }
        }
        System.out.println("Task not found: " + taskName);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ToDoListWithPriority toDoList = new ToDoListWithPriority();

        while (true) {
            System.out.println("\nTo-Do List Menu:");
            System.out.println("0: See current to-do list");
            System.out.println("1: Add a task with priority");
            System.out.println("2: Mark a task as completed");
            System.out.println("3: See completed tasks");
            System.out.println("4: Delete a task completely");
            System.out.println("5: Exit");
            System.out.print("Enter your choice: ");

            int choice = getValidChoice(scanner);

            switch (choice) {
                case 0:
                    toDoList.displayTasks();
                    break;
                case 1:
                    String taskName = getTaskName(scanner);
                    int priority = getValidPriority(scanner);
                    toDoList.addTask(taskName, priority);
                    break;
                case 2:
                    String completedTaskName = getTaskName(scanner);
                    toDoList.markTaskCompleted(completedTaskName);
                    break;
                case 3:
                    toDoList.displayCompletedTasks();
                    break;
                case 4:
                    String deleteTaskName = getTaskName(scanner);
                    toDoList.removeTask(deleteTaskName);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static int getValidChoice(Scanner scanner) {
        int choice = -1;
        while (true) {
            try {
                choice = scanner.nextInt();
                if (choice < 0 || choice > 5) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 5.");
                    scanner.nextLine();
                } else {
                    scanner.nextLine();
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return choice;
    }

    private static String getTaskName(Scanner scanner) {
        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();
        while (taskName.isEmpty()) {
            System.out.println("Error: Task name cannot be empty.");
            System.out.print("Enter task name: ");
            taskName = scanner.nextLine();
        }
        return taskName;
    }

    private static int getValidPriority(Scanner scanner) {
        int priority = -1;
        while (true) {
            System.out.print("Enter priority (lower number means higher priority): ");
            try {
                priority = scanner.nextInt();
                scanner.nextLine();
                if (priority <= 0) {
                    System.out.println("Error: Priority must be a positive integer.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number for priority.");
                scanner.nextLine();
            }
        }
        return priority;
    }
}
