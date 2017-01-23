package net.unit8.sigcolle.controller;

import enkan.component.doma2.DomaProvider;
import enkan.data.Flash;
import enkan.data.HttpResponse;
import enkan.data.Session;
import kotowari.component.TemplateEngine;
import net.unit8.sigcolle.dao.CampaignDao;
import net.unit8.sigcolle.dao.SignatureDao;
import net.unit8.sigcolle.dao.UserDao;
import net.unit8.sigcolle.form.CampaignForm;
import net.unit8.sigcolle.form.MyPageForm;
import net.unit8.sigcolle.form.SignatureForm;
import net.unit8.sigcolle.model.Campaign;
import net.unit8.sigcolle.model.Signature;
import net.unit8.sigcolle.model.User;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;

import static enkan.util.BeanBuilder.builder;
import static enkan.util.HttpResponseUtils.RedirectStatusCode.SEE_OTHER;
import static enkan.util.HttpResponseUtils.redirect;
import static enkan.util.ThreadingUtils.some;

/**
 * @author kawasima
 */
public class MyPageController {
    @Inject
    TemplateEngine templateEngine;

    @Inject
    DomaProvider domaProvider;

    private HttpResponse showCampaign(Long campaignId, SignatureForm signature, String message) {
        CampaignDao campaignDao = domaProvider.getDao(CampaignDao.class);
        Campaign campaign = campaignDao.selectById(campaignId);

        UserDao userDao = domaProvider.getDao(UserDao.class);
        User user = userDao.selectByUserId(campaign.getCreatedBy());
        String createdBy = user.getLastName() + user.getFirstName();

        SignatureDao signatureDao = domaProvider.getDao(SignatureDao.class);
        int signatureCount = signatureDao.countByCampaignId(campaignId);

        return templateEngine.render("campaign",
                "campaign", campaign,
                "signatureCount", signatureCount,
                "signature", signature,
                "message", message,
                "createdBy", createdBy
        );
    }

    public HttpResponse index(MyPageForm form, Session session) throws IOException {
        MyPageForm myPageForm = new MyPageForm();
        UserDao userDao = domaProvider.getDao(UserDao.class);
        User user = userDao.selectByUserId(Long.parseLong(session.get("userId").toString()));
        myPageForm.setFirstName(user.getFirstName());
        myPageForm.setLastName(user.getLastName());
        myPageForm.setEmail(user.getEmail());

        myPageForm.setPass(user.getPass());
        return templateEngine.render("myPage",
                "user", myPageForm);
    }

    @Transactional
    public HttpResponse update(MyPageForm form, Session session) throws IOException {
        if (form.hasErrors()) {
            return index(form, session);
        }
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);

        UserDao dao = domaProvider.getDao(UserDao.class);
        User user = builder(new User())
                .set(User::setUserId, Long.parseLong(session.get("userId").toString()))
                .set(User::setFirstName,  form.getFirstName())
                .set(User::setLastName, form.getLastName())
                .set(User::setEmail, form.getEmail())
                .set(User::setPass, form.getPass())
                .build();
        dao.update(user);

        String name = user.getLastName() + " " + user.getFirstName();
        session.put("name", name);
        session.put("userId", user.getUserId());

        return index(form, session);

    }
}
