    package net.unit8.sigcolle.form;

import enkan.component.doma2.DomaProvider;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.inject.Inject;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.List;

    /**
 * @author kawasima
 */
@Data
public class CampaignRegisterForm extends FormBase {
    @Inject
    DomaProvider domaProvider;

    @NotBlank
    @Length(max = 30)
    private String title;

    @NotBlank
    @Length(max = 10000)
    private String statement;

    private Long goal;

    @Override
    public boolean hasErrors() {
        return super.hasErrors();
    }

    @Override
    public boolean hasErrors(String name) {
        return super.hasErrors(name);
    }

    @Override
    public List<String> getErrors(String name) {
        return super.getErrors(name);
    }

}
