package net.honux.qna2020.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @CreatedDate
    @JsonProperty
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonProperty
    private LocalDateTime modifiedDate;

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id; }

    public String getFormattedCreateDate() {
        return createdDate.format(dateTimeFormatter);
    }

    public String getFormattedModifiedDate() {
        return modifiedDate.format(dateTimeFormatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity a = (User) o;
        return id.equals(a.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
