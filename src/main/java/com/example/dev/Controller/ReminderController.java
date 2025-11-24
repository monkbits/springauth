package com.example.dev.Controller;

import com.example.dev.Entities.Reminder;
import com.example.dev.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reminder")
public class ReminderController {
    @Autowired
    private ReminderService reminderService;

    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Reminder> addreminder(@RequestBody Reminder reminder){
        Reminder r = reminderService.addReminder(reminder);
        return ResponseEntity.ok(r);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<List<Reminder>> getReminders(){
        List <Reminder> r = reminderService.getReminders();
        return ResponseEntity.ok(r);
    }
    @RequestMapping(value = "/delete",method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Boolean> deleteReminder(@RequestParam String reminderId){
        boolean s = reminderService.deleteReminder(reminderId);
        return ResponseEntity.ok(s);
    }

    @RequestMapping(value = "/edit",method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Reminder> deleteReminder(@RequestBody HashMap<String,String> reminderDetails){
        Reminder remind = reminderService.updateReminder(reminderDetails);
        System.out.println(remind);
        return ResponseEntity.ok(remind);
    }
}
