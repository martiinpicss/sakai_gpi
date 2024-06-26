/**
 * Copyright (c) 2007-2015 The Apereo Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://opensource.org/licenses/ecl2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.emailtemplateservice.impl.test;

import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.Date;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Before;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.sakaiproject.emailtemplateservice.api.EmailTemplateService;
import org.sakaiproject.emailtemplateservice.api.model.EmailTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmailTemplateServiceTestConfiguration.class})
@FixMethodOrder(NAME_ASCENDING)
@Slf4j
public class EmailTemplateServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";
 
    private static final String US_LOCALE = "en_us";
    private static final String ZA_LOCALE = "en_ZA";

    private static final String ADMIN_USER = "admin";

    @Autowired EmailTemplateService emailTemplateService;

    EmailTemplate template1 = new EmailTemplate();
    EmailTemplate template2 = new EmailTemplate();
    EmailTemplate template3 = new EmailTemplate();

    Long template1Id = null;

    @Before
    public void onSetUp() throws Exception {

        emailTemplateService.deleteAllTemplates();
        populateData();
    }

    private void populateData() {

        template1.setKey(KEY_1);
        template1.setLocale(EmailTemplate.DEFAULT_LOCALE);
        template1.setLastModified(new Date());
        template1.setOwner(ADMIN_USER);
        template1.setSubject("Subject 1");
        template1.setMessage("message 1");
        template1 = emailTemplateService.saveTemplate(template1);

        template1Id = template1.getId();

        template2.setKey(KEY_2);
        template2.setLocale(EmailTemplate.DEFAULT_LOCALE);
        template2.setLastModified(new Date());
        template2.setOwner(ADMIN_USER);
        template2.setSubject("Subject 2");
        template2.setMessage("message 2");
        template2 = emailTemplateService.saveTemplate(template2);
        
        template3.setKey(KEY_1);
        template3.setLocale(ZA_LOCALE);
        template3.setLastModified(new Date());
        template3.setOwner(ADMIN_USER);
        template3.setSubject("Subject 1");
        template3.setMessage("message 1");
        template3 = emailTemplateService.saveTemplate(template3);
    }
 
    @Test
    public void testSaveTemplate() {

        //saving should set the ID

        template1 = emailTemplateService.saveTemplate(template1);
        Assert.assertNotNull(template1.getId());

        template2 = emailTemplateService.saveTemplate(template2);
        Assert.assertNotNull(template2.getId());
        //if these are the same there is something very wrong
        Assert.assertNotSame(template2.getId(), template1.getId());

        /*
        //we should not be able to save a new template in the same locale/key
        EmailTemplate template3 = new EmailTemplate();
        template3.setKey(KEY_1);
        template3.setLocale(US_LOCALE);
        template3.setLastModified(new Date());
        template3.setOwner(ADMIN_USER);
        template3.setSubject("Subject 1");
        template3.setMessage("message 1");
        
        EmailTemplate template4 = new EmailTemplate();
        template4.setKey(KEY_1);
        template4.setLocale(ZA_LOCALE);
        template4.setLastModified(new Date());
        template4.setOwner(ADMIN_USER);
        template4.setSubject("Subject 1");
        template4.setMessage("message 1");
        
        emailTemplateService.saveTemplate(template3);
        Assert.assertThrows(IllegalArgumentException.class, () -> emailTemplateService.saveTemplate(template4));
        */
    }

    @Test
    public void testGetTemplatebyId() {

        EmailTemplate t1 = emailTemplateService.getEmailTemplateById(template1Id);
        Assert.assertNotNull(t1);
        Assert.assertEquals(t1.getKey(), KEY_1);
    }

    @Test
    public void testGetEmailTemplate() {

        EmailTemplate ti = emailTemplateService.getEmailTemplate(KEY_1, null);
        Assert.assertNotNull(ti);
        
        //There is no specific template for en_us we still expect the default
        EmailTemplate t2 = emailTemplateService.getEmailTemplate(KEY_1, new Locale("en", "us"));
        Assert.assertNotNull(t2);
        Assert.assertEquals(EmailTemplate.DEFAULT_LOCALE, t2.getLocale());
    }
    
    @Test
    public void testEmptyTemplate() {
        
        EmailTemplate badTemplate = new EmailTemplate();
        try {
            emailTemplateService.saveTemplate(badTemplate);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            //we expect this
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Assert.fail();
        }
        
        badTemplate.setKey("someKey");
        try {
            emailTemplateService.saveTemplate(badTemplate);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            //we expect this
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Assert.fail();
        }
        
        
        badTemplate.setOwner("admin");
        try {
            emailTemplateService.saveTemplate(badTemplate);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            //we expect this
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Assert.fail();
        }
        
        badTemplate.setSubject("something");
        try {
            emailTemplateService.saveTemplate(badTemplate);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            //we expect this
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Assert.fail();
        }
        
        badTemplate.setMessage("something");
        badTemplate.setLocale(" ");
        try {
            emailTemplateService.saveTemplate(badTemplate);
            Assert.assertEquals(EmailTemplate.DEFAULT_LOCALE, badTemplate.getLocale());
            Assert.assertNotSame("Template Locale can't be empty", "", badTemplate.getLocale());
            
        } catch (IllegalArgumentException e) {
            //we expect this
            Assert.fail();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Assert.fail();
        }
        
        badTemplate.setLocale("thisIsNotAGoodLocale");
        //this should not work
        try {
            emailTemplateService.saveTemplate(badTemplate);
        } catch (IllegalArgumentException e) {
            //we expect this
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Assert.fail();
        }
    }

    @Test
    public void testTemplateExistsWithDifferentId() {
        EmailTemplate t1 = emailTemplateService.getEmailTemplateById(template1Id);

        // "Normal" case where there isn't a template with this key that has a different id
        boolean exists = emailTemplateService.templateExistsWithDifferentId(t1.getKey(), null, t1.getId());
        Assert.assertFalse(exists);

        // Make sure the id is different
        Long bogusId = t1.getId() + 1;

        // "Bad" case where there is a template for this key with a different id
        exists = emailTemplateService.templateExistsWithDifferentId(t1.getKey(), null, bogusId);
        Assert.assertTrue(exists);

        // No template for this key, null id
        exists = emailTemplateService.templateExistsWithDifferentId("RANDOM_KEY", null, null);
        Assert.assertFalse(exists);

        // Existing template for this key, null id
        exists = emailTemplateService.templateExistsWithDifferentId(t1.getKey(), null, null);
        Assert.assertTrue(exists);

    }
}
