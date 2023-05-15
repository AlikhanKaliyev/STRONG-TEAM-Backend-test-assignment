package com.example.strong_backend_test_assignment.service;

import com.example.strong_backend_test_assignment.domain.dto.newsDTO.NewsDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO.CreateNewsSourceDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO.NewsSourceDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO.UpdateNewsSourceDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO.NewsTopicDTO;
import com.example.strong_backend_test_assignment.domain.model.NewsSource;
import com.example.strong_backend_test_assignment.domain.model.NewsTopic;
import com.example.strong_backend_test_assignment.repository.NewsRepository;
import com.example.strong_backend_test_assignment.repository.NewsSourceRepository;
import com.example.strong_backend_test_assignment.repository.NewsTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsSourceService {
    final private NewsSourceRepository newsSourceRepository;

    final private NewsTopicRepository newsTopicRepository;

    final private NewsRepository newsRepository;

    public NewsSourceDTO createNewsSource(CreateNewsSourceDTO createNewsSourceDTO) {
        createNewsSourceDTO.validate();
        NewsSource newsSource = new NewsSource();
        newsSource.setName(createNewsSourceDTO.getName());
        newsSourceRepository.save(newsSource);
        return new NewsSourceDTO(
                newsSource.getId(),
                newsSource.getName()
        );
    }
    public List<NewsSourceDTO> getAllNewsSources() {
        List<NewsSourceDTO> listOfNewsSourceDTO = new ArrayList<>();
        newsSourceRepository.findAll().forEach((newsSource) -> {
            listOfNewsSourceDTO.add(
                    new NewsSourceDTO(
                            newsSource.getId(),
                            newsSource.getName()
                    )
            );
        });
        return listOfNewsSourceDTO;
    }

    public NewsSourceDTO updateNewsSource(long id, UpdateNewsSourceDTO updateNewsSourceDTO) {
        updateNewsSourceDTO.validate();
        Optional<NewsSource> sourceToUpdate = newsSourceRepository.findById(id);
        if(!sourceToUpdate.isPresent()) {
            throw new RuntimeException("news source with such id does not exist");
        }
        if(updateNewsSourceDTO.getName() != null) {
            sourceToUpdate.get().setName(updateNewsSourceDTO.getName());
        }
        newsSourceRepository.save(sourceToUpdate.get());
        return new NewsSourceDTO(
                sourceToUpdate.get().getId(),
                sourceToUpdate.get().getName()
        );
    }

    public void deleteNewsSource(long id) {
        newsSourceRepository.deleteById(id);
    }

    public NewsSourceDTO getNewsSource(long id) {
        Optional<NewsSource> source = newsSourceRepository.findById(id);
        if(!source.isPresent()) {
            throw new RuntimeException("no news source with such id");
        }
        return new NewsSourceDTO(
            source.get().getId(),
            source.get().getName()
        );
    }

    public List<NewsDTO> getNewsBySource(long id, int page, int size) {
        Optional<NewsSource> source = newsSourceRepository.findById(id);
        List<NewsDTO> listOfNewsDTO = new ArrayList<>();
        if(!source.isPresent()) {
            throw new RuntimeException("no source with such id");
        }
        NewsSourceDTO sourceDTO = new NewsSourceDTO(
                source.get().getId(),
                source.get().getName()
        );
        newsRepository.findBySource(source.get(),PageRequest.of(page,size)).forEach((news) -> {
            List<NewsTopic> topics = news.getTopics();
            List<NewsTopicDTO> topicsDTO = new ArrayList<>();
            for(int i=0;i<topics.size();i++) {
                topicsDTO.add(
                        new NewsTopicDTO(
                                topics.get(i).getId(),
                                topics.get(i).getName()
                        )
                );
            }
            listOfNewsDTO.add(
                    new NewsDTO(
                        news.getId(),
                        news.getTitle(),
                        news.getContent(),
                            sourceDTO,
                            topicsDTO
                    )
            );
        });
        return listOfNewsDTO;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void eachSourceNewsStatistics() {
        List<NewsSource> sources = newsSourceRepository.findAll();
        try (PrintWriter writer = new PrintWriter(new FileWriter("source_news_statistics.csv"))) {
            writer.println("id,source,count");
            for(int i =0;i<sources.size();i++) {
                int countOfNews = newsRepository.findBySource(sources.get(i)).size();
                writer.println(sources.get(i).getId() + "," + sources.get(i).getName() + "," + countOfNews);
            }
            System.out.println("Statistics file is created in root folder");
        } catch (IOException e) {
            System.err.println("Error :" + e.getMessage());
        }
    }
}
