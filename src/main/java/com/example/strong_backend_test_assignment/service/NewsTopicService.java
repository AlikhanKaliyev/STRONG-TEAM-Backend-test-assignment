package com.example.strong_backend_test_assignment.service;

import com.example.strong_backend_test_assignment.domain.dto.newsDTO.NewsDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO.NewsSourceDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO.CreateNewsTopicDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO.NewsTopicDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO.UpdateNewsTopicDTO;
import com.example.strong_backend_test_assignment.domain.model.NewsTopic;
import com.example.strong_backend_test_assignment.repository.NewsRepository;
import com.example.strong_backend_test_assignment.repository.NewsSourceRepository;
import com.example.strong_backend_test_assignment.repository.NewsTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsTopicService {
    private final NewsTopicRepository newsTopicRepository;

    private final NewsRepository newsRepository;


    public NewsTopicDTO createNewsTopic(CreateNewsTopicDTO createNewsTopicDTO) {
        createNewsTopicDTO.validate();
        NewsTopic newsTopic = new NewsTopic();
        newsTopic.setName(createNewsTopicDTO.getName());
        newsTopicRepository.save(newsTopic);
        return new NewsTopicDTO(
                newsTopic.getId(),
                newsTopic.getName()
        );
    }

    public List<NewsTopicDTO> getAllNewsTopic() {
        List<NewsTopicDTO> listOfNewsTopicDTO = new ArrayList<>();
        newsTopicRepository.findAll().forEach((topic) -> {
            listOfNewsTopicDTO.add(
                    new NewsTopicDTO(
                            topic.getId(),
                            topic.getName()
                    )
            );
        });
        return listOfNewsTopicDTO;
    }
    public NewsTopicDTO getNewsTopic(long id) {
        Optional<NewsTopic> topic = newsTopicRepository.findById(id);
        if(!topic.isPresent()) {
            throw new RuntimeException("no news source with such id");
        }
        return new NewsTopicDTO(
                topic.get().getId(),
                topic.get().getName()
        );
    }

    public void deleteNewsTopic(long id) {
        newsTopicRepository.deleteById(id);
    }

    public NewsTopicDTO updateNewsTopic(long id, UpdateNewsTopicDTO updateNewsTopicDTO) {
        updateNewsTopicDTO.validate();
        Optional<NewsTopic> topicToUpdate = newsTopicRepository.findById(id);
        if(!topicToUpdate.isPresent()) {
            throw new RuntimeException("news topic with such id does not exist");
        }
        if(updateNewsTopicDTO.getName() != null) {
            topicToUpdate.get().setName(updateNewsTopicDTO.getName());
        }
        newsTopicRepository.save(topicToUpdate.get());
        return new NewsTopicDTO(
                topicToUpdate.get().getId(),
                topicToUpdate.get().getName()
        );
    }

    public List<NewsDTO> findNewsByTopic(long id, int page, int size) {
        Optional<NewsTopic> topic = newsTopicRepository.findById(id);
        List<NewsDTO> listOfNewsDTO = new ArrayList<>();
        if(!topic.isPresent()) {
            throw new RuntimeException("no topic with such id");
        }
        newsRepository.findByTopics(topic.get()).forEach((news) -> {
            List<NewsTopicDTO> topicsDTO = new ArrayList<>();
            NewsSourceDTO sourceDTO = new NewsSourceDTO(
              news.getSource().getId(),
              news.getSource().getName()
            );
            List<NewsTopic> topics = news.getTopics();
            for(int i =0;i<topics.size();i++) {
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

}
