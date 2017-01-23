    package net.unit8.sigcolle.form;

import enkan.component.doma2.DomaProvider;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.inject.Inject;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * @author kawasima
 */
@Data
public class CampaignRegisterForm extends FormBase {
    @Inject
    DomaProvider domaProvider;

    @Length(min = 4, max = 20)
    private String title;

    @Length(min = 4, max = 20)
    private String statement;

    private Long goal;

}
