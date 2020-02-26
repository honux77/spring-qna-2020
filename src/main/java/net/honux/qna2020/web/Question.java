package net.honux.qna2020.web;

import javax.persistence.*;
import java.util.List;

import static net.honux.qna2020.web.WebUtils.htmlContentsForRead;

@Entity
public class Question extends AbstractEntity {

    private String title;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_author"))
    private User author;

    @Lob
    private String contents;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    private int answerCount = 0;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getAnswerCount() {return answerCount; }

    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    //for read
    public String getContentsForRead() {
        return htmlContentsForRead(contents);
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean matchAuthor(User sessionUser) {
        return author.equals(sessionUser);
    }

    public void update(Question question) throws IllegalAccessException {
        this.title = question.title;
        this.contents = question.contents;
    }

    public void addAnswer() {
        answerCount++;
    }

    public void deleteAnswer() {
        answerCount--;
    }
}
