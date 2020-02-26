package net.honux.qna2020.web;

import org.springframework.web.util.HtmlUtils;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;

import static net.honux.qna2020.web.WebUtils.htmlContentsForRead;
@Entity
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="fk_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_ans_author"))
    private User author;

    @Lob
    String contents;

    public Answer() {};
    public Answer(Question question, User author, String contents) {
        this.question = question;
        this.author = author;
        this.contents = contents;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public Long getQuestionId() { return question.getId(); }


    //for read
    public String getContentsForRead() {
        return htmlContentsForRead(contents);
    };

    public boolean matchAuthor(User sessionUser) {
        return this.author.equals(sessionUser);
    }
}
