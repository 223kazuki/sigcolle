package net.unit8.sigcolle.controller;

import enkan.component.doma2.DomaProvider;
import enkan.data.HttpResponse;
import enkan.data.Session;
import kotowari.component.TemplateEngine;
import net.unit8.sigcolle.dao.CampaignDao;
import net.unit8.sigcolle.form.CampaignRegisterForm;
import net.unit8.sigcolle.form.SearchForm;
import net.unit8.sigcolle.model.Campaign;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static enkan.util.BeanBuilder.builder;

/**
 * Created by tie303856 on 2016/12/14.
 */
public class SearchController {
    @Inject
    TemplateEngine templateEngine;

    @Inject
    DomaProvider domaProvider;

    // 検索画面表示
    @Transactional
    public HttpResponse index() throws IOException {

        return templateEngine.render("search",
                "search", new SearchForm()
        );
    }

    // 検索
    @Transactional
    public HttpResponse search(SearchForm form, Session session) throws IOException {

        CampaignDao dao = domaProvider.getDao(CampaignDao.class);
        Campaign campaign = builder(new Campaign())
                .set(Campaign::setTitle, form.getTitle())
                .build();
        List<Campaign> results = dao.select(form.getTitle());

        return templateEngine.render("search",
                "results", results, "search", form
        );
    }
}
