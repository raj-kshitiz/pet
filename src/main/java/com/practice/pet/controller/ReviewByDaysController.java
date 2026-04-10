package com.practice.pet.controller;

import com.practice.pet.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/days")
public class ReviewByDaysController {

    private final
    ReviewService reviewService;

    public ReviewByDaysController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/today")
    public ResponseEntity<String> getAmountSpentToday(){
        String amountSpentToday = reviewService.getAmountSpentToday();
        return new ResponseEntity<>(amountSpentToday, HttpStatus.OK);
    }

    @GetMapping("/this-week")
    public ResponseEntity<String> getAmountSpentThisWeek(){
        String amountSpentThisWeek = reviewService.getAmountSpentThisWeek();
        return new ResponseEntity<>(amountSpentThisWeek, HttpStatus.OK);
    }

    @GetMapping("/this-month")
    public ResponseEntity<String> getAmountSpentThisMonth(){
        String amountSpentThisMonth = reviewService.getAmountSpentThisMonth();
        return new ResponseEntity<>(amountSpentThisMonth, HttpStatus.OK);
    }

    @GetMapping("/this-year")
    public ResponseEntity<String> getAmountSpentThisYear(){
        String amountSpentThisYear = reviewService.getAmountSpentThisYear();
        return new ResponseEntity<>(amountSpentThisYear, HttpStatus.OK);
    }

    @GetMapping("/{days}")
    public ResponseEntity<String> getAmountSpentLastXDays(@PathVariable int days){
        String amountSpentLastXDays = reviewService.getAmountSpentLastXDays(days);
        return new ResponseEntity<>(amountSpentLastXDays, HttpStatus.OK);
    }
}
