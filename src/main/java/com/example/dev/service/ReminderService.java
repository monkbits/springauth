package com.example.dev.service;

import com.example.dev.Entities.Reminder;
import com.example.dev.repository.ReminderRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {
    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private Gson Gson;
    public Reminder addReminder(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    public List<Reminder> getReminders() {
        return reminderRepository.findAll();
    }

    public Boolean deleteReminder(String reminderId) {
        if(reminderRepository.existsById(Long.valueOf(reminderId))){
            reminderRepository.deleteById(Long.valueOf(reminderId));
            reminderRepository.flush();
            return true;
        }
        return false;
    }

    public Reminder updateReminder(HashMap<String, String> reminderDetails) {
        long id = Long.valueOf(reminderDetails.get("reminderId"));
        return reminderRepository.findById(id)
                .map(reminder ->{
                    Optional.ofNullable(reminderDetails.get("title"))
                            .filter(s -> !s.isEmpty())
                            .ifPresent(reminder::setTitle);
                    Optional.ofNullable(reminderDetails.get("description"))
                            .filter(s -> !s.isEmpty())
                            .ifPresent(reminder::setDescription);
                    Optional.ofNullable(reminderDetails.get("dueon"))
                            .filter(s -> !s.isEmpty())
                            .map(LocalDateTime::parse)
                            .ifPresent(reminder::setDue_on);
                    Optional.ofNullable(reminderDetails.get("status"))
                            .filter(s -> !s.isEmpty())
                            .map(Boolean::valueOf)
                            .ifPresent(reminder::setStatus);

                    System.out.println(Gson.toJson(reminder));
                    return reminderRepository.save(reminder);
                }).orElse(null);

    }
//    public Reminder updateReminder(HashMap<String, String> reminderDetails) {
//        try{
//            long id = Long.valueOf(reminderDetails.get("reminderId"));
//            return reminderRepository.findById(id)
//                    .map(reminder ->{
//                        String title        = reminderDetails.get("title");
//                        String description  = reminderDetails.get("description");
//                        String due_on       = reminderDetails.get("dueon");
//                        String status       = reminderDetails.get("status");
//
//                        if(title        != null || !title.isEmpty()         ) reminder.setTitle(title);
//                        if(description  != null || !description.isEmpty()   ) reminder.setDescription(description);
//                        if(due_on       != null || !due_on.isEmpty()        ) reminder.setDue_on(LocalDateTime.parse(due_on));
//                        if(status       != null || !status.isEmpty()        ) reminder.setStatus(Boolean.valueOf(status));
//
//                        return reminderRepository.save(reminder);
//                    }).orElse(null);
//
//        } catch (Exception e) {
////            throw new RuntimeException(e);
//        }
//        return null;
//    }

//  public Reminder updateReminder(Long id, Reminder reminderDetails) {
//      return reminderRepository.findById(id).map(reminder -> {
//          reminder.setTitle(reminderDetails.getTitle());
//          reminder.setDescription(reminderDetails.getDescription()); // rename desc -> description
//          reminder.setDueOn(reminderDetails.getDueOn());
//          reminder.setStatus(reminderDetails.getStatus() != null ? reminderDetails.getStatus() : reminder.getStatus());
//          return reminderRepository.save(reminder);
//      }).orElse(null);
//  }
}
