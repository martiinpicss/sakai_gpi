/**
 * Copyright (c) 2003-2021 The Apereo Foundation
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
package org.sakaiproject.messaging.api.testhandler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.sakaiproject.event.api.Event;
import org.sakaiproject.messaging.api.UserNotificationData;
import org.sakaiproject.messaging.api.UserNotificationHandler;

import org.springframework.stereotype.Component;

@Component
public class TestUserNotificationHandler implements UserNotificationHandler {

    public List<String> getHandledEvents() {
        return Collections.EMPTY_LIST;
    }

    public Optional<List<UserNotificationData>> handleEvent(Event e) {
        return null;
    }
}
