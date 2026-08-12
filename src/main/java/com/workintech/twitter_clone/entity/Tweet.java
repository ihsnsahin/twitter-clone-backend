package com.workintech.twitter_clone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="tweet", schema = "twitter")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="content")
    private String content;
    @Column(name="tweet_time")
    private LocalDate tweetTime;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    @JsonManagedReference("tweet-comments")
    private List<Comment> comments;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    @JsonManagedReference("tweet-likes")
    private List<Like> likes;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    @JsonManagedReference("tweet-retweets")
    private List<Retweet> retweets;

    @ManyToOne (cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;
}
