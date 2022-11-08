package by.diomov.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String organizer;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp time;

    @Column(nullable = false)
    private String location;
}