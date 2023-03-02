package com.aptech.mymusic.data;

import com.aptech.mymusic.domain.entities.Song;
import com.aptech.mymusic.domain.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class ApiService {

    private final AdsSongRepository adsSongRepository;
    private final AlbumRepository albumRepository;
    private final CategoryRepository categoryRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final TopicRepository topicRepository;
    private final EntityManager em;

    public ApiService(AdsSongRepository adsSongRepository, AlbumRepository albumRepository, CategoryRepository categoryRepository, PlaylistRepository playlistRepository, SongRepository songRepository, TopicRepository topicRepository, EntityManager em) {
        this.adsSongRepository = adsSongRepository;
        this.albumRepository = albumRepository;
        this.categoryRepository = categoryRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.topicRepository = topicRepository;
        this.em = em;
    }

    public <T> List<T> getRandData(@NotNull Class<T> cls, int limit) {
        String clsName = cls.getSimpleName();
        return em.createQuery("SELECT e FROM " + clsName + " e ORDER BY RAND()", cls)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Song> getNewlyReleasedMusic() {
        return em.createQuery("SELECT s FROM Song s WHERE status = 0 ORDER BY s.id DESC", Song.class)
                .setMaxResults(10)
                .getResultList();
    }

    public List<Song> getAllSongFrom(String type, String id) {
        String query = "SELECT s FROM Song s WHERE s.status = 0 AND FUNCTION('JSON_CONTAINS', s." + type + "Ids, :id) = 1 ORDER BY s.id DESC";
        return em.createQuery(query, Song.class)
                .setParameter("id", id)
                .getResultList();
    }

    public AdsSongRepository getAdsSongRepository() {
        return adsSongRepository;
    }

    public AlbumRepository getAlbumRepository() {
        return albumRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public PlaylistRepository getPlaylistRepository() {
        return playlistRepository;
    }

    public SongRepository getSongRepository() {
        return songRepository;
    }

    public TopicRepository getTopicRepository() {
        return topicRepository;
    }

}