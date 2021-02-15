package com.example.restful.controllers;

import com.example.restful.data.Item;
import com.example.restful.data.ItemRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ItemController {
    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/item")
    public List<Item> getItems() {
        return repository.findAll();
    }

    @GetMapping("/item/{id}")
    public Item getItem(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @PostMapping("/item")
    public Item postItem(@RequestBody Item item) {
        return repository.save(item);
    }

    @PutMapping("/item/{id}")
    public Item putItem(@PathVariable Long id, @RequestBody Item item) {
        return repository.findById(id)
                .map(dbItem -> {
                    dbItem.setName(item.getName());
                    return repository.save(dbItem);
                })
                .orElseGet(() -> {
                    item.setId(id);
                    return repository.save(item);
                });
    }

    @DeleteMapping("/item/{id}")
    public void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
