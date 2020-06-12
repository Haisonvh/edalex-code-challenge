package challenge.database;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@Entity
public class MessageModel implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "Message must have length from 1 to 250")
    @Pattern(regexp = "^[a-zA-Z\\s\\.,;!?0-9]*$",message = "Message should not contain special character")
    @Length(max = 250,message = "Message must have length from 1 to 250")
    private String message;

    public MessageModel() {
    }

    public MessageModel(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageModel)) {
            return false;
        }
        MessageModel mess = (MessageModel) o;
        return Objects.equals(this.id, mess.id) && Objects.equals(this.message, mess.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.message);
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + this.id + ", message='" + this.message + '\''+ '}';
    }
}
