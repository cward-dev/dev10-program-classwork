package learn.hodgepodge;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class HodgepodgeController {

    @GetMapping("/name")
    public String myName() {
        return "Chris Ward";
    }

    @GetMapping("/current/time")
    public LocalDateTime whatTimeIsIt() {
        return LocalDateTime.now();
    }

    @GetMapping("/greet/{name}")
    public String greeting(@PathVariable String name) {
        return String.format("Hello, %s!", name);
    }

    static int sheepCount = 0;

    @GetMapping("/sheep")
    public int howManySheep() {
        return sheepCount;
    }

    @PutMapping("/sheep/{amount}")
    public void increaseSheepByAmount(@PathVariable int amount) {
        sheepCount += amount;
    }

    @PostMapping("/sheep")
    public void increaseSheepWithObject(@RequestBody SheepValue value) {
        sheepCount += value.getAmount();
    }

    @DeleteMapping("/sheep")
    public ResponseEntity<Void> lostSheep() {
        if (sheepCount > 0) {
            sheepCount--;
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    static ArrayList<String> todos = new ArrayList<>();

    @GetMapping("/todo")
    public List<String> toDos() {
        return todos;
    }

    @PutMapping("/todo")
    public void toDoBulkUpload(@RequestBody List<String> items) {
        todos.addAll(items);
    }

    @PutMapping("/todo/{item}")
    public void toDoAddOne(@PathVariable String item) {
        todos.add(item);
    }

    @DeleteMapping("/todo/{index}")
    public ResponseEntity<Void> toDoDeleteAtIndex(@PathVariable int index) {
        if (index <= 0) {
            return ResponseEntity.notFound().build();
        }

        if (todos.size() > index) {
            todos.remove(index - 1);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/todo")
    public void toDoReplaceList(@RequestBody List<String> items) {
        todos.clear();
        todos.addAll(items);
    }
}
