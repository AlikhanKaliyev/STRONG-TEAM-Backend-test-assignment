package com.example.strong_backend_test_assignment.controller;

import com.example.strong_backend_test_assignment.domain.dto.newsDTO.NewsDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO.CreateNewsSourceDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO.NewsSourceDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO.UpdateNewsSourceDTO;
import com.example.strong_backend_test_assignment.service.NewsSourceService;
import com.example.strong_backend_test_assignment.domain.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news-sources")
public class NewsSourceController {
    final private NewsSourceService newsSourceService;

    @PostMapping()
    public ResponseEntity postNewsSource(@RequestBody CreateNewsSourceDTO createNewsSourceDTO) {
        try {
            NewsSourceDTO _newsSourceDTO = newsSourceService.createNewsSource(createNewsSourceDTO);
            return new ResponseEntity(_newsSourceDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity putNewsSource(@PathVariable long id, @RequestBody UpdateNewsSourceDTO updateNewsSourceDTO) {
        try {
            NewsSourceDTO _newsSourceDTO = newsSourceService.updateNewsSource(id,updateNewsSourceDTO);
            return new ResponseEntity(_newsSourceDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity getAllNewsSources() {
        try {
            List<NewsSourceDTO> _listOfAllNewsSourcesDTO = newsSourceService.getAllNewsSources();
            return new ResponseEntity(_listOfAllNewsSourcesDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getNewsSource(@PathVariable long id) {
        try {
            NewsSourceDTO _newsSourceDTO = newsSourceService.getNewsSource(id);
            return new ResponseEntity(_newsSourceDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteNewsSource(@PathVariable long id) {
        try {
            newsSourceService.deleteNewsSource(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/news")
    public ResponseEntity getNewsBySource(@PathVariable long id, @RequestParam int page, @RequestParam int size) {
        try {
            List<NewsDTO> _listOfNewsDTO = newsSourceService.getNewsBySource(id,page,size);
            return new ResponseEntity(_listOfNewsDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
