package com.example.strong_backend_test_assignment.service;

import com.example.strong_backend_test_assignment.domain.dto.newsDTO.CreateNewsDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsDTO.NewsDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsDTO.UpdateNewsDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO.NewsSourceDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO.NewsTopicDTO;
import com.example.strong_backend_test_assignment.domain.model.News;
import com.example.strong_backend_test_assignment.domain.model.NewsSource;
import com.example.strong_backend_test_assignment.domain.model.NewsTopic;
import com.example.strong_backend_test_assignment.repository.NewsRepository;
import com.example.strong_backend_test_assignment.repository.NewsSourceRepository;
import com.example.strong_backend_test_assignment.repository.NewsTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsTopicRepository newsTopicRepository;
    private final NewsSourceRepository newsSourceRepository;

    public NewsDTO createNews(CreateNewsDTO createNewsDTO) {
        createNewsDTO.validate();
        News news = new News();
        news.setTitle(createNewsDTO.getTitle());
        news.setContent(createNewsDTO.getContent());
        Optional<NewsSource> newsSource = newsSourceRepository.findById(createNewsDTO.getSourceId());
        news.setSource(newsSource.get());
        NewsSourceDTO sourceDTO = new NewsSourceDTO(
                newsSource.get().getId(),
                newsSource.get().getName()
        );
        List<Long> topicIds = createNewsDTO.getTopics();
        List<NewsTopic> topics = new ArrayList<NewsTopic>();
        List<NewsTopicDTO> topicsDTO = new ArrayList<NewsTopicDTO>();
        for(int i = 0; i < topicIds.size(); i++) {
            Optional<NewsTopic> newsTopic = newsTopicRepository.findById(topicIds.get(i));
            topics.add(newsTopic.get());
            topicsDTO.add(
                    new NewsTopicDTO(
                            newsTopic.get().getId(),
                            newsTopic.get().getName()
                    )
            );
        }
        news.setTopics(topics);
        newsRepository.save(news);
        return new NewsDTO(
                news.getId(),
                news.getTitle(),
                news.getContent(),
                sourceDTO,
                topicsDTO
        );
    }

    public NewsDTO getNews(long id) {
        Optional<News> news = newsRepository.findById(id);
        if(!news.isPresent()) {
            throw new RuntimeException("no news with such id");
        }
        NewsSourceDTO sourceDTO = new NewsSourceDTO(
                news.get().getSource().getId(),
                news.get().getSource().getName()
        );
        List<NewsTopicDTO> TopicsDTO = new ArrayList<>();
        news.get().getTopics().forEach((topic) -> {
            TopicsDTO.add(
                    new NewsTopicDTO(
                            topic.getId(),
                            topic.getName()
                    )
            );
        });
        return new NewsDTO(
                news.get().getId(),
                news.get().getTitle(),
                news.get().getContent(),
                sourceDTO,
                TopicsDTO
        );
    }

    public List<NewsDTO> getAllNews(int page, int size) {
        List<NewsDTO> listOfNewsDTO = new ArrayList<NewsDTO>();
        newsRepository.findAll(PageRequest.of(page,size)).forEach((singleNews) -> {
            List<NewsTopicDTO> topics = new ArrayList<NewsTopicDTO>();
            for (int i =0;i<singleNews.getTopics().size();i++) {
                topics.add(new NewsTopicDTO(
                        singleNews.getTopics().get(i).getId(),
                        singleNews.getTopics().get(i).getName()
                ));
            }
            NewsSourceDTO sourceDTO = new NewsSourceDTO(
                    singleNews.getSource().getId(),
                    singleNews.getSource().getName()
            );
            listOfNewsDTO.add(
                    new NewsDTO(
                            singleNews.getId(),
                            singleNews.getTitle(),
                            singleNews.getContent(),
                            sourceDTO,
                            topics
                    )
            );
        });
        return listOfNewsDTO;
    }

    public NewsDTO updateNews(long id, UpdateNewsDTO updateNewsDTO) {
        updateNewsDTO.validate();
        Optional<News> newsToUpdate = newsRepository.findById(id);
        if(!newsToUpdate.isPresent()) {
            throw new RuntimeException("news with such id does not exist");
        }
        Optional<NewsSource> source;
        if (updateNewsDTO.getSourceId() != null) {
            source = newsSourceRepository.findById(updateNewsDTO.getSourceId());
            if(!source.isPresent()) {
                throw new RuntimeException("provided source does not exist");
            }
            newsToUpdate.get().setSource(source.get());
        }
        if (updateNewsDTO.getTitle() != null) {
            newsToUpdate.get().setTitle(updateNewsDTO.getTitle());
        }
        if (updateNewsDTO.getContent() != null) {
            newsToUpdate.get().setContent(updateNewsDTO.getContent());
        }
        List<NewsTopic> listOfTopics = new ArrayList<>();
        List<NewsTopicDTO> listOfTopicsDTO = new ArrayList<>();
        List<Long> topicIds = updateNewsDTO.getTopics();
        if (topicIds != null) {
            for (int i = 0; i < topicIds.size(); i++) {
                Optional<NewsTopic> newsTopic = newsTopicRepository.findById(topicIds.get(i));
                listOfTopics.add(newsTopic.get());
            }
            newsToUpdate.get().setTopics(listOfTopics);
        }
        for (int i = 0; i < newsToUpdate.get().getTopics().size(); i++) {
            listOfTopicsDTO.add(new NewsTopicDTO(
                    newsToUpdate.get().getTopics().get(i).getId(),
                    newsToUpdate.get().getTopics().get(i).getName()
            ));
        }
        newsRepository.save(newsToUpdate.get());
        return new NewsDTO(
          newsToUpdate.get().getId(),
          newsToUpdate.get().getTitle(),
          newsToUpdate.get().getContent(),
          new NewsSourceDTO(
                  newsToUpdate.get().getSource().getId(),
                  newsToUpdate.get().getSource().getName()
          ),
          listOfTopicsDTO
        );
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
