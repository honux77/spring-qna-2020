package net.honux.qna2020.web;

import javax.persistence.*;

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

    public Answer(Question question, User author) {
        this.question = question;
        this.author = author;
    }


    public String getAuthorName() {
        return author.getName();
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String answer) {
        this.contents = answer;
    }
}
