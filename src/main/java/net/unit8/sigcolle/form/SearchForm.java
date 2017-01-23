    package net.unit8.sigcolle.form;

import enkan.component.doma2.DomaProvider;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.inject.Inject;

    /**
     * @author kawasima
     */
    @Data
    public class SearchForm extends FormBase {
        @Inject
        DomaProvider domaProvider;

        @Length(min = 1, max = 20)
        private String title;

//        @Length(min = 4, max = 20)
//        private String statement;
//
//        private Long goal;

    }
