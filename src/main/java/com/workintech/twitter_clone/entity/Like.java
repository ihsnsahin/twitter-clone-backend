package com.workintech.twitter_clone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
        name = "like",
        /*schema = "twitter",*/
        //user ve tweet birlikte aynı olmasına izin vermemek için böyle bir kod yazdım.
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "tweet_id"})
        }
)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @ManyToOne (cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    @JsonBackReference("user-likes")
    private User user;


    @ManyToOne (cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="tweet_id")
    @JsonBackReference("tweet-likes")
    private Tweet tweet;
}
