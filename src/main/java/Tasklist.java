import java.util.List;
import java.util.ArrayList;

public class Tasklist {
    private List<Task> tasks;

    public Tasklist(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Tasklist() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }
    
    public List<Task> getAllTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }
}