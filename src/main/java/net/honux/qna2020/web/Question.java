package net.honux.qna2020.web;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_author"))
    private User author;
    private String contents;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createDate;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getId() {return id; };

    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    //for read
    public String getContentsForRead() {
        return HtmlUtils.htmlEscape(contents).replace("\r\n", "<br>\n");
    }

    public String getContents() {
        return contents;
    }

    public String getFormattedCreateDate() {
        return createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public boolean matchAuthor(User sessionUser) {
        return author.equals(sessionUser);
    }

    public void update(Question question) throws IllegalAccessException {
        this.title = question.title;
        this.contents = question.contents;
    }
}
