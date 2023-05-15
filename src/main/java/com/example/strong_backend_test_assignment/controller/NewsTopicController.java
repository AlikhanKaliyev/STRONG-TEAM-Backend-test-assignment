package com.example.strong_backend_test_assignment.controller;

import com.example.strong_backend_test_assignment.domain.dto.newsDTO.NewsDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO.CreateNewsTopicDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO.NewsTopicDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO.UpdateNewsTopicDTO;
import com.example.strong_backend_test_assignment.service.NewsTopicService;
import com.example.strong_backend_test_assignment.domain.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news-topics")
public class NewsTopicController {
    private final NewsTopicService newsTopicService;

    @PostMapping()
    public ResponseEntity postNewsTopic(@RequestBody CreateNewsTopicDTO createNewsTopicDTO) {
        try {
            NewsTopicDTO _newsTopicDTO = newsTopicService.createNewsTopic(createNewsTopicDTO);
            return new ResponseEntity(_newsTopicDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity putNewsTopic(@PathVariable long id, @RequestBody UpdateNewsTopicDTO updateNewsTopicDTO) {
        try {
            NewsTopicDTO _newsTopicDTO = newsTopicService.updateNewsTopic(id, updateNewsTopicDTO);
            return new ResponseEntity(_newsTopicDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity getAllNewsTopics() {
        try {
            List<NewsTopicDTO> _listOfNewsTopicsDTO = newsTopicService.getAllNewsTopic();
            return new ResponseEntity(_listOfNewsTopicsDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getNewsTopic(@PathVariable long id) {
        try {
            NewsTopicDTO _newsTopicDTO = newsTopicService.getNewsTopic(id);
            return new ResponseEntity(_newsTopicDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNewsTopic(@PathVariable long id) {
        try {
            newsTopicService.deleteNewsTopic(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/news")
    public ResponseEntity getNewsByTopic(@PathVariable long id, @RequestParam int page, @RequestParam int size) {
        try {
            List<NewsDTO> _listOfNewsDTO = newsTopicService.findNewsByTopic(id,page,size);
            return new ResponseEntity(_listOfNewsDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
