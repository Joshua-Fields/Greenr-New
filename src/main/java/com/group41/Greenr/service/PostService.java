package com.group41.Greenr.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.group41.Greenr.web.dto.PostRepository;
import com.group41.Greenr.model.Post;
import java.util.Base64;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepo;

    public void savePostToDB(MultipartFile file, String name, String description) {
        Post p = new Post();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a valid file");
        }
        try {
            p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.setPostName(name);
        p.setPostDesc(description);

        postRepo.save(p);
    }

    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    public void deletePostById(Long id) {
        postRepo.deleteById(id);
    }

    public void editTitle(Long id, String name) {
        Post p = new Post();
        p = postRepo.findById(id).get();
        p.setPostName(name);
        postRepo.save(p);
    }

    public void editDescription(Long id, String description) {
        Post p = new Post();
        p = postRepo.findById(id).get();
        p.setPostDesc(description);
        postRepo.save(p);
    }
}