package com.example.strong_backend_test_assignment.controller;

import com.example.strong_backend_test_assignment.domain.dto.newsDTO.CreateNewsDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsDTO.NewsDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsDTO.UpdateNewsDTO;
import com.example.strong_backend_test_assignment.service.NewsService;
import com.example.strong_backend_test_assignment.domain.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;

    @PostMapping()
    public ResponseEntity postNews(@RequestBody CreateNewsDTO createNewsDTO) {
        try {
            NewsDTO _newsDTO = newsService.createNews(createNewsDTO);
            return new ResponseEntity(_newsDTO,HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage;
            if(e.getMessage() == "No value present") {
                errorMessage = "Some of the provided topics or source do not exist";
            } else {
                errorMessage = e.getMessage();
            }
            return new ResponseEntity(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping()
    public ResponseEntity getAllNews(@RequestParam int page, @RequestParam int size) {
        try {
            List<NewsDTO> _listOfNewsDTO = newsService.getAllNews(page,size);
            return new ResponseEntity(_listOfNewsDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity putNews(@PathVariable long id, @RequestBody UpdateNewsDTO updateNewsDTO){
        try {
            NewsDTO _newsDTO = newsService.updateNews(id,updateNewsDTO);
            return new ResponseEntity(_newsDTO,HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage;
            if(e.getMessage() == "No value present") {
                errorMessage = "Some of the provided topics or source do not exist";
            } else {
                errorMessage = e.getMessage();
            }
            return new ResponseEntity(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable long id) {
        try {
            newsService.deleteNews(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getNews(@PathVariable long id) {
        try {
            NewsDTO _newsDTO = newsService.getNews(id);
            return new ResponseEntity(_newsDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
