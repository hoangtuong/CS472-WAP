package model;

public class Task {

    private int id = 0;
    private String task;
    private String dueDate;
    private String category;
    private String priority;    //CRITICAL, URGENT, HIGH, NORMAL, LOW, LOWEST
    private int userId;
    private String status = "Created";

    public Task() {}

    public Task(int id, String task, String dueDate, String category, String priority, String status, int userId) {
        this.id = id;
        this.task = task;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.userId = userId;
    }

    public Task(String task, String dueDate, String category, String priority, String status, int userId) {
        this.task = task;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
