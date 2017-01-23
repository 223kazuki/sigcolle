package net.unit8.sigcolle.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.seasar.doma.*;

import java.io.Serializable;

/**
 * @author kawasima
 */
@Entity
@Data
public class Campaign implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campaignId;

    @Getter
    @Setter
    private String title;

    // Markdown description
    @Getter
    @Setter
    private String statement;

    @Getter
    @Setter
    private Long goal;

    @Getter
    @Setter
    private Long createdBy;
}
