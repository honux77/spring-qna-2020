package net.honux.qna2020.web;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.util.HtmlUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="fk_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_ans_author"))
    private User author;

    @Lob
    String contents;

    @CreationTimestamp
    LocalDateTime createDate;

    public Answer() {};
    public Answer(Question question, User author, String contents) {
        this.question = question;
        this.author = author;
        this.contents = contents;
    }


    public String getAuthorName() {
        return author.getName();
    }

    //for read
    public String getContentsForRead() {
        return HtmlUtils.htmlEscape(contents).replace("\r\n", "<br>\n");
    }

    public String getFormattedCreateDate() {
        return createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
