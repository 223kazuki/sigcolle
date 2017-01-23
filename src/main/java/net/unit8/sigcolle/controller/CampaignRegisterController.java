package net.unit8.sigcolle.controller;

import enkan.collection.Multimap;
import enkan.component.doma2.DomaProvider;
import enkan.data.HttpResponse;
import enkan.data.Session;
import kotowari.component.TemplateEngine;
import net.unit8.sigcolle.dao.CampaignDao;
import net.unit8.sigcolle.dao.UserDao;
import net.unit8.sigcolle.form.CampaignForm;
import net.unit8.sigcolle.form.CampaignRegisterForm;
import net.unit8.sigcolle.model.Campaign;
import net.unit8.sigcolle.model.User;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.seasar.doma.jdbc.NoResultException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;

import static enkan.util.BeanBuilder.builder;
import static enkan.util.HttpResponseUtils.RedirectStatusCode.SEE_OTHER;
import static enkan.util.HttpResponseUtils.redirect;

/**
 * Created by tie303856 on 2016/12/14.
 */
public class CampaignRegisterController {
    @Inject
    TemplateEngine templateEngine;

    @Inject
    DomaProvider domaProvider;

    // ログイン画面表示
    @Transactional
    public HttpResponse index(CampaignRegisterForm form) throws IOException {

        return templateEngine.render("campaignRegister",
                "campaignRegister", new CampaignRegisterForm()
        );
    }

    // ログイン処理
    @Transactional
    public HttpResponse register(CampaignRegisterForm form, Session session) throws IOException {
        Object userId = session.get("userId");
        if (userId == null) {
            return builder(redirect("/", SEE_OTHER))
                    .set(HttpResponse::setSession, session)
                    .build();
        }

        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);

        CampaignDao dao = domaProvider.getDao(CampaignDao.class);
        Campaign campaign = builder(new Campaign())
                .set(Campaign::setTitle, form.getTitle())
                .set(Campaign::setStatement,  processor.markdownToHtml(form.getStatement()))
                .set(Campaign::setGoal, form.getGoal())
                .set(Campaign::setCreatedBy, Long.parseLong(userId.toString()))
                .build();
        dao.insert(campaign);

        return builder(redirect("/", SEE_OTHER))
                .set(HttpResponse::setSession, session)
                .build();
    }
}
