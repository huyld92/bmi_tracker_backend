/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

//import com.fu.bmi_tracker.model.entities.NotificationObject;
import com.fu.bmi_tracker.services.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BaoLG
 */
@Tag(name = "Notification", description = "Notification management APIs")
@RestController
@RequestMapping("api/test/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    /*
    @Autowired
    NotificationService notificationScheduleService;
    */
    
    /*
    @Operation(
            summary = "Create new Notification with form",
            description = "Create new Notification with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Notification.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewNotification(@RequestBody Notification notificationDetails) {

        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Notification notification = new Notification();
        notification.setTitile(notificationDetails.getTitile());
        notification.setContent(notificationDetails.getContent());
        notification.setCreatedTime(Instant.now());
        //notification.setAccountID(principal.getId());
        //notification.setIsRead(Boolean.TRUE);
        //notification.setIsActive(Boolean.TRUE);

        Notification notificationSave = notificationService.save(notification);
        if (notificationSave == null) {
            return new ResponseEntity<>("Failed to create new notification", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(notificationSave, HttpStatus.CREATED);
    }
    */
     /*
    @Operation(
            summary = "Get All Notification",
            description = "Get All Notification")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Notification.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    //@PreAuthorize("hasRole('ADMIN')") //MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllNotification() {

        Iterable<Notification> notificationList = notificationService.findAll();

        if (!notificationList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }   
    */
    /*
    @Operation(summary = "Deactive  a notification")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactive/{id}")
    //@PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> deactiveNotification(@PathVariable("id") int id) {
        Optional<Notification> notification = notificationService.findById(id);

        if (notification.isPresent()) {
            notification.get().setIsActive(Boolean.FALSE);
            notificationService.save(notification.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find notification with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }
    */
    
    /*
    @PostMapping("/sendBySchedule")
    public ResponseEntity<?> sendNotificationBySchedule(LocalDate date) {       

        //Get date in
        List<NotificationObject> notificationList = notificationScheduleService.generateNotificationList(date); //Put schedule here
        if (!notificationList.isEmpty()) {
            //Send notification to all register token device
            for (int i = 0; i < notificationList.size(); i++) {
                notificationScheduleService.sendNotification(notificationList.get(i));
            }
            return new ResponseEntity<>(notificationList, HttpStatus.OK);
        }
        return new ResponseEntity<>("No notification to push",HttpStatus.NOT_FOUND);
    }
    */
    /*
    @Operation(
            summary = "Get All Notification",
            description = "Get All Notification")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Notification.class), mediaType = "application/json")})})
    @GetMapping(value = "/getAllNoti")
    //@PreAuthorize("hasRole('ADMIN')") //MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllPersonalNotification() {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Notification> notifyList = notificationService.getAccountNotificationList(principal.getId());
        
        return new ResponseEntity<>(notifyList, HttpStatus.OK);
    }   
    
    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestParam String targetToken) {       
        
        String message = notificationService.sendNotification(new NotificationObject(targetToken.trim()));
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    */
}
